package com.kubeiwu.commontool.volley.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.kubeiwu.commontool.volley.request.params.MultipartRequestParams;

/**
 * A request for making a Multi Part request
 * @param <T>
 * 
 * @param <T> Response expected
 */
public abstract class MultiPartRequest<T> extends Request<T> {

	private ErrorListener errorListener = null;
	private Listener<T> listener = null;
	private MultipartRequestParams params = null;
	private HttpEntity httpEntity = null;

	public MultiPartRequest(int method, String url, MultipartRequestParams params, ErrorListener errorListener, Listener<T> listener) {
		super(method, url, errorListener);
		this.params = params;
		this.errorListener = errorListener;
		this.listener = listener;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (params != null) {
			httpEntity = params.getEntity();
			try {
				httpEntity.writeTo(baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();
		if (null == headers || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	@Override
	public String getBodyContentType() {
		String str = httpEntity.getContentType().getValue();
		return str;
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	public void deliverError(VolleyError error) {
		if (errorListener != null) {
			errorListener.onErrorResponse(error);
		}
	}
}
