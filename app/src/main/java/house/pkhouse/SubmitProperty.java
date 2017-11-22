package house.pkhouse;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.FragmentAdapter;
import house.pkhouse.adapter.ImageAdapter;


public class SubmitProperty extends AppCompatActivity {


    SubmitSecondPage second;
    SubmitFirstPage f;
    SubmitThirldPage thirld;

    private ProgressBar bar;
    Drawable b;

    String submitedID = null;

   // Button buttonSubmit;

    ViewPager viewPager;
    String title = null,property_for = null,property_type = null,price = null,area = null, ed_area = null ,availability = null,rooms = null,
            bathrooms = null,floors = null,description = null,property_structure = null,floor_structure = null,walls_structure = null,
            doors_structure = null,windows_structure = null,electrical_structure = null,
            location = null,country = null, city = null,
            name = null,email= null,phone_number = null,
            accountType = null,
            clientNo = null;

    ImageView  iv_right_arrow, iv_left_arrow;
    Button bbSubmit;


    ImageView iv_first=null, iv_second=null, iv_thirld=null, iv_fourth=null, iv_fith=null, iv_sixth=null;
    Button bt_slectIVFirst, bt_slectIVSecond, bt_slectIVThirld, bt_slectIVForth, bt_slectIVFith, bt_slectIVSixth;

    File imageFileGalary;
    File imageFileFromCamera;
    Uri filePath1 = null;
    Uri filePath2 = null;
    Uri filePath3 = null;
    Uri filePath4 = null;
    Uri filePath5 = null;
    Uri filePath6 = null;

    RelativeLayout rl_chat_icon;


    String timestamp1 , timestamp2 , timestamp3, timestamp4, timestamp5, timestamp6;
    Calendar myCalendar = Calendar.getInstance();

    String encodedImage1="", encodedImage2="", encodedImage3="", encodedImage4="", encodedImage5="", encodedImage6="";

    int imageSelecter = 0;

    public static int CAMERA_CODE = 1;
    public static int GALLERY_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 000000;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 000001;

    String normalOrFranchiser = "";



    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;

    ArrayList<String> imagesEncodedList;

    ArrayList<Uri> mArrayUri;

    String selectedStringPropertyType = "no";
    String radioValue = "no";

    private int Request_Code = 101;
    ArrayList<String> fetchList;
    ArrayList<String> tempList;

    private static final int STORAGE_PERMISSION_CODE = 123;

    GridView gv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_property);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(SubmitProperty.this ,R.color.colorRed)));
        getSupportActionBar().setTitle("");

       getSupportActionBar().setDisplayOptions(getSupportActionBar().getDisplayOptions() | getSupportActionBar().DISPLAY_SHOW_CUSTOM);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

        getSupportActionBar().setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_action, null);

        fetchList= new ArrayList<String>();
        tempList = new ArrayList<String>();


        Intent intent = getIntent();
        Log.e("TAG", "TES TEST " + intent);
        if (intent.getExtras()== null){

        }else {
            final String userAs = intent.getExtras().getString("from", null);
            if (userAs!=null) {
                normalOrFranchiser = userAs;
            }
        }



        iv_right_arrow = (ImageView)v.findViewById(R.id.iv_right_arrow);
        iv_left_arrow = (ImageView)v.findViewById(R.id.iv_left_arrow);
        bbSubmit = (Button)v.findViewById(R.id.btbt_submit);
        iv_left_arrow.setVisibility(View.GONE);
        getSupportActionBar().setCustomView(v);


        f = new SubmitFirstPage();
        second = new SubmitSecondPage();
        thirld = new SubmitThirldPage();

        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        gv = (GridView) findViewById(R.id.PhoneImageGrid);



        iv_first = (ImageView) findViewById(R.id.image_view1);
        iv_second = (ImageView) findViewById(R.id.image_view2);
        iv_thirld = (ImageView) findViewById(R.id.image_view3);
        iv_fourth = (ImageView) findViewById(R.id.image_view4);
        iv_fith = (ImageView) findViewById(R.id.image_view5);
        iv_sixth = (ImageView) findViewById(R.id.image_view6);

        bt_slectIVFirst = (Button) findViewById(R.id.bt_select_image1);
        bt_slectIVSecond = (Button) findViewById(R.id.bt_select_image2);
        bt_slectIVThirld = (Button) findViewById(R.id.bt_select_image3);
        bt_slectIVForth = (Button) findViewById(R.id.bt_select_image4);
        bt_slectIVFith = (Button) findViewById(R.id.bt_select_image5);
        bt_slectIVSixth = (Button) findViewById(R.id.bt_select_image6);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //buttonSubmit = (Button) findViewById(R.id.bt_submit_property);

        tabLayout.addTab(tabLayout.newTab().setText("Page 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Page 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Page 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setVisibility(View.GONE);

       // buttonSubmit.setVisibility(View.GONE);


        viewPager = (ViewPager) findViewById(R.id.pager);
        final FragmentAdapter adapter = new FragmentAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                String tabString = tab.getText().toString();
                int tabPos = tab.getPosition();
                Log.e("TAG", "TAG VALUE " + tabString);
                Log.e("TAG", "TAG VALUE " + tabPos);

                if (tabPos == 2) {

                   // buttonSubmit.setVisibility(View.VISIBLE);
                    iv_left_arrow.setVisibility(View.VISIBLE);
                    iv_right_arrow.setVisibility(View.GONE);
                    bbSubmit.setVisibility(View.VISIBLE);



                    sharedPreferences = getSharedPreferences("user", 0);
                    sharedPreferences1 = getSharedPreferences("franchiser", 0);

                    startingWithThreeRestriction();


                    if (normalOrFranchiser.equals("normal")) {

                        sharedPreferences = getSharedPreferences("user", 0);
                    }
                    if (normalOrFranchiser.equals("franchiser")){
                        sharedPreferences =  getSharedPreferences("franchiser", 0);
                    }
                        Log.e("TAG", "SharePreference " + sharedPreferences);
                    if (sharedPreferences!=null) {



                        String mName = sharedPreferences.getString("name", null);
                        String mEmail = sharedPreferences.getString("email", null);
                        String mPphone = sharedPreferences.getString("phone", null);

                        if (mName!=null) {
                            Log.e("TAG", "SharePreference 11 " + name);

                            // set new title to the MenuItem
                            thirld.m_ed_Name.setText(mName);
                            thirld.m_ed_email.setText(mEmail);
                            thirld.m_ed_phoneNumber.setText(mPphone);

                            thirld.m_ll_contact_et.setVisibility(View.GONE);

                            thirld.m_ll_account_type.setVisibility(View.GONE);
                            thirld.m_rl_contact_tv.setVisibility(View.GONE);
                            thirld.m_tv_fithline.setVisibility(View.GONE);


                        }

                    }
                    if (sharedPreferences1!=null) {



                        String mName = sharedPreferences1.getString("name", null);
                        String mEmail = sharedPreferences1.getString("email", null);
                        String mPphone = sharedPreferences1.getString("phone", null);

                        if (mName!=null) {
                            Log.e("TAG", "SharePreference 11 " + name);

                            // set new title to the MenuItem
                            thirld.m_ed_Name.setText(mName);
                            thirld.m_ed_email.setText(mEmail);
                            thirld.m_ed_phoneNumber.setText(mPphone);


                            thirld.m_ll_contact_et.setVisibility(View.GONE);
                            thirld.m_ll_et_client_no.setVisibility(View.VISIBLE);
                            thirld.m_ll_account_type.setVisibility(View.GONE);
                            thirld.m_rl_contact_tv.setVisibility(View.GONE);
                            thirld.m_tv_fithline.setVisibility(View.GONE);




                        }
                    }



                } else if (tabPos == 0) {
                    //buttonSubmit.setVisibility(View.GONE);

                    iv_left_arrow.setVisibility(View.GONE);
                    iv_right_arrow.setVisibility(View.VISIBLE);
                    bbSubmit.setVisibility(View.GONE);



                } else if (tabPos == 1) {
                   // buttonSubmit.setVisibility(View.GONE);

                    iv_left_arrow.setVisibility(View.VISIBLE);
                    iv_right_arrow.setVisibility(View.VISIBLE);
                    bbSubmit.setVisibility(View.GONE);


                    b = second.image1.getDrawable();
                    Log.e("TAG", "this is drawable: " + b);

                    galaryImageSelectionHandler();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        nextButton();
        prevButton();
        //submitButtonHandler();
        submitProperty();
        chatIconClick();
        setupViews();


    }//end of on create



    @Override
    protected void onStart() {
        super.onStart();
        checkCameraPermission();
                    checkWriteExternalPermission();
    }


    public void checkCameraPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(SubmitProperty.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(SubmitProperty.this,
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
        if (ContextCompat.checkSelfPermission(SubmitProperty.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(SubmitProperty.this,
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
                   // checkWriteExternalPermission();
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



    public void selectImageFromGalaryIntent(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_CODE);
    }

    public void selectImageFromCameraIntent(){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CODE);
    }


    public void showImageChoseDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(SubmitProperty.this);

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

    //endcoding image into base64
    public String getStringImage(Bitmap bmp){

        String encodedImag = null;

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImag = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }catch (java.lang.OutOfMemoryError e){
            e.printStackTrace();
        }
        return encodedImag;
        }

   /* public void submitButtonHandler(){
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                title = f.ed_protpertyTitle.getText().toString();
                property_for = f.sp_propertyFor.getSelectedItem().toString();
                if(f.sp_propertyFor.getSelectedItemPosition()==0){
                    property_for="";
                }
                property_type = f.sp_propertyType.getSelectedItem().toString();
                if(f.sp_propertyType.getSelectedItemPosition()==0){
                    property_type="";
                }
                price = f.ed_propertyPrice.getText().toString();
                area = f.sp_landArea.getSelectedItem().toString();
                if(f.sp_landArea.getSelectedItemPosition()==0){
                    area="";
                }
                availability = f.sp_propertyAvail.getSelectedItem().toString();
                if(f.sp_propertyAvail.getSelectedItemPosition()==0){
                    availability="";
                }

                ed_area = f.ed_landAread.getText().toString();
                rooms = f.ed_noOfRooms.getText().toString();
                bathrooms = f.ed_ofBathrooms.getText().toString();
                floors = f.ed_floors.getText().toString();
                description = f.ed_description.getText().toString();

                Log.e("TAG", "TEST TEST " + timestamp1);


                location = thirld.m_ed_location.getText().toString();

                country = thirld.sp_country.getSelectedItem().toString();
                city = thirld.sp_city.getSelectedItem().toString();

                if (country.equals("Chose a Country")){
                    country = "";
                }
                if (city.equals("All Cities")){
                 city = "";
                }
                country = thirld.sp_country.getSelectedItem().toString();
                city = thirld.sp_city.getSelectedItem().toString();

                name = thirld.m_ed_Name.getText().toString();
                email = thirld.m_ed_email.getText().toString();

                phone_number = thirld.m_ed_phoneNumber.getText().toString();

                if (title.isEmpty() || price.isEmpty() || ed_area.isEmpty() || description.isEmpty()){
                    Toast.makeText(SubmitProperty.this, "Fields with a red star should not be empty.", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 2, true);

                }else if (f.sp_landArea.getSelectedItemPosition()==0){
                    Toast.makeText(SubmitProperty.this, "Please enter area scal", Toast.LENGTH_SHORT).show();

                }

                if(name.isEmpty() || email.isEmpty()){

                    Toast.makeText(SubmitProperty.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }

                if (thirld.m_ed_phoneNumber.getText().length()==0) {

                        if (thirld.m_sp_selec_country_for_phone.getSelectedItemId() == 0) {

                        Toast.makeText(SubmitProperty.this, "Please Choose a Country for Phone", Toast.LENGTH_SHORT).show();
                        } else if (thirld.m_et_mobile_code.getText().length() == 0) {
                        Toast.makeText(SubmitProperty.this, "Please Enter phone code", Toast.LENGTH_SHORT).show();

                        } else if (thirld.m_register_phone.getText().length() == 0) {
                        Toast.makeText(SubmitProperty.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();

                        } else if (thirld.m_et_mobile_code.getText().length() < 3) {
                        Toast.makeText(SubmitProperty.this, "Please Enter Valid Phone Code", Toast.LENGTH_SHORT).show();

                        } else if (thirld.m_register_phone.getText().length() < 7) {
                        Toast.makeText(SubmitProperty.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            sharedPreferences = getSharedPreferences("user", 0);
                            sharedPreferences1 = getSharedPreferences("franchiser", 0);

                            String myName = null;

                            if (sharedPreferences!=null){

                                myName = sharedPreferences.getString("name", null);
                            }

                             if (sharedPreferences1!=null){

                                myName = sharedPreferences1.getString("name", null);
                            }

                            if (myName==null) {


                                country = thirld.sp_country.getSelectedItem().toString();
                                city = thirld.sp_city.getSelectedItem().toString();

                                if (country.equals("Chose a Country")){
                                    Toast.makeText(SubmitProperty.this, "Please Chose a country", Toast.LENGTH_SHORT).show();
                                }
                                else if (city.equals("All Cities")){
                                    Toast.makeText(SubmitProperty.this, "Pleae Choose a city", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                country = thirld.sp_country.getSelectedItem().toString();
                                city = thirld.sp_city.getSelectedItem().toString();


                                phone_number = thirld.m_ed_phoneNumber.getText().toString();
                                Log.e("TAG", "MY NUMBER 1234: " + phone_number);


                                String countryCode = thirld.m_tv_country_code.getText().toString();
                                String mobileCode = thirld.m_et_mobile_code.getText().toString();
                                String myPhone = thirld.m_register_phone.getText().toString();

                                phone_number = countryCode + mobileCode + myPhone;


                                area =  ed_area + " " + area;
                                Log.e("TAG", "Title " + title);
                                Log.e("TAG", "Peroperty for " + property_for);
                                Log.e("TAG", "Property Type " + property_type);
                                Log.e("TAG", "Price " + price);
                                Log.e("TAG", "Area " + area);
                                Log.e("TAG", "Availability " + availability);
                                Log.e("TAG", "bathrooms " + bathrooms);
                                Log.e("TAG", "Floors " + floors);
                                Log.e("TAG", "Description " + description);
                                Log.e("TAG", "Property Structure " + property_structure);
                                Log.e("TAG", "floor " + floor_structure);
                                Log.e("TAG", "walls " + walls_structure);
                                Log.e("TAG", "doors " + doors_structure);
                                Log.e("TAG", "windows " + windows_structure);
                                Log.e("TAG", "electrical " + electrical_structure);
                                Log.e("TAG", "location " + location);
                                Log.e("TAG", "country " + country);
                                Log.e("TAG", "city " + city);
                                Log.e("TAG", "name " + name);
                                Log.e("TAG", "email " + email);
                                Log.e("TAG", "phone " + phone_number);
                                    Log.e("TAG", "Property Type 12" + radioValue);
                                    Log.e("TAG", "Value 11" + selectedStringPropertyType);



                                    for (int i = 0; i<fetchList.size(); i++){
                                        Log.e("TAG", "Images Are: " + fetchList.get(i));
                                    }



                                //submiting property if user not register
                                new LoadingToServer().execute();
                            }
                            }

                        }

                    }



                else {


                    country = thirld.sp_country.getSelectedItem().toString();
                    city = thirld.sp_city.getSelectedItem().toString();

                    if (country.equals("Chose a Country")){
                        Toast.makeText(SubmitProperty.this, "Please Chose a country", Toast.LENGTH_SHORT).show();
                    }
                    else if (city.equals("All Cities")){
                        Toast.makeText(SubmitProperty.this, "Pleae Choose a city", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        country = thirld.sp_country.getSelectedItem().toString();
                        city = thirld.sp_city.getSelectedItem().toString();

                    area =  ed_area + " " + area;
                    Log.e("TAG", "title " + title);
                    Log.e("TAG", "proeprty for " + property_for);
                    Log.e("TAG", "property type " + property_type);
                    Log.e("TAG", "price " + price);
                    Log.e("TAG", "area " + area);
                    Log.e("TAG", "availability " + availability);
                    Log.e("TAG", "bathrooms " + bathrooms);
                    Log.e("TAG", "floor " + floors);
                    Log.e("TAG", "description " + description);
                    Log.e("TAG", "structure " + property_structure);
                    Log.e("TAG", "floor " + floor_structure);
                    Log.e("TAG", "walls " + walls_structure);
                    Log.e("TAG", "doors " + doors_structure);
                    Log.e("TAG", "windows " + windows_structure);
                    Log.e("TAG", "electrical " + electrical_structure);
                    Log.e("TAG", "location " + location);
                    Log.e("TAG", "country " + country);
                    Log.e("TAG", "city " + city);
                    Log.e("TAG", "name " + name);
                    Log.e("TAG", "email " + email);
                    Log.e("TAG", "phone " + phone_number);
                        Log.e("TAG", "Property Type 12" + radioValue);
                        Log.e("TAG", "Value 11" + selectedStringPropertyType);


                             new LoadingToServer().execute();


                    Log.e("TAG", "Image 1 " + encodedImage1);

                        for (int i = 0; i<fetchList.size(); i++){
                            Log.e("TAG", "Images Are: " + fetchList.get(i));
                        }




                    // Log.e("TAG", "TimeStamp 1 " + encodedImage1);


                }
                }
            }
        });
    }//end of submit button

*/

    public void galaryImageSelectionHandler(){

        Button btSelectImage1 = second.btImage1;
        btSelectImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 1;

                showImageChoseDialog();
                Toast.makeText(SubmitProperty.this, "Button 1 Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        Button btSelectImage2 = second.btImage2;
        btSelectImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 2;

                showImageChoseDialog();
                Toast.makeText(SubmitProperty.this, "Button 2 Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        Button btSelectImage3 = second.btImage3;
        btSelectImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 3;

                showImageChoseDialog();

            }
        });

        Button btSelectImage4 = second.btImage4;
        btSelectImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 4;

                showImageChoseDialog();

            }
        });

        Button btSelectImage5 = second.btImage5;
        btSelectImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 5;

                showImageChoseDialog();

            }
        });

        Button btSelectImage6 = second.btImage6;
        btSelectImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 6;


                AlertDialog.Builder alert = new AlertDialog.Builder(SubmitProperty.this);
                alert.setTitle("Alert!");
                alert.setMessage("Select Image From Gallary or from Camera");
                alert.setPositiveButton("Gallary", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //starting activity for galary
                        Intent galryIntent = new Intent(SubmitProperty.this, MultiPhotoSelectActivity.class);
                        startActivityForResult(galryIntent, Request_Code);
                    }
                });

                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //calling camera Activity
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_CODE);

                        Toast.makeText(SubmitProperty.this, "Camera Not Available Please Select From Galary", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                alert.show();

                //showImageChoseDialog();*//*

            }
        });

        ImageView chosePhoto = second.chooseImage;
        chosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(SubmitProperty.this);
                alert.setTitle("Alert!");
                alert.setMessage("Select Image From gallary or from Camera");
                alert.setPositiveButton("Gallary", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //starting activity for galary
                        Intent galryIntent = new Intent(SubmitProperty.this, MultiPhotoSelectActivity.class);
                        startActivityForResult(galryIntent, Request_Code);
                    }
                });

                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //calling camera Activity
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_CODE);
                    }
                });

                alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                alert.show();

                //showImageChoseDialog();*//*

            }
        });

    }


    public void nextButton(){
        iv_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitFirstPage f = new SubmitFirstPage();

                title = f.ed_protpertyTitle.getText().toString();
                price = f.ed_propertyPrice.getText().toString();
                ed_area = f.ed_landAread.getText().toString();

                selectedStringPropertyType = f.getSelectedValues();
                radioValue =  f.radioValue;

                Log.e("TAG", "The selected Value Is: " + selectedStringPropertyType);
                Log.e("TAG", "The selected Radio: " + radioValue);

                description = f.ed_description.getText().toString();
                if (title.isEmpty() || price.isEmpty() || ed_area.isEmpty() || description.isEmpty()){
                    Toast.makeText(SubmitProperty.this, "Fields with a red star should not be empty.", Toast.LENGTH_SHORT).show();
                }
                else if (f.sp_propertyFor.getSelectedItemPosition()==0){

                    Toast.makeText(SubmitProperty.this, "Please Select Property For", Toast.LENGTH_SHORT).show();
                }
                else if (f.sp_landArea.getSelectedItemPosition()==0){
                    Toast.makeText(SubmitProperty.this, "Please enter area scal", Toast.LENGTH_SHORT).show();

                }else if (selectedStringPropertyType.equals("no")){
                    Toast.makeText(SubmitProperty.this, "Please Select Property Type", Toast.LENGTH_SHORT).show();
                }
                else {
                    jumpToNext(v);
                }
            }
        });
    }


    public void prevButton(){
        iv_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToPreviouce(v);
            }
        });
    }

    public void jumpToNext(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }


    public void jumpToPreviouce(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }



    //async class

    public class LoadingToServer extends  AsyncTask<String , Void ,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            thirld.m_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection connection = null;
            try {

                URL url = new URL(AllURLs.ADD_PROPERTY); //"https://www.pk.house/app_webservices/add_property.php");


                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("name", name)
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("phone", phone_number)
                        .appendQueryParameter("propertytitle", title)
                        .appendQueryParameter("propertyfor", property_for)
                        .appendQueryParameter("propertytype", radioValue)
                        .appendQueryParameter("propertytypeiner", selectedStringPropertyType)
                        .appendQueryParameter("propertyprice", price)
                        .appendQueryParameter("propertyavail", availability)
                        .appendQueryParameter("landarea", area)
                        .appendQueryParameter("noofrooms", rooms)
                        .appendQueryParameter("noofbathrooms", bathrooms)
                        .appendQueryParameter("noofloors", floors)
                        .appendQueryParameter("description", description)
                        .appendQueryParameter("structureofproperty", property_structure)
                        .appendQueryParameter("structureofflooring", floor_structure)
                        .appendQueryParameter("wallsstructure", walls_structure)
                        .appendQueryParameter("doorsstructure", doors_structure)
                        .appendQueryParameter("windowsstructure", windows_structure)
                        .appendQueryParameter("electricalstructure", electrical_structure)
                        .appendQueryParameter("imageurl1", encodedImage1)
                        .appendQueryParameter("timestamp1", timestamp1)
                        .appendQueryParameter("imageurl2", encodedImage2)
                        .appendQueryParameter("timestamp2", timestamp2)
                        .appendQueryParameter("imageurl3", encodedImage3)
                        .appendQueryParameter("timestamp3", timestamp3)
                        .appendQueryParameter("imageurl4", encodedImage4)
                        .appendQueryParameter("timestamp4", timestamp4)
                        .appendQueryParameter("imageurl5", encodedImage5)
                        .appendQueryParameter("timestamp5", timestamp5)
                        .appendQueryParameter("imageurl6", encodedImage6)
                        .appendQueryParameter("timestamp6", timestamp6)
                        .appendQueryParameter("location", location)
                        .appendQueryParameter("country", country)
                        .appendQueryParameter("city", city)
                        .appendQueryParameter("accounttype", accountType)
                        .appendQueryParameter("clientno", clientNo);

Log.e("TAg", "The Resssuultt vvaluuee: " + accountType);



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

            String responce =  returnParsedJsonObject(result);
            Log.e("TAG", "RESULT 123 " + result);
            Log.e("TAG", "RESULT 1233 " + responce);
            if (responce == null)
            {
                thirld.m_bar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Server not Responding...", Toast.LENGTH_SHORT).show();

                bbSubmit.setVisibility(View.VISIBLE);
            }
            else if(responce.equalsIgnoreCase("true"))
            {

                if (!fetchList.isEmpty()){
                    for (int i = 0; i<fetchList.size(); i++){

                        String imagePath = fetchList.get(i);

                        uploadMultipart(imagePath, submitedID);
                        Log.e("TAG", "THE ID IS: " + submitedID);
                    }
                }


                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                thirld.m_bar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Property Submited Successfully",Toast.LENGTH_SHORT).show();

                //adding data into sharePreferences
                sharedPreferences = getSharedPreferences("userinfo", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("email", email);
                editor.putString("phone", phone_number);
                editor.commit();

               // finish();

                rateUsDialog();




            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message

                thirld.m_bar.setVisibility(View.GONE);

                Toast.makeText(SubmitProperty.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                thirld.m_bar.setVisibility(View.GONE);
                Toast.makeText(SubmitProperty.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

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
                submitedID = resultObject.getString("property_id");




            }
        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }


    public void rateUsDialog(){
        AlertDialog.Builder rateUsDialog = new AlertDialog.Builder(SubmitProperty.this);
        rateUsDialog.setTitle("Property Submited Successfully!");
        rateUsDialog.setMessage("Click 'Rate Now' To Rate App " +"\nClick 'Rate Later' To Main Screnn");
        rateUsDialog.setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                startActivity(rateIntent);
                dialog.dismiss();
                finish();

            }
        });

        rateUsDialog.setNegativeButton("Rate Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        rateUsDialog.setCancelable(false);
        rateUsDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void startingWithThreeRestriction(){
        thirld = new SubmitThirldPage();
        thirld.m_et_mobile_code.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                String x = s.toString();
                if(x.startsWith("0"))
                {

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }

                if (x.startsWith("1")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("2")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("9")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("4")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("5")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("6")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("7")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }
                if (x.startsWith("8")){

                    Toast.makeText(SubmitProperty.this, "Pleae enter number starting with 3", Toast.LENGTH_SHORT).show();
                    thirld.m_et_mobile_code.setText("");
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });//end for login editText


    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2



        if (requestCode == Request_Code) {

            Log.e("TAG", "Default: " + fetchList.size());
            if (fetchList.size() == 0) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    fetchList = extras.getStringArrayList("list");
                }
            } else {

                Bundle extras1 = data.getExtras();

                if (extras1 != null) {
                    tempList = extras1.getStringArrayList("list");

                    //adding the previouce array to current array
                    fetchList.addAll(tempList);

                }
            }


            //fetchList = getIntent().getStringArrayListExtra("list");
            Log.e("TAG", "MY URLS ss: " + fetchList);

            if (fetchList != null) {


                for (int i = 0; i < fetchList.size(); i++) {

                    Log.e("TAG", "MY URLS: " + fetchList.get(i));

                }

                second = new SubmitSecondPage();
                second.gv.setAdapter(new ImageAdapter(this, fetchList, 0));
            }

        }

        if (requestCode == CAMERA_CODE) {

            Log.e("TAG", "DATA : " + data);

            ImageFromCamera(data);
            Log.e("TAG", "SSSize: " + fetchList.size());

            if (fetchList != null) {


                for (int i = 0; i < fetchList.size(); i++) {

                    Log.e("TAG", "MY URLS: " + fetchList.get(i));

                }
            }

           second = new SubmitSecondPage();
            second.gv.setAdapter(new ImageAdapter(this, fetchList, 0));


            //imageFrom Camera

        }

    }//end of result code_ok if


    public void uploadMultipart(String path, String id) {
        //getting name for the image

        //getting the actual path of the image
        Log.e("TAGE", "FILE PATH 1122: " + path);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, AllURLs.ADD_PROPERTY_IMAGES) //"https://www.pk.house/app_webservices/add_property_images.php")
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", "imagename") //Adding text parameter to the request
                    .addParameter("property_id", id)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload




        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void ImageFromCamera(Intent data){

        if (data != null) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");

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
            File imageFileFromCamera = new File(getRealPathFromURI(tempUri));
            Log.e("TAG", "Image FILE URL: " + imageFileFromCamera);


            if (fetchList.size()!=0){
                //fetchList.clear();
                tempList.clear();
                tempList.add(imageFileFromCamera.toString());
                Log.e("TAG", "test list Previouce Size: " + fetchList.size());
                fetchList.addAll(tempList);
                Log.e("TAG", "test list Current Size Size: " + fetchList.size());
            }else {

                fetchList.add(imageFileFromCamera.toString());
                Log.e("TAG", "test Test Test: " + fetchList.size());
            }
        }

    }//end of image From Camera


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (!savedInstanceState.isEmpty()) {
            fetchList = savedInstanceState.getStringArrayList("arrylist");
            Log.e("TAG", "Another size: " + fetchList.size());
        }
    }

    public void submitProperty(){

        bbSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                title = f.ed_protpertyTitle.getText().toString();
                property_for = f.sp_propertyFor.getSelectedItem().toString();
                if(f.sp_propertyFor.getSelectedItemPosition()==0){
                    property_for="";
                }
                property_type = f.sp_propertyType.getSelectedItem().toString();
                if(f.sp_propertyType.getSelectedItemPosition()==0){
                    property_type="";
                }
                price = f.ed_propertyPrice.getText().toString();
                area = f.sp_landArea.getSelectedItem().toString();
                if(f.sp_landArea.getSelectedItemPosition()==0){
                    area="";
                }
                availability = f.sp_propertyAvail.getSelectedItem().toString();
                if(f.sp_propertyAvail.getSelectedItemPosition()==0){
                    availability="";
                }

                ed_area = f.ed_landAread.getText().toString();
                rooms = f.ed_noOfRooms.getText().toString();
                bathrooms = f.ed_ofBathrooms.getText().toString();
                floors = f.ed_floors.getText().toString();
                description = f.ed_description.getText().toString();

                Log.e("TAG", "TEST TEST " + timestamp1);


                location = thirld.m_ed_location.getText().toString();
                //location =  thirld.m_customAutoCompleteTextView.googlePlace.getDescription(); //Return the description (city + region + country)
                location = thirld.m_customAutoCompleteTextView.getText().toString();

                country = thirld.sp_country.getSelectedItem().toString();
                city = thirld.sp_city.getSelectedItem().toString();

                if (country.equals("Chose a Country")){
                    country = "";
                }
                if (city.equals("All Cities")){
                    city = "";
                }
                country = thirld.sp_country.getSelectedItem().toString();
                city = thirld.sp_city.getSelectedItem().toString();

                name = thirld.m_ed_Name.getText().toString();
                email = thirld.m_ed_email.getText().toString();

                phone_number = thirld.m_ed_phoneNumber.getText().toString();

                if (title.isEmpty() || price.isEmpty() || ed_area.isEmpty() || description.isEmpty()){
                    Toast.makeText(SubmitProperty.this, "Fields with a red star should not be empty.", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 2, true);

                }
                else if (f.sp_propertyFor.getSelectedItemPosition()==0){

                    Toast.makeText(SubmitProperty.this, "Please Select Property For", Toast.LENGTH_SHORT).show();
                }
                else if (f.sp_landArea.getSelectedItemPosition()==0){
                    Toast.makeText(SubmitProperty.this, "Please enter area scal", Toast.LENGTH_SHORT).show();

                }

                if(name.isEmpty() || email.isEmpty()){

                    Toast.makeText(SubmitProperty.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }

                if (thirld.m_ed_phoneNumber.getText().length()==0) {

                    if (thirld.m_sp_selec_country_for_phone.getSelectedItemId() == 0) {

                        Toast.makeText(SubmitProperty.this, "Please Choose a Country for Phone", Toast.LENGTH_SHORT).show();
                    } else if (thirld.m_et_mobile_code.getText().length() == 0) {
                        Toast.makeText(SubmitProperty.this, "Please Enter phone code", Toast.LENGTH_SHORT).show();

                    } else if (thirld.m_register_phone.getText().length() == 0) {
                        Toast.makeText(SubmitProperty.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();

                    } else if (thirld.m_et_mobile_code.getText().length() < 3) {
                        Toast.makeText(SubmitProperty.this, "Please Enter Valid Phone Code", Toast.LENGTH_SHORT).show();

                    } else if (thirld.m_register_phone.getText().length() < 7) {
                        Toast.makeText(SubmitProperty.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        sharedPreferences = getSharedPreferences("user", 0);
                        sharedPreferences1 = getSharedPreferences("franchiser", 0);

                        String myName = null;

                        if (sharedPreferences!=null){

                            myName = sharedPreferences.getString("name", null);
                        }

                        if (sharedPreferences1!=null){

                            myName = sharedPreferences1.getString("name", null);
                        }

                        if (myName==null) {


                            country = thirld.sp_country.getSelectedItem().toString();
                            city = thirld.sp_city.getSelectedItem().toString();

                            if (country.equals("Chose a Country")){
                                Toast.makeText(SubmitProperty.this, "Please Choose a country", Toast.LENGTH_SHORT).show();
                            }
                            else if (city.equals("All Cities")){
                                Toast.makeText(SubmitProperty.this, "Pleae Choose a city", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                country = thirld.sp_country.getSelectedItem().toString();
                                city = thirld.sp_city.getSelectedItem().toString();

                                accountType = thirld.m_sp_account_type.getSelectedItem().toString();
                                clientNo = thirld.m_et_client_no.getText().toString();

                                Log.e("TAG", "Account Type " + accountType);
                                Log.e("TAG", "Client No. " + clientNo);


                                phone_number = thirld.m_ed_phoneNumber.getText().toString();
                                Log.e("TAG", "MY NUMBER 1234: " + phone_number);


                                String countryCode = thirld.m_tv_country_code.getText().toString();
                                String mobileCode = thirld.m_et_mobile_code.getText().toString();
                                String myPhone = thirld.m_register_phone.getText().toString();

                                phone_number = countryCode + mobileCode + myPhone;


                                area =  ed_area + " " + area;
                                Log.e("TAG", "Title " + title);
                                Log.e("TAG", "Peroperty for " + property_for);
                                Log.e("TAG", "Property Type " + property_type);
                                Log.e("TAG", "Price " + price);
                                Log.e("TAG", "Area " + area);
                                Log.e("TAG", "Availability " + availability);
                                Log.e("TAG", "bathrooms " + bathrooms);
                                Log.e("TAG", "Floors " + floors);
                                Log.e("TAG", "Description " + description);
                                Log.e("TAG", "Property Structure " + property_structure);
                                Log.e("TAG", "floor " + floor_structure);
                                Log.e("TAG", "walls " + walls_structure);
                                Log.e("TAG", "doors " + doors_structure);
                                Log.e("TAG", "windows " + windows_structure);
                                Log.e("TAG", "electrical " + electrical_structure);
                                Log.e("TAG", "location " + location);
                                Log.e("TAG", "country " + country);
                                Log.e("TAG", "city " + city);
                                Log.e("TAG", "name " + name);
                                Log.e("TAG", "email " + email);
                                Log.e("TAG", "phone " + phone_number);
                                Log.e("TAG", "Property Type 12" + radioValue);
                                Log.e("TAG", "Value 11" + selectedStringPropertyType);





                                for (int i = 0; i<fetchList.size(); i++){
                                    Log.e("TAG", "Images Are: " + fetchList.get(i));
                                }



                                //submiting property if user not register
                                new LoadingToServer().execute();

                                bbSubmit.setVisibility(View.GONE);
                            }
                        }

                    }

                }



                else {


                    country = thirld.sp_country.getSelectedItem().toString();
                    city = thirld.sp_city.getSelectedItem().toString();

                    if (country.equals("Chose a Country")){
                        Toast.makeText(SubmitProperty.this, "Please Choose a country", Toast.LENGTH_SHORT).show();
                    }
                    else if (city.equals("All Cities")){
                        Toast.makeText(SubmitProperty.this, "Pleae Choose a city", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        country = thirld.sp_country.getSelectedItem().toString();
                        city = thirld.sp_city.getSelectedItem().toString();

                        accountType = thirld.m_sp_account_type.getSelectedItem().toString();
                        clientNo = thirld.m_et_client_no.getText().toString();

                        Log.e("TAG", "Account Type " + accountType);
                        Log.e("TAG", "Client No. " + clientNo);

                        area =  ed_area + " " + area;

                        Log.e("TAG", "title " + title);
                        Log.e("TAG", "proeprty for " + property_for);
                        Log.e("TAG", "property type " + property_type);
                        Log.e("TAG", "price " + price);
                        Log.e("TAG", "area " + area);
                        Log.e("TAG", "availability " + availability);
                        Log.e("TAG", "bathrooms " + bathrooms);
                        Log.e("TAG", "floor " + floors);
                        Log.e("TAG", "description " + description);
                        Log.e("TAG", "structure " + property_structure);
                        Log.e("TAG", "floor " + floor_structure);
                        Log.e("TAG", "walls " + walls_structure);
                        Log.e("TAG", "doors " + doors_structure);
                        Log.e("TAG", "windows " + windows_structure);
                        Log.e("TAG", "electrical " + electrical_structure);
                        Log.e("TAG", "location " + location);
                        Log.e("TAG", "country " + country);
                        Log.e("TAG", "city " + city);
                        Log.e("TAG", "name " + name);
                        Log.e("TAG", "email " + email);
                        Log.e("TAG", "phone " + phone_number);
                        Log.e("TAG", "Property Type 12" + radioValue);
                        Log.e("TAG", "Value 11" + selectedStringPropertyType);


                        new LoadingToServer().execute();


                        Log.e("TAG", "Image 1 " + encodedImage1);

                        for (int i = 0; i<fetchList.size(); i++){
                            Log.e("TAG", "Images Are: " + fetchList.get(i));
                        }


                        bbSubmit.setVisibility(View.GONE);

                        // Log.e("TAG", "TimeStamp 1 " + encodedImage1);


                    }
                }



            }
        });
    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SubmitProperty.this, LiveSupport.class);
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

}


