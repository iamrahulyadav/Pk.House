package house.pkhouse;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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
import house.pkhouse.adapter.FranchiserAdapter;
import house.pkhouse.adapter.SingleImageDataAdapter;

public class ShowFranchisers extends AppCompatActivity {

    RelativeLayout rl_chat_icon;
    ListView listViewShowProperties;
    private ProgressBar progressBar;


    Dialog singleIamgeListDialog;

    final String TAG = "ShowLiisActvity";



    ListView list;
    FranchiserAdapter adapter;

    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> singlePropertyImageList;

    String country, city, area, mobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_franchisers);




        init();

        showListofProperties();
        chatIconClick();


    }

    public void init(){

        Intent i = getIntent();
        country = i.getExtras().getString("country", "");
        city = i.getExtras().getString("city", "");
       area =  i.getExtras().getString("area", "");
       mobile =  i.getExtras().getString("mobile", "");

        Log.e("TAG", "Country is: " + country);
        Log.e("TAG", "Country is: " + mobile);
        Log.e("TAG", "Country is: " + city);
        Log.e("TAG", "Country is: " + area);


        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        contactList = new ArrayList<>();
        singlePropertyImageList = new ArrayList<>();

        singleIamgeListDialog = new Dialog(ShowFranchisers.this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ShowFranchisers.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

        list=(ListView)findViewById(R.id.listView_show_properties);

        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);

        if (sharedPreferences1!=null){
            String name = sharedPreferences1.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                rl_chat_icon.setVisibility(View.VISIBLE);



            }

        }


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

                URL url = new URL(AllURLs.GET_FRANCHISERS_BY_APPLYING_FILTER); //"https://www.pk.house/app_webservices/franchiser_listing.php");


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
                        .appendQueryParameter("mobile", mobile)
                        .appendQueryParameter("location", area);






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
                    JSONArray contacts = jsonObj.getJSONArray("franchiser_data");

                    Log.e("TAG", "RESULT 2" + contacts);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        // for (int i = 0; i < contacts.; i++) {

                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("user_id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String number = c.getString("mobile_number");
                        String landlineNumber = c.getString("landline");
                        String companyName = c.getString("company_name");
                        String area_location = c.getString("area_location");
                        String city = c.getString("city");
                        String country = c.getString("country");
                        String Imageurl = c.getString("profile_image");

                        final String staticURL = AllURLs.FRANCHISER_CNIC; //"https://www.pk.house/frontend/franchiser_cnic/";

                        String imageURL = staticURL + Imageurl;

                        Log.e("TAG", "URL 123 " + imageURL);

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("number", number);
                        contact.put("company_name", companyName);
                        contact.put("landline", landlineNumber);
                        contact.put("area_location", area_location);
                        contact.put("city", city);
                        contact.put("country", country);
                        contact.put("imageurl", imageURL);


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

            adapter=new FranchiserAdapter(ShowFranchisers.this, contactList);
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }



    }//end of GettingDataFromServer


    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ShowFranchisers.this, FranchiserChatRoom.class);
                startActivity(i);
            }
        });
    }


}//***************** Shoaib Anwar #  ********************




