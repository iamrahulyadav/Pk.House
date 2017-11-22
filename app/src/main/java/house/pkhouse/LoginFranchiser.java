package house.pkhouse;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import house.pkhouse.Constant.AllURLs;

public class LoginFranchiser extends AppCompatActivity {


    EditText loginMobile, loginPassword;
    EditText registerName, registerEmail, registerPhone;
    Button loginButton;
    LinearLayout ll_login_ui, ll_register_ui;
    private ProgressBar bar;
    TextView tvSignUp, tvLogin;
    String name = null;
    TextView tv_forget_password;

    Spinner register_sp_select_country;
    TextView register_tv_country_code;
    EditText register_et_mobile_code, register_phone;

    RelativeLayout rl_chat_icon;
    
    private final String serverUrlLogin = AllURLs.LOGIN_FRANCHISER; //"https://www.pk.house/app_webservices/login_franchiser.php";
    private final String serverUrlRegister = AllURLs.FRANCHISER_REGISTRATION; // "https://www.pk.house/app_webservices/add_franchise_registration.php";
    private final String serverUrlForgotPassword = AllURLs.FORGOT_PASSWORD; // "https://www.pk.house/app_webservices/forgot.php";
    private final String asFranchiser = "franchiser";

    int indicator = 1;
    int value = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_franchiser);


        init();
        startingWithZeroRestriction();
        btLoginHandler();
        singUpScreen();
        visibleLoginUI();
        spinnerValuesOnSelectionCountry();
        forgoetPasswordHandler();
        chatIconClick();
    }

    public void init(){
        //initializing for login view
        loginMobile  = (EditText) findViewById(R.id.login_email);
        loginPassword  = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.bt_login_now);

        //initializing for register view
        registerName = (EditText) findViewById(R.id.et_register_name);
        registerEmail = (EditText) findViewById(R.id.et_register_email);
        registerPhone = (EditText) findViewById(R.id.register_phone);

        ll_login_ui = (LinearLayout) findViewById(R.id.login_ui);
        ll_register_ui = (LinearLayout) findViewById(R.id.register_ui);
        tvSignUp = (TextView) findViewById(R.id.tv_dont_have_account);
        tvLogin = (TextView) findViewById(R.id.tv_have_account);

        register_sp_select_country = (Spinner) findViewById(R.id.sp_select_country);
        register_tv_country_code = (TextView) findViewById(R.id.tv_country_code);
        register_et_mobile_code = (EditText) findViewById(R.id.et_mobile_code);
        register_phone = (EditText) findViewById(R.id.register_phone);

        tv_forget_password = (TextView) findViewById(R.id.tv_forgot_password);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(LoginFranchiser.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

        Intent i = getIntent();
        if (i.getExtras()!=null){
            value = i.getExtras().getInt("value");
        }

    }



    public void startingWithZeroRestriction(){
        loginMobile.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                String x = s.toString();
                if(x.startsWith("0"))
                {
                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92 format", Toast.LENGTH_LONG).show();
                    loginMobile.setText("");
                }

                if (x.startsWith("1")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("2")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("3")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("4")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("5")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("6")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("7")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
                }
                if (x.startsWith("8")){

                    Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    loginMobile.setText("");
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
                   // Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92 format", Toast.LENGTH_LONG).show();
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



    public void btLoginHandler(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (indicator==1) {

                    int login_mail = loginMobile.getText().length();
                    int login_pass = loginPassword.getText().length();
                    String login_email = loginMobile.getText().toString();
                    if (login_mail == 0) {
                        Toast.makeText(LoginFranchiser.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                    } else if (login_pass == 0) {
                        Toast.makeText(LoginFranchiser.this, "Please Enter pass", Toast.LENGTH_SHORT).show();
                    } else if (login_pass <= 4) {
                        Toast.makeText(LoginFranchiser.this, "Password should be atleast 5 charecters long", Toast.LENGTH_SHORT).show();
                    } else {
                        String lEmail = loginMobile.getText().toString();
                        String lPass = loginPassword.getText().toString();
                        Toast.makeText(LoginFranchiser.this, "Loging...", Toast.LENGTH_SHORT).show();

                        loginUser(lEmail, lPass);

                        //AsyncDataClass asyncRequestObject = new AsyncDataClass();
                        //asyncRequestObject.execute(serverUrlLogin, lEmail, lPass, asFranchiser);
                    }

                }//end for login part
                
                
                if (indicator==2){
                    

                    int rName  = registerName.getText().length();
                    int rEmail = registerEmail.getText().length();
                    int rPhone = registerPhone.getText().length();
                    String mRegisterName = registerName.getText().toString();
                    String mRegisterEmail = registerEmail.getText().toString();
                    String mRegisterPhone = registerPhone.getText().toString();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                    long sp_country = register_sp_select_country.getSelectedItemId();

                    String registerMobileCode = register_et_mobile_code.getText().toString();

                    //  int registerPass = etRegisterPass.getText().length();



                    if (rName==0){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    }
                    else if (rEmail==0){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    }
                    else if(!mRegisterEmail.matches(emailPattern)){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    }
                    else if (sp_country==0){
                        Toast.makeText(LoginFranchiser.this, "Please Chose a Country for Phone", Toast.LENGTH_SHORT).show();
                    }
                    else if (registerMobileCode.length()==0){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Mobile Code", Toast.LENGTH_SHORT).show();
                    }
                    else if (registerMobileCode.length()==0){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Mobile Code", Toast.LENGTH_SHORT).show();
                    }
                    else if (registerMobileCode.length()!=3){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Valid Mobile Code", Toast.LENGTH_SHORT).show();
                    }

                    else if (register_phone.length()==0){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                    }

                    else if (register_phone.length()<7){
                        Toast.makeText(LoginFranchiser.this, "Please Enter Valid Phone", Toast.LENGTH_SHORT).show();
                    }



                    else {

                        String countrycode = register_tv_country_code.getText().toString();
                        String rePhone = register_phone.getText().toString();
                        String finalPhone  = countrycode+registerMobileCode+rePhone;
                        Log.e("TAG", "FinalPhone: " + finalPhone);
                        //  String rPass = etRegisterPass.getText().toString();

                        mRegisterPhone = finalPhone;

                        AsyncDataClass asyncRequestObject = new AsyncDataClass();

                        asyncRequestObject.execute(serverUrlRegister, mRegisterName, mRegisterEmail, mRegisterPhone);
                    }


                }
                
            }
        });
    }//end of login button handling


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


                if (indicator==1) {

                    nameValuePairs.add(new BasicNameValuePair("number", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                    // nameValuePairs.add(new BasicNameValuePair("user_type", params[3]));

                }
                if (indicator==2){

                    nameValuePairs.add(new BasicNameValuePair("name", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("email", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("number", params[3]));

                }

                //for forget password
                if (indicator==3){

                    nameValuePairs.add(new BasicNameValuePair("number", params[1]));
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

                Toast.makeText(LoginFranchiser.this, "Server connection failed", Toast.LENGTH_LONG).show();

                bar.setVisibility(View.GONE);
                tv_forget_password.setVisibility(View.VISIBLE);



                return;

            }

            String jsonResult = returnParsedJsonObject(result);

            Log.e("TAG", "RESULT 123" + result);
            Log.e("TAG", "RESULT 123" + jsonResult);



            if (indicator==1) {

                if (result != null) {


                    if (name == null) {
                        Toast.makeText(LoginFranchiser.this, "Invalid Password or Phone", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);

                    } else {

                        Toast.makeText(LoginFranchiser.this, "Login Successfully ", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);

                        if (value==1){

                            finish();
                        }
                        else {
                            Intent submitProperty = new Intent(LoginFranchiser.this, SubmitProperty.class);
                            submitProperty.putExtra("from", "franchiser");
                            startActivity(submitProperty);
                            finish();
                        }
                    }
                }

            }

            if (indicator==2){

                if (result != null) {

                    if (jsonResult.equals("Already-Registered")) {
                        Toast.makeText(LoginFranchiser.this, "Already Register", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);
                    } else if (jsonResult.equals("Successfully-Registered")){

                        Toast.makeText(LoginFranchiser.this, "Register Successfully ", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);

                        Intent startLogin = new Intent(LoginFranchiser.this, LoginFranchiser.class);
                        if (value==1){
                            startLogin.putExtra("value", value);
                        }
                        startActivity(startLogin);
                        finish();
/*
                        Intent submitProperty = new Intent(LoginFranchiser.this, SubmitProperty.class);
                        submitProperty.putExtra("from", "franchiser");
                        startActivity(submitProperty);
                        finish();*/

                    }
                }
            }

            if (indicator==3){


                if (result!=null) {



                    if (jsonResult.equals("true")) {

                        Toast.makeText(LoginFranchiser.this, "Your Password Has Been sent to Your Number", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        tv_forget_password.setVisibility(View.GONE);
                       // etLogiMobile.setText("");
                        //etLoginPassword.setText("");

                        finish();
                        return;


                    }

                    if (jsonResult.equals("You are not a registered user")) {


                        Toast.makeText(LoginFranchiser.this, "Phone Number is not registered", Toast.LENGTH_SHORT).show();

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

        if (indicator == 1) {

        try {

            resultObject = new JSONObject(result);

            boolean flag = true;





                resultObject = new JSONObject(result);


                name = resultObject.getString("name");
                String email = resultObject.getString("email");
                String phone = resultObject.getString("number");
                String password = resultObject.getString("password");
                String userId = resultObject.getString("fr_id");
               String username = resultObject.getString("username");
                String usertype = resultObject.getString("user_type");
                String companyName = resultObject.getString("company_name");

                String areaLocation = resultObject.getString("area_location");
                String city = resultObject.getString("city");
                String country = resultObject.getString("country");
                String profileImage = resultObject.getString("profile_image");



                SharedPreferences sharedPreferences = getSharedPreferences("franchiser", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("email", email);
                editor.putString("phone", phone);
                editor.putString("password", password);
                editor.putString("fr_id", userId);
            editor.putString("username", username);
            editor.putString("user_type", usertype);
            editor.putString("company_name", companyName);

            editor.putString("area_location", areaLocation);
            editor.putString("country", country);
            editor.putString("city", city);
            editor.putString("profile_image", profileImage);

                editor.clear();
                editor.commit();


                Log.e("TAG", "RESULT 111 " + email);
                Log.e("TAG", "RESULT 111 " + name);
                Log.e("TAG", "RESULT 111 " + phone);
                Log.e("TAG", "RESULT 111 " + password);
                Log.e("TAG", "RESULT 111 " + userId);


            }catch(JSONException e){

                e.printStackTrace();

            }

        }//end of if for indicator 1

        if (indicator==2){

            try {

                resultObject = new JSONObject(result);

                boolean flag = true;





                resultObject = new JSONObject(result);


                //get Result for registring franchiser

                //name = resultObject.getString("name");
                String ress = resultObject.getString("status");

                returnedResult = ress;



            }catch(JSONException e){

                e.printStackTrace();

            }


        }


        if (indicator==3){

            try {

                resultObject = new JSONObject(result);

                boolean flag = true;





                resultObject = new JSONObject(result);


                //get Result for registring franchiser

                //name = resultObject.getString("name");
                String ress = resultObject.getString("status");

                returnedResult = ress;



            }catch(JSONException e){

                e.printStackTrace();

            }


        }




        return returnedResult;

    }

    public void singUpScreen(){
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indicator = 2;

                ll_login_ui.setVisibility(View.GONE);
                ll_register_ui.setVisibility(View.VISIBLE);
                loginButton.setText("Register Now");



              /*  Intent franchiserRegistrationScreen =  new Intent(LoginFranchiser.this, UpdateFranchiserProfile.class);
                startActivity(franchiserRegistrationScreen);

                finish();
*/
            }
        });
    }//end of sign up ui button

    public void visibleLoginUI(){


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                indicator = 1;

                ll_login_ui.setVisibility(View.VISIBLE);
                ll_register_ui.setVisibility(View.GONE);
                loginButton.setText("Login Now");

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


//forgot password function
    public void forgoetPasswordHandler(){
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //careating dialog for forgot password

                final Dialog forGetPassword = new Dialog(LoginFranchiser.this);
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

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("1")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("2")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("3")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("4")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("5")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("6")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("7")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                            etPhone.setText("");
                        }
                        if (x.startsWith("8")){

                            Toast.makeText(LoginFranchiser.this, "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(LoginFranchiser.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Log.e("TAG", " Email  " + dialogEmail);
                            forGetPassword.dismiss();

                            indicator = 3;

                            AsyncDataClass asyncRequestObject = new AsyncDataClass();
                            asyncRequestObject.execute(serverUrlForgotPassword, dialogEmail);
                        }


                    }
                });

                forGetPassword.show();
            }
        });
    }//end of forgot password\


    //login User
    private void loginUser(String number, String password) {
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
                    Toast.makeText(LoginFranchiser.this, "Invalid Password or Email", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                    tv_forget_password.setVisibility(View.VISIBLE);
                } else {

                    Toast.makeText(LoginFranchiser.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                    tv_forget_password.setVisibility(View.VISIBLE);

                    if (value==1){

                        finish();
                    }
                    else {

                    Intent submitProperty = new Intent(LoginFranchiser.this, SubmitProperty.class);
                    submitProperty.putExtra("from", "normal");
                    startActivity(submitProperty);
                    finish();

                }
                }


            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();

                if (indicator==1) {

                    data.put("number",params[0]);
                    data.put("password",params[1]);

                }
                if (indicator==2){


                    data.put("name",params[0]);
                    data.put("email",params[1]);
                    data.put("number",params[1]);

                }

                //for forget password
                if (indicator==3){

                    data.put("name",params[0]);

                }


                String result = ruc.sendPostRequest(serverUrlLogin,data);

                //  Toast.makeText(LoginOrRegister.this, "rss : " + result, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "RESULT RESULT : " + result);

                return  result;


            }
        }

        LoginUser ru = new LoginUser();
        ru.execute(number,password);
    }


    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginFranchiser.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }


}
