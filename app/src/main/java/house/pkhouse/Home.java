package house.pkhouse;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import house.pkhouse.Constant.AllURLs;

public class Home extends BaseActvitvityForDrawer {

    Button btFindYourDreamHome, btSubmitYourProperty, btPropertyForWanted;
    ImageView imgFbIcon, imgTwIcon, imgGpIcon;
    private WebView mWebView;
    RelativeLayout rl_chat_icon;
    RelativeLayout test;
    ImageView image_cross, im_image;

    private static int SplashScreenTimeOut = 3000;//3 seconds8
    private int timer = 3;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();
        buttosClick();
        socialClick();
       //setupViews();
        chatIconClick();
        onPressHanlderForFindYourDreamHome();
        onPressHandleForPropertyFroWanted();
        onPressHandleForSubmitProperty();


        test = (RelativeLayout) findViewById(R.id.test);

        onCrossClick();
        openYoutube();
        mHandler = new Handler();
        useHandler();

    }

    private void buildDialog() {
        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_bottom);

        if (test.getVisibility() == View.VISIBLE) {
            test.startAnimation(slideUp);
        }
    }





    public void init(){

        btFindYourDreamHome = (Button) findViewById(R.id.bt_find_your_dream_home);
        btSubmitYourProperty = (Button) findViewById(R.id.bt_submit_your_prooperty);
        btPropertyForWanted = (Button) findViewById(R.id.bt_property_for_wanted);

        mWebView = (WebView) findViewById(R.id.webview);

        imgFbIcon = (ImageView) findViewById(R.id.fb_icon);
        imgTwIcon = (ImageView) findViewById(R.id.tw_icon);
        imgGpIcon = (ImageView) findViewById(R.id.gp_icon);
        image_cross = (ImageView) findViewById(R.id.im_cross);
        im_image = (ImageView) findViewById(R.id.im_image);
        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);
    }



    public void onCrossClick(){

        image_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_bottom);

                if (test.getVisibility() == View.VISIBLE) {
                    test.startAnimation(slideDown);
                    test.setVisibility(View.GONE);
                }

            }
        });
    }

    public void buttosClick(){

        btSubmitYourProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitProperty = new Intent(Home.this, SubmitProperty.class);
                startActivity(submitProperty);
            }
        });//end of submit property button


        btFindYourDreamHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findDreamHome = new Intent(Home.this, FindYourDreamProperties.class);
                startActivity(findDreamHome);
            }
        });//end of find property button


        btPropertyForWanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent wantedProperties = new Intent(Home.this, WantedProperties.class);
                startActivity(wantedProperties);

            }
        });//end of wanted properties click



    }


    public void socialClick(){
        fbIconClick();
        twitterIconClick();
        gPlusIconClick();

    }

    public void fbIconClick(){

        imgFbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getOpenFacebookIntent(Home.this);
                startActivity(getOpenFacebookIntent(Home.this));
               // Toast.makeText(Home.this, "Message", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void twitterIconClick(){

        imgTwIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(Home.this, "comming soon", Toast.LENGTH_SHORT).show();

                startActivity(getOpenTwitterIntent(Home.this, "PkHouse2"));

            }
        });
    }



    public void gPlusIconClick(){

        imgGpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/116215910530174649705")));

            }
        });
    }


    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/698090883696269/"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/Pkhouse"));
        }
    }

    public static Intent getOpenTwitterIntent(Context c, String Username) {

        try {
            c.getPackageManager().getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+ Username));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + Username));
        }
    }
/*

    private void setupViews() {

    */
/*    RelativeLayout startChat = (RelativeLayout) findViewById(R.id.bt_start_chat);
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start = new Intent(Home.this, LiveSupport.class);
                startActivity(start);
            }
        });
*//*

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setBackgroundColor(Color.TRANSPARENT);

        Log.e("TAG", "The Url is: " + mWebView.getUrl());
        WebSettings mWebSettings = mWebView.getSettings();

        //YXWebViewClient yxWebViewClient = new YXWebViewClient();
        //mWebView.setWebViewClient(yxWebViewClient);

        mWebSettings.setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new WebAppInterface(this) {

            @JavascriptInterface           // For API 17+
            public void performClick(String strl)
            {

                Toast.makeText (Home.this, "Button Clicked", Toast.LENGTH_SHORT).show();
            }


        }, "ok");

        mWebView.loadUrl("file:///android_asset/chatscript.html");

    }

*/

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }


   /* public class YXWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.i("Listener", "Start");

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("Listener", "Finish");
            Log.i("Listener", "Finish" + view.getUrl());

        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            Log.i("Listener", "Finish" + view.getUrl());
            return super.shouldOverrideKeyEvent(view, event);

        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            Log.i("Listener", "Finish 2343" + view.getUrl());
        }

    }//end of YXWebViewclient


*/



    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }

    private void setupViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new Object() {

        }, "demo");

        mWebView.loadUrl(AllURLs.CHAT_URL);

    }


    //Thread for starting mainActivity
    private Runnable mRunnableStartMainActivity = new Runnable() {
        @Override
        public void run() {
            Log.d("Handler", " Calls");
            timer--;
            mHandler = new Handler();
            mHandler.postDelayed(this, 1000);


            if (timer == 0) {

                test.setVisibility(View.VISIBLE);
                buildDialog();


            }
        }
    };


    //handler for the starign activity
    Handler newHandler;
    public void useHandler(){

        newHandler = new Handler();
        newHandler.postDelayed(mRunnableStartMainActivity, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableStartMainActivity);
    }

    public void openYoutube(){

        im_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Home.this, YoutubeWebView.class);
                startActivity(i);
            }
        });
    }

    public void onPressHanlderForFindYourDreamHome(){

        btFindYourDreamHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    btFindYourDreamHome.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroud_button_press_state));
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    btFindYourDreamHome.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroud_button));

                    Intent findDreamHome = new Intent(Home.this, FindYourDreamProperties.class);
                    startActivity(findDreamHome);
                }
                return true;
            }
        });
    }//end of find your dream homn press

    public void onPressHandleForPropertyFroWanted(){

        btPropertyForWanted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    btPropertyForWanted.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroud_button_press_state));
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    btPropertyForWanted.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroud_button));

                    Intent findDreamHome = new Intent(Home.this, FindYourDreamProperties.class);
                    startActivity(findDreamHome);
                }

                return true;
            }
        });
    }//end of propertry for wanted button

    public void onPressHandleForSubmitProperty(){

        btSubmitYourProperty.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    btSubmitYourProperty.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroud_button_press_state));



                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    btSubmitYourProperty.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroud_button));


                    Intent submitProperty = new Intent(Home.this, SubmitProperty.class);
                    startActivity(submitProperty);
                }

                return true;
            }
        });
    }
}
