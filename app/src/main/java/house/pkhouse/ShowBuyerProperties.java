package house.pkhouse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.DataAdapter;

import house.pkhouse.adapter.SingleImageDataAdapter;

public class ShowBuyerProperties extends AppCompatActivity {


    ListView listViewShowProperties;
    private ProgressBar progressBar;

    String country, city, propertyType, propertyStatus, propertyArea, minPrice, maxPrice;

    Dialog singleIamgeListDialog;

    final String TAG = "ShowLiisActvity";

    int imageCode = 0;
    String image_ID;

    ListView list;
    DataAdapter adapter;
    SingleImageDataAdapter singleAdapter;

    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> singlePropertyImageList;

    View loadMoreView;
    RelativeLayout rl_chat_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buyer_properties);
        init();

        showListofProperties();
        listviewOnItemClickHander();
        loadMore();
        chatIconClick();
/*
        loadMoreView = ((LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.loadmore, null, false);
        listViewShowProperties.addFooterView(loadMoreView);*/

    }

    public void init(){



        //getting intent values
        Intent parameterValues = getIntent();
        country = parameterValues.getExtras().getString("country", "");
        city = parameterValues.getExtras().getString("city", "");
        propertyType = parameterValues.getExtras().getString("ptype", "");
        propertyStatus = parameterValues.getExtras().getString("pstatus", "");
        propertyArea = parameterValues.getExtras().getString("parea", "");
        minPrice = parameterValues.getExtras().getString("minprice", "");
        maxPrice = parameterValues.getExtras().getString("maxprice", "");


        Log.e("TAGE", " Country: " + country);
        Log.e("TAGE", "City: " + city);
        Log.e("TAGE", "Property Type: " + propertyType);
        Log.e("TAGE", "Property Status: " + propertyStatus);
        Log.e("TAGE", " Area: " + propertyArea);
        Log.e("TAGE", "Min Price: " + minPrice);
        Log.e("TAGE", "Max Price: " + maxPrice);





        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        contactList = new ArrayList<>();
        singlePropertyImageList = new ArrayList<>();

        singleIamgeListDialog = new Dialog(ShowBuyerProperties.this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ShowBuyerProperties.this ,R.color.colorRed)));
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
    }


    public void listviewOnItemClickHander(){
        listViewShowProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {







                imageCode = 1;

                TextView tv_id = (TextView)view.findViewById(R.id.tv_protperty_id);
                TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
                TextView tv_propertyTitle = (TextView)view.findViewById(R.id.tv_property_title);
                TextView tv_propertyPropertyLandArea = (TextView)view.findViewById(R.id.tv_land_area);
                TextView tv_propertyCity = (TextView)view.findViewById(R.id.tv_location);
                TextView tv_propertyPhone = (TextView)view.findViewById(R.id.tv_contact);
                TextView tv_protperty_type = (TextView)view.findViewById(R.id.tv_protperty_type);
                TextView tv_protperty_status = (TextView)view.findViewById(R.id.tv_protperty_status);
                TextView tv_protperty_description = (TextView)view.findViewById(R.id.tv_protperty_description);
                ImageView image=(ImageView)view.findViewById(R.id.image);

                final BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                final Bitmap imageBitmap = bitmapDrawable.getBitmap();


                final String propertyID = tv_id.getText().toString();
                final String propertyTitle = tv_propertyTitle.getText().toString();
                final String propertyPrice = tv_price.getText().toString();
                final String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                final String propertyCity = tv_propertyCity.getText().toString();
                final String propertyContact = tv_propertyPhone.getText().toString();
                final String propertyType = tv_protperty_type.getText().toString();
                final String propertyStatus = tv_protperty_status.getText().toString();
                final String propertyDescription = tv_protperty_description.getText().toString();



                Log.e("TAG", "Property Id: " + propertyID);
                Log.e("TAG", "Property TILE: " + propertyTitle);
                Log.e("TAG", "Property Price: " + propertyPrice);
                Log.e("TAG", "Property Area: " + propertyLandArea);
                Log.e("TAG", "Property City: " + propertyCity);
                Log.e("TAG", "Property Contact: " + propertyContact);
                Log.e("TAG", "Property Type: " + propertyType);
                Log.e("TAG", "Property Status: " + propertyStatus);
                Log.e("TAG", "Property Description: " + propertyDescription);

                image_ID = propertyID;


                final Dialog singleListViewItemDialog = new Dialog(ShowBuyerProperties.this);
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





               /* singleAdapter = new SingleImageLazyLoad(ShowBuyerProperties.this, singlePropertyImageList);
                gridView.setAdapter(singleAdapter);*/

                // new GetContacts().execute();

                singleListViewItemDialog.show();


                dialog_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        singleListViewItemDialog.dismiss();

                        Intent i = new Intent(ShowBuyerProperties.this, SinglePropertyImage.class);
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

                URL url = new URL(AllURLs.GET_BUYER_PROPERTIES); //"https://www.pk.house/app_webservices/get_buyer_properties.php");


                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("country", country)
                        .appendQueryParameter("city", city)
                        .appendQueryParameter("propertytype", propertyType)
                        .appendQueryParameter("propertystatus", propertyStatus)
                        .appendQueryParameter("propertyarea", propertyArea)
                        .appendQueryParameter("minprice", minPrice)
                        .appendQueryParameter("maxprice", maxPrice);

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
                if (response_code == HttpURLConnection.HTTP_OK) {


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

     /*       String responce =  returnParsedJsonObject(result);
            Log.e("TAG", "RESULT 123 " + result);
            Log.e("TAG", "RESULT 1233 " + responce);
            if (responce == null)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Server not Responding...", Toast.LENGTH_SHORT).show();
            }
            else if(responce.equalsIgnoreCase("true"))
            {
                *//* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 *//*
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Data Sent Successfully...",Toast.LENGTH_SHORT).show();




            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message

                progressBar.setVisibility(View.GONE);

                Toast.makeText(ShowBuyerProperties.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(ShowBuyerProperties.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

            }*/


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
                        //String property_title = c.getString("property_title");
                        String price = c.getString("price");
                        String landArea = c.getString("land_area");
                       // String country = c.getString("country");
                        String city = c.getString("city");
                        String propertyLocation = c.getString("location");
                        String propertyType = c.getString("property_type");
                        //String propertystatus = c.getString("status");
                        String description = c.getString("property_description");
                        String buyer_name = c.getString("buyer_name");
                        //String rooms = c.getString("rooms");
                        //String bathrooms = c.getString("bathrooms");
                        //String floors = c.getString("floors");
                        //String status_property = c.getString("status_property");
                        //String Imageurl = c.getString("images");
                        //String dealer_email = c.getString("dealer_email");
                        //String dealer_phone = c.getString("dealer_phone");

                       // String phone = dealer_phone;

                        final String staticURL = AllURLs.PROERTY_IMAGES; //"https://www.pk.house/frontend/propertyimages/";

                        //String imageURL = staticURL + Imageurl;



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
                        contact.put("imageurl", "");
                        contact.put("property_title", "");
                        contact.put("price", price);
                        contact.put("landArea", landArea);
                        contact.put("country", country);
                        contact.put("city", city);
                        contact.put("phone", "03350388888");
                        contact.put("property_type", propertyType);
                        contact.put("status", "");
                        contact.put("property_description", description);
                        contact.put("location", propertyLocation);

                        contact.put("dealer_email", "");

                        contact.put("rooms", "");
                        contact.put("bathrooms", "");
                        contact.put("floors", "");
                        contact.put("status_property", "");

                        contactList.add(contact);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
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

            adapter=new DataAdapter(ShowBuyerProperties.this, contactList);
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }



    }//end of GettingDataFromServer



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

        listViewShowProperties.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                //Algorithm to check if the last item is visible or not
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    // you have reached end of list, load more data
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //blank, not using this
            }
        });
    }


    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ShowBuyerProperties.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }


}//***************** Shoaib Anwar # 03233008757 ********************




