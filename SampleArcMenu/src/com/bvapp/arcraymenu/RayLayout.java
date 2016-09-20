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
import android.util.Log;
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

public class RayLayout extends RelativeLayout {

    private static final int POSITIVE_DIRECTION_V = 1;
    private static final int POSITIVE_DIRECTION_H = 2;
    private static final int NEGATIVE_DIRECTION_V = 3;
    private static final int NEGATIVE_DIRECTION_H = 4;

    private int	      expandDirection      = 2;

    private int	      mLayoutPadding       = 56;

    private int	      mLayoutWidth;

    /**
     * children will be set the same size.
     */
    private int	      mChildSize	   = 32;

    /* the distance between child Views */
    private int	      mChildGap;

    /* left space to place the switch button */
    private int	      mHolderWidth	 = 32;

    private boolean	  mExpanded	    = false;

    private boolean	  directionControl     = true;

    private boolean	  menuItemRotatationInClosing;

    public RayLayout(Context context) {
	super(context);
    }

    public RayLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    private int computeChildGap(final float width, final int childCount,
	    final int childSize, final int minGap) {
	return Math.max((int) (width / childCount - childSize), minGap);
    }

    private Rect computeChildFrame(final boolean expanded, final int padding,
	    final int childIndex, final int gap, final int size) {
	int edgeValue = 0;
	int nEdge = mLayoutWidth / 2 + size;
	int pEdge = mLayoutWidth / 2 - size;
	Rect rect = new Rect(0, 0, size, size);
	;
	if (expandDirection == NEGATIVE_DIRECTION_V) {
	    edgeValue = expanded ? (padding - childIndex * (gap + size) + gap)
		    : (padding - size / 2);
	    rect = new Rect(pEdge, edgeValue - size, nEdge, edgeValue);
	} else if (expandDirection == NEGATIVE_DIRECTION_H) {
	    edgeValue = expanded ? (padding - childIndex * (gap + size) + gap)
		    : (padding - size / 2);
	    rect = new Rect(edgeValue - size, pEdge, edgeValue, nEdge);

	} else if (expandDirection == POSITIVE_DIRECTION_H) {

	    edgeValue = expanded ? (padding + childIndex * (gap + size) + gap)
		    : ((padding - size) / 2);
	    rect = new Rect(edgeValue, pEdge, edgeValue + size, nEdge);
	} else if (expandDirection == POSITIVE_DIRECTION_V) {
	    edgeValue = expanded ? (padding + childIndex * (gap + size) + gap)
		    : ((padding - size) / 2);
	    rect = new Rect(pEdge, edgeValue, nEdge, edgeValue + size);
	}
	return rect;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
	if (directionControl) {
	    return mHolderWidth + mChildSize * getChildCount();
	} else {
	    return mChildSize;
	}
    }

    @Override
    protected int getSuggestedMinimumWidth() {
	if (directionControl) {
	    return mChildSize;
	} else {
	    return mHolderWidth + mChildSize * getChildCount();
	}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	switch (expandDirection) {
	    case POSITIVE_DIRECTION_H:
		directionControl = false;
		setMeasuredDimension(getSuggestedMinimumWidth()
			+ mLayoutPadding, getSuggestedMinimumHeight()
			+ mLayoutPadding);
		mLayoutWidth = getSuggestedMinimumHeight() + mLayoutPadding;
		break;
	    case POSITIVE_DIRECTION_V:
		directionControl = true;
		setMeasuredDimension(getSuggestedMinimumWidth()
			+ mLayoutPadding, getSuggestedMinimumHeight()
			+ mLayoutPadding);
		mLayoutWidth = getSuggestedMinimumWidth() + mLayoutPadding;
		break;
	    case NEGATIVE_DIRECTION_H:
		directionControl = false;
		setMeasuredDimension(getSuggestedMinimumWidth()
			+ mLayoutPadding, getSuggestedMinimumHeight()
			+ mLayoutPadding);
		mLayoutWidth = getSuggestedMinimumHeight() + mLayoutPadding;
		break;
	    case NEGATIVE_DIRECTION_V:
		directionControl = true;
		setMeasuredDimension(getSuggestedMinimumWidth()
			+ mLayoutPadding, getSuggestedMinimumHeight()
			+ mLayoutPadding);
		mLayoutWidth = getSuggestedMinimumWidth() + mLayoutPadding;
		break;
	}

	final int count = getChildCount();

	Log.i("Log", "H = " + getSuggestedMinimumHeight() + "  W = "
		+ getSuggestedMinimumWidth() + "  ChildCount = " + count);
	int directionLenght = getMeasuredWidth() - mLayoutPadding;
	if (directionControl) {
	    directionLenght = getMeasuredHeight() - mLayoutPadding;
	}

	mChildGap = computeChildGap(directionLenght - mHolderWidth, count,
		mChildSize, 0);

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
	int padding = 0;
	switch (expandDirection) {
	    case POSITIVE_DIRECTION_H:
		padding = mHolderWidth;
		break;
	    case POSITIVE_DIRECTION_V:
		padding = mHolderWidth;
		break;
	    case NEGATIVE_DIRECTION_H:
		padding = mExpanded ? (getWidth() - mHolderWidth)
			: (getWidth());
		break;
	    case NEGATIVE_DIRECTION_V:
		padding = mExpanded ? (getHeight() - mHolderWidth)
			: (getHeight());
		break;
	}
	final int childCount = getChildCount();

	for (int i = 0; i < childCount; i++) {
	    Rect frame = computeChildFrame(mExpanded, padding, i, mChildGap,
		    mChildSize);
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
	return count - 1 - index;
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
	final int childCount = getChildCount();
	int padding = 0;
	switch (expandDirection) {
	    case POSITIVE_DIRECTION_H:
		padding = mHolderWidth;
		break;
	    case POSITIVE_DIRECTION_V:
		padding = mHolderWidth;
		break;
	    case NEGATIVE_DIRECTION_H:
		padding = mExpanded ? (getWidth())
			: (getWidth() - mHolderWidth);
		break;
	    case NEGATIVE_DIRECTION_V:
		padding = mExpanded ? (getHeight())
			: (getHeight() - mHolderWidth);
		break;
	}

	Rect frame = computeChildFrame(!expanded, padding, index, mChildGap,
		mChildSize);

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

    public void setChildSize(int size) {
	if (mChildSize == size || size < 0) {
	    return;
	}

	mChildSize = size;

	requestLayout();
    }

    public void setHolderWidth(int mHolderWidth) {
	this.mHolderWidth = mHolderWidth;

	requestLayout();
    }

    public void setLayoutPadding(int mLayoutPadding) {
	this.mLayoutPadding = mLayoutPadding;
	requestLayout();
    }

    public void setHintDirection(int direction) {
	if (direction < 1 || direction > 4) {
	    return;
	}

	expandDirection = direction;

	requestLayout();
    }

    public void setItemRotation(boolean rotate) {
	menuItemRotatationInClosing = rotate;
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
