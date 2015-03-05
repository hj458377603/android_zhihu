package org.dream.zhihu.adapter;

import java.util.List;

import org.dream.zhihu.R;
import org.dream.zhihu.bean.news.Story;
import org.dream.zhihu.utils.VolleyApplication;
import org.dream.zhihu.view.LoadListView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsListAdapter extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	private List<Story> data;

	public NewsListAdapter(Activity a, List<Story> d, LoadListView listView) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public List<Story> getData() {
		return data;
	}

	public Story getStory(int position) {
		if (data != null && data.size() > position) {
			return data.get(position);
		}
		return null;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.news_item, null);

		TextView title = (TextView) vi.findViewById(R.id.newsTitle); // ±êÌâ

		final NetworkImageView imageView = (NetworkImageView) vi
				.findViewById(R.id.newsImage); // ËõÂÔÍ¼

		Story story = data.get(position);
		title.setText(story.title);
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		imageView.setDefaultImageResId(R.drawable.downloading);
		imageView.setErrorImageResId(R.drawable.ic_launcher);
		if (story != null && story.images != null && story.images.size() > 0) {
			imageView.setImageUrl(story.images.get(0), imageLoader);
		}
		return vi;
	}

}
