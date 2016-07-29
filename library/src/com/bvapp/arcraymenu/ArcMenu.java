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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bvapp.samplearcmenu.R;

/**
 * A custom view that looks like the menu in <a href="https://path.com">Path
 * 2.0</a> (for iOS).
 * 
 * @author Capricorn
 * 
 */
public class ArcMenu extends RelativeLayout {

    public static final int     TOP_LEFT	     = 1;
    public static final int     TOP_RIGHT	    = 2;
    public static final int     TOP_MIDDLE	   = 3;
    public static final int     BOTTOM_LEFT	  = 4;
    public static final int     BOTTOM_RIGHT	 = 5;
    public static final int     BOTTOM_MIDDLE	= 6;
    public static final int     RIGHT_MIDDLE	 = 7;
    public static final int     LEFT_MIDDLE	  = 8;
    public static final int     CENTER	       = 9;

    private static final String DEFAULT_NORMAL_COLOR = "#cf1c1f";

    private static final String DEFAULT_PRESS_COLOR  = "#ac181a";

    private static final String DEFAULT_SING_COLOR   = "#757575";

    private Context	     context;

    private ArcLayout	   mArcLayout;

    private CircleMenu	  mHintBackground;

    private CircleShadow	mHintShadow;

    private ImageView	   mHintTopImage;

    private ImageView	   mHintViewV;

    private ImageView	   mHintViewH;

    private TextView	    mHintTextView;

    private FrameLayout	 controlLayout;

    private String	      hintPressColor       = DEFAULT_PRESS_COLOR;

    private String	      hintNormalColor      = DEFAULT_NORMAL_COLOR;

    private String	      hintSingColor	= DEFAULT_SING_COLOR;

    private int		 hintGravity	  = CENTER;

    private int		 hintSize	     = 56;

    private boolean	     mHintTextVisibilityControl;

    private boolean	     elevationCheck;

    private int		 shifHint	     = 24;

    private int		 shifHintPlus	 = shifHint;

    private int		 shifChild;

    private int		 shadowBorderWidth;

    private int		 shadowBorderHeight;

    private int		 shadowElevation;

    private int		 shadowMargin;

    public ArcMenu(Context context) {
	super(context);
	init(context);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	init(context);
	applyAttrs(context, attrs);
    }

    private void init(Context context) {
	LayoutInflater li = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	li.inflate(R.layout.arc_menu, this);

	mArcLayout = (ArcLayout) findViewById(R.id.item_layout);

	controlLayout = (FrameLayout) findViewById(R.id.control_layout);

	mHintBackground = (CircleMenu) findViewById(R.id.control_hint_background);
	mHintViewV = (ImageView) findViewById(R.id.control_hint_v);
	mHintViewH = (ImageView) findViewById(R.id.control_hint_h);
	mHintTextView = (TextView) findViewById(R.id.control_hint_Text);
	mHintTopImage = (ImageView) findViewById(R.id.control_hint_image);
	mHintShadow = (CircleShadow) findViewById(R.id.control_hint_Shadow);

	controlLayout.setOnTouchListener(new OnTouchListener() {

	    @Override
	    public boolean onTouch(View view, MotionEvent event) {

		switch (event.getAction()) {

		    case MotionEvent.ACTION_DOWN:
			mHintBackground.setColorFilter(hintPressColor);
			break;
		    case MotionEvent.ACTION_UP:
			mHintBackground.setColorFilter(hintNormalColor);
			if (mHintTextVisibilityControl) {
			    mHintViewV
				    .startAnimation(createHintSwitchAnimationV(mArcLayout
					    .isExpanded()));
			    mHintViewH
				    .startAnimation(createHintSwitchAnimationH(mArcLayout
					    .isExpanded()));
			}
			mArcLayout.switchState(true);

			break;
		    case MotionEvent.ACTION_MOVE:
			mHintBackground.setColorFilter(hintNormalColor);
			break;
		}

		return false;
	    }
	});

	controlLayout.setClickable(true);

    }

    private void applyAttrs(Context context, AttributeSet attrs) {
	if (attrs != null) {
	    TypedArray b = getContext().obtainStyledAttributes(attrs,
		    R.styleable.ArcMenu);

	    int defaultChildSize = mArcLayout.getChildSize();

	    int childSize = b.getDimensionPixelSize(
		    R.styleable.ArcMenu_childSize, defaultChildSize);
	    mArcLayout.setChildSize(childSize);

	    int hintPixelSize = b.getDimensionPixelSize(
		    R.styleable.ArcMenu_hintSize,
		    convertDpToPixel(hintSize, context));
	    setHintSize(hintPixelSize);

	    shifHint = 24;
	    shifChild = (int) (shifHint + childSize / 1.8f);

	    int newHintGravity = b.getInt(R.styleable.ArcMenu_hintGravity,
		    hintGravity);

	    Log.i("Log", "Gravity = " + newHintGravity);
	    mArcLayout.setHintGravity(newHintGravity, shifChild);

	    String color = b.getString(R.styleable.ArcMenu_hintNormalColor);
	    if (color != null) {
		Log.i("Log", color);
		hintNormalColor = color;
	    }

	    color = b.getString(R.styleable.ArcMenu_hintPressColor);
	    if (color != null) {
		Log.i("Log", color);
		hintPressColor = color;
	    }

	    color = b.getString(R.styleable.ArcMenu_hintUpperMarkColor);
	    if (color != null) {
		Log.i("Log", color);
		hintSingColor = color;
	    }

	    Drawable drawable = b.getDrawable(R.styleable.ArcMenu_hintTopImage);
	    if (drawable != null) {
		mHintTopImage.setImageDrawable(drawable);
		mHintTopImage.setColorFilter(Color.parseColor(hintSingColor),
			PorterDuff.Mode.SRC_ATOP);
		;

		mHintViewV.setVisibility(View.INVISIBLE);
		mHintViewH.setVisibility(View.INVISIBLE);
		mHintTextView.setVisibility(View.INVISIBLE);
		mHintTextVisibilityControl = false;
	    } else {
		mHintTopImage.setVisibility(View.INVISIBLE);
		String hintText = b.getString(R.styleable.ArcMenu_hintText);
		float textSize = b.getDimension(
			R.styleable.ArcMenu_hintTextSize, 16);
		if (hintText != null) {
		    mHintTextView.setText(hintText);
		    mHintTextView.setTextColor(Color.parseColor(hintSingColor));
		    mHintTextView.setTextSize(textSize);

		    mHintViewV.setVisibility(View.INVISIBLE);
		    mHintViewH.setVisibility(View.INVISIBLE);

		    mHintTextVisibilityControl = false;
		} else {
		    mHintTextView.setVisibility(View.INVISIBLE);
		    mHintTextVisibilityControl = true;
		}
	    }

	    String shadowColor = b.getString(R.styleable.ArcMenu_shadowColor);
	    String shadowRColor = b
		    .getString(R.styleable.ArcMenu_shadowAroundColor);
	    if (shadowColor != null || shadowRColor != null) {
		Log.i("Log", "shadowColor = " + shadowColor);
		mHintShadow.setShadowColor(shadowColor, shadowRColor);
	    }

	    shadowBorderWidth = b.getDimensionPixelSize(
		    R.styleable.ArcMenu_shadowBorderWidth, 0);
	    shadowBorderHeight = b.getDimensionPixelSize(
		    R.styleable.ArcMenu_shadowBorderHeight, 0);
	    shadowElevation = b.getDimensionPixelSize(
		    R.styleable.ArcMenu_shadowElevation, 0);

	    shadowMargin = b.getDimensionPixelSize(
		    R.styleable.ArcMenu_shadowMargin, 0);
	    Log.i("Log", "shadowMargin = " + shadowMargin);
	    setShadow(shadowElevation, shadowBorderHeight, shadowBorderWidth,
		    shadowMargin);
	    setHintGravity(newHintGravity);

	    Log.i("Log", elevationCheck ? "True" : "False");
	    mHintBackground.setColorFilter(hintNormalColor);

	    mHintViewV.setBackgroundColor(Color.parseColor(hintSingColor));
	    mHintViewH.setBackgroundColor(Color.parseColor(hintSingColor));

	    boolean menuItemRotatation = b.getBoolean(
		    R.styleable.ArcMenu_rotateInClosing, false);
	    Log.i("Log", "menu Item Rotatation = " + menuItemRotatation);
	    mArcLayout.setItemRotation(menuItemRotatation);

	    b.recycle();
	}
    }

    public void addItem(View item, OnClickListener listener) {
	mArcLayout.addView(item);
	item.setOnClickListener(getItemClickListener(listener));
    }

    private OnClickListener getItemClickListener(final OnClickListener listener) {
	return new OnClickListener() {

	    @Override
	    public void onClick(final View viewClicked) {
		Animation animation = bindItemAnimation(viewClicked, true, 250);
		animation.setAnimationListener(new AnimationListener() {

		    @Override
		    public void onAnimationStart(Animation animation) {

		    }

		    @Override
		    public void onAnimationRepeat(Animation animation) {

		    }

		    @Override
		    public void onAnimationEnd(Animation animation) {
			postDelayed(new Runnable() {

			    @Override
			    public void run() {
				itemDidDisappear();
			    }
			}, 0);
		    }
		});

		final int itemCount = mArcLayout.getChildCount();
		for (int i = 0; i < itemCount; i++) {
		    View item = mArcLayout.getChildAt(i);
		    if (viewClicked != item) {
			bindItemAnimation(item, false, 200);
		    }
		}

		mArcLayout.invalidate();

		if (mHintTextVisibilityControl) {
		    mHintViewV.startAnimation(createHintSwitchAnimationV(true));
		    mHintViewH.startAnimation(createHintSwitchAnimationH(true));
		}

		if (listener != null) {
		    listener.onClick(viewClicked);
		}
	    }
	};
    }

    private Animation bindItemAnimation(final View child,
	    final boolean isClicked, final long duration) {
	Animation animation = createItemDisapperAnimation(duration, isClicked);
	child.setAnimation(animation);

	return animation;
    }

    private void itemDidDisappear() {
	final int itemCount = mArcLayout.getChildCount();
	for (int i = 0; i < itemCount; i++) {
	    View item = mArcLayout.getChildAt(i);
	    item.clearAnimation();
	}

	mArcLayout.switchState(false);
    }

    private static Animation createItemDisapperAnimation(final long duration,
	    final boolean isClicked) {
	AnimationSet animationSet = new AnimationSet(true);
	animationSet.addAnimation(new ScaleAnimation(1.0f, isClicked ? 1.4f
		: 0.0f, 1.0f, isClicked ? 1.4f : 0.0f,
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		0.5f));
	animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));

	animationSet.setDuration(duration);
	animationSet.setInterpolator(new DecelerateInterpolator());
	animationSet.setFillAfter(true);

	return animationSet;
    }

    private static Animation createHintSwitchAnimationV(final boolean expanded) {
	Animation animation = new RotateAnimation(expanded ? -135 : 0,
		expanded ? 0 : -135, Animation.RELATIVE_TO_SELF, 0.5f,
		Animation.RELATIVE_TO_SELF, 0.5f);
	animation.setStartOffset(0);
	animation.setDuration(250);
	animation.setInterpolator(new DecelerateInterpolator());
	animation.setFillAfter(true);

	return animation;
    }

    private static Animation createHintSwitchAnimationH(final boolean expanded) {
	Animation animation = new RotateAnimation(expanded ? 45 : 0,
		expanded ? 0 : 45, Animation.RELATIVE_TO_SELF, 0.5f,
		Animation.RELATIVE_TO_SELF, 0.5f);
	animation.setStartOffset(0);
	animation.setDuration(250);
	animation.setInterpolator(new DecelerateInterpolator());
	animation.setFillAfter(true);

	return animation;
    }

    private int convertDpToPixel(float dp, Context context) {
	Resources resources = context.getResources();
	DisplayMetrics metrics = resources.getDisplayMetrics();
	int px = (int) (dp * (metrics.densityDpi / 160f));
	return px;
    }

    public void setShadow(int se, int h, int w, int sm) {
	shifHintPlus = shifHint;
	if (w != 0 || h != 0 || se > 0) {
	    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHintShadow
		    .getLayoutParams();
	    if ((h > 0 && sm > 0) || (h == 0 && (se > 0 && sm > 0))) {
		params.topMargin = sm;
	    } else if (h < 0 && sm > 0) {
		params.bottomMargin = sm;
	    }

	    if (w > 0 && sm > 0) {
		params.leftMargin = sm;
	    } else if (w < 0 && sm > 0) {
		params.rightMargin = sm;
	    }

	    shifHintPlus -= 2;

	    if (se > 0) {
		elevationCheck = true;
	    }
	    int offset = 8;
	    int se1 = se;
	    int h1 = h;
	    int w1 = w;
	    if (se < 0) {
		se1 *= -1;
	    }
	    if (h < 0) {
		h1 *= -1;
	    }
	    if (w < 0) {
		w1 *= -1;
	    }
	    params.height = hintSize + se1 + h1 + offset;
	    params.width = hintSize + se1 + w1 + offset;
	    shifHintPlus -= se / 2;

	    int r = se;
	    int dx = w;
	    int dy = h;

	    Log.i("Log", "r = " + r);
	    Log.i("Log", "dx = " + dx);
	    Log.i("Log", "dy = " + dy);

	    mHintShadow.setShadowValus(r / 2, dx, dy, hintSize / 2);
	    mHintShadow.setDraw(true);
	    mHintShadow.setLayoutParams(params);
	}
    }

    public void setHintGravity(int gravity) {
	if (gravity < 1 || gravity > 9) {
	    return;
	}
	FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) controlLayout
		.getLayoutParams();

	switch (gravity) {
	    case BOTTOM_LEFT:
		params.gravity = Gravity.LEFT | Gravity.BOTTOM;
		params.setMargins(elevationCheck ? shifHintPlus : shifHint, 0,
			0, elevationCheck ? shifHintPlus : shifHint);
		mArcLayout.setDegrees(270, 360);
		break;
	    case BOTTOM_MIDDLE:
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
		params.setMargins(0, 0, 0, elevationCheck ? shifHintPlus
			: shifHint);
		mArcLayout.setDegrees(180, 360);
		break;
	    case BOTTOM_RIGHT:
		params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		params.setMargins(0, 0, elevationCheck ? shifHintPlus
			: shifHint, elevationCheck ? shifHintPlus : shifHint);
		mArcLayout.setDegrees(270, 180);
		break;
	    case LEFT_MIDDLE:
		params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
		params.setMargins(elevationCheck ? shifHintPlus : shifHint, 0,
			0, 0);
		mArcLayout.setDegrees(-90, 90);
		break;
	    case RIGHT_MIDDLE:
		params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		params.setMargins(0, 0, elevationCheck ? shifHintPlus
			: shifHint, 0);
		mArcLayout.setDegrees(270, 90);
		break;
	    case TOP_LEFT:
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.setMargins(elevationCheck ? shifHintPlus : shifHint,
			elevationCheck ? shifHintPlus : shifHint, 0, 0);
		mArcLayout.setDegrees(0, 90);
		break;
	    case TOP_MIDDLE:
		params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
		params.setMargins(0, elevationCheck ? shifHintPlus : shifHint,
			0, 0);
		mArcLayout.setDegrees(0, 180);
		break;
	    case TOP_RIGHT:
		params.gravity = Gravity.RIGHT | Gravity.TOP;
		params.setMargins(0, elevationCheck ? shifHintPlus : shifHint,
			elevationCheck ? shifHintPlus : shifHint, 0);
		mArcLayout.setDegrees(90, 180);
		break;
	    case CENTER:
		params.gravity = Gravity.CENTER;
		mArcLayout.setDegrees(0, 360);
		break;
	}
	controlLayout.setLayoutParams(params);
    }

    public void setHintColor(String s1, String s2) {
	if (s1 != null && s1 != "") {
	    hintNormalColor = s1;
	}
	if (s2 != null && s2 != "") {
	    hintPressColor = s2;
	}
	mHintBackground.setColorFilter(hintNormalColor);
    }

    public void setUpperMarkColor(String s3) {
	if (s3 != null && s3 != "") {
	    hintSingColor = s3;
	}
	mHintViewV.setBackgroundColor(Color.parseColor(hintSingColor));
	mHintViewH.setBackgroundColor(Color.parseColor(hintSingColor));
    }

    public void setHintTopImage(int drawable) {
	if (drawable != 0) {
	    mHintTextVisibilityControl = false;
	    mHintTopImage.setImageResource(drawable);
	    mHintTopImage.setColorFilter(Color.parseColor(hintSingColor),
		    PorterDuff.Mode.SRC_ATOP);
	    ;

	    mHintViewV.setVisibility(View.INVISIBLE);
	    mHintViewH.setVisibility(View.INVISIBLE);
	    mHintTextView.setVisibility(View.INVISIBLE);
	    mHintTopImage.setVisibility(View.VISIBLE);
	}
    }

    public void setHintText(String hintText, float textSize) {

	if (hintText != null && hintText != "") {
	    mHintTextVisibilityControl = false;
	    mHintTextView.setText(hintText);
	    mHintTextView.setTextColor(Color.parseColor(hintSingColor));
	    if (textSize > 6 && textSize < 32) {
		mHintTextView.setTextSize(textSize);
	    }

	    mHintViewV.setVisibility(View.INVISIBLE);
	    mHintViewH.setVisibility(View.INVISIBLE);

	    mHintTopImage.setVisibility(View.INVISIBLE);
	    mHintTextView.setVisibility(View.VISIBLE);
	}
    }

    public void setHintShadowColor(String shadowColor, String shadowRColor) {

	if (shadowColor != null || shadowRColor != null) {
	    mHintShadow.setShadowColor(shadowColor, shadowRColor);
	}
    }

    public void setRotationInClosing(boolean l) {
	mArcLayout.setItemRotation(l);
    }

    public void setHintSize(int size) {
	if (size < hintSize || size > (hintSize + 64)) {
	    return;
	}

	hintSize = size;
	FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHintBackground
		.getLayoutParams();
	params.height = hintSize;
	params.width = hintSize;
	mHintBackground.setLayoutParams(params);

	requestLayout();
    }
}
