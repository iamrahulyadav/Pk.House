package house.pkhouse;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.PublicSuffixFilter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import javax.net.ssl.TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.ResourceBundle;

import house.pkhouse.Constant.AllURLs;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class LoginOrRegister extends AppCompatActivity {


    EditText etLogiMobile, etLoginPassword;
    EditText etRegisterName, etRegisterEmail, etRegisterPhone; //etRegisterPass,

    Spinner register_sp_select_country;
    TextView register_tv_country_code;
    EditText register_et_mobile_code, register_phone;

    String forgotPassNumber = null;

    LinearLayout ll_login_email, ll_login_pass, ll_dont_have_an_account;
    RelativeLayout rl_login_title_text;
    TextView tv_dont_have_accont;
    Button bt_login_now;

    LinearLayout ll_register_email, ll_register_name, ll_register_pass, ll_register_phone, ll_already_have_an_account;
    RelativeLayout rl_register_title_text;
    TextView tv_already_have_account, tv_forget_password;
    Button bt_create_account;

    private final String serverUrlLogin = AllURLs.LOGIN_NORMAL_USER; //"https://www.pk.house/app_webservices/login.php";
    private final String serverUrlRegistration = AllURLs.REGISTER_NORMAL_USER; // "https://www.pk.house/app_webservices/registration.php";

    private final String serverUrlForgotPassword = AllURLs.FORGOT_PASSWORD; // "https://www.pk.house/app_webservices/forgot.php";
    private final String asNormalUser = "normal";

    int loginRegister = 0;

    RelativeLayout rl_chat_icon;


    String name = null;

    SharedPreferences sharedPreferences;
    private ProgressBar bar;

    int value = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        init();
        startingWithZeroRestriction();
        visible_loginView();
        visibleRegisterViews();
        btLoginHandler();
        btCreateAccountHandler();
        forgoetPasswordHandler();
        spinnerValuesOnSelectionCountry();

        chatIconClick();
    }

    public void init(){

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(LoginOrRegister.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

        etLogiMobile = (EditText) findViewById(R.id.login_email);
        etLoginPassword = (EditText) findViewById(R.id.login_password);

        etRegisterName = (EditText) findViewById(R.id.register_name);
        etRegisterEmail = (EditText) findViewById(R.id.register_email);
        //etRegisterPass = (EditText) findViewById(R.id.register_pass);
        etRegisterPhone = (EditText) findViewById(R.id.register_phone);


        //registing views for login
        ll_login_email = (LinearLayout) findViewById(R.id.ll_login_email);
        ll_login_pass = (LinearLayout) findViewById(R.id.ll_login_password);
        ll_dont_have_an_account = (LinearLayout) findViewById(R.id.ll_tv_dont_have_account);
        rl_login_title_text = (RelativeLayout) findViewById(R.id.rl_login_title_text);
        tv_dont_have_accont = (TextView) findViewById(R.id.tv_dont_have_account);
        tv_forget_password = (TextView) findViewById(R.id.tv_forgot_password);
        bt_login_now = (Button) findViewById(R.id.bt_login_now);

        //registring views for registeration
        ll_register_name = (LinearLayout) findViewById(R.id.ll_register_name);
        ll_register_email = (LinearLayout) findViewById(R.id.ll_register_email);
        ll_register_pass = (LinearLayout) findViewById(R.id.ll_regiter_pass);
        ll_register_phone = (LinearLayout) findViewById(R.id.ll_regiter_phone);
        ll_already_have_an_account = (LinearLayout) findViewById(R.id.ll_tv_already_have_account);
        rl_register_title_text = (RelativeLayout) findViewById(R.id.rl_register_title_text);
        tv_already_have_account = (TextView) findViewById(R.id.tv_already_have_account);
        bt_create_account = (Button) findViewById(R.id.bt_create_account);


        register_sp_select_country = (Spinner) findViewById(R.id.sp_select_country);
        register_tv_country_code = (TextView) findViewById(R.id.tv_country_code);
        register_et_mobile_code = (EditText) findViewById(R.id.et_mobile_code);
        register_phone = (EditText) findViewById(R.id.register_phone);


        bar = (ProgressBar) this.findViewById(R.id.progressBar);


        Intent i = getIntent();
        if (i.getExtras()!=null){
            value = i.getExtras().getInt("value");
            Log.e("TAG", "THE 1 THE 1: " + value);
        }

    }//end of initialization method

    public void startingWithZeroRestriction(){
        etLogiMobile.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                String x = s.toString();
                if(x.startsWith("0"))
                {

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }

                if (x.startsWith("1")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("2")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("3")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("4")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("5")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("6")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("7")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }
                if (x.startsWith("8")){

                    Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    etLogiMobile.setText("");
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });//end for login editText


        register_et_mobile_code.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                String x = s.toString();
                if(x.startsWith("0"))
                {
                    //Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92 format", Toast.LENGTH_SHORT).show();
                    register_et_mobile_code.setText("");
                }


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });//end for restriction editText


    }



    public void visible_loginView(){
        tv_already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ll_register_name.setVisibility(View.GONE);
                ll_register_email.setVisibility(View.GONE);
               // ll_register_pass.setVisibility(View.GONE);
                ll_register_phone.setVisibility(View.GONE);
                ll_already_have_an_account.setVisibility(View.GONE);
                rl_register_title_text.setVisibility(View.GONE);
                tv_already_have_account.setVisibility(View.GONE);
                bt_create_account.setVisibility(View.GONE);

                ll_login_email.setVisibility(View.VISIBLE);
                ll_login_pass.setVisibility(View.VISIBLE);
                ll_dont_have_an_account.setVisibility(View.VISIBLE);
                rl_login_title_text.setVisibility(View.VISIBLE);
                tv_dont_have_accont.setVisibility(View.VISIBLE);
                bt_login_now.setVisibility(View.VISIBLE);




            }
        });
    }//end of viewing loginViews

    public void visibleRegisterViews(){

        tv_dont_have_accont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_register_name.setVisibility(View.VISIBLE);
                ll_register_email.setVisibility(View.VISIBLE);
              //  ll_register_pass.setVisibility(View.VISIBLE);
                ll_register_phone.setVisibility(View.VISIBLE);
                ll_already_have_an_account.setVisibility(View.VISIBLE);
                rl_register_title_text.setVisibility(View.VISIBLE);
                tv_already_have_account.setVisibility(View.VISIBLE);
                bt_create_account.setVisibility(View.VISIBLE);

                ll_login_email.setVisibility(View.GONE);
                ll_login_pass.setVisibility(View.GONE);
                ll_dont_have_an_account.setVisibility(View.GONE);
                rl_login_title_text.setVisibility(View.GONE);
                tv_dont_have_accont.setVisibility(View.GONE);
                bt_login_now.setVisibility(View.GONE);


            }
        });


    }


    public void btLoginHandler(){

        bt_login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int login_mail = etLogiMobile.getText().length();
                int login_pass = etLoginPassword.getText().length();
             //   String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String loginEmail = etLogiMobile.getText().toString();





                if (login_mail==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }

              /*  else if(!loginEmail.matches(emailPattern)){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }*/
                else if (login_pass==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter pass", Toast.LENGTH_SHORT).show();
                }
                else if (login_pass<=4){
                    Toast.makeText(LoginOrRegister.this, "Password should be atleast 5 charecters long", Toast.LENGTH_SHORT).show();
                }else {
                    String lMobile = etLogiMobile.getText().toString();
                    String lPass  = etLoginPassword.getText().toString();
                    Toast.makeText(LoginOrRegister.this, "Loging...", Toast.LENGTH_SHORT).show();


                    loginRegister = 1;


                    //Login(serverUrlLogin, lMobile, lPass, asNormalUser);
                    //bar.setVisibility(View.VISIBLE);


                    loginUser(lMobile, lPass, asNormalUser);

                    //AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    //asyncRequestObject.execute(serverUrlLogin, lMobile, lPass, asNormalUser);
                }

            }
        });
    }//end of login button handling

    public void btCreateAccountHandler(){

        bt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[0-9]{10}";
                String register_email = etRegisterEmail.getText().toString();
                String register_phone = etRegisterPhone.getText().toString();

                long sp_country = register_sp_select_country.getSelectedItemId();

                String registerMobileCode = register_et_mobile_code.getText().toString();

                int registerName = etRegisterName.getText().length();
                int registerEmail = etRegisterEmail.getText().length();
              //  int registerPass = etRegisterPass.getText().length();
                int registerPhone = etRegisterPhone.getText().length();


                if (registerName==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if (registerEmail==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Emial", Toast.LENGTH_SHORT).show();
                }

                else if (!register_email.matches(emailPattern)){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else if (sp_country==0){
                    Toast.makeText(LoginOrRegister.this, "Please Chose a Country for Phone", Toast.LENGTH_SHORT).show();
                }
                else if (registerMobileCode.length()==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Mobile Code", Toast.LENGTH_SHORT).show();
                }
                else if (registerMobileCode.length()==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Mobile Code", Toast.LENGTH_SHORT).show();
                }
                else if (registerMobileCode.length()!=3){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Mobile Code", Toast.LENGTH_SHORT).show();
                }

                else if (register_phone.length()==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                }

                else if (register_phone.length()<7){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Phone", Toast.LENGTH_SHORT).show();
                }


             /*   else if (registerPass==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (registerPass<=4){
                    Toast.makeText(LoginOrRegister.this, "Password should be atleast 5 charecters long", Toast.LENGTH_SHORT).show();
                }*/

                else {



                    String rName = etRegisterName.getText().toString();
                    String rEmail = etRegisterEmail.getText().toString();
                    String countrycode = register_tv_country_code.getText().toString();
                    String rPhone = etRegisterPhone.getText().toString();

                    String finalPhone  = countrycode+registerMobileCode+register_phone;
                    Log.e("TAG", "FinalPhone: " + finalPhone);
                  //  String rPass = etRegisterPass.getText().toString();

                    rPhone = finalPhone;

                    Toast.makeText(LoginOrRegister.this, "Registring", Toast.LENGTH_SHORT).show();


                    loginRegister = 2;

                    AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    asyncRequestObject.execute(serverUrlRegistration, rName, rEmail, rPhone);
                }


            }
        });

    }//end of createAccount button handling

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }



    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 8000);

            HttpConnectionParams.setSoTimeout(httpParameters, 8000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";

            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);


                //for login user
                if (loginRegister==1) {

                    nameValuePairs.add(new BasicNameValuePair("number", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("user_type", params[3]));

                }
                //for registring user
                if (loginRegister==2){

                    nameValuePairs.add(new BasicNameValuePair("name", params[1]));

                    nameValuePairs.add(new BasicNameValuePair("email", params[2]));

                    nameValuePairs.add(new BasicNameValuePair("phone", params[3]));

                }

                //for forget password
                if (loginRegister==3){

                    //nameValuePairs.add(new BasicNameValuePair("number", params[1]));
                    ///

                    HttpsURLConnection connection = null;
                    try {

                        URL url = new URL(AllURLs.FORGOT_PASSWORD); //"https://www.pk.house/app_webservices/add_property.php");


                        connection = (HttpsURLConnection) url.openConnection();
                        connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
                        connection.setReadTimeout(15000);
                        connection.setConnectTimeout(15000);
                        connection.setRequestMethod("POST");
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        Uri.Builder builder = new Uri.Builder()

                                .appendQueryParameter("number", forgotPassNumber);


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

                    ///


                }



                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                Log.e("TAG", "Resulted Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return jsonResult;

        }

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            bar.setVisibility(View.VISIBLE);
            tv_forget_password.setVisibility(View.GONE);



        }

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);




            Log.e("TAG", "Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(LoginOrRegister.this, "Server connection failed", Toast.LENGTH_SHORT).show();

                bar.setVisibility(View.GONE);
                tv_forget_password.setVisibility(View.VISIBLE);



                return;

            }

            String jsonResult = returnParsedJsonObject(result);

            Log.e("TAG", "RESULT 123" + result);
            Log.e("TAG", "RESULT 12345" + jsonResult);


            if (loginRegister==1){

                if (result!=null){



                    if (name==null){
                        Toast.makeText(LoginOrRegister.this, "Invalid Password or Email", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);
                    }
                    else {

                        Toast.makeText(LoginOrRegister.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);

                        Log.e("TAG", "TTTTTT 123123: " + value);

                        if (value==1){

                            finish();
                        }
                        else {
                            Intent submitProperty = new Intent(LoginOrRegister.this, SubmitProperty.class);
                            submitProperty.putExtra("from", "normal");
                            startActivity(submitProperty);
                            finish();
                        }
                    }
                }
            }




            if (loginRegister==2){

                if (result!=null) {

                    if (jsonResult.equals("already registered")) {

                        Toast.makeText(LoginOrRegister.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);

                        hindingViewOfRegistration();

                        return;

                    }

                    if (jsonResult.equals("registered successfully")) {

                        Toast.makeText(LoginOrRegister.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);



                            Intent submitProperty = new Intent(LoginOrRegister.this, LoginOrRegister.class);
                            submitProperty.putExtra("from", "normal");

                        if (value==1){
                            submitProperty.putExtra("value", value);
                        }
                            startActivity(submitProperty);
                            finish();



                    }

                }

            }


            if (loginRegister==3){


                if (result!=null) {

                    if (jsonResult.equals("false")) {

                        Toast.makeText(LoginOrRegister.this, "Number Not Found", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);

                        tv_forget_password.setVisibility(View.GONE);
                        etLogiMobile.setText("");
                        etLoginPassword.setText("");

                        finish();
                        return;


                    }

                    if (jsonResult.equals("true")) {

                        Toast.makeText(LoginOrRegister.this, "Your Password Has Been sent to Your Number", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        tv_forget_password.setVisibility(View.GONE);
                        etLogiMobile.setText("");
                        etLoginPassword.setText("");

                        finish();
                        return;


                    }

                    if (jsonResult.equals("You are not a registered user")) {


                        Toast.makeText(LoginOrRegister.this, "Phone Number is not registered", Toast.LENGTH_SHORT).show();

                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);

                    }

                }

            }



        }

        private StringBuilder inputStreamToString(InputStream is) {

            String rLine = "";

            StringBuilder answer = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {

                while ((rLine = br.readLine()) != null) {

                    answer.append(rLine);

                }

            } catch (IOException e) {

// TODO Auto-generated catch block

                e.printStackTrace();

            }

            return answer;

        }

    }

    private String returnParsedJsonObject(String result){

        JSONObject resultObject = null;

        String returnedResult = null;

        try {

            resultObject = new JSONObject(result);

            boolean flag = true;



            if (loginRegister==1) {

                resultObject = new JSONObject(result);



                    name = resultObject.getString("name");
                    String email = resultObject.getString("email");
                    String phone = resultObject.getString("number");
                    String password = resultObject.getString("password");
                String userId = resultObject.getString("user_id");

                    SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("phone", phone);
                editor.putString("user_id", userId);
                    editor.clear();
                    editor.commit();


                    Log.e("TAG", "RESULT 111 " + email);
                    Log.e("TAG", "RESULT 111 " + name);
                    Log.e("TAG", "RESULT 111 " + phone);
                    Log.e("TAG", "RESULT 111 " + password);
                Log.e("TAG", "RESULT 111 " + userId);


            }

            if (loginRegister==2) {
                returnedResult = resultObject.getString("status");

                Log.e("TAG", "RESULT 111 " + returnedResult);

            }

            if (loginRegister==3){
                returnedResult = resultObject.getString("status");

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }

    public void hindingViewOfRegistration(){


        ll_register_name.setVisibility(View.GONE);
        ll_register_email.setVisibility(View.GONE);
       // ll_register_pass.setVisibility(View.GONE);
        ll_register_phone.setVisibility(View.GONE);
        ll_already_have_an_account.setVisibility(View.GONE);
        rl_register_title_text.setVisibility(View.GONE);
        tv_already_have_account.setVisibility(View.GONE);
        bt_create_account.setVisibility(View.GONE);

        ll_login_email.setVisibility(View.VISIBLE);
        ll_login_pass.setVisibility(View.VISIBLE);
        ll_dont_have_an_account.setVisibility(View.VISIBLE);
        rl_login_title_text.setVisibility(View.VISIBLE);
        tv_dont_have_accont.setVisibility(View.VISIBLE);
        bt_login_now.setVisibility(View.VISIBLE);
    }


    public void forgoetPasswordHandler(){
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //careating dialog for forgot password

                final Dialog forGetPassword = new Dialog(LoginOrRegister.this);
                forGetPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
                forGetPassword.setContentView(R.layout.dialog_forgot_password);

                final EditText etPhone = (EditText) forGetPassword.findViewById(R.id.dialo_email_forgot_password);


                etPhone.addTextChangedListener(new TextWatcher()
                {
                    public void afterTextChanged(Editable s)
                    {
                        String x = s.toString();
                        if(x.startsWith("0"))
                        {

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                         if (x.startsWith("1")){

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("2")){

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("3")){

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("4")){

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("5")){

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("6")){

                            Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("7")){

                        Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                        etPhone.setText("");
                    }
                        if (x.startsWith("8")){

                        Toast.makeText(LoginOrRegister.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                        etPhone.setText("");
                    }



                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {

                    }
                });//end for login editText


                Button btOk = (Button) forGetPassword.findViewById(R.id.dialog_forgetpassword_bt_ok);

                btOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String dialogEmail = etPhone.getText().toString();
                        if (dialogEmail.isEmpty()){

                            Toast.makeText(LoginOrRegister.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        }
                       else {

                            Log.e("TAG", " Email  " + dialogEmail);
                            forGetPassword.dismiss();

                            loginRegister = 3;

                            AsyncDataClass asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(serverUrlForgotPassword, dialogEmail);

                            forgotPassNumber =  dialogEmail;
                        }


                    }
                });

                forGetPassword.show();
            }
        });
    }

    //spiner valuse for register country selection
    public void spinnerValuesOnSelectionCountry(){
        register_sp_select_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){

                    register_tv_country_code.setText("");


                }
                if (position==1){

                    register_tv_country_code.setText("92");


                }

                if (position==2){



                    register_tv_country_code.setText("971");


                }

                if (position==3){


                    register_tv_country_code.setText("968");


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end of spinner values on Selection



    //login User
    private void loginUser(String number, String password, String usertype) {
        class LoginUser extends AsyncTask<String, Void, String>{


            LoginUserClass ruc = new LoginUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               bar.setVisibility(View.VISIBLE);
                tv_forget_password.setVisibility(View.GONE);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                bar.setVisibility(View.GONE);
                tv_forget_password.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "Result: " + s,Toast.LENGTH_LONG).show();

                String jsonResult = returnParsedJsonObject(s);
                //Toast.makeText(getApplicationContext(), "Result: " + jsonResult ,Toast.LENGTH_LONG).show();

                if (name==null){
                    Toast.makeText(LoginOrRegister.this, "Invalid Password or Email", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                    tv_forget_password.setVisibility(View.VISIBLE);
                } else {

                    Toast.makeText(LoginOrRegister.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);

                    if (value==1){

                        finish();
                    }
                    else {

                        Intent submitProperty = new Intent(LoginOrRegister.this, SubmitProperty.class);
                        submitProperty.putExtra("from", "normal");
                        startActivity(submitProperty);
                        finish();
                    }
                }


            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();

                //for login user
                if (loginRegister==1) {


                    data.put("number",params[0]);
                    data.put("password",params[1]);
                    data.put("user_type",params[2]);

                }
                //for registring user
                if (loginRegister==2){

                    data.put("name",params[0]);
                    data.put("email",params[1]);
                    data.put("phone",params[2]);

                }

                //for forget password
                if (loginRegister==3){

                    data.put("number",params[0]);
                }



                String result = ruc.sendPostRequest(serverUrlLogin,data);

              //  Toast.makeText(LoginOrRegister.this, "rss : " + result, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "RESULT RESULT : " + result);

                return  result;


            }
        }

        LoginUser ru = new LoginUser();
        ru.execute(number,password,usertype);
    }



    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginOrRegister.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }


}
