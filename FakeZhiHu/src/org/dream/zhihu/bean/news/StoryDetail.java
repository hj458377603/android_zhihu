package org.dream.zhihu.bean.news;

import java.util.List;

import org.dream.zhihu.bean.recommender.Recommender;

public class StoryDetail extends BaseStory {
	private static final long serialVersionUID = -7608115702790333253L;
	public String body;
	public List<String> css;
	public String image;
	public String image_source;
	public List<String> js;
	public String share_url;
	public List<Recommender> recommenders;
}
