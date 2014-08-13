package com.kubeiwu.commontool;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.kubeiwu.commontool.volley.cache.KRequestQueueManager;
import com.kubeiwu.commontool.volley.cache.core.DisplayImageOptions;
import com.kubeiwu.commontool.volley.request.KGsonArrayRequest;
import com.kubeiwu.commontool.volley.request.KGsonObjectRequest;
import com.kubeiwu.commontool.volley.request.MultiPartRequest;
import com.kubeiwu.commontool.volley.request.SimpleMultiPartRequest;
import com.kubeiwu.commontool.volley.request.params.MultipartRequestParams;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		ListView listView=new ListView(this);
		//		listView.setAdapter(new BaseAdapter() {
		//			@Override
		//			public View getView(int position, View convertView, ViewGroup parent) {
		//				return null;
		//			}
		//			
		//			@Override
		//			public long getItemId(int position) {
		//				return 0;
		//			}
		//			
		//			@Override
		//			public Object getItem(int position) {
		//				return null;
		//			}
		//			
		//			@Override
		//			public int getCount() {
		//				return 100;
		//			}
		//		});
		ImageView listView = new ImageView(this);
		KRequestQueueManager.getImageLoaderManager().displayImage("http://img.blog.csdn.net/20140413210233046", listView);
		KRequestQueueManager.getImageLoaderManager().displayImage(
				"http://b.hiphotos.baidu.com/image/w%3D230/sign=4b4bdca5bc096b63811959533c328733/5882b2b7d0a20cf470a0e55574094b36acaf9903.jpg",
				listView);
		KRequestQueueManager
				.getImageLoaderManager()
				.displayImage(
						"http://d.hiphotos.baidu.com/image/h%3D1050%3Bcrop%3D0%2C0%2C1680%2C1050/sign=e015c13334d12f2ed105aa607af2ee01/a1ec08fa513d2697c217a8b957fbb2fb4216d80a.jpg",
						listView);
		//		KVolleyManager
		//		.getImageLoaderManager()
		//		.displayImage(
		//				"http://img.blog.csdn.net/20140413205455484?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3VvbGluX2Jsb2c=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast",
		//				listView);
		for (String s : ss) {
			KRequestQueueManager.getImageLoaderManager().displayImage(s, listView,
					new DisplayImageOptions.Builder().setImageForEmptyUri(R.drawable.ic_launcher).build());
		}
		setContentView(listView);
		String url = "http://market.konkacloud.cn/client/recommend?type=4";
		KRequestQueueManager.getRequestQueue().add(new KGsonObjectRequest<Javabean>(url, null, listener, errorListener));
		KRequestQueueManager.getRequestQueue().add(new KGsonArrayRequest<Javabean>(url, null, gsonArraylistener, errorListener));
//		KRequestQueueManager.getRequestQueue().add(new SimpleMultiPartRequest(url, new Listener<String>() {
//			@Override
//			public void onResponse(String response) {
//				
//			}
//		}, null).addFile("ces", Environment.getExternalStorageDirectory().list()[0]) );
		MultipartRequestParams params=new MultipartRequestParams();
		params.put("file", new File(""));
		KRequestQueueManager.getRequestQueue().add(new SimpleMultiPartRequest(url, params));
	}

	Listener<Javabean> listener = new Listener<Javabean>() {
		@Override 
		public void onResponse(Javabean response) {
			System.out.println(response.getId());
			System.out.println(response.getName());
		}
	};
	Listener<List<Javabean>> gsonArraylistener = new Listener<List<Javabean>>() {
		@Override
		public void onResponse(List<Javabean> response) {
		}
	};
	ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println(error.getMessage());
		}
	};

	String[] ss = {
			"http://png-4.findicons.com/files/icons/983/cem/128/phone_icon_by_cemagraphics.png",//
			"http://png-5.findicons.com/files/icons/99/office/128/contact.png",//
			"http://png-2.findicons.com/files/icons/997/vista_style/128/home_phone.png",//
			"http://png-3.findicons.com/files/icons/2219/dot_pictograms/128/phone.png",//
			"http://png-5.findicons.com/files/icons/268/mobile_device/128/htc_trinity.png",
			"http://png-4.findicons.com/files/icons/102/openphone/128/notes.png",//
			"http://png-2.findicons.com/files/icons/102/openphone/128/chat.png" };
}
