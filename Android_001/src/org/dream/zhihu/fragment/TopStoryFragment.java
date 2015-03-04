package org.dream.zhihu.fragment;

import java.util.ArrayList;
import java.util.List;

import org.dream.android_001.R;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class TopStoryFragment extends Fragment {
	private static final String LOGTAG = "output";
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_topstory, container,
				false);
		initializeViewPager(view);
		initializeDots(view);
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void initializeViewPager(View view) {
		viewPager = (ViewPager) view.findViewById(R.id.id_viewPager);
		for (int i = 0; i < mImgIds.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(mImgIds[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setBackgroundResource(R.drawable.dot_focus);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity(), "clicked!",
							Toast.LENGTH_SHORT).show();
				}
			});
			mImgs.add(imageView);
		}
		viewPager.setFocusable(true);
		viewPager.setAdapter(new PagerAdapter() {
			// 实例化Item
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				Log.d(LOGTAG, "add***:" + position);
				container.addView(mImgs.get(position));
				return mImgs.get(position);
			};

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				Log.d(LOGTAG, "remove###:" + position);
				container.removeView(mImgs.get(position));
			};

			@Override
			public boolean isViewFromObject(View view, Object obj) {
				return view == obj;
			}

			@Override
			public int getCount() {
				return mImgIds.length;
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
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

	private void initializeDots(View view) {
		viewGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
		for (int i = 0; i < mImgIds.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			LayoutParams lp = new LayoutParams(6, 6);
			lp.setMargins(3, 0, 3, 0);
			imageView.setLayoutParams(lp);
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
