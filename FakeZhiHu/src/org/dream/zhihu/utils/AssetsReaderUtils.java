package org.dream.zhihu.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;

public class AssetsReaderUtils {
	public static String getFromAssets(Activity activity, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(activity
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
