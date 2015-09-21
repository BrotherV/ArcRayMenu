/*
 * Copyright (C) 2015 BrotherV (Develope on Capricon Arc and Ray Menu)
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

package com.bvapp.testmenu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcraymenu.R;
import com.bvapp.arcraymenu.RayMenu;

/**
 * 
 * @author Capricorn
 * 
 */
public class ActivitySecond extends Activity {
    private static final int[] ITEM_DRAWABLES = { R.drawable.facebook,
	    R.drawable.twitter, R.drawable.flickr, R.drawable.instagram,
	    R.drawable.skype, R.drawable.github };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_second);

	RayMenu rayMenu_1 = (RayMenu) findViewById(R.id.ray_menu);
	RayMenu rayMenu_2 = (RayMenu) findViewById(R.id.ray_menu_1);
	RayMenu rayMenu_3 = (RayMenu) findViewById(R.id.ray_menu_2);
	RayMenu rayMenu_4 = (RayMenu) findViewById(R.id.ray_menu_3);

	initRayMenu(rayMenu_1, ITEM_DRAWABLES);
	initRayMenu(rayMenu_2, ITEM_DRAWABLES);
	initRayMenu(rayMenu_3, ITEM_DRAWABLES);
	initRayMenu(rayMenu_4, ITEM_DRAWABLES);

    }

    private void initRayMenu(final RayMenu menu, int[] itemDrawables) {
	final int itemCount = itemDrawables.length;
	for (int i = 0; i < itemCount; i++) {
	    ImageView item = new ImageView(this);
	    item.setImageResource(ITEM_DRAWABLES[i]);

	    final int position = i;
	    menu.addItem(item, new OnClickListener() {

		@Override
		public void onClick(View v) {
		    Log.i("Log", "Click Test out");
		    Toast.makeText(ActivitySecond.this, "position:" + position,
			    Toast.LENGTH_SHORT).show();
		    // menu.setHintTopImage(R.drawable.pen);
		}
	    });// Add a menu item
	}
    }
}
