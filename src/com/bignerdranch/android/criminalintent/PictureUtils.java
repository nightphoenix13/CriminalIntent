package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.*;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

public class PictureUtils
{ // class start
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getScaledDrawable(Activity a, String path) // getScaledDrawable method start
	{
		Display display = a.getWindowManager().getDefaultDisplay();
		float destWidth = display.getWidth();
		float destHeight = display.getHeight();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		
		int inSampleSize = 1;
		if (srcHeight > destHeight || srcWidth > destWidth)
		{
			if (srcWidth > srcHeight)
			{
				inSampleSize = Math.round(srcHeight / destHeight);
			} // end if
			else
			{
				inSampleSize = Math.round(srcWidth / destWidth);
			} // end else
		} // end if
		
		options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return new BitmapDrawable(a.getResources(), bitmap);
	} // getScaledDrawable method end
	
	public static void cleanImageView(ImageView imageView) // cleanImageView method start
	{
		if (!(imageView.getDrawable() instanceof BitmapDrawable))
		{
			return;
		} // end if
		
		BitmapDrawable b = (BitmapDrawable)imageView.getDrawable();
		b.getBitmap().recycle();
		imageView.setImageDrawable(null);
	} // cleanImageView method end
} // class end
