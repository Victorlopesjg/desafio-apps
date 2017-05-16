package br.com.victor.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class ImageUtils {

	public static Drawable getImage(int id, Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return context.getResources().getDrawable(id, context.getTheme());
		} else {
			return context.getResources().getDrawable(id);
		}
	}
	
}