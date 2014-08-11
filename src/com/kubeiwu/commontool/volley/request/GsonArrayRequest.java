package com.kubeiwu.commontool.volley.request;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class GsonArrayRequest<T> extends Request<List<T>> {
	private final Gson gson = new Gson();
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Map<String, String> params;
	private final Listener<List<T>> listener;

	/**
	 * Make a GET request and return a parsed object from JSON.
	 *
	 * @param url URL of the request to make
	 * @param clazz Relevant class object, for Gson's reflection
	 * @param headers Map of request headers
	 */
	public GsonArrayRequest(String url, Class<T> clazz, Map<String, String> headers, Listener<List<T>> listener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.params = null;
		this.listener = listener;
	}

	/**
	 * Make a request and return a parsed object from JSON.
	 *
	 * @param url URL of the request to make
	 * @param clazz Relevant class object, for Gson's reflection
	 * @param headers Map of request headers
	 */
	public GsonArrayRequest(int type, String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
			Listener<List<T>> listener, ErrorListener errorListener) {
		super(type, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.params = params;
		this.listener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params != null ? params : super.getParams();
	}

	@Override
	protected void deliverResponse(List<T> response) {
		if (null != listener) {
			listener.onResponse(response);
		}
	}

	@Override
	protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
		List<T> list = new ArrayList<T>();
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			list = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
			return Response.success(list, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	//	@Override
	//	protected Response<T> parseNetworkResponse(NetworkResponse response) {
	//		try {
	//			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	//			Type t = new TypeToken<List<T>>() {
	//			}.getType();
	//			return Response.success(gson.fromJson(json, t), HttpHeaderParser.parseCacheHeaders(response));
	//		} catch (UnsupportedEncodingException e) {
	//			return Response.error(new ParseError(e));
	//		} catch (JsonSyntaxException e) {
	//			return Response.error(new ParseError(e));
	//		}
	//	}

}