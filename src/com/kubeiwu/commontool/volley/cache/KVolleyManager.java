//2014-8-8
package com.kubeiwu.commontool.volley.cache;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author  cgpllx1@qq.com (www.kubeiwu.com)
 * @date    2014-8-8
 */
public class KVolleyManager {
	private static RequestQueue mQueue = null;
	private static KVolleyManager mKVolleyManager;
	private static ImageLoaderManager mImageLoaderManager;

	private KVolleyManager() {
		//not use 
	}

	public static KVolleyManager getInstance() {
		if (mKVolleyManager == null) {
			mKVolleyManager = new KVolleyManager();
		}
		return mKVolleyManager;
	}

	public void init(Context context) {
		mQueue = Volley.newRequestQueue(context);
		mImageLoaderManager = new ImageLoaderManager(context);
	}

	public static RequestQueue getRequestQueue() {
		if (mQueue == null) {
			throw new IllegalArgumentException("You must initialize KVolleyManager");
		}
		return mQueue;
	}

	public static ImageLoaderManager getImageLoaderManager() {
		if (mImageLoaderManager == null) {
			throw new IllegalArgumentException("You must initialize KVolleyManager");
		}
		return mImageLoaderManager;
	}
}
