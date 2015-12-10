/*
 * Copyright (C) 2015 BrotherV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bvapp.arcraymenu;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * A Layout that arranges its children around its center. The arc can be set by
 * calling {@link #setArc(float, float) setArc()}. You can override the method
 * {@link #onMeasure(int, int) onMeasure()}, otherwise it is always
 * WRAP_CONTENT.
 * 
 * @author Capricorn
 * 
 */
public class ArcLayout extends RelativeLayout {
    /**
     * children will be set the same size.
     */
    public static final int   TOP_LEFT	     = 1;
    public static final int   TOP_RIGHT	    = 2;
    public static final int   TOP_MIDDLE	   = 3;
    public static final int   BOTTOM_LEFT	  = 4;
    public static final int   BOTTOM_RIGHT	 = 5;
    public static final int   BOTTOM_MIDDLE	= 6;
    public static final int   RIGHT_MIDDLE	 = 7;
    public static final int   LEFT_MIDDLE	  = 8;
    public static final int   CENTER	       = 9;

    private int	       childCount	   = 2;

    private int	       mChildSize	   = 32;

    private int	       mChildPadding	= 5;

    private int	       mLayoutPadding       = 10;

    public static final float DEFAULT_FROM_DEGREES = 270.0f;

    public static final float DEFAULT_TO_DEGREES   = 360.0f;

    public static final int   DEFAULT_HINT_GRAVITY = CENTER;

    private int	       hintGravity	  = DEFAULT_HINT_GRAVITY;

    private float	     mFromDegrees	 = DEFAULT_FROM_DEGREES;

    private float	     mToDegrees	   = DEFAULT_TO_DEGREES;

    private static final int  MIN_RADIUS	   = 100;

    /* the distance between the layout's center and any child's center */
    private int	       mRadius;

    private boolean	   mExpanded	    = false;

    /*    */
    private int	       shifDistance;

    private boolean	   menuItemRotatationInClosing;

    private boolean	   checkCenterGravity;

    public ArcLayout(Context context) {
	super(context);
    }

    public ArcLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    private static int computeRadius(final float arcDegrees,
	    final int childCount, final int childSize, final int childPadding,
	    final int minRadius) {
	if (childCount < 2) {
	    return minRadius;
	}

	final float perDegrees = arcDegrees / (childCount - 1);
	final float perHalfDegrees = perDegrees / 2;
	final int perSize = childSize + childPadding;

	final int radius = (int) ((perSize / 2) / Math.sin(Math
		.toRadians(perHalfDegrees)));

	return Math.max(radius, minRadius);
    }

    private static Rect computeChildFrame(final int centerX, final int centerY,
	    final int radius, final float degrees, final int size) {

	final double childCenterX = centerX + radius
		* Math.cos(Math.toRadians(degrees));
	final double childCenterY = centerY + radius
		* Math.sin(Math.toRadians(degrees));

	return new Rect((int) (childCenterX - size / 2),
		(int) (childCenterY - size / 2),
		(int) (childCenterX + size / 2),
		(int) (childCenterY + size / 2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	final int radius = mRadius = computeRadius(
		Math.abs(mToDegrees - mFromDegrees), getChildCount(),
		mChildSize, mChildPadding, MIN_RADIUS);
	final int size = radius * 2 + mChildSize + mChildPadding
		+ mLayoutPadding * 2;
	int plusSize = (int) (mChildSize * 1.5f);

	switch (hintGravity) {
	    case BOTTOM_LEFT:
		setMeasuredDimension(size / 2 + plusSize, size / 2 + plusSize);
		break;
	    case BOTTOM_MIDDLE:
		setMeasuredDimension(size + plusSize, size / 2 + plusSize);
		break;
	    case BOTTOM_RIGHT:
		setMeasuredDimension(size / 2 + plusSize, size / 2 + plusSize);
		break;
	    case LEFT_MIDDLE:
		setMeasuredDimension(size / 2 + plusSize, size + plusSize);
		break;
	    case RIGHT_MIDDLE:
		setMeasuredDimension(size / 2 + plusSize, size + plusSize);
		break;
	    case TOP_LEFT:
		setMeasuredDimension(size / 2 + plusSize, size / 2 + plusSize);
		break;
	    case TOP_MIDDLE:
		setMeasuredDimension(size + plusSize, size / 2 + plusSize);
		break;
	    case TOP_RIGHT:
		setMeasuredDimension(size / 2 + plusSize, size / 2 + plusSize);
		break;
	    case CENTER:
		setMeasuredDimension(size, size);
		break;
	}

	final int count = getChildCount();
	for (int i = 0; i < count; i++) {
	    getChildAt(i)
		    .measure(
			    MeasureSpec.makeMeasureSpec(mChildSize,
				    MeasureSpec.EXACTLY),
			    MeasureSpec.makeMeasureSpec(mChildSize,
				    MeasureSpec.EXACTLY));
	}
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

	int centerX = getWidth() / 2;
	int centerY = getHeight() / 2;

	switch (hintGravity) {
	    case BOTTOM_LEFT:
		centerX = (int) (shifDistance);
		centerY = getHeight() - shifDistance;
		break;
	    case BOTTOM_MIDDLE:
		centerX = getWidth() / 2;
		centerY = getHeight() - shifDistance;
		break;
	    case BOTTOM_RIGHT:
		centerX = getWidth() - shifDistance;
		centerY = getHeight() - shifDistance;
		break;
	    case LEFT_MIDDLE:
		centerX = (int) (shifDistance);
		centerY = getHeight() / 2;
		break;
	    case RIGHT_MIDDLE:
		centerX = getWidth() - (int) (shifDistance);
		centerY = getHeight() / 2;
		break;
	    case TOP_LEFT:
		centerX = (int) (shifDistance);
		centerY = (int) (shifDistance);
		break;
	    case TOP_MIDDLE:
		centerX = getWidth() / 2;
		centerY = (int) (shifDistance);
		break;
	    case TOP_RIGHT:
		centerX = getWidth() - (int) (shifDistance);
		centerY = (int) (shifDistance);
		break;
	    case CENTER:
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		break;
	}

	final int radius = mExpanded ? mRadius : 0;

	if ((hintGravity == CENTER) && !checkCenterGravity) {
	    mToDegrees -= mToDegrees / getChildCount();
	    checkCenterGravity = true;
	}

	childCount = getChildCount();
	final float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);

	float degrees = mFromDegrees;
	for (int i = 0; i < childCount; i++) {
	    Rect frame = computeChildFrame(centerX, centerY, radius, degrees,
		    mChildSize);
	    degrees += perDegrees;
	    getChildAt(i).layout(frame.left, frame.top, frame.right,
		    frame.bottom);
	}
    }

    /**
     * refers to {@link LayoutAnimationController#getDelayForView(View view)}
     */
    private long computeStartOffset(final int childCount,
	    final boolean expanded, final int index, final float delayPercent,
	    final long duration, Interpolator interpolator) {
	final float delay = delayPercent * duration;
	final long viewDelay = (long) (getTransformedIndex(expanded,
		childCount, index) * delay);
	final float totalDelay = delay * childCount;

	float normalizedDelay = viewDelay / totalDelay;
	normalizedDelay = interpolator.getInterpolation(normalizedDelay);

	return (long) (normalizedDelay * totalDelay);
    }

    private int getTransformedIndex(final boolean expanded, final int count,
	    final int index) {
	if (expanded) {
	    return count - 1 - index;
	}

	return index;
    }

    private Animation createExpandAnimation(float fromXDelta, float toXDelta,
	    float fromYDelta, float toYDelta, long startOffset, long duration,
	    Interpolator interpolator) {
	Animation animation = new RotateAndTranslateAnimation(0, toXDelta, 0,
		toYDelta, 0, 720);
	animation.setStartOffset(startOffset);
	animation.setDuration(duration);
	animation.setInterpolator(interpolator);
	animation.setFillAfter(true);

	return animation;
    }

    private Animation createShrinkAnimation(float fromXDelta, float toXDelta,
	    float fromYDelta, float toYDelta, long startOffset, long duration,
	    Interpolator interpolator) {
	AnimationSet animationSet = new AnimationSet(false);
	animationSet.setFillAfter(true);

	final long preDuration = duration / 2;
	Animation rotateAnimation = new RotateAnimation(0, 360,
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		0.5f);
	rotateAnimation.setStartOffset(startOffset);
	rotateAnimation.setDuration(preDuration);
	rotateAnimation.setInterpolator(new LinearInterpolator());
	rotateAnimation.setFillAfter(true);

	if (menuItemRotatationInClosing) {
	    animationSet.addAnimation(rotateAnimation);
	}

	Animation translateAnimation = new RotateAndTranslateAnimation(0,
		toXDelta, 0, toYDelta, 360, 720);
	translateAnimation.setStartOffset(startOffset + preDuration);
	translateAnimation.setDuration(duration - preDuration);
	translateAnimation.setInterpolator(interpolator);
	translateAnimation.setFillAfter(true);

	animationSet.addAnimation(translateAnimation);

	return animationSet;
    }

    private void bindChildAnimation(final View child, final int index,
	    final long duration) {
	final boolean expanded = mExpanded;
	int centerX = getWidth() / 2;
	int centerY = getHeight() / 2;

	switch (hintGravity) {
	    case BOTTOM_LEFT:
		centerX = (int) (shifDistance);
		centerY = getHeight() - shifDistance;
		break;
	    case BOTTOM_MIDDLE:
		centerX = getWidth() / 2;
		centerY = getHeight() - shifDistance;
		break;
	    case BOTTOM_RIGHT:
		centerX = getWidth() - shifDistance;
		centerY = getHeight() - shifDistance;
		break;
	    case LEFT_MIDDLE:
		centerX = (int) (shifDistance);
		centerY = getHeight() / 2;
		break;
	    case RIGHT_MIDDLE:
		centerX = getWidth() - (int) (shifDistance);
		centerY = getHeight() / 2;
		break;
	    case TOP_LEFT:
		centerX = (int) (shifDistance);
		centerY = (int) (shifDistance);
		break;
	    case TOP_MIDDLE:
		centerX = getWidth() / 2;
		centerY = (int) (shifDistance);
		break;
	    case TOP_RIGHT:
		centerX = getWidth() - (int) (shifDistance);
		centerY = (int) (shifDistance);
		break;
	    case CENTER:
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		break;
	}

	final int radius = expanded ? 0 : mRadius;

	final int childCount = getChildCount();
	final float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);
	Rect frame = computeChildFrame(centerX, centerY, radius, mFromDegrees
		+ index * perDegrees, mChildSize);

	final int toXDelta = frame.left - child.getLeft();
	final int toYDelta = frame.top - child.getTop();

	Interpolator interpolator = mExpanded ? new AccelerateInterpolator()
		: new OvershootInterpolator(1.5f);
	final long startOffset = computeStartOffset(childCount, mExpanded,
		index, 0.1f, duration, interpolator);

	Animation animation = mExpanded ? createShrinkAnimation(0, toXDelta, 0,
		toYDelta, startOffset, duration, interpolator)
		: createExpandAnimation(0, toXDelta, 0, toYDelta, startOffset,
			duration, interpolator);

	final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
	animation.setAnimationListener(new AnimationListener() {

	    @Override
	    public void onAnimationStart(Animation animation) {

	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {

	    }

	    @Override
	    public void onAnimationEnd(Animation animation) {
		if (isLast) {
		    postDelayed(new Runnable() {

			@Override
			public void run() {
			    onAllAnimationsEnd();
			}
		    }, 0);
		}
	    }
	});

	child.setAnimation(animation);
    }

    public boolean isExpanded() {
	return mExpanded;
    }

    public void setArc(float fromDegrees, float toDegrees) {
	if (mFromDegrees == fromDegrees && mToDegrees == toDegrees) {
	    return;
	}

	mFromDegrees = fromDegrees;
	mToDegrees = toDegrees;

	requestLayout();
    }

    public void setChildSize(int size) {
	if (mChildSize == size || size < 0) {
	    return;
	}

	mChildSize = size;

	requestLayout();
    }

    public void setHintGravity(int gravity, int shift) {
	if (gravity > 9 || gravity < 1) {
	    return;
	}
	hintGravity = gravity;
	shifDistance = shift;
	requestLayout();
    }

    public void setDegrees(int fromDegree, int toDegree) {
	mFromDegrees = fromDegree;
	mToDegrees = toDegree;
	requestLayout();
    }

    public void setItemRotation(boolean rotate) {
	menuItemRotatationInClosing = rotate;
    }

    public int getChildSize() {
	return mChildSize;
    }

    /**
     * switch between expansion and shrinkage
     * 
     * @param showAnimation
     */
    public void switchState(final boolean showAnimation) {
	if (showAnimation) {
	    final int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
		bindChildAnimation(getChildAt(i), i, 300);
	    }
	}

	mExpanded = !mExpanded;

	if (!showAnimation) {
	    requestLayout();
	}

	invalidate();
    }

    private void onAllAnimationsEnd() {
	final int childCount = getChildCount();
	for (int i = 0; i < childCount; i++) {
	    getChildAt(i).clearAnimation();
	}

	requestLayout();
    }
}
