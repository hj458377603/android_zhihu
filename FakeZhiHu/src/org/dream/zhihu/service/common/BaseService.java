package org.dream.zhihu.service.common;

import org.dream.zhihu.utils.IRequestCallback;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public class BaseService {
	public <T> ErrorListener initErrorListener(final IRequestCallback<T> requestCallback){
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (requestCallback != null) {
					requestCallback.onFail(error);
				}
			}
		};
	}
}
