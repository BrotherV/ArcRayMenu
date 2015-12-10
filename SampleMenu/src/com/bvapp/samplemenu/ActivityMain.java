package com.bvapp.samplemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcraymenu.ArcMenu;

public class ActivityMain extends Activity {

    private static final int[] ITEM_DRAWABLES = { R.drawable.facebook,
	    R.drawable.twitter, R.drawable.flickr, R.drawable.instagram,
	    R.drawable.skype, R.drawable.github };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arcMenu1);

	initArcMenu(arcMenu, ITEM_DRAWABLES);

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
