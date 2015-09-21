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
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleMenu extends ImageView {

    public static final String	   DEFAULT_NORMAL_COLOR = "#cf1c1f";

    private static Drawable	      drawable;

    private static PorterDuffColorFilter colorFilter;

    private Paint			paint;

    public CircleMenu(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	initialize();
    }

    public CircleMenu(Context context, AttributeSet attrs) {
	super(context, attrs);
	initialize();
    }

    public CircleMenu(Context context) {
	super(context);
	initialize();
    }

    private void initialize() {
	paint = new Paint();
	paint.setAntiAlias(true);

	colorFilter = new PorterDuffColorFilter(
		Color.parseColor(DEFAULT_NORMAL_COLOR),
		PorterDuff.Mode.SRC_ATOP);

	paint.setColorFilter(colorFilter);
    }

    public void setColorFilter(String color) {
	colorFilter = new PorterDuffColorFilter(Color.parseColor(color),
		PorterDuff.Mode.SRC_ATOP);

	paint.setColorFilter(colorFilter);
	postInvalidate();
    }

    // ////
    @Override
    protected void onDraw(Canvas canvas) {
	// super.onDraw(canvas);
	drawable = getDrawable();

	if (drawable == null) {
	    return;
	}

	if (getWidth() == 0 || getHeight() == 0) {
	    return;
	}

	int w = getWidth();

	Bitmap b = ((BitmapDrawable) drawable).getBitmap();
	Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
	Bitmap smoothRoundBitmap = getSmoothCroppedBitmap(bitmap, w);
	canvas.drawBitmap(smoothRoundBitmap, 0, 0, paint);
    }

    // //////
    // //////
    public static Bitmap getSmoothCroppedBitmap(Bitmap bmp, int radius) {
	Bitmap sbmp;

	if (bmp.getWidth() != radius || bmp.getHeight() != radius)
	    sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, true);
	else
	    sbmp = bmp;

	Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
		Config.ARGB_8888);

	Canvas canvas = new Canvas(output);

	final Paint paint = new Paint();
	final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
	paint.setAntiAlias(true);
	paint.setFilterBitmap(true);
	canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
		sbmp.getWidth() / 2 + 0.1f, paint);
	paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	canvas.drawBitmap(sbmp, rect, rect, paint);
	return output;
    }
}
