package house.pkhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

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

import javax.net.ssl.HttpsURLConnection;

import house.pkhouse.Constant.AllURLs;

public class UpdateNormalUser extends AppCompatActivity {

    EditText etupdateName, etUpdatePhone, etNewPassword;
    Button btUpdate;

    String mName, mPhone, mPassword, String, mEmail;
    String userId;

    private ProgressBar bar;

    RelativeLayout rl_chat_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_normal_user);

        init();
        getPrefereces();
        updatButtonHandler();
        chatIconClick();
    }

    public void init(){
        etupdateName = (EditText) findViewById(R.id.register_name);
        etUpdatePhone = (EditText) findViewById(R.id.register_phone);
        etNewPassword = (EditText) findViewById(R.id.new_password);

        btUpdate = (Button) findViewById(R.id.bt_update_account);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(UpdateNormalUser.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

    }

    public void getPrefereces(){


        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        String sName = sharedPreferences.getString("name", null);

        if (sName!=null){

            etupdateName.setText(sName);
            userId = sharedPreferences.getString("user_id", null);
            String sPhone = sharedPreferences.getString("phone", null);
            String sEmail = sharedPreferences.getString("email", null);
            mEmail = sEmail;
            etUpdatePhone.setText(sPhone);

        }


    }
    
    public void updatButtonHandler(){
        
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                
                if (etupdateName.getText().length()==0){

                    Toast.makeText(UpdateNormalUser.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (etUpdatePhone.getText().length()==0){
                    Toast.makeText(UpdateNormalUser.this, "Please Phone", Toast.LENGTH_SHORT).show();
                }
                else if (etNewPassword.getText().length()==0){
                    Toast.makeText(UpdateNormalUser.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    
                }
                else {
             
                    
                    String name = etupdateName.getText().toString();
                    String phone = etUpdatePhone.getText().toString();
                    String passwrod = etNewPassword.getText().toString();
                    
                    mName = name;
                    mPhone = phone;
                    mPassword = passwrod;

                    new LoadingToServer().execute();
                    
                }
            }
        });
    }//end for button click


    //async class

    public class LoadingToServer extends AsyncTask<String , Void ,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection connection = null;
            try {

                URL url = new URL(AllURLs.UPDATE_NORMAL_USER_ACCOUNT); //"https://www.pk.house/app_webservices/update_normal_account.php");


                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("user_id", userId)
                        .appendQueryParameter("name", mName)
                        .appendQueryParameter("email", mEmail)
                        .appendQueryParameter("number", mPhone)
                        .appendQueryParameter("password", mPassword)
                        .appendQueryParameter("user_type", "normal");



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

            String responce =  returnParsedJsonObject(result);
            Log.e("TAG", "RESULT 123 " + result);
            Log.e("TAG", "RESULT 1233 " + responce);
            if (responce == null)
            {
                bar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Server not Responding...", Toast.LENGTH_SHORT).show();
            }
            else if(responce.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                bar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Data Update Successfully...",Toast.LENGTH_SHORT).show();

                finish();



            }else if (result.equalsIgnoreCase("Already-Registered")){

                // If username and password does not match display a error message

                bar.setVisibility(View.GONE);

                Toast.makeText(UpdateNormalUser.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                bar.setVisibility(View.GONE);
                Toast.makeText(UpdateNormalUser.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

            }
        }

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


    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UpdateNormalUser.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }


}
