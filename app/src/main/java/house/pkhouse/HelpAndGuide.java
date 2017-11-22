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

public class HelpAndGuide extends AppCompatActivity {

    WebView youtube_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_guide);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(HelpAndGuide.this ,R.color.colorRed)));


        final ProgressDialog pd = ProgressDialog.show(HelpAndGuide.this, "", "Please Wait...", true);

        String frameVideo = "<html><body><br> <iframe width=\"350\" height=\"400\" src=\"https://www.youtube.com/embed/FJ8Gmqq8-2Y\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        final WebView displayVideo = (WebView)findViewById(R.id.webview);
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
