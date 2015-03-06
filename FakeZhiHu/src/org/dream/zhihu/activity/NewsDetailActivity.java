package org.dream.zhihu.activity;

import java.util.ArrayList;

import org.dream.zhihu.R;
import org.dream.zhihu.bean.news.Story;
import org.dream.zhihu.bean.news.StoryDetail;
import org.dream.zhihu.service.news.NewsService;
import org.dream.zhihu.utils.AssetsReaderUtils;
import org.dream.zhihu.utils.IRequestCallback;
import org.dream.zhihu.utils.ImageViewUtils;
import org.dream.zhihu.utils.VolleyApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

@SuppressLint({ "SetJavaScriptEnabled" })
public class NewsDetailActivity extends Activity {
	private NewsService newsService = new NewsService();
	private WebView webView;
	private Story story;
	private NetworkImageView coverImageView;
	private TextView textView;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		story = (Story) bundle.get("story");

		webView = (WebView) findViewById(R.id.webview_news_detail);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new MyWebViewClient());

		coverImageView = (NetworkImageView) findViewById(R.id.coverImage);
		textView = (TextView) findViewById(R.id.title);

		getNewsDetail(story.id);
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	/**
	 * 获取资讯详情
	 * 
	 * @param id
	 */
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
					htmlData = htmlData.replace(
							"<div class=\"img-place-holder\"></div>", "");
					coverImageView.setImageUrl(t.image, VolleyApplication
							.getInstance().getImageLoader());
					textView.setText(t.title);
					webView.loadData(htmlData, "text/html", "utf-8");
				}
			}

			@Override
			public void onFail(VolleyError error) {
				Toast.makeText(NewsDetailActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}, VolleyApplication.getInstance().getRequestQueue());
	}

	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
		webView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "var images='';"
				+ "for(var i=0;i<objs.length;i++)  "
				+ "{"
				+ "	   images=images+objs[i].src+',';"
				+ "}"
				+ "images=images.substring(0,images.length-1);"
				+ "for(var j=0;j<objs.length;j++)  "
				+ "{"
				+ "    objs[j].onclick=function()  "
				+ "    {  "
				+ "         window.location.href='openimg:'+this.src+','+images;  "
				+ "    }  " + "}" + "})()");
	}

	// js通信接口
	public class JsInterface {

		private Context context;

		public JsInterface(Context context) {
			this.context = context;
		}

		public void openImage(ArrayList<String> images, int position) {
			ImageViewUtils.imageBrower(position, images, context,
					ImagePagerActivity.class);
		}
	}

	// 监听
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			Log.d("output", "url:" + url);
			if (url != null && url.startsWith("openimg:")) {
				JsInterface jsInterface = new JsInterface(
						NewsDetailActivity.this);
				url = url.replace("openimg:", "");
				String[] imageParamsStrings = url.split(",");

				int position = 1;
				ArrayList<String> images = new ArrayList<String>();
				if (imageParamsStrings.length > 1) {
					for (int i = 2; i < imageParamsStrings.length; i++) {
						images.add(imageParamsStrings[i]);

						// 为何js中j计数无效，js调用java无效，改用配置的图片地址格式，以达到效果
						if (imageParamsStrings[0].equals(imageParamsStrings[i])) {
							position = i - 2;
						}
					}
				}
				jsInterface.openImage(images, position);
				return true;
			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			// html加载完成之后，添加监听图片的点击js函数
			addImageClickListner();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {

			super.onReceivedError(view, errorCode, description, failingUrl);

		}
	}
}
