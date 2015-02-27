package org.dream.android_001;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity {
	private ViewPager viewPager;
	private ViewGroup viewGroup;
	/**
	 * 本地图片资源id Assets与res的区别 1、assets文件夹下文件不会被映射到R.java文件中,res文件夹下的文件会
	 * 2、assets可以有子目录,res不可以
	 */
	private int[] mImgIds = new int[] { R.drawable.guide_image1,
			R.drawable.guide_image2, R.drawable.guide_image3 };

	/**
	 * 用于存储ImageView列表，并用于销毁等一系列操作
	 */
	private List<ImageView> mImgs = new ArrayList<ImageView>();

	private List<ImageView> mDotImgs = new ArrayList<ImageView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeViewPager();
		initializeDots();
	}

	private void initializeViewPager() {
		viewPager = (ViewPager) findViewById(R.id.id_viewPager);
		for (int i = 0; i < mImgIds.length; i++) {
			ImageView imageView = new ImageView(MainActivity.this);
			imageView.setImageResource(mImgIds[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setBackgroundResource(R.drawable.dot_focus);
			mImgs.add(imageView);
		}

		viewPager.setAdapter(new PagerAdapter() {
			// 实例化Item
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				Log.d("output", "add***:" + (position % mImgIds.length));
				container.addView(mImgs.get(position % mImgIds.length), 0);
				return mImgs.get(position % mImgIds.length);
			};

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				Log.d("output", "remove###:" + (position % mImgIds.length));
				container.removeView(mImgs.get(position % mImgIds.length));
			};

			@Override
			public boolean isViewFromObject(View view, Object obj) {
				return view == obj;
			}

			@Override
			public int getCount() {
				// return mImgIds.length;
				return Integer.MAX_VALUE;
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 > 2) {
					arg0 = arg0 % mImgIds.length;
				}
				for (int i = 0; i < mImgIds.length; i++) {
					mDotImgs.get(i).setBackgroundResource(
							R.drawable.dot_unfocus);

					if (arg0 == i) {
						mDotImgs.get(i).setBackgroundResource(
								R.drawable.dot_focus);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}

	private void initializeDots() {
		viewGroup = (ViewGroup) findViewById(R.id.viewGroup);
		for (int i = 0; i < mImgIds.length; i++) {
			ImageView imageView = new ImageView(MainActivity.this);
			imageView.setLayoutParams(new LayoutParams(6, 6));
			if (i == 0) {
				imageView.setBackgroundResource(R.drawable.dot_focus);
			} else {
				imageView.setBackgroundResource(R.drawable.dot_unfocus);
			}// end if

			mDotImgs.add(imageView);
			viewGroup.addView(imageView);
		}// end for
	}
}
