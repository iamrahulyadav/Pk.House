package house.pkhouse;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.DataAdapter;
import house.pkhouse.adapter.RecyclerAdapterSinglerImage;
import house.pkhouse.adapter.SingleImageDataAdapter;

public class SinglePropertyImage extends AppCompatActivity {


    AppAdapter adapterSharing = null;
    ListView sharingListView;
    ShareDialog shareDialog;
    CallbackManager callbackManager;


    ListView listViewShowProperties;
    private ProgressBar progressBar;

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    final String TAG = "ShowLiisActvity";

    boolean checkState;

    ListView list;
    DataAdapter adapter;
    SingleImageDataAdapter singleAdapter;
    ArrayList<HashMap<String, String>> singlePropertyImageList;

    String pID;
    long down = 0;

    GridView gridView;
    TextView pTitle;
    TextView tvShowPrice, tvShowArea, tvShowDescription, tvShowStatus, tvShowPhone, tvShowLocation, tv_contact_num;
    String propertyTitle;
    String propertyPrice, propertyCity, propertyContact, propertyStatus, propertyDescription, propertyType, propertyLandArea;
    String phoneNumber;


    private RecyclerView recyclerView;
    private RecyclerAdapterSinglerImage mAdapter;

    private ImageView shareIcon;
    private CheckBox check_box_favourit;

    final  String appLinke = AllURLs.APP_URL; //"https://play.google.com/store/apps/details?id=house.pkhouse";
    private LinearLayout bt_bottom_call, bt_bottom_sms, bt_bottom_email;

    static final int MY_PERMISSIONS_PHONE_CALL = 1000;

    String mEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_property_image);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        init();
        showListofProperties();

        dynamicToolbarColor();
        toolbarTextAppernce();
        //sharing app
        shareIconClickListener();
        setupViews();
        checkBoxkStateChangeListner();
        btCallClicl();
        smsButtonClick();
        emailButtonClick();

    }//end of OnCreate


    public void init(){
        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        Intent i = getIntent();
        pID = i.getExtras().getString("ID", null);
        //propertyTitle = i.getExtras().getString("TITLE", null);
        propertyTitle = i.getExtras().getString("TITLEFULL", null);
        propertyPrice = i.getExtras().getString("PRICE", null);
        propertyCity = i.getExtras().getString("CITY", null);
        Log.e("TAG", "C FOR CITY: " + propertyCity);
        propertyContact = i.getExtras().getString("PHONE", null);
        propertyStatus = i.getExtras().getString("STATUS", null);
        propertyDescription = i.getExtras().getString("DESCRIPTION", null);
        propertyType = i.getExtras().getString("TYPE", null);
        propertyLandArea = i.getExtras().getString("AREA", null);
        mEmail = i.getExtras().getString("dealer_email", "");

        singlePropertyImageList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        pTitle = (TextView) findViewById(R.id.sinlge_image_dialog_title_text) ;
        tvShowPrice = (TextView) findViewById(R.id.tv_show_price);
        tvShowArea = (TextView) findViewById(R.id.tv_show_area);
        tvShowLocation = (TextView) findViewById(R.id.tv_show_location) ;
        tvShowDescription = (TextView) findViewById(R.id.tv_show_description);
        tvShowStatus = (TextView) findViewById(R.id.tv_show_status);
        tvShowPhone = (TextView) findViewById(R.id.tv_show_phone);
        tv_contact_num = (TextView) findViewById(R.id.tv_contact_num);
        shareIcon = (ImageView) findViewById(R.id.share);
        check_box_favourit = (CheckBox)findViewById (R.id.check_box_favourit);
        checkState = check_box_favourit.isChecked();

        pTitle.setText(propertyTitle);
        tvShowPrice.setText(propertyPrice);
        tvShowArea.setText(propertyLandArea);
        tvShowLocation.setText(propertyCity);
        tvShowDescription.setText(propertyDescription);
        tvShowStatus.setText(propertyStatus);
        tvShowPhone.setText(propertyType);
        tv_contact_num.setText(propertyContact);

        phoneNumber = propertyContact;


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(propertyTitle);



  /*      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(SinglePropertyImage.this ,R.color.colorGreen)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
*/
        list=(ListView)findViewById(R.id.listView_show_properties);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        bt_bottom_call = (LinearLayout) findViewById(R.id.bt_bottom_call);
        bt_bottom_call.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_press_and_release));
        bt_bottom_sms = (LinearLayout) findViewById(R.id.bt_bottom_sms);
        bt_bottom_sms.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_press_and_release));
        bt_bottom_email = (LinearLayout) findViewById(R.id.bt_bottom_email);
        bt_bottom_email.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_press_and_release));

    }//endo of init


    public void showListofProperties(){

        new GetContacts().execute();

    }//end of showListOfProperties


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


                // Making a request to url and getting response
                String url = AllURLs.GET_PROPERTIES_IMAGE_WITH_ID + pID; //"https://www.pk.house/app_webservices/get_properties_images.php?propertyid="+pID;
                Log.e("TAG", "URL  @@ " + url);
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        Log.e("TAG", "RESULT 1" + jsonObj);



                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("property_images");

                        Log.e("TAG", "RESULT 2" + contacts);

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String Imageurl = c.getString("name");

                            final String staticURL = AllURLs.PROERTY_IMAGES; //"https://www.pk.house/frontend/propertyimages/";

                            String imageURL = staticURL+Imageurl;

                            Log.e("TAG", "URL 123 " + " TEST TEST ");
                            Log.e("TAG", "URL 123 " + imageURL);
                            // tmp hash map for single contact
                            HashMap<String, String> contact = new HashMap<>();
                            contact.put("imageurl", imageURL);

                            singlePropertyImageList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               /* Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/

                                Toast.makeText(SinglePropertyImage.this, "Picture Not Available", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Please Check your Internet Connection!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /*singleAdapter = new SingleImageDataAdapter(SinglePropertyImage.this, singlePropertyImageList);
            gridView.setAdapter(singleAdapter);*/

            mAdapter = new RecyclerAdapterSinglerImage(SinglePropertyImage.this, singlePropertyImageList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(SinglePropertyImage.this, LinearLayoutManager.HORIZONTAL, false);


            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);


            //Toast.makeText(SinglePropertyImage.this, "Count " + singlePropertyImageList.size(), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "COUNT: " + singlePropertyImageList.size());
            TextView tvNumber = (TextView)findViewById(R.id.tv_image_number);
            tvNumber.setText(""+singlePropertyImageList.size());
            progressBar.setVisibility(View.GONE);


        }
    }


    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.default_image);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.colorRed)));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.colorRed)));

            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }


    //sharing property on shocial
    public void shareIconClickListener(){


        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pm=getPackageManager();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"velmurugan@androidtoppers.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                email.putExtra(Intent.EXTRA_TEXT, "Hi,This is Test");

                email.setType("text/plain");


                Dialog aDialog = new Dialog(SinglePropertyImage.this);
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


            }
        });

    }

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
                                .setContentUrl(Uri.parse("https://www.pk.house/property-detail/" + pID))
                                .build();
                        shareDialog.show(linkContent);
                    }//end for share

                }else {

                    Intent in = new Intent(Intent.ACTION_MAIN);
                    in.addCategory(Intent.CATEGORY_LAUNCHER);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    in.setComponent(name);
                    in.putExtra(Intent.EXTRA_TEXT, "Ttitle: " + propertyStatus + "\nPrice " + propertyPrice + "\nArea: " + propertyLandArea + "\n\nDownlaod App For Detail: " + appLinke + "\n\nhttps://www.pk.house/property-detail/"+pID);


                    startActivity(in);
                }

                dialog.dismiss();
            }
        });
    }//end of share item list onClick listner


    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm=null;

        AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(SinglePropertyImage.this, R.layout.share_app_list, apps);
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



    private void setupViews() {
        WebView mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new Object() {

        }, "demo");

        mWebView.loadUrl(AllURLs.CHAT_URL);

    }

    public void checkBoxkStateChangeListner(){

       check_box_favourit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               checkboxStateChange();

           }
       });


    }//end of checkboxkClick listner

    public void checkboxStateChange(){

        if (!checkState){


            SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
            SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
            if (sharedPreferences!=null) {

                String name = sharedPreferences.getString("name", null);

                if (name != null) {

                    Toast.makeText(SinglePropertyImage.this, "Your are login", Toast.LENGTH_SHORT).show();

                } else {

                    if (sharedPreferences1 != null) {
                        String name1 = sharedPreferences1.getString("name", null);

                        if (name1 != null) {
                            Toast.makeText(SinglePropertyImage.this, "Your are login", Toast.LENGTH_SHORT).show();


                        } else {
                            check_box_favourit.setChecked(false);
                            Toast.makeText(SinglePropertyImage.this, "Please Login First", Toast.LENGTH_SHORT).show();
                            check_box_favourit.setChecked(false);
                            Intent i = new Intent(SinglePropertyImage.this, LoginSelector.class);
                            i.putExtra("fromcheckbox", 1);
                            startActivity(i);
                        }

                    }

                }

            }



                    }
        else {

        check_box_favourit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(check_box_favourit.isChecked()){


                    Log.e("TAG", "yes yes yes yes");
                }
                else {


                    Log.e("TAG", "no no no no");
                }

            }
        });
    }
    }


    //handling calling button click
    public void btCallClicl(){

        bt_bottom_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
    }//end of call button click method

    public void smsButtonClick(){

        bt_bottom_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }//end of smsbuttonClick

    public void emailButtonClick(){


        bt_bottom_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    //call method

    public void call(){


        int permissionCheck = ContextCompat.checkSelfPermission(SinglePropertyImage.this, android.Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_PHONE_CALL);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "+"+phoneNumber));
            startActivity(callIntent);
        }
    }//end of call method

    //sms method
    public void sendSms() {
        try {

            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", phoneNumber);
            smsIntent.putExtra("sms_body","Proprty ID: " + pID);
            startActivity(smsIntent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Log.d("Error", "Error");
        }
    }//end of send sms method

    public void sendMail(){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","pk.house1979@gmail.com" +","+mEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pk.house property ID:"+ pID);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "test test ");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


}
