package org.dream.zhihu.utils;

import java.io.File;

import org.dream.zhihu.R;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class VolleyApplication extends Application {
	private RequestQueue mRequestQueue;
	private static VolleyApplication instance;
	private ImageLoader mImageLoader;

	public static VolleyApplication getInstance() {
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
				.showImageForEmptyUri(R.drawable.ic_launcher) //
				.showImageOnFail(R.drawable.ic_launcher) //
				.cacheInMemory(true) //
				.cacheOnDisk(true) //
				.build();//
		ImageLoaderConfiguration config = new ImageLoaderConfiguration//
		.Builder(getApplicationContext())//
				.defaultDisplayImageOptions(defaultOptions)//
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)// ����һ����ͼƬ
				.writeDebugLogs()//
				.build();//
		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(
				config);
		// ��ʼ���ڴ滺��Ŀ¼
		File cacheDir = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"volleyCache");
		/**
		 * ��ʼ��RequestQueue,��ʵ���������ʹ��Volley.newRequestQueue������һ��RequestQueue,
		 * ֱ��ʹ�ù��캯�����Զ���������Ҫ��RequestQueue,�����̳߳صĴ�С�ȵ�
		 */
		mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir),
				new BasicNetwork(new HurlStack()), 3);

		instance = this;

		// ��ʼ��ͼƬ�ڴ滺��
		MemoryCache mCache = new MemoryCache();
		// ��ʼ��ImageLoader
		mImageLoader = new ImageLoader(mRequestQueue, mCache);
		// �������Volley.newRequestQueue,��ô���������Բ��õ���
		mRequestQueue.start();
	}
}
