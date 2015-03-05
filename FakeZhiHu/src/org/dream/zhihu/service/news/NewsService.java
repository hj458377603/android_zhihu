package org.dream.zhihu.service.news;

import org.dream.zhihu.bean.news.LastedNewsResult;
import org.dream.zhihu.bean.news.StoryDetail;
import org.dream.zhihu.service.common.BaseService;
import org.dream.zhihu.service.common.config.ServiceConfig;
import org.dream.zhihu.service.common.http.GsonRequest;
import org.dream.zhihu.utils.IRequestCallback;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.google.gson.reflect.TypeToken;

public class NewsService extends BaseService {
	/**
	 * 获取最新的资讯列表
	 * 
	 * @param requestCallback
	 * @param queue
	 * @return
	 */
	public void getLastedNewsResult(
			IRequestCallback<LastedNewsResult> requestCallback,
			RequestQueue queue) {
		String url = ServiceConfig.LASTED_NEWS_URL;
		TypeToken<LastedNewsResult> typeToken = new TypeToken<LastedNewsResult>() {
		};

		GsonRequest<LastedNewsResult> gsonRequest = new GsonRequest<LastedNewsResult>(
				Method.GET, url, requestCallback,
				initErrorListener(requestCallback), typeToken);
		queue.add(gsonRequest);
	}

	/**
	 * 获取更早的资讯
	 * 
	 * @param date
	 * @param requestCallback
	 * @param queue
	 */
	public void getBeforeNewsResult(String date,
			IRequestCallback<LastedNewsResult> requestCallback,
			RequestQueue queue) {
		String url = String.format(ServiceConfig.BEFORE_NEWS_URL, date);
		TypeToken<LastedNewsResult> typeToken = new TypeToken<LastedNewsResult>() {
		};

		GsonRequest<LastedNewsResult> gsonRequest = new GsonRequest<LastedNewsResult>(
				Method.GET, url, requestCallback,
				initErrorListener(requestCallback), typeToken);
		queue.add(gsonRequest);
	}

	/**
	 * 获取资讯详情
	 * @param id
	 * @param requestCallback
	 * @param queue
	 */
	public void getNewsDetail(long id,
			IRequestCallback<StoryDetail> requestCallback, RequestQueue queue) {
		String url = String.format(ServiceConfig.NEWS_DETAIL_URL, id);
		TypeToken<StoryDetail> typeToken = new TypeToken<StoryDetail>() {
		};

		GsonRequest<StoryDetail> gsonRequest = new GsonRequest<StoryDetail>(
				Method.GET, url, requestCallback,
				initErrorListener(requestCallback), typeToken);
		queue.add(gsonRequest);
	}
}
