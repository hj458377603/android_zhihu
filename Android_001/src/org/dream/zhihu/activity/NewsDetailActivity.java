package org.dream.zhihu.activity;

import org.dream.android_001.R;
import org.dream.zhihu.bean.news.StoryDetail;
import org.dream.zhihu.service.news.NewsService;
import org.dream.zhihu.utils.AssetsReaderUtils;
import org.dream.zhihu.utils.IRequestCallback;
import org.dream.zhihu.utils.VolleyApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.VolleyError;

public class NewsDetailActivity extends Activity {
	private NewsService newsService = new NewsService();
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);

		webView = (WebView) findViewById(R.id.webview_news_detail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		long id = bundle.getLong("id");
		Toast.makeText(NewsDetailActivity.this, id + "", Toast.LENGTH_SHORT)
				.show();
		getNewsDetail(id);
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {

		return super.onCreateView(name, context, attrs);
	}

	private void getNewsDetail(long id) {
		newsService.getNewsDetail(id, new IRequestCallback<StoryDetail>() {

			@Override
			public void onSuccess(StoryDetail t) {
				// 重构，只需要读取一次
				if (t != null) {
					String newsDetailTemplateString = AssetsReaderUtils
							.getFromAssets(NewsDetailActivity.this,
									"newsdetail_template.html");
					String htmlData = newsDetailTemplateString.replace(
							"__BODY_PLACEHOLDER__", t.body);
					// 重构，多个css,js
					if (t.css != null && t.css.size() > 0) {
						htmlData = htmlData.replace("__CSS_PLACEHOLDER__",
								t.css.get(0));
					}
					webView.loadData(htmlData, "text/html", "utf-8");
					// Log.d("output", newsDetailTemplateString);
				}
			}

			@Override
			public void onFail(VolleyError error) {
				Toast.makeText(NewsDetailActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}, VolleyApplication.getInstance().getRequestQueue());
	}
}
