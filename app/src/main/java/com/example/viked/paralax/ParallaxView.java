package com.example.viked.paralax;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Viked on 9/17/2016.
 */
public class ParallaxView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private ImageView mImageView;

    private int childCount = 0;
    private int childWidth = 0;
    private int childHeight = 0;

    private int currentItem = 0;
    private int scrollItem = 0;
    private int scrollOffset = 0;

    public ParallaxView(Context context) {
        super(context);
        initView();
    }

    public ParallaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        initialParams();
        View view = inflate(getContext(), R.layout.paralax_view_pager, null);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mImageView = (ImageView) view.findViewById(R.id.background_image_view);

        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mViewPager.addOnPageChangeListener(this);
        addView(view);


    }

    private void initialParams() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View child;
                int count, width, height;
                if (mImageView.getWidth() > 0
                        && mViewPager.getWidth() > 0
                        && (count = mViewPager.getChildCount()) > 0
                        && (width = (child = mViewPager.getChildAt(0)).getWidth()) > 0
                        && (height = child.getHeight()) > 0) {
                    childCount = count;
                    childWidth = width;
                    childHeight = height;
                    resizeBackgroundImage();
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);

    }

    public void setParalaxBackground(int imageId) {
        mImageView.setImageResource(imageId);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        scrollItem = position;
        scrollOffset = positionOffsetPixels;
        resizeBackgroundImage();
    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    
    private void resizeBackgroundImage() {
        Drawable d = mImageView.getDrawable();
        int origW = d.getIntrinsicWidth();
        int origH = d.getIntrinsicHeight();


        int fullWidth = childCount * childWidth;

        float scaleX = (float) fullWidth / (float) origW;
        float scaleY = (float) childHeight / (float) origH;

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);

        matrix.setTranslate(scrollOffset - (childWidth * (childCount - scrollItem) + 1), 0);
        mImageView.setImageMatrix(matrix);
    }


}
