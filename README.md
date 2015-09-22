#ArcMenu & RayMenu
This menu designed to use on Android. app designers can use this menu for their applications. I tried to fix issues in previos version of [arcraymenu](https://github.com/daCapricorn/ArcMenu) and applied many changes.

##ArcMenu
* 1-Corner issues fixed.
* 2-Shadow added to Hint button and can add specific color on main shadow and around shadow.
* 3-Plus marker animation changed
* 4-Can add text on Hint button
* 5-Can add specific image on Hint button
* 6-Can add specific color on Hint normal button and press color.
* 7-Attribute completly changed

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/YOUTUBE_VIDEO_ID_HERE/0.jpg)](https://youtu.be/vI3pf3Thlss)
* ![Preview](http://s1.postimg.org/im0ag2knj/Screenshot_2015_09_21_13_28_31.png)  ![Preview](http://s1.postimg.org/v5qtq2jgv/Screenshot_2015_09_21_13_28_53.png)
* ![Preview](http://s1.postimg.org/t2kzw591r/Screenshot_2015_09_21_13_28_58.png)  ![Preview](http://s1.postimg.org/4uvtduvpr/Screenshot_2015_09_21_13_29_02.png)
* ![Preview](http://s1.postimg.org/jfcw8oqof/Screenshot_2015_09_21_13_29_06.png)  ![Preview](http://s1.postimg.org/q9rsc48pb/Screenshot_2015_09_21_13_29_11.png)
* ![Preview](http://s1.postimg.org/kzmtktogf/Screenshot_2015_09_21_13_29_15.png)  ![Preview](http://s1.postimg.org/8m9zdwyrz/Screenshot_2015_09_21_13_29_19.png)
* ![Preview](http://s1.postimg.org/4r6lbcfm7/Screenshot_2015_09_21_13_29_31.png)
 
##ArcMenu Usage
If you want set all attr in xml file, you must also add ``` xml xmlns:arc="http://schemas.android.com/apk/res-auto" ``` to your xml activity.

###Set Hint size
* xml usage
``` xml
  arc:hintSize="56dp"
```
* java usage
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setHintSize(hintSize);
```

###Set Child size
* xml usage
``` xml
  arc:childSize="48dp"
```
* java usage
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setChildSize(childSize);
```

###Set Hint Button Colors and Marker Color
* xml usage (color is a type of string without alpha)
``` xml
  arc:hintNormalColor="#24bb27"
  arc:hintPressColor="#1c8e1f"
  arc:hintUpperMarkColor="#cdcbcb"
```
* java usage (color is a type of string without alpha)
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setHintColor(hintNormalColor, hintPressColor);
  arcMenu.setUpperMarkColor(hintUpperMarkColor);
```

###Set Hint Button Text
* xml usage (Remember, if you set text plus marker will be disable automatically)
``` xml
  arc:hintText="M"
  arc:hintTextSize="16dp"
```
* java usage (Remember, if you set text plus marker will be disable automatically)
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setHintText(hintText,textSize);
```

###Set Hint Button Image
* xml usage (Remember, if you set the image, text and plus marker will be disable automatically)
``` xml
  arc:hintTopImage = "@drawable/pen"
```
* java usage (Remember, if you set the image, text and plus marker will be disable automatically)
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setHintTopImage(R.drawable.pen);
```

###Set Shadow
* xml usage
``` xml
  arc:shadowMargin ="1dp"
  arc:shadowBorderHeight="1dp"
  arc:shadowBorderWidth="-1dp"
  arc:shadowElevation="8dp"
```
* java usage
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setShadow(shadowElevation, shadowBorderHeight, shadowBorderWidth, shadowMargin);
```

###Set Shadow Colors
* xml usage
``` xml
  arc:shadowColor="#24bb27"
  arc:shadowAroundColor="#1c8e1f"
```
* java usage
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setHintShadowColor(shadowColor, shadowAroundColor);
```

###Set Corner Gravity in ArcMenu
* (Remember, if you want set attr programatically you must set the gravity at the end.)
* xml usage
``` xml
  arc:hintGravity="Bottom_Left" // set hint gravity to Bottom_Left corner and automatically adjusts the angle.
```
* java usage
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setHintGravity(ArcMenu.TOP_LEFT);
```

###Also you can enable and disable rotation animation is closing
* xml usage
``` xml
  arc:rotateInClosing="true"
```
* java usage
``` java
  ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
  arcMenu.setRotationInClosing(true);
```
###xml Sample
``` xml
 android:id="@+id/arc_menu_6"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_gravity="top|left"
 arc:childSize="48dp"
 arc:hintGravity="Top_Left"
 arc:hintNormalColor="#2a79d2"
 arc:hintPressColor="#1e5797"
 arc:hintUpperMarkColor="#cdcbcb"
 arc:rotateInClosing="true" 
 arc:hintTopImage = "@drawable/pen"
 arc:shadowMargin ="1dp"
 arc:shadowBorderHeight="-1dp"
 arc:shadowBorderWidth="-1dp"
 arc:shadowElevation="8dp"
```
----

##RayMenu
* 1-Hint direction issues fixed.
* 2-Shadow added to Hint button and can add specific color on main shadow and around shadow.
* 3-Plus marker animation changed
* 4-Can add text on Hint button
* 5-Can add specific image on Hint button
* 6-Can add specific color on Hint normal button and press color.
* 7-Attribute completly changed

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/YOUTUBE_VIDEO_ID_HERE/0.jpg)](https://www.youtube.com/watch?v=tzQ7qBW-bxg)
* ![Preview](http://s1.postimg.org/4099rtnv3/Screenshot_2015_09_21_13_11_04.png)  ![Preview](http://s1.postimg.org/41j7l8pov/Screenshot_2015_09_21_13_11_27.png)

###In Ray Menu all values are the same except Corner Gravity but still there are two new values.

###Set Ray Menu Direction
With this parameter you can set hint button gravity and child opening direction, for example Negativ_vertivar set hint button to bottom and child opening direction from down to up.
* xml usage
``` xml
  arc:MenuDirection="Negative_Vertical"  //set Hint button to bottom and child grow to up
```
* java usage
``` java
  RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
  rayMenu.setMenuDirection(RayMenu.NEGATIVE_DIRECTION_V);
```

###Set Holder Width
You can specify the first child position from the beginning when menu is open.
* xml usage
``` xml
  arc:holderWidth="64dp"
```
* java usage
``` java
  RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
  rayMenu.setHolderWidth(64);
```
###xml Sample
``` xml
 android:id="@+id/ray_menu_1"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_gravity="top|right"
 android:gravity="center"
 android:paddingLeft="5dp"
 android:paddingRight="5dp"
 arc:MenuDirection="1"
 arc:childSize="48dp"
 arc:hintNormalColor="#2a79d2"
 arc:hintPressColor="#1e5797"
 arc:hintUpperMarkColor="#cdcbcb"
 arc:rotateInClosing="true" 
 arc:hintTopImage = "@drawable/pen"
 arc:holderWidth="64dp"
 arc:shadowMargin ="1dp"
 arc:shadowBorderHeight="1dp"
 arc:shadowElevation="8dp"
```
----
##Author

**BrotherV**

I must sincerely thank [Capricon] (https://github.com/daCapricorn). Tell me about your dreams and if you have problem with this library just tell me.

[My email] (mailto:mohsen_hatami@ymail.com)
