package house.pkhouse;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import house.pkhouse.Constant.AllURLs;

public class YoutubeWebView extends AppCompatActivity {

    WebView youtube_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_web_view);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(YoutubeWebView.this ,R.color.colorRed)));

        //youtube_webview = (WebView) findViewById(R.id.youtube_webview);





        final ProgressDialog pd = ProgressDialog.show(YoutubeWebView.this, "", "Please Wait...", true);

        //src=https://www.youtube.com/embed/DL2XC6Rj6To?ecver=1
        String frameVideo = "<html><body><br> <iframe width=\"350\" height=\"400\" src=https://www.youtube.com/embed/zXzBb-1mc6k?ecver=1&autoplay=1&loop=1&playlist=DL2XC6Rj6To frameborder=\"0\" allowfullscreen></iframe></body></html>";

        final WebView displayVideo = (WebView)findViewById(R.id.youtube_webview);
        displayVideo.setWebViewClient(new WebViewClient(){
           /* @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }*/
        });
        WebSettings webSettings = displayVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setLoadWithOverviewMode(true);
        //webSettings.setUseWideViewPort(true);
       // webSettings.setBuiltInZoomControls(true);

        displayVideo.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = displayVideo.getUrl();



        }

        });

        displayVideo.loadData(frameVideo, "text/html", "utf-8");





        //setupViews();

    }

    private void setupViews() {


        WebSettings mWebSettings = youtube_webview.getSettings();

        mWebSettings.setJavaScriptEnabled(true);


        youtube_webview.addJavascriptInterface(new Object() {

        }, "demo");

        youtube_webview.loadUrl("https://www.m.youtube.com/watch?v=QqTWlZzZOG4");

    }


}
