package com.qfdqc.views.pulltoloadmoreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SimilarJDMyScrollView scrollView;
    private WebSettings ws;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] strs = new String[]{"first", "second", "third", "fourth", "fifthsecond", "third", "fourth", "fifth", "second", "third", "fourth", "fifth"};
        final Integer[] resArray = new Integer[] { R.drawable.a1,R.drawable.a1 };
        List<Integer>    xx= new ArrayList<Integer>();
        xx.add(resArray[0]);
        xx.add(resArray[1]);
        CtAutoListViewHigth listView = (CtAutoListViewHigth) findViewById(R.id.list_item);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));
        listView.setFocusable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "====" + position, 1).show();
            }
        });
        MyViewPager page = (MyViewPager) findViewById(R.id.home_pager);
        page.setImgList(xx);
        page.startRoll();
        String urlid="http://app.car-house.cn/app.php/Goods/detail/id/509";
        WebView web_view = (WebView) findViewById(R.id.web_view);
        initCtrls(web_view,urlid);
    }

    private void initCtrls(WebView webView,String url) {
//        webView.clearCache(true);
//        webView.clearHistory();
//        webView.clearFormData();
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.setInitialScale(25);//初始化的时候缩放问题
        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);


        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setBlockNetworkImage(false);

        //设置缩放功能   //能不能缩放 取决于网页设置
        webSettings.setDisplayZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //WebViewClient用来处理WebView各种通知、请求事件等,重写里面的方法即可  // 点击页面中的链接会调用这个方法
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // 跳转到另外的activity
//				Intent intent = new Intent(getApplication(),SecondActivity.class);
//				startActivity(intent);
//				return super.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });
        //主要处理解析，渲染网页等浏览器做的事情 WebChromeClienty用来处理WebView的Javascript的对话框、网站图标、网站title、加载进度等,重写里面的方法即可
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, final int progress) {
                if (progress == 100) {
//                    myProgressBar.setVisibility(View.GONE);
                } else {
//                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
////                        myProgressBar.setVisibility(View.VISIBLE);
//                    }
//                    myProgressBar.setProgress(progress);
                }
                super.onProgressChanged(view, progress);/*
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (progress > p)
							p = progress;
						if (!isFinished)
						if (p >= 100) {
							isFinished = true;
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
								}
							}, 430);
						} else {
							isFinished = false;
						}
					}
				});
			*/
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {//回调的时候显示数据  （e车保）
//				if (null != title){
//					tvTitle.setText(title);
//				}
            }
        });

        //这个处理 js
//        webView.addJavascriptInterface(new Object() {
//            @SuppressWarnings("unused")
//            public void loaded() {
//            }
//        }, "ecar.js");

//        if (!StringHelper.isNullOrEmpty(url)){//http://api.echebao.cn/app/html/safe.html   ?scid=s&lat=s&lng=s
//			//String[] items = SPHelper.GetValueByKey(this, new String[]{AppContentKey.SETTING_UID, AppContentKey.SETTING_LATITUDE, AppContentKey.SETTING_LONGITUDE});
//			//url += String.format("%scid=%s&lat=%s&lng=%s", url.indexOf("?") != -1 ? "&" : "?", items[0], items[1], items[2] );
            webView.loadUrl(url);
        }
}
