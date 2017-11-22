package house.pkhouse;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Collections;
import java.util.List;

import house.pkhouse.Constant.AllURLs;


public class  BaseActvitvityForDrawer extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    MenuItem navLoginRegister;
    MenuItem navUsername;
   // MenuItem navChosePlane;
   // MenuItem navFranchiser;
    MenuItem navContactUs;
    MenuItem navViewYourProperties;
    MenuItem navBuyerActvity;
    MenuItem navShowFranchiserList;
    MenuItem navTermsAndCondtion;
    MenuItem navShareApp;
    MenuItem navLiveSupport;
    MenuItem navHelp;
    MenuItem navChatRoom;

    AppAdapter adapterSharing = null;
    ListView sharingListView;
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    private WebView mWebView;

    Dialog mDialog;


    //private final String appLink = "http://onelink.to/jbzc2j";
    private final String appLink = "https://play.google.com/store/apps/details?id=house.pkhouse";


    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_base_actvity_for_drawer);
        view = new View(this);


        /**
         *Setup the DrawerLayout and NavigationView
         */



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        mNavigationView.setItemIconTintList(null);

        // get menu from navigationView
        Menu menu = mNavigationView.getMenu();

        // find MenuItem you want to change
        navUsername = menu.findItem(R.id.nav_item_name);
        navLoginRegister = menu.findItem(R.id.nav_item_login_registe);
        //navFranchiser = menu.findItem(R.id.nav_item_franchise);
        navContactUs = menu.findItem(R.id.nav_item_contact_us);
        navViewYourProperties = menu.findItem(R.id.nav_item_view_your_properties);
        navBuyerActvity = menu.findItem(R.id.nav_item_buyer);
        navShowFranchiserList = menu.findItem(R.id.nav_item_show_franchiser);
        navTermsAndCondtion = menu.findItem(R.id.nav_item_termscondition);
        navShareApp = menu.findItem(R.id.nav_item_share_app);
        navLiveSupport = menu.findItem(R.id.nav_item_live_support);
        navHelp = menu.findItem(R.id.nav_item_help);
        navChatRoom = menu.findItem(R.id.nav_item_chat_romm);




        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
        Log.e("TAG", "SharePreference " + sharedPreferences);

        if (sharedPreferences!=null){

            String name = sharedPreferences.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");

                //navChosePlane.setTitle("Chose Plane");
            }

        }
        //checking if shareprefereces empty or contain values
        if (sharedPreferences1!=null){
            String name = sharedPreferences1.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");

               // navChosePlane.setTitle("Chose Plane");
            }

        }


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_name){

                    SharedPreferences sharedPreferences = getSharedPreferences("franchiser", 0);
                    SharedPreferences spNormalUser = getSharedPreferences("user", 0);
                    String name = sharedPreferences.getString("name", null);
                    String normalUserName = spNormalUser.getString("name", null);



                    if (name!=null) {

                        Intent mainActvity = new Intent(BaseActvitvityForDrawer.this, UpdateFranchiserProfile.class);
                        startActivity(mainActvity);
                        //finish();

                    }


                    if (normalUserName!=null){
                        Intent mainActvity = new Intent(BaseActvitvityForDrawer.this, UpdateNormalUser.class);
                        startActivity(mainActvity);
                        //finish();
                    }
                }

                if (menuItem.getItemId() == R.id.nav_item_home) {
                    //home activity
                    Toast.makeText(getApplicationContext(), "Home Activity", Toast.LENGTH_SHORT).show();
                    Intent mainActvity = new Intent(BaseActvitvityForDrawer.this, Home.class);
                    startActivity(mainActvity);
                    finish();
                }

                if (menuItem.getItemId() == R.id.nav_item_about) {
                    //map activity
                    Intent aboutUs = new Intent(BaseActvitvityForDrawer.this, AboutUs.class);
                    startActivity(aboutUs);
                }

                if (menuItem.getItemId() == R.id.nav_item_contact_us){
                    //starting contact us actvity
                    Intent aboutUs = new Intent(BaseActvitvityForDrawer.this, ContactUs.class);
                    startActivity(aboutUs);
                }

                if (menuItem.getItemId() == R.id.nav_item_buyer){
                    //starting buyer actvity
                    Intent buyerActivity = new Intent(BaseActvitvityForDrawer.this, Buyer.class);
                    startActivity(buyerActivity);
                }

                if (menuItem.getItemId() == R.id.nav_item_termscondition){
                    //starting buyer actvity
                    Intent buyerActivity = new Intent(BaseActvitvityForDrawer.this, TermsAndConditions.class);
                    startActivity(buyerActivity);
                }

                if (menuItem.getItemId() == R.id.nav_item_share_app){
/*

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, Install Pk House The Online Sell and buy Properties Portal" + "\n"+appLink);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
*/


                    //

                    PackageManager pm=getPackageManager();

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"velmurugan@androidtoppers.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                    email.putExtra(Intent.EXTRA_TEXT, "Hi,This is Test");

                    email.setType("text/plain");


                    Dialog aDialog = new Dialog(BaseActvitvityForDrawer.this);
                    aDialog.setContentView(R.layout.sharing_app_list_dialog);
                    aDialog.setTitle("Share");
                    sharingListView = (ListView)aDialog.findViewById(R.id.listViewSharing);
                    aDialog.show();
                    //calling listView click
                    shareListViewClickListener(aDialog);

                    List<ResolveInfo> launchables = pm.queryIntentActivities(email, 0);
                    Collections.sort(launchables, new ResolveInfo.DisplayNameComparator(pm));

                    adapterSharing=new AppAdapter(pm, launchables);
                    sharingListView.setAdapter(adapterSharing);
                    mDialog = aDialog;

                    //

                }

/*

                if (menuItem.getItemId() == R.id.nav_item_franchise){
                    if (navFranchiser.getTitle().toString().equals("Franchise")) {

                        if (navLoginRegister.getTitle().equals("Log Out")){

                        }else {

                            //Intent mapActivity = new Intent(BaseActvitvityForDrawer.this, LoginFranchiser.class);
                            Intent loginSelector = new Intent(BaseActvitvityForDrawer.this, LoginSelector.class);
                            startActivity(loginSelector);
                            finish();
                        }
                    }
                }*/


                if (menuItem.getItemId() == R.id.nav_item_live_support){

                    Intent viewSubmitedProperties = new Intent(BaseActvitvityForDrawer.this, LiveSupport.class);
                    startActivity(viewSubmitedProperties);
                }

                if (menuItem.getItemId() == R.id.nav_item_help){


                    Intent helpAndGuide = new Intent(BaseActvitvityForDrawer.this, HelpAndGuide.class);
                    startActivity(helpAndGuide);
                }

                if (menuItem.getItemId() == R.id.nav_item_view_your_properties){

                    if (navViewYourProperties.getTitle().toString().equals("View Your Properties")){

                        Intent viewSubmitedProperties = new Intent(BaseActvitvityForDrawer.this, ViewSubmitedProperties.class);
                        startActivity(viewSubmitedProperties);


                    }
                }

                if (menuItem.getItemId() == R.id.nav_item_show_franchiser){


                        Intent viewSubmitedProperties = new Intent(BaseActvitvityForDrawer.this, ShowFranchiserFilter.class);
                    //Intent viewSubmitedProperties = new Intent(BaseActvitvityForDrawer.this, ShowFranchisers.class);
                        startActivity(viewSubmitedProperties);


                }

                if (menuItem.getItemId()==R.id.nav_item_login_registe){


                    String title =  menuItem.getTitle().toString();
                    Log.e("TAG", "Menu TAG " + title);


                    if (title.equals("Login/Register")) {

                        //Intent mapActivity = new Intent(BaseActvitvityForDrawer.this, LoginOrRegister.class);
                        Intent normalLogin = new Intent(BaseActvitvityForDrawer.this, LoginSelector.class);
                        startActivity(normalLogin);
                        //finish();
                    }


                    if (title.equals("Log Out")){

                        //do logout Function here
                        final SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
                        final SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor.clear();
                        editor.commit();
                        editor1.clear();
                        editor1.commit();
                        finish();
                        Intent mapActivity = new Intent(BaseActvitvityForDrawer.this, Home.class);
                        startActivity(mapActivity);

                    }

                }

                if (menuItem.getItemId()==R.id.nav_item_chat_romm){


                    String title =  menuItem.getTitle().toString();
                    Log.e("TAG", "Menu TAG " + title);

                    if (title.equals("Chat Room")) {

                        Intent i = new Intent(BaseActvitvityForDrawer.this, FranchiserChatRoom.class);
                        startActivity(i);

                    }

                }



                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        mWebView = (WebView) findViewById(R.id.webview);

        setupViews();

    }//end on Create

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
        Log.e("TAG", "SharePreference " + sharedPreferences);

        if (sharedPreferences!=null){

            String name = sharedPreferences.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");
            //    navChosePlane.setTitle("Chose Plane");
                navViewYourProperties.setTitle("View Your Properties");
                navViewYourProperties.setIcon(R.drawable.view);


            }

        }
        if (sharedPreferences1!=null){
            String name = sharedPreferences1.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");
              //  navChosePlane.setIcon(R.drawable.choose_plan);
               // navChosePlane.setTitle("Chose Plane");
                navViewYourProperties.setTitle("View Your Properties");
                navViewYourProperties.setIcon(R.drawable.view);

                navChatRoom.setTitle("Chat Room");
                navChatRoom.setIcon(R.drawable.chat_room_icon);

            }

        }
    }



    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm=null;

        AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(BaseActvitvityForDrawer.this, R.layout.share_app_list, apps);
            this.pm=pm;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView==null) {
                convertView=newView(parent);
            }

            bindView(position, convertView);

            return(convertView);
        }

        private View newView(ViewGroup parent) {
            return(getLayoutInflater().inflate(R.layout.share_app_list, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label=(TextView)row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon=(ImageView)row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }//end for the share App adapter class

    //share list onItemClick Listener
    public void shareListViewClickListener(final Dialog dialog){
        sharingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ResolveInfo launchable=adapterSharing.getItem(position);
                ActivityInfo activity=launchable.activityInfo;
                ComponentName name=new ComponentName(activity.applicationInfo.packageName,
                        activity.name);

                String nam =  activity.applicationInfo.packageName;
                Log.e("TAG", "MAIN CHECKER: " + nam);

                if (nam.equals("com.facebook.katana")){

                    // Toast.makeText(ShowProperties.this, "Hello Test", Toast.LENGTH_SHORT).show();

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse("Hi, Install Pk House The Online Sell and buy Properties Portal" + "\n"+appLink))
                                .build();
                        shareDialog.show(linkContent);
                    }//end for share

                }else {


                    //Intent in = new Intent(Intent.ACTION_MAIN);
                    Intent in = new Intent(Intent.ACTION_SEND);
                    in.addCategory(Intent.CATEGORY_LAUNCHER);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    in.setComponent(name);
                    //in.setType("vnd.android-dir/mms-sms");
                    in.putExtra(Intent.EXTRA_TEXT, "Hi, Install Pk House The Online Sell and buy Properties Portal" + "\n"+appLink);
                    startActivity(in);

                }

                dialog.dismiss();

            }



        });


    }//end of share item list onClick listner

    private void setupViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new Object() {

        }, "demo");

        mWebView.loadUrl(AllURLs.CHAT_URL);

    }



}
