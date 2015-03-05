package org.dream.zhihu.activity;

import java.util.List;

import org.dream.zhihu.R;
import org.dream.zhihu.adapter.NewsListAdapter;
import org.dream.zhihu.bean.news.LastedNewsResult;
import org.dream.zhihu.bean.news.Story;
import org.dream.zhihu.service.news.NewsService;
import org.dream.zhihu.utils.IRequestCallback;
import org.dream.zhihu.utils.VolleyApplication;
import org.dream.zhihu.view.LoadListView;
import org.dream.zhihu.view.LoadListView.ILoadListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.VolleyError;

public class MainActivity extends Activity {
	private NewsService newsService = new NewsService();
	private NewsListAdapter adapter = null;
	private LoadListView listView;
	private String date;
	private List<Story> newsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (LoadListView) findViewById(R.id.newsList);
		listView.setLoadMoreListener(new ILoadListener() {
			@Override
			public void onLoad() {
				getBeforeNewsList();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int postion, long id) {
				Story story = adapter.getStory(postion);
				Intent intent = new Intent();
				intent.putExtra("story", story);
				intent.setClass(MainActivity.this, NewsDetailActivity.class);
				startActivity(intent);
			}
		});

		getLastedNewsList();
	}

	/**
	 * 获取最新资讯列表
	 */
	private void getLastedNewsList() {
		newsService.getLastedNewsResult(
				new IRequestCallback<LastedNewsResult>() {

					@Override
					public void onSuccess(LastedNewsResult t) {
						if (t != null) {
							date = t.date;
							newsList = t.stories;
							adapter = new NewsListAdapter(MainActivity.this,
									newsList, listView);
							listView.setAdapter(adapter);
						}
						listView.loadComplete();
					}

					@Override
					public void onFail(VolleyError error) {
						Toast.makeText(MainActivity.this, error.getMessage(),
								Toast.LENGTH_SHORT).show();
						listView.loadComplete();
					}
				}, VolleyApplication.getInstance().getRequestQueue());

	}

	/**
	 * 获取更早的资讯列表
	 */
	private void getBeforeNewsList() {
		newsService.getBeforeNewsResult(date,
				new IRequestCallback<LastedNewsResult>() {

					@Override
					public void onSuccess(LastedNewsResult t) {
						if (t != null) {
							date = t.date;
							if (newsList != null) {
								newsList.addAll(t.stories);
								adapter.notifyDataSetChanged();
							}
							listView.loadComplete();
						}
					}

					@Override
					public void onFail(VolleyError error) {
						Toast.makeText(MainActivity.this, error.getMessage(),
								Toast.LENGTH_LONG).show();
						listView.loadComplete();
					}
				}, VolleyApplication.getInstance().getRequestQueue());
	}
}
