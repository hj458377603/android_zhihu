package org.dream.zhihu.utils;

import com.android.volley.VolleyError;

public interface IRequestCallback<T> {
	void onSuccess(T t);
	void onFail(VolleyError error);
}
