package house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import house.pkhouse.Constant.AllURLs;

public class LiveSupport extends AppCompatActivity {

    private WebView mWebView;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_support);

       // setupViews();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(LiveSupport.this ,R.color.colorRed)));
        getSupportActionBar().setTitle("Live Support");

//        Settings.canDrawOverlays(LiveSupport.this);


        //ZopimChat.init("4agByfaz7vV6U8vT6YlXi68KEvjt8FpM");

        startActivity(new Intent(getApplicationContext(), ZopimChatActivity.class));
        finish();



    }

    private void setupViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new Object() {

        }, "demo");

        mWebView.loadUrl(AllURLs.CHAT_URL);

    }

}//***************** Shoaib Anwar #  ********************
