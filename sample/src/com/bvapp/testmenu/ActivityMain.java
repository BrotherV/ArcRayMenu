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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcraymenu.ArcMenu;
import com.bvapp.arcraymenu.R;

public class ActivityMain extends Activity {

    private static final int[] ITEM_DRAWABLES = { R.drawable.facebook,
	    R.drawable.twitter, R.drawable.flickr, R.drawable.instagram,
	    R.drawable.skype, R.drawable.github };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
	ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);
	ArcMenu arcMenu3 = (ArcMenu) findViewById(R.id.arc_menu_3);
	ArcMenu arcMenu4 = (ArcMenu) findViewById(R.id.arc_menu_4);
	ArcMenu arcMenu5 = (ArcMenu) findViewById(R.id.arc_menu_5);
	ArcMenu arcMenu6 = (ArcMenu) findViewById(R.id.arc_menu_6);
	ArcMenu arcMenu7 = (ArcMenu) findViewById(R.id.arc_menu_7);
	ArcMenu arcMenu8 = (ArcMenu) findViewById(R.id.arc_menu_8);
	ArcMenu arcMenu9 = (ArcMenu) findViewById(R.id.arc_menu_9);

	initArcMenu(arcMenu, ITEM_DRAWABLES);
	initArcMenu(arcMenu2, ITEM_DRAWABLES);
	initArcMenu(arcMenu3, ITEM_DRAWABLES);
	initArcMenu(arcMenu4, ITEM_DRAWABLES);
	initArcMenu(arcMenu5, ITEM_DRAWABLES);
	initArcMenu(arcMenu6, ITEM_DRAWABLES);
	initArcMenu(arcMenu7, ITEM_DRAWABLES);
	initArcMenu(arcMenu8, ITEM_DRAWABLES);
	initArcMenu(arcMenu9, ITEM_DRAWABLES);

    }

    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
	final int itemCount = itemDrawables.length;
	for (int i = 0; i < itemCount; i++) {
	    ImageView item = new ImageView(this);
	    item.setImageResource(itemDrawables[i]);

	    final int position = i;
	    menu.addItem(item, new OnClickListener() {

		@Override
		public void onClick(View v) {
		    Toast.makeText(ActivityMain.this, "position:" + position,
			    Toast.LENGTH_SHORT).show();
		}
	    });
	}
    }
}
