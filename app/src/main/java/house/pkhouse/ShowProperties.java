package house.pkhouse;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.DataAdapter;

import house.pkhouse.adapter.SingleImageDataAdapter;
import house.pkhouse.loader.ImagesLoader;

import static android.R.id.custom;
import static android.R.id.message;

public class ShowProperties extends AppCompatActivity {

    ShareDialog shareDialog;
    CallbackManager callbackManager;
    //ShareButton shareButton;

    AppAdapter adapterSharing = null;
    ListView sharingListView;

    ListView listViewShowProperties;
    int start = 0;
    int limit = 10;
    int listSize = 0;
    int currentValue = 0;
    boolean loadingMore = false;
    View loadMoreView;
    private ProgressBar progressBar;

    String country, city, propertyType, propertyStatus, propertyArea, minPrice, maxPrice, propertyTypeInter, locatoin;

    Dialog singleIamgeListDialog;

    final String TAG = "ShowLiisActvity";

    int imageCode = 0;
    String image_ID;
    String propertyId;
    String mTitle, mTitleFull, mPrice, mArea;
    String apkLink;
    ListView list;
    DataAdapter adapter;
    SingleImageDataAdapter singleAdapter;

    RelativeLayout rl_chat_icon;

    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> singlePropertyImageList;


    Bitmap imageBitmap;

    static final int MY_PERMISSIONS_PHONE_CALL = 1000;
    String fnNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_show_properties);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);



        init();

        showListofProperties();
        listviewOnItemClickHander();
        loadMore();
        chatIconClick();
        setupViews();



    }

    public void init(){



        //getting intent values
        Intent parameterValues = getIntent();
        country = parameterValues.getExtras().getString("country", "");
        city = parameterValues.getExtras().getString("city", "");
        propertyType = parameterValues.getExtras().getString("ptype", "");
        propertyTypeInter = parameterValues.getExtras().getString("piner", "");
        propertyStatus = parameterValues.getExtras().getString("pstatus", "");
        propertyArea = parameterValues.getExtras().getString("parea", "");
        minPrice = parameterValues.getExtras().getString("minprice", "");
        maxPrice = parameterValues.getExtras().getString("maxprice", "");
        locatoin = parameterValues.getExtras().getString("location", "");



        Log.e("TAGE", " Country: " + country);
        Log.e("TAGE", "City: " + city);
        Log.e("TAGE", "Property Type: " + propertyType);
        Log.e("TAGE", "Property Type Iner: " + propertyTypeInter);
        Log.e("TAGE", "Property Status: " + propertyStatus);
        Log.e("TAGE", " Area: " + propertyArea);
        Log.e("TAGE", "Min Price: " + minPrice);
        Log.e("TAGE", "Max Price: " + maxPrice);

        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        contactList = new ArrayList<>();
        singlePropertyImageList = new ArrayList<>();

        singleIamgeListDialog = new Dialog(ShowProperties.this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ShowProperties.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

        list=(ListView)findViewById(R.id.listView_show_properties);

        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);





    }//endo of init

    public void showListofProperties(){

        GettingDataFromServer load = new GettingDataFromServer();
        load.execute();

      //  new GetContacts().execute();

    }//end of showListOfProperties



    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();

        ImagesLoader imagesLoader = new ImagesLoader(ShowProperties.this);
        imagesLoader.clearCache();
    }


    public void listviewOnItemClickHander(){
        listViewShowProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                imageCode = 1;

               final TextView tv_id = (TextView)view.findViewById(R.id.tv_protperty_id);
                final  TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
                final TextView tv_propertyTitle = (TextView)view.findViewById(R.id.tv_property_title);
                final TextView tv_propertyTitleFull = (TextView)view.findViewById(R.id.tv_property_title_full);
               final TextView tv_propertyPropertyLandArea = (TextView)view.findViewById(R.id.tv_land_area);
               final TextView tv_propertyCity = (TextView)view.findViewById(R.id.tv_location);
               final TextView tv_propertyPhone = (TextView)view.findViewById(R.id.tv_contact);
                final TextView tv_protperty_type = (TextView)view.findViewById(R.id.tv_protperty_type);
                TextView tv_protperty_status = (TextView)view.findViewById(R.id.tv_protperty_status);
                TextView tv_protperty_description = (TextView)view.findViewById(R.id.tv_protperty_description);
                TextView tv_image_url = (TextView)view.findViewById(R.id.tv_url);
                final ImageView image=(ImageView)view.findViewById(R.id.image);


              //  ImageView shareonFB = (ImageView)view.findViewById(R.id.share_on_fb);
                //ImageView shareonTW = (ImageView)view.findViewById(R.id.share_on_tw);


               final String title = tv_propertyTitle.getText().toString();
                final String titleFull = tv_propertyTitleFull.getText().toString();
                final String price = tv_price.getText().toString();
                final  String area = tv_propertyPropertyLandArea.getText().toString();
                final String contact = tv_propertyPhone.getText().toString();
                final  String appLinke = AllURLs.APP_URL; //"https://play.google.com/store/apps/details?id=house.pkhouse";
                final String pID = tv_id.getText().toString();
                final  String imageURL = tv_image_url.getText().toString();
                propertyId = pID;
                mTitle = title;
                mTitleFull = titleFull;
                mPrice = price;
                mArea = area;
                apkLink = appLinke;

                tv_propertyPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phone1 = contact.substring(0, 2);
                        String[] a = contact.split("92");
                        String right = a[0].toString();
                        String left = a[1].toString();
                        String number = "0"+left;
                        Log.e("TAG", "phone Number Is: " + phone1);
                        Log.e("TAG", "phone Number Is: " + right);
                        Log.e("TAG", "phone Number Is: " + left);
                        Log.e("TAG", "phone Number Is: " + number);

                        fnNumber = number;
                        call(fnNumber);


                    }
                });


               // final GlideBitmapDrawable bitmapDrawable = (GlideBitmapDrawable) image.getDrawable();
                 //Bitmap bitmap = bitmapDrawable.getBitmap();


                //final Bitmap imageBitmap = ((GlideBitmapDrawable)image.getDrawable().getCurrent()).getBitmap();





               Glide
                        .with(ShowProperties.this)
                        .load(imageURL)
                        .asBitmap()
                       .override(200,200)
                       .centerCrop()
                        .into(new SimpleTarget<Bitmap>(300,300) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                imageBitmap = resource;
                            }
                        });

                //Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                //imageBitmap = bitmap;



               final String propertyID = tv_id.getText().toString();
               final String propertyTitle = tv_propertyTitle.getText().toString();
                final String propertyTitleFull = tv_propertyTitleFull.getText().toString();
                final String propertyPrice = tv_price.getText().toString();
               final String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                final String propertyCity = tv_propertyCity.getText().toString();
                final String propertyContact = tv_propertyPhone.getText().toString();
                final String propertyType = tv_protperty_type.getText().toString();
                final String propertyStatus = tv_protperty_status.getText().toString();
                final String propertyDescription = tv_protperty_description.getText().toString();



                Log.e("TAG", "Property Id: " + propertyID);
                Log.e("TAG", "Property TILE: " + propertyTitle);
                Log.e("TAG", "Property TILE Full: " + mTitleFull);
                Log.e("TAG", "Property Price: " + propertyPrice);
                Log.e("TAG", "Property Area: " + propertyLandArea);
                Log.e("TAG", "Property City: " + propertyCity);
                Log.e("TAG", "Property Contact: " + propertyContact);
                Log.e("TAG", "Property Type: " + propertyType);
                Log.e("TAG", "Property Status: " + propertyStatus);
                Log.e("TAG", "Property Description: " + propertyDescription);

                image_ID = propertyID;




                Intent i = new Intent(ShowProperties.this, SinglePropertyImage.class);
                i.putExtra("ID", propertyID);
                i.putExtra("TITLE", propertyTitle);
                i.putExtra("TITLEFULL", mTitleFull);
                i.putExtra("PRICE", propertyPrice);
                i.putExtra("CITY", propertyCity);
                i.putExtra("PHONE", propertyContact);
                i.putExtra("STATUS", propertyStatus);
                i.putExtra("DESCRIPTION", propertyDescription);
                i.putExtra("TYPE", propertyType);
                i.putExtra("AREA", propertyLandArea);
                startActivity(i);


               final Dialog singleListViewItemDialog = new Dialog(ShowProperties.this);
                singleListViewItemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                singleListViewItemDialog.setContentView(R.layout.single_list_item_view_dialog);

                TextView dialog_tv_propertyTitle = (TextView)singleListViewItemDialog.findViewById(R.id.dialog_tv_title);
                TextView dialog_tv_price = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_price);
                TextView dialog_tv_propertyPropertyLandArea = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_area);
                TextView dialog_tv_propertyCity = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_location);
                TextView dialog_tv_propertyPhone = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_contact);
                TextView dialog_tv_protperty_type = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_type);
                TextView dialog_tv_protperty_status = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_status);
                TextView dialog_tv_protperty_description = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_description);
                ImageView dialog_image = (ImageView)singleListViewItemDialog.findViewById(R.id.dialog_property_image);


                //share image
                ImageView img_share_icon = (ImageView)singleListViewItemDialog.findViewById(R.id.img_share_icon);

                img_share_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String property_url = "https://www.pk.house/property-detail/"+pID;
                        String complete_data_to_send = "Ttitle: " + title + "\nPrice " + price + "\nArea: " + area + "\n\nDownlaod App For Detail: " + appLinke + "\n\nProperty Detail: http://www.pk.house/property-detail/"+pID;



                        PackageManager pm=getPackageManager();

                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"velmurugan@androidtoppers.com"});
                        email.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                        email.putExtra(Intent.EXTRA_TEXT, "Hi,This is Test");

                        email.setType("text/plain");


                        Dialog aDialog = new Dialog(ShowProperties.this);
                        aDialog.setContentView(R.layout.sharing_app_list_dialog);
                        aDialog.setTitle("Share");
                        sharingListView = (ListView)aDialog.findViewById(R.id.listViewSharing);
                        singleListViewItemDialog.cancel();
                        aDialog.show();
                        //calling listView click
                        shareListViewClickListener();

                      /*  final Dialog dialog = new Dialog(ShowProperties.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
                        WMLP.gravity = Gravity.CENTER;
                        dialog.getWindow().setAttributes(WMLP);
                        dialog.getWindow().setBackgroundDrawable(
                                new ColorDrawable(Color.WHITE));
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.setContentView(R.layout.sharing_app_list_dialog);
                        sharingListView = (ListView)dialog.findViewById(R.id.listViewSharing);
                        dialog.show();*/

                        List<ResolveInfo> launchables = pm.queryIntentActivities(email, 0);
                        Collections.sort(launchables, new ResolveInfo.DisplayNameComparator(pm));

                        adapterSharing=new AppAdapter(pm, launchables);
                        sharingListView.setAdapter(adapterSharing);







/*
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, complete_data_to_send);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        singleListViewItemDialog.dismiss();*/
                    }
                });

               // GridView  gridView = (GridView)singleListViewItemDialog.findViewById(R.id.gridView);

               // RecyclerView recylerView = (RecyclerView)singleListViewItemDialog.findViewById(R.id.recycler_view);



                dialog_tv_propertyTitle.setText(propertyTitle);
                dialog_tv_price.setText(propertyPrice);
                dialog_tv_propertyPropertyLandArea.setText(propertyLandArea);
                dialog_tv_propertyCity.setText(propertyCity);
                dialog_tv_propertyPhone.setText(propertyContact);
                dialog_tv_protperty_type.setText(propertyType);
                dialog_tv_protperty_status.setText(propertyStatus);
                dialog_tv_protperty_description.setText(propertyDescription);

               dialog_image.setImageBitmap(imageBitmap);





               /* singleAdapter = new SingleImageLazyLoad(showProperties.this, singlePropertyImageList);
                gridView.setAdapter(singleAdapter);*/

               // new GetContacts().execute();

                //**************************************************************************
                //singleListViewItemDialog.show();


                dialog_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        singleListViewItemDialog.dismiss();

                        Intent i = new Intent(ShowProperties.this, SinglePropertyImage.class);
                        i.putExtra("ID", propertyID);
                        i.putExtra("TITLE", propertyTitle);
                        i.putExtra("PRICE", propertyPrice);
                        i.putExtra("CITY", propertyCity);
                        i.putExtra("PHONE", propertyContact);
                        i.putExtra("STATUS", propertyStatus);
                        i.putExtra("DESCRIPTION", propertyDescription);
                        i.putExtra("TYPE", propertyType);
                        i.putExtra("AREA", propertyLandArea);
                        startActivity(i);



                    }
                });


            }
        });
    }//end of listveiw item click


    public class GettingDataFromServer extends  AsyncTask<String , Void ,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection connection = null;
            try {

                URL url = new URL(AllURLs.GET_PROPERTIES); //"https://www.pk.house/app_webservices/get_properties.php");


                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Log.e("TAG", "The resul of city is: " + city);

                Uri.Builder builder = new Uri.Builder()



                        .appendQueryParameter("country", country)
                        .appendQueryParameter("city", city)
                        .appendQueryParameter("propertytype", propertyTypeInter)
                        .appendQueryParameter("propertystatus", propertyStatus)
                        .appendQueryParameter("propertyarea", propertyArea)
                        .appendQueryParameter("minprice", minPrice)
                        .appendQueryParameter("maxprice", maxPrice)
                        .appendQueryParameter("location", locatoin);

                Log.e("TAG", "property Type 111 " + propertyType);




                String query = builder.build().getEncodedQuery().toString();

                // Open connection for sending data
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                connection.connect();

                int response_code = connection.getResponseCode();


                // Check if successful connection made
                if (response_code == HttpsURLConnection.HTTP_OK) {


                    // Read data sent from server
                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    Log.e("TAG", "RESULT 123 33: " + result);
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);

                    Log.e("TAG", "RESULT 1" + jsonObj);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("property_data");

                    Log.e("TAG", "RESULT 2" + contacts);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                       // for (int i = 0; i < contacts.; i++) {

                            JSONObject c = contacts.getJSONObject(i);
                        String property_id = c.getString("property_id");
                        String property_title = c.getString("property_title");
                        String price = c.getString("price");
                        String landArea = c.getString("land_area");
                        String country = c.getString("country");
                        String city = c.getString("city");
                        String propertyLocation = c.getString("location");
                        String propertyType = c.getString("property_type");
                        String propertystatus = c.getString("status");
                        String description = c.getString("property_description");
                        String rooms = c.getString("rooms");
                        String bathrooms = c.getString("bathrooms");
                        String floors = c.getString("floors");
                        String status_property = c.getString("status_property");
                        String Imageurl = c.getString("images");
                        String dealer_email = c.getString("dealer_email");
                        String dealer_phone = c.getString("dealer_phone");

                        String phone = dealer_phone;

                        final String staticURL = AllURLs.PROERTY_IMAGES; //"https://www.pk.house/frontend/propertyimages/";

                        String imageURL = staticURL + Imageurl;



                        String extensionRemoved = price.split("\\.")[0];

                        if (extensionRemoved.length() == 4){

                            Log.e("TAG", "Leanth is Thousand");


                        }
                        if (extensionRemoved.length() == 5){

                            Log.e("TAG", "Leanth is T.T");

                            char rr = price.charAt(0);
                            char bb = price.charAt(1);
                            String fc = String.valueOf(rr);
                            String sc = String.valueOf(bb);

                            String ss  = fc+sc+" Thousands";
                            Log.e("TAG", "Formated Price " + ss);

                            price = ss;

                        }
                        if (extensionRemoved.length() == 6){

                            Log.e("TAG", "Leanth is L");


                            char rr = price.charAt(0);
                            char bb = price.charAt(1);

                            String fc = String.valueOf(rr);
                            String sc = String.valueOf(bb);
                            String ss  = fc+"."+sc+" Lakhs";
                            Log.e("TAG", "Formated Price " + ss);

                            price = ss;

                        }
                        if (extensionRemoved.length() == 7){

                            Log.e("TAG", "Leanth is T.L");

                            char rr = price.charAt(0);
                            String fc = String.valueOf(rr);
                            char bb = price.charAt(1);
                            String sc = String.valueOf(bb);
                            char cc = price.charAt(2);
                            String tc = String.valueOf(cc);
                            String ss  = fc+sc+"."+tc+" Lakhs";
                            Log.e("TAG", "Formated Price " + ss);

                            price = ss;


                        }
                        if (extensionRemoved.length() == 8){

                            Log.e("TAG", "Leanth is ");

                            char rr = price.charAt(0);
                            String fc = String.valueOf(rr);
                            char bb = price.charAt(1);
                            String sc = String.valueOf(bb);
                            char cc = price.charAt(2);
                            String tc = String.valueOf(cc);

                            String ss  = fc+"."+sc+tc+" Crore";

                            Log.e("TAG", "Formated Price " + ss);

                            price = ss;


                        }
                        if (extensionRemoved.length() == 9){

                            Log.e("TAG", "Leanth is T.C");
                            char rr = price.charAt(0);
                            String fc = String.valueOf(rr);
                            char bb = price.charAt(1);
                            String sc = String.valueOf(bb);
                            char cc = price.charAt(2);
                            String tc = String.valueOf(cc);

                            String ss  = fc+sc+"."+tc+" Crore";
                            Log.e("TAG", "Formated Price " + ss);

                            price = ss;



                        }
                        if (extensionRemoved.length() == 10){

                            Log.e("TAG", "Leanth is  H.C");
                            char rr = price.charAt(0);
                            String fc = String.valueOf(rr);
                            char bb = price.charAt(1);
                            String sc = String.valueOf(bb);
                            char cc = price.charAt(2);
                            String tc = String.valueOf(cc);
                            char dd = price.charAt(3);
                            String frc = String.valueOf(dd);
                            String ss  = fc+sc+tc+"."+frc+" Crore";
                            Log.e("TAG", "Formated Price H.C" + ss);

                            price = ss;


                        }



                        Log.e("TAG", "Price are "  + extensionRemoved);


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("property_id", property_id);
                        contact.put("imageurl", imageURL);
                        contact.put("property_title", property_title);
                        contact.put("price", price);
                        contact.put("landArea", landArea);
                        contact.put("country", country);
                        contact.put("city", city);
                        contact.put("phone", phone);
                        contact.put("property_type", propertyType);
                        contact.put("status", propertystatus);
                        contact.put("property_description", description);
                        contact.put("location", propertyLocation);

                        contact.put("dealer_email", dealer_email);

                        contact.put("rooms", rooms);
                        contact.put("bathrooms", bathrooms);
                        contact.put("floors", floors);
                        contact.put("status_property", status_property);

                        contactList.add(contact);

                        Log.e("TAG", "Image URL: " + imageURL);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Property Data Not Found ",
                                    Toast.LENGTH_LONG).show();
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
            progressBar.setVisibility(View.GONE);


            listSize = contactList.size();

            Log.e("TAG", "list size: " + listSize);
            settingAdatpter();
        }



    }//end of GettingDataFromServer



    public void settingAdatpter(){


            list.setAdapter(adapter);
            adapter = new DataAdapter(ShowProperties.this, contactList);
            list.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private String returnParsedJsonObject(String result){

        JSONObject resultObject = null;

        String returnedResult = null;

        try {

            if (result!=null) {
                resultObject = new JSONObject(result);

                returnedResult = resultObject.getString("response");
            }
        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }


    public void loadMore() {

        listViewShowProperties.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !(loadingMore)){


                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {

        String path="";
        try {


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

            return Uri.parse(path);
    }

    public void call(String mn){


        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_PHONE_CALL);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mn));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_PHONE_CALL:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    call(fnNumber);
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


    //app adapter for showing share applications

    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm=null;

        AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(ShowProperties.this, R.layout.share_app_list, apps);
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
    public void shareListViewClickListener(){
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
                                .setContentUrl(Uri.parse(AllURLs.PROPERTY_DETAIL + propertyId)) //"https://www.pk.house/property-detail/" + propertyId))
                                .build();
                        shareDialog.show(linkContent);
                    }//end for share

                }else {

                    Intent in = new Intent(Intent.ACTION_MAIN);
                    in.addCategory(Intent.CATEGORY_LAUNCHER);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    in.setComponent(name);
                    in.putExtra(Intent.EXTRA_TEXT, "Ttitle: " + mTitle + "\nPrice " + mPrice + "\nArea: " + mArea + "\n\nDownlaod App For Detail: " + apkLink + "\n\nhttps://www.pk.house/property-detail/"+propertyId);


                    startActivity(in);
                }

            }
        });
    }//end of share item list onClick listner

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ShowProperties.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }


    private void setupViews() {
       WebView mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new Object() {

        }, "demo");

        mWebView.loadUrl(AllURLs.CHAT_URL);

    }

}//***************** Shoaib Anwar # 03233008757 ********************




