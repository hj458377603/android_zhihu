package org.dream.zhihu.utils;

import java.util.ArrayList;

import org.dream.zhihu.common.Constants;

import android.content.Context;
import android.content.Intent;

public class ImageViewUtils {

	/**
	 * ´ò¿ªÍ¼Æ¬ä¯ÀÀÆ÷
	 * 
	 * @param position
	 * @param imageUrls
	 * @param context
	 * @param as
	 */
	public static void imageBrower(int position, ArrayList<String> imageUrls,
			Context context, Class<?> as) {
		Intent intent = new Intent(context, as);
		intent.putExtra(Constants.IMAGE_URLS, imageUrls);
		intent.putExtra(Constants.IMAGE_INDEX, position);
		context.startActivity(intent);
	}
}
