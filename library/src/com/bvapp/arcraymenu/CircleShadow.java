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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleShadow extends ImageView {

    private static final String DEFAULT_SHADOW_COLOR	= "#7F7F7F";

    private static final String DEFAULT_SHADOW_AROUND_COLOR = "#6a6a6a";

    private Paint	       paint;

    private float	       radius;
    private float	       xCenter;
    private float	       yCenter;

    private float	       shadowRadius		= 2.0f;
    private float	       shadowXDelta		= 2.0f;
    private float	       shadowYDelta		= 2.0f;

    private boolean	     canDraw;

    private String	      initialColor		= DEFAULT_SHADOW_COLOR;
    private String	      initialRoundColor	   = DEFAULT_SHADOW_AROUND_COLOR;

    /*
	 * 
	 */
    public CircleShadow(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    /*
	 * 
	 */
    public CircleShadow(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    /*
	 * 
	 */
    public CircleShadow(Context context) {
	super(context);
    }

    /*
     * 
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
	    int bottom) {
	super.onLayout(changed, left, top, right, bottom);
	xCenter = getWidth() / 2;
	yCenter = getHeight() / 2;
    }

    private void setShadowColor() {
	paint = new Paint();
	paint.setAntiAlias(true);
	paint.setColor(Color.parseColor(initialColor));
	this.setLayerType(LAYER_TYPE_SOFTWARE, paint);
	paint.setShadowLayer(shadowRadius, shadowXDelta, shadowYDelta,
		Color.parseColor(initialRoundColor));
    }

    public void setShadowValus(float r, float dx, float dy, float radius) {
	shadowRadius = r;
	shadowXDelta = dx;
	shadowYDelta = dy;
	this.radius = radius;
	setShadowColor();
    }

    public void setShadowColor(String s1, String s2) {
	if (s1 != null && s1 != "") {
	    initialColor = s1;
	}
	if (s2 != null && s1 != "") {
	    initialRoundColor = s2;
	}
    }

    /*
	 * 
	 */
    public void setDraw(boolean l) {
	canDraw = l;
	postInvalidate();
    }

    /*
	 * 
	 */
    @Override
    protected void onDraw(final Canvas canvas) {
	super.onDraw(canvas);

	if (canDraw) {
	    canvas.drawCircle(xCenter, yCenter, radius, paint);
	}
    }
}
