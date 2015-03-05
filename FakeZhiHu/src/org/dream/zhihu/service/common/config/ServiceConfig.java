package org.dream.zhihu.service.common.config;

public class ServiceConfig {
	/**
	 * 最新资讯
	 */
	public static final String LASTED_NEWS_URL="http://news-at.zhihu.com/api/4/stories/latest";
	
	/**
	 * 更早资讯
	 */
	public static final String BEFORE_NEWS_URL="http://news-at.zhihu.com/api/4/stories/before/%s";

	/**
	 * 资讯详情
	 */
	public static final String NEWS_DETAIL_URL="http://news-at.zhihu.com/api/4/story/%s";
}
