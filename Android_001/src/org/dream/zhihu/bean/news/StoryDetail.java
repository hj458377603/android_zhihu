package org.dream.zhihu.bean.news;

import java.util.List;

import org.dream.zhihu.bean.recommender.Recommender;

public class StoryDetail extends BaseStory {
	public String body;
	public List<String> css;
	public String image;
	public String image_source;
	public List<String> js;
	public String share_url;
	public List<Recommender> recommenders;
}
