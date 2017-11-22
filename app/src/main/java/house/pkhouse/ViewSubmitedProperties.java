package house.pkhouse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.DataAdapter;
import house.pkhouse.adapter.OwnPropertyDataAdapter;
import house.pkhouse.adapter.SingleImageDataAdapter;


  public class ViewSubmitedProperties extends AppCompatActivity {


    ListView listViewShowProperties;
    private ProgressBar progressBar;


    Dialog singleIamgeListDialog;

    final String TAG = "ShowLiisActvity";

    int imageCode = 0;
    String image_ID;
    String firstID;

      Bitmap imageBitmap;

    String result12 = "1";

    ListView list;
    OwnPropertyDataAdapter adapter;
    SingleImageDataAdapter singleAdapter;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> singlePropertyImageList;

      RelativeLayout rl_chat_icon;


      String extensionRemoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_submited_properties);
        init();

        showListofProperties();
        listviewOnItemClickHander();
       // listViewLognClickHandler();

        chatIconClick();
        setupViews();

    }

    public void init(){

        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);

        String userIdAsNormal = sharedPreferences.getString("user_id", null);

        String userIdAsFrenchiser = sharedPreferences1.getString("fr_id", null);

        if (userIdAsNormal!=null){
            firstID = userIdAsNormal;
        }
        if (userIdAsFrenchiser!=null){
            firstID = userIdAsFrenchiser;
        }



        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        contactList = new ArrayList<>();
        singlePropertyImageList = new ArrayList<>();

        singleIamgeListDialog = new Dialog(ViewSubmitedProperties.this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ViewSubmitedProperties.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

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

            if (imageCode==1){
                // Making a request to url and getting response
                String url = AllURLs.GET_PROPERTIES_IMAGE_WITH_ID + image_ID; //"https://www.pk.house/app_webservices/get_properties_images.php?propertyid="+image_ID;
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

                            if (!singlePropertyImageList.isEmpty()){
                                singlePropertyImageList.clear();
                            }
                            singlePropertyImageList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/
                                Toast.makeText(ViewSubmitedProperties.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
            }else if (imageCode==2){//if image code 2 then call delete service

                // Making a request to url and getting response
                String url = AllURLs.DELETE_PROPERTY + image_ID; //"https://www.pk.house/app_webservices/delete_property.php?property_id="+image_ID;
                Log.e("TAG", "GET URL: " + url);
                // String url = "http://www.pk.estate/app_webservices/get_properties.php";
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {



                   // Toast.makeText(ViewSubmitedProperties.this, "Delected Successfully", Toast.LENGTH_LONG).show();

                    result12 = jsonStr.toString();



                }
                else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Server Connection Fail",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }//end for delete else statment
            else { //starting else for calling all iamges actvity services




                // Making a request to url and getting response
                String url = AllURLs.USER_LISTING_WITH_ID + firstID; //"https://www.pk.house/app_webservices/user_listing.php?user_id="+firstID;
                Log.e("TAG", "ID IS: " + firstID);
               // String url = "http://www.pk.estate/app_webservices/get_properties.php";
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        Log.e("TAG", "RESULT 1" + jsonObj);

                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("property_data");

                        Log.e("TAG", "RESULT 2" + contacts);

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String property_id = c.getString("property_id");
                            String property_title = c.getString("property_title");
                            String price = c.getString("price");
                            String pricefull = c.getString("price");
                            String landArea = c.getString("land_area");
                            String city = c.getString("city");
                            String propertyLocation = c.getString("location");
                            String country = c.getString("country");
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

                            Log.e("TAG", "URL 123 " + imageURL);


                            extensionRemoved = price.split("\\.")[0];

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
                            contact.put("fullprice", pricefull);
                            contact.put("landArea", landArea);
                            contact.put("country", country);
                            contact.put("city", city);
                            contact.put("phone", phone);
                            contact.put("property_type", propertyType);
                            contact.put("status", propertystatus);
                            contact.put("property_description", description);
                            contact.put("location", propertyLocation);
                            contact.put("rooms", rooms);
                            contact.put("bathrooms", bathrooms);
                            contact.put("floors", floors);
                            contact.put("status_property", status_property);

                            contact.put("dealer_email", dealer_email);

                            contactList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/

                                Toast.makeText(ViewSubmitedProperties.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Server Connection Fail",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            adapter=new OwnPropertyDataAdapter(ViewSubmitedProperties.this, contactList);
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);


            if (!result12.equals("1")){
                Toast.makeText(ViewSubmitedProperties.this, "Property Delected Successfully", Toast.LENGTH_LONG).show();
                Log.e("TAG", "SSSSSSS " + "123");

                final AlertDialog.Builder alert = new AlertDialog.Builder(ViewSubmitedProperties.this);
                alert.setTitle("Alert!");
                alert.setMessage("Property Deleted Successfully");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                alert.setCancelable(false);
                alert.show();

            }

        }
    }//end of getContact async class

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
                TextView tv_fullPrice = (TextView)view.findViewById(R.id.tv_full_price);
                TextView tv_propertyTitle = (TextView)view.findViewById(R.id.tv_property_title);
                TextView tv_propertyTitleFull = (TextView)view.findViewById(R.id.tv_property_title_full);
                TextView tv_propertyPropertyLandArea = (TextView)view.findViewById(R.id.tv_land_area);
                TextView tv_propertyCountry = (TextView)view.findViewById(R.id.tv_country);
                TextView tv_propertyCity = (TextView)view.findViewById(R.id.tv_location);
                TextView tv_propertyPhone = (TextView)view.findViewById(R.id.tv_contact);
                TextView tv_protperty_type = (TextView)view.findViewById(R.id.tv_protperty_type);
                TextView tv_protperty_status = (TextView)view.findViewById(R.id.tv_protperty_status);
                TextView tv_protperty_description = (TextView)view.findViewById(R.id.tv_protperty_description);
                ImageView image=(ImageView)view.findViewById(R.id.image);


                TextView tv_image_url = (TextView)view.findViewById(R.id.tv_url);

                TextView tv_property_rooms = (TextView)view.findViewById(R.id.tv_protperty_rooms);
                TextView tv_property_floors = (TextView)view.findViewById(R.id.tv_protperty_floors);
                TextView tv_property_bathrooms = (TextView)view.findViewById(R.id.tv_protperty_bathrooms);
                TextView tv_property_status_prpoperty = (TextView)view.findViewById(R.id.tv_protperty_status_property);
                TextView tv_propertyLocation = (TextView)view.findViewById(R.id.tv_location);

               // final BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                //final Bitmap imageBitmap = bitmapDrawable.getBitmap();





                final String propertyID = tv_id.getText().toString();
                final String propertyTitle = tv_propertyTitle.getText().toString();
                final String propertyTitleFull = tv_propertyTitleFull.getText().toString();
                final String propertyPrice = tv_price.getText().toString();
                final String fullPrice = tv_fullPrice.getText().toString();
                final String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                final String propertyCity = tv_propertyCity.getText().toString();
                final String propertyCountry = tv_propertyCountry.getText().toString();
                final String propertyContact = tv_propertyPhone.getText().toString();
                final String propertyType = tv_protperty_type.getText().toString();
                final String propertyStatus = tv_protperty_status.getText().toString();
                final String propertyDescription = tv_protperty_description.getText().toString();

                final String propertyLocation = tv_propertyLocation.getText().toString();
                final String propertyRooms = tv_property_rooms.getText().toString();
                final String propertyFloors = tv_property_floors.getText().toString();
                final String propertyBathrooms = tv_property_bathrooms.getText().toString();
                final String propertyStatus_prpoperty = tv_property_status_prpoperty.getText().toString();

                final  String imageURL = tv_image_url.getText().toString();


                Glide
                        .with(ViewSubmitedProperties.this)
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


                Log.e("TAG", "Property Id: " + propertyID);
                Log.e("TAG", "Property TILE: " + propertyTitle);
                Log.e("TAG", "Property TILE Full: " + propertyTitleFull);
                Log.e("TAG", "Property Price: " + propertyPrice);
                Log.e("TAG", "Property Area: " + propertyLandArea);
                Log.e("TAG", "Property Country: " + propertyCountry);
                Log.e("TAG", "Property City: " + propertyCity);
                Log.e("TAG", "Property Contact: " + propertyContact);
                Log.e("TAG", "Property Type: " + propertyType);
                Log.e("TAG", "Property Status: " + propertyStatus);
                Log.e("TAG", "Property Description: " + propertyDescription);

                image_ID = propertyID;


                final Dialog singleListViewItemDialog = new Dialog(ViewSubmitedProperties.this);
                singleListViewItemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                singleListViewItemDialog.setContentView(R.layout.single_list_item_dialog_own_property);

                TextView dialog_tv_propertyTitle = (TextView)singleListViewItemDialog.findViewById(R.id.dialog_tv_title);
                TextView dialog_tv_price = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_price);
                TextView dialog_tv_propertyPropertyLandArea = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_area);
                TextView dialog_tv_propertyCity = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_location);
                TextView dialog_tv_propertyPhone = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_contact);
                TextView dialog_tv_protperty_type = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_type);
                TextView dialog_tv_protperty_status = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_status);
                TextView dialog_tv_protperty_description = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_description);
                ImageView dialog_image = (ImageView)singleListViewItemDialog.findViewById(R.id.dialog_property_image);

                //registering button share and delete
                TextView dialog_tv_edit_property = (TextView) singleListViewItemDialog.findViewById(R.id.dialog_tv_edit_property);
                TextView dialog_tv_delete_property = (TextView) singleListViewItemDialog.findViewById(R.id.dialog_tv_delete_property);

                //setting click listern for button delete
                dialog_tv_delete_property.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageCode=2;

                        singleListViewItemDialog.dismiss();

                        new GetContacts().execute();



                        //call delete functionlaity here
                        Log.e("TAG", "ID ID: " + propertyID);
                    }
                });//end of click for delete button


                //button click lister for edit property

                dialog_tv_edit_property.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Log.e("TAG", "Property Id: " + propertyID);
                        Log.e("TAG", "Property TILE: " + propertyTitle);
                        Log.e("TAG", "Property TILEFull: " + propertyTitleFull);
                        Log.e("TAG", "Property Price: " + propertyPrice);
                        Log.e("TAG", "Property description: " + propertyDescription);
                        Log.e("TAG", "Property Area: " + propertyLandArea);
                        Log.e("TAG", "Property Country: " + propertyCountry);
                        Log.e("TAG", "Property City: " + propertyCity);
                        Log.e("TAG", "Property Location: " + propertyLocation);
                        Log.e("TAG", "Property Contact: " + propertyContact);
                        Log.e("TAG", "Property Type: " + propertyType);
                        Log.e("TAG", "Property Status: " + propertyStatus);
                        Log.e("TAG", "Property Rooms: " + propertyRooms);
                        Log.e("TAG", "Property Floors: " + propertyFloors);
                        Log.e("TAG", "Property Bathrooms: " + propertyBathrooms);
                        Log.e("TAG", "Property status_property: " + propertyStatus_prpoperty);



                        Intent editProperties = new Intent(ViewSubmitedProperties.this, EditProperty.class);
                        editProperties.putExtra("propertyID", propertyID);
                        editProperties.putExtra("propertyTitle", propertyTitle);
                        editProperties.putExtra("propertyTitleFull", propertyTitleFull);
                        editProperties.putExtra("propertyPrice", fullPrice);
                        editProperties.putExtra("propertyDescription", propertyDescription);
                        editProperties.putExtra("propertyLandArea", propertyLandArea);
                        editProperties.putExtra("propertyCountry", propertyCountry);
                        editProperties.putExtra("propertyCity", propertyCity);
                        editProperties.putExtra("propertyLocation", propertyLocation);
                        editProperties.putExtra("propertyContact", propertyContact);
                        editProperties.putExtra("propertyType", propertyType);
                        editProperties.putExtra("propertyStatus", propertyStatus);
                        editProperties.putExtra("propertyRooms", propertyRooms);
                        editProperties.putExtra("propertyFloors", propertyFloors);
                        editProperties.putExtra("propertyBathrooms", propertyBathrooms);
                        editProperties.putExtra("propertyStatus_prpoperty", propertyStatus_prpoperty);
                        startActivity(editProperties);
                        singleListViewItemDialog.dismiss();
                        finish();

                    }
                });//end of click listern for edit Property




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

                singleListViewItemDialog.show();


                dialog_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        singleListViewItemDialog.dismiss();

                        Intent i = new Intent(ViewSubmitedProperties.this, SinglePropertyImage.class);
                        i.putExtra("ID", propertyID);
                        i.putExtra("TITLE", propertyTitle);
                        i.putExtra("TITLEFULL", propertyTitleFull);
                        i.putExtra("PRICE", propertyPrice);
                        i.putExtra("CITY", propertyCity);
                        i.putExtra("PHONE", propertyContact);
                        i.putExtra("STATUS", propertyStatus);
                        i.putExtra("DESCRIPTION", propertyDescription);
                        i.putExtra("TYPE", propertyType);
                        i.putExtra("AREA", propertyLandArea);

                        startActivity(i);


                    }
                });//end of dialog_image click



            }

        });
    }//end of listview on itemClick



    public void listViewLognClickHandler(){

        listViewShowProperties.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ViewSubmitedProperties.this, "Manage Properties...", Toast.LENGTH_SHORT).show();


                TextView tv_id = (TextView)view.findViewById(R.id.tv_protperty_id);
                TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
                TextView tv_propertyTitle = (TextView)view.findViewById(R.id.tv_property_title);
                TextView tv_propertyPropertyLandArea = (TextView)view.findViewById(R.id.tv_land_area);
                TextView tv_propertyCountry = (TextView)view.findViewById(R.id.tv_country);
                TextView tv_propertyCity = (TextView)view.findViewById(R.id.tv_city);
                TextView tv_propertyLocation = (TextView)view.findViewById(R.id.tv_location);
                TextView tv_propertyPhone = (TextView)view.findViewById(R.id.tv_contact);
                TextView tv_protperty_type = (TextView)view.findViewById(R.id.tv_protperty_type);
                TextView tv_protperty_status = (TextView)view.findViewById(R.id.tv_protperty_status);
                TextView tv_protperty_description = (TextView)view.findViewById(R.id.tv_protperty_description);

                TextView tv_property_rooms = (TextView)view.findViewById(R.id.tv_protperty_rooms);
                TextView tv_property_floors = (TextView)view.findViewById(R.id.tv_protperty_floors);
                TextView tv_property_bathrooms = (TextView)view.findViewById(R.id.tv_protperty_bathrooms);
                TextView tv_property_status_prpoperty = (TextView)view.findViewById(R.id.tv_protperty_status_property);

                ImageView image=(ImageView)view.findViewById(R.id.image);

                final BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                final Bitmap imageBitmap = bitmapDrawable.getBitmap();


                final String propertyID = tv_id.getText().toString();
                final String propertyTitle = tv_propertyTitle.getText().toString();
                final String propertyPrice = tv_price.getText().toString();
                final String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                final String propertyCountry = tv_propertyCountry.getText().toString();
                final String propertyCity = tv_propertyCity.getText().toString();
                final String propertyLocation = tv_propertyLocation.getText().toString();
                final String propertyContact = tv_propertyPhone.getText().toString();
                final String propertyType = tv_protperty_type.getText().toString();
                final String propertyStatus = tv_protperty_status.getText().toString();
                final String propertyDescription = tv_protperty_description.getText().toString();
                final String propertyRooms = tv_property_rooms.getText().toString();
                final String propertyFloors = tv_property_floors.getText().toString();
                final String propertyBathrooms = tv_property_bathrooms.getText().toString();
                final String propertyStatus_prpoperty = tv_property_status_prpoperty.getText().toString();

                image_ID = propertyID;
                Log.e("TAG", "ID ID: " + image_ID);


                AlertDialog.Builder alert = new AlertDialog.Builder(ViewSubmitedProperties.this);
                alert.setTitle("ALERT!");
                alert.setMessage("Edit Or Delete your property");

                alert.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        //




                        Log.e("TAG", "Property Id: " + propertyID);
                        Log.e("TAG", "Property TILE: " + propertyTitle);
                        Log.e("TAG", "Property Price: " + propertyPrice);
                        Log.e("TAG", "Property description: " + propertyDescription);
                        Log.e("TAG", "Property Area: " + propertyLandArea);
                        Log.e("TAG", "Property Country: " + propertyCountry);
                        Log.e("TAG", "Property City: " + propertyCity);
                        Log.e("TAG", "Property Location: " + propertyLocation);
                        Log.e("TAG", "Property Contact: " + propertyContact);
                        Log.e("TAG", "Property Type: " + propertyType);
                        Log.e("TAG", "Property Status: " + propertyStatus);
                        Log.e("TAG", "Property Rooms: " + propertyRooms);
                        Log.e("TAG", "Property Floors: " + propertyFloors);
                        Log.e("TAG", "Property Bathrooms: " + propertyBathrooms);
                        Log.e("TAG", "Property status_property: " + propertyStatus_prpoperty);



                        Intent editProperties = new Intent(ViewSubmitedProperties.this, EditProperty.class);
                        editProperties.putExtra("propertyID", propertyID);
                        editProperties.putExtra("propertyTitle", propertyTitle);
                        editProperties.putExtra("propertyPrice", propertyPrice);
                        editProperties.putExtra("propertyDescription", propertyDescription);
                        editProperties.putExtra("propertyLandArea", propertyLandArea);
                        editProperties.putExtra("propertyCountry", propertyCountry);
                        editProperties.putExtra("propertyCity", propertyCity);
                        editProperties.putExtra("propertyLocation", propertyLocation);
                        editProperties.putExtra("propertyContact", propertyContact);
                        editProperties.putExtra("propertyType", propertyType);
                        editProperties.putExtra("propertyStatus", propertyStatus);
                        editProperties.putExtra("propertyRooms", propertyRooms);
                        editProperties.putExtra("propertyFloors", propertyFloors);
                        editProperties.putExtra("propertyBathrooms", propertyBathrooms);
                        editProperties.putExtra("propertyStatus_prpoperty", propertyStatus_prpoperty);
                        startActivity(editProperties);
                        finish();




//
                    }
                });//end of edit

                alert.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        imageCode=2;

                        new GetContacts().execute();

                        //call delete functionlaity here
                        Log.e("TAG", "ID ID: " + propertyID);
                    }
                });

                alert. create();
                alert.show();

                return true;
            }
        });


    }//end of listViewLognClickHandler

    public void deletingRecord(){

    }

      public void chatIconClick(){

          rl_chat_icon.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  Intent i = new Intent(ViewSubmitedProperties.this, LiveSupport.class);
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


}//***************** Shoaib Anwar ********************




