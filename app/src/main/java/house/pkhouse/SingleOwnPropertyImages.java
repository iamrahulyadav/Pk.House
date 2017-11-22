package house.pkhouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
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
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.DataAdapter;
import house.pkhouse.adapter.RecyclerAdapterSinglerImage;
import house.pkhouse.adapter.SingleImageDataAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SingleOwnPropertyImages extends AppCompatActivity {


    ListView listViewShowProperties;
    private ProgressBar progressBar;

    final String TAG = "ShowLiisActvity";

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    ListView list;
    DataAdapter adapter;
    SingleImageDataAdapter singleAdapter;
    ArrayList<HashMap<String, String>> singlePropertyImageList;

    String pID;

    GridView gridView;
    TextView pTitle;
    TextView tvShowPrice, tvShowArea, tvShowDescription, tvShowStatus, tvShowPhone, tvShowCity;
    String propertyTitle;
    String propertyPrice, propertyCity, propertyContact, propertyStatus, propertyDescription, propertyType, propertyLandArea;



    private RecyclerView recyclerView;
    private RecyclerAdapterSinglerImage mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_own_property_images);

        checkPermissionReadStorage(getApplicationContext(), this);
        init();
        showListofProperties();
        dynamicToolbarColor();
        toolbarTextAppernce();

    }//end of OnCreate


    public void init(){
        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        Intent i = getIntent();
        pID = i.getExtras().getString("ID", null);
        propertyTitle = i.getExtras().getString("TITLE", null);
        propertyPrice = i.getExtras().getString("PRICE", null);
        propertyCity = i.getExtras().getString("CITY", null);
        propertyContact = i.getExtras().getString("PHONE", null);
        propertyStatus = i.getExtras().getString("STATUS", null);
        propertyDescription = i.getExtras().getString("DESCRIPTION", null);
        propertyType = i.getExtras().getString("TYPE", null);
        propertyLandArea = i.getExtras().getString("AREA", null);

        singlePropertyImageList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        pTitle = (TextView) findViewById(R.id.sinlge_image_dialog_title_text) ;
        tvShowPrice = (TextView) findViewById(R.id.tv_show_price);
        tvShowArea = (TextView) findViewById(R.id.tv_show_area);
        tvShowDescription = (TextView) findViewById(R.id.tv_show_description);
        tvShowStatus = (TextView) findViewById(R.id.tv_show_status);
        tvShowPhone = (TextView) findViewById(R.id.tv_show_phone);
        pTitle.setText(propertyTitle);
        tvShowPrice.setText(propertyPrice);
        tvShowArea.setText(propertyLandArea);
        tvShowDescription.setText(propertyDescription);
        tvShowStatus.setText(propertyStatus);
        tvShowPhone.setText(propertyContact);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.tv_pk_house));


      /*  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(SingleOwnPropertyImages.this ,R.color.colorGreen)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);*/

        list=(ListView)findViewById(R.id.listView_show_properties);

        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
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

                            Toast.makeText(SingleOwnPropertyImages.this, "No Image Found", Toast.LENGTH_SHORT).show();
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

            /*singleAdapter = new SingleImageDataAdapter(SingleOwnPropertyImages.this, singlePropertyImageList);
            gridView.setAdapter(singleAdapter);*/

            mAdapter = new RecyclerAdapterSinglerImage(SingleOwnPropertyImages.this, singlePropertyImageList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(SingleOwnPropertyImages.this, LinearLayoutManager.HORIZONTAL, false);


            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            //Toast.makeText(SingleOwnPropertyImages.this, "Count " + singlePropertyImageList.size(), Toast.LENGTH_SHORT).show();
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
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));

            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    public void checkPermissionReadStorage(Context context, Activity activity){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        110);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 110: {
                //premission to read storage
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "We Need permission Storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}




