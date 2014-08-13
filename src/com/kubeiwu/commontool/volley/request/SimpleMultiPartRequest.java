package com.kubeiwu.commontool.volley.request;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.kubeiwu.commontool.volley.request.params.MultipartRequestParams;

/**
 * A Simple request for making a Multi Part request whose response is retrieve as String
 * 
 * @param <T> Response expected
 */
public class SimpleMultiPartRequest extends MultiPartRequest<String> {

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(response));
		} catch (Exception je) {
			return Response.error(new ParseError(response));
		}
	}

	public SimpleMultiPartRequest(int method, String url, MultipartRequestParams params, ErrorListener errorListener, Listener<String> listener) {
		super(method, url, params, errorListener, listener);
	}

	public SimpleMultiPartRequest(String url, MultipartRequestParams params) {
		this(Method.POST, url, params, null, null);
	}

}