package house.pkhouse;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.style.UpdateAppearance;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

import co.geeksters.googleplaceautocomplete.lib.CustomAutoCompleteTextView;
import house.pkhouse.Constant.AllURLs;
import house.pkhouse.loader.ImagesLoader;

public class UpdateFranchiserProfile extends AppCompatActivity {


    EditText etName, etCompanyName, etEmail, etPhone, etNewPassword, etPassword, etCountry, etCity, etLandlineNo;
    ImageView prifileImage;
    Button btCreateAccount;

    String userType = "franchiser";
    String mId, mName, mEmail, mPhone, mCountry, mCity, mLocation, mPassword, mCompanyName, mLandLineNumber;
    String profileImg;
    String encodedImage3="";
    String timestamp3;
    Calendar myCalendar = Calendar.getInstance();

    private CustomAutoCompleteTextView etLocation;


    File imageFileFromCamera;
    Uri filePath3 = null;


    RelativeLayout rl_chat_icon;

    int imageSelecter = 0;

    public static int CAMERA_CODE = 1;
    public static int GALLERY_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 000000;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 000001;



    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiser_registration);

        init();
        galaryImageSelectionHandler();
        registerButtonHandler();
        chatIconClick();
    }


    public void init(){


        etName = (EditText) findViewById(R.id.register_name);
        etCompanyName = (EditText) findViewById(R.id.register_company_name);
        etEmail = (EditText) findViewById(R.id.register_email);
        etPhone = (EditText) findViewById(R.id.register_phone);
        etLandlineNo = (EditText) findViewById(R.id.register_phone_landline);
        etNewPassword = (EditText) findViewById(R.id.new_password);
        etCountry = (EditText) findViewById(R.id.register_country);
        etCity = (EditText) findViewById(R.id.register_city);
       // etLocation = (EditText) findViewById(R.id.register_location);
        etPassword = (EditText) findViewById(R.id.register_pass);
        prifileImage = (ImageView) findViewById(R.id.circleView);

        etLocation = (CustomAutoCompleteTextView) findViewById(R.id.register_location);

        btCreateAccount = (Button) findViewById(R.id.bt_create_account);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);


        etPhone.setInputType(InputType.TYPE_NULL);
        etPhone.setTextIsSelectable(true);
        etPhone.setKeyListener(null);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(UpdateFranchiserProfile.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);


        SharedPreferences sharedPreferences = getSharedPreferences("franchiser", 0);
        String name = sharedPreferences.getString("name", null);

        if (name!=null) {


            String email = sharedPreferences.getString("email", "");
            String phone = sharedPreferences.getString("phone", "");
            String password = sharedPreferences.getString("password", "");
            String fr_id = sharedPreferences.getString("fr_id", "");
            String company_name = sharedPreferences.getString("company_name", "");
            String area_location = sharedPreferences.getString("area_location", "");
            String city = sharedPreferences.getString("city", "");
            String country = sharedPreferences.getString("country", "");
            String profile_image = sharedPreferences.getString("profile_image", "");


            Log.e("TAG", "FRANCHISER: email " + email);
            Log.e("TAG", "FRANCHISER: phone " + phone);
            Log.e("TAG", "FRANCHISER: password " + password);
            Log.e("TAG", "FRANCHISER: fr_id " + fr_id);
            Log.e("TAG", "FRANCHISER: company name " + company_name);
            Log.e("TAG", "FRANCHISER: are location " + area_location);
            Log.e("TAG", "FRANCHISER: city " + city);
            Log.e("TAG", "FRANCHISER: country " + country);
            Log.e("TAG", "FRANCHISER: profile_image " + profile_image);


    /*        if (company_name.equals(null));
            {
                company_name = "";
            }
            if (country.equals(null));
            {
                country = "";
            }
            if (city.equals(null));
            {
                city = "";
            }
            if (area_location.equals(null));
            {
                area_location = "";
            }
*/

            etName.setText(name);

            etEmail.setText(email);
            etCompanyName.setText(company_name);
            etPhone.setText(phone);
            etPassword.setText(password);
            etNewPassword.setText(password);
            etCountry.setText(country);
            etCity.setText(city);
            etLocation.setText(area_location);


            mId = fr_id;
            mName = name;
            mEmail = email;
            mPhone = phone;
            mPassword = password;
            mCompanyName = company_name;
            mCountry = country;
            mCity = city;
            mLocation = area_location;
            profileImg = profile_image;



            loadImages();
        }


    }


    public void loadImages(){

        ImagesLoader imageLoader = new ImagesLoader(UpdateFranchiserProfile.this);

        String ProfileImageUrl = AllURLs.FRANCHISER_CNIC + profileImg; //"https://www.pk.house/frontend/franchiser_cnic/"+profileImg;

        Log.e("TAG", "URL IMAGE " + ProfileImageUrl);




                //oldBitmap1 = ((BitmapDrawable)second.image1.getDrawable()).getBitmap();

        imageLoader.DisplayImage(ProfileImageUrl, prifileImage);

                Long tsLong = System.currentTimeMillis() / 1000;

               // bitmapChecker = 1;


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCameraPermission();
        checkWriteExternalPermission();
    }


    public void checkCameraPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(UpdateFranchiserProfile.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(UpdateFranchiserProfile.this,
                    new String[]{android.Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }

    }

    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(UpdateFranchiserProfile.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(UpdateFranchiserProfile.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.d("tag", "Permission Granted Write External ");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("tag", "Permission Not Granted Write External ");
                    checkWriteExternalPermission();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                    Log.d("tag", "Permission Granted Camera ");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("tag", "Permission Not Granted");
                    checkCameraPermission();
                }
                return;
            }




            // other 'case' lines to check for other
            // permissions this app might request
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_CODE) {
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == CAMERA_CODE) {
                onSelectFromCameraResult(data);

            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bmToUpload = null;
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bmToUpload = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                // filePath = data.getData();


                bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);

                int height = bitmap.getHeight();
                int weidth = bitmap.getWidth();
                Log.e("TAG", "IMAGE HEIGHT " + height);
                Log.e("TAG", "IMAGE WEIDTH " + weidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (imageSelecter == 3) {
            prifileImage.setImageBitmap(bitmap);
            filePath3 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();

            encodedImage3 = getStringImage(bmToUpload);


        }


    }

    //camera image
    private void onSelectFromCameraResult(Intent data) {
        if (data != null) {




            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap photoToUpload = (Bitmap) data.getExtras().get("data");

            int height = photo.getHeight();
            int weidth = photo.getWidth();

            Log.e("TAG", "IMAGE HEIGHT Old " + height);
            Log.e("TAG", "IMAGE Weidht Old " + weidth);

            photo = Bitmap.createScaledBitmap(photo, 512, 512, true);

            int heightnew = photo.getHeight();
            int weidthnew = photo.getWidth();

            Log.e("TAG", "IMAGE HEIGHT New " + heightnew);
            Log.e("TAG", "IMAGE Weidht New " + heightnew);




            Uri tempUri = getImageUri(getApplicationContext(), photo);
            Log.e("TAG", "IMAGE URI " + tempUri);
            imageFileFromCamera = new File(getRealPathFromURI(tempUri));

            Log.e("TAG", "IMAGE URI 1 " + imageFileFromCamera);



            if (imageSelecter==3) {
                prifileImage.setImageBitmap(photo);

                filePath3 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp3 = tsLong.toString();
                encodedImage3 = getStringImage(photoToUpload);


            }

        }
    }


    public void selectImageFromGalaryIntent(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_CODE);
    }

    public void selectImageFromCameraIntent(){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CODE);
    }


    public void showImageChoseDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateFranchiserProfile.this);

        dialog.setTitle("Please Choose Image...");
        dialog.setPositiveButton("From Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectImageFromGalaryIntent();
            }
        });

        dialog.setNegativeButton("Take Photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //select image from camera intent
                selectImageFromCameraIntent();
            }
        });

        dialog.setNeutralButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //endcoding image into base64
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    public void galaryImageSelectionHandler(){


        prifileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 3;
                showImageChoseDialog();
            }
        });

    }


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

                URL url = new URL(AllURLs.UPDATE_FRANCHISER_ACCOUNT); //"https://www.pk.house/app_webservices/update_franchise_account.php");


                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("fr_id", mId)
                        .appendQueryParameter("name", mName)
                        .appendQueryParameter("country", mCountry)
                        .appendQueryParameter("city", mCity)
                        .appendQueryParameter("company_name", mCompanyName)
                        .appendQueryParameter("area_location", mLocation)
                        .appendQueryParameter("email", mEmail)
                        .appendQueryParameter("number", mPhone)
                        .appendQueryParameter("landline", mLandLineNumber)
                        .appendQueryParameter("password", mPassword)
                        .appendQueryParameter("user_type", userType)
                        .appendQueryParameter("profile_image", encodedImage3)
                        .appendQueryParameter("profiletimestep", timestamp3);



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



            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message

                bar.setVisibility(View.GONE);

                Toast.makeText(UpdateFranchiserProfile.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                bar.setVisibility(View.GONE);
                Toast.makeText(UpdateFranchiserProfile.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

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

    public void registerButtonHandler(){

        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String uName = etName.getText().toString();
                String uCompanyName = etCompanyName.getText().toString();
                String uPbone = etPhone.getText().toString();
                String mNewPassword = etNewPassword.getText().toString();
                String uCoutery = etCountry.getText().toString();
                String uCity = etCity.getText().toString();
                String uLocation = etLocation.getText().toString();

                String uLandLineNo = etLandlineNo.getText().toString();




                if (uName.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
               else if (uCompanyName.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please Enter Company Name", Toast.LENGTH_SHORT).show();
                }
               else if (uPbone.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
               else if (mNewPassword.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please You New password", Toast.LENGTH_SHORT).show();
                }
                else if (uLocation.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please Enter Loaction", Toast.LENGTH_SHORT).show();
                }
                else if (uCoutery.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please Enter Country", Toast.LENGTH_SHORT).show();
                }
                else if (etCity.length()==0){
                    Toast.makeText(UpdateFranchiserProfile.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                }


                else {



                    mName = uName;
                    mCompanyName = uCompanyName;
                    mPhone = uPbone;
                    mPassword = mNewPassword;
                    mCity = uCity;
                    mCountry = uCoutery;
                    mLocation = uLocation;
                    mLandLineNumber = uLandLineNo;





                    //calling server to submit data
                   new LoadingToServer().execute();



                }

            }
        });
    }
    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UpdateFranchiserProfile.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }

}
