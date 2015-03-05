package org.dream.zhihu.bean.news;

import java.io.Serializable;

public class BaseStory implements Serializable{
	private static final long serialVersionUID = 4399172781061516054L;

	// 030314->03ÔÂ03ÈÕ14Ê±
	public String ga_prefix;

	public long id;

	public String title;

	public int type;
}
