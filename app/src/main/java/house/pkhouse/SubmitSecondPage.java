package house.pkhouse;

/**
 * Created by User-10 on 16-Mar-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.adapter.ImageAdapter;
import house.pkhouse.adapter.RecyclerAdapterSinglerImage;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SubmitSecondPage extends Fragment {

    public String pID;
    public static ArrayList<String> singlePropertyImageList;
    public static  ArrayList<String> imagefromserverList;

    public static ImageView image1, image2, image3, image4, image5, image6;
    public static Button btImage1, btImage2, btImage3, btImage4, btImage5, btImage6;
    public static ImageView chooseImage;

    public static GridView gv;
    public static int Request_Code = 101;

    ArrayList<String> fetchList;
    ArrayList<String> tempList;



    public static final int STORAGE_PERMISSION_CODE = 123;
    public static int CAMERA_CODE = 1;
    public static int GALLERY_CODE = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.submit_second_page, container, false);

        singlePropertyImageList = new ArrayList<>();
        imagefromserverList = new ArrayList<>();

        image1 = (ImageView) view.findViewById(R.id.image_view1);
        image2 = (ImageView) view.findViewById(R.id.image_view2);
        image3 = (ImageView) view.findViewById(R.id.image_view3);
        image4 = (ImageView) view.findViewById(R.id.image_view4);
        image5 = (ImageView) view.findViewById(R.id.image_view5);
        image6 = (ImageView) view.findViewById(R.id.image_view6);

        btImage1 = (Button) view.findViewById(R.id.bt_select_image1);
        btImage2 = (Button) view.findViewById(R.id.bt_select_image2);
        btImage3 = (Button) view.findViewById(R.id.bt_select_image3);
        btImage4 = (Button) view.findViewById(R.id.bt_select_image4);
        btImage5 = (Button) view.findViewById(R.id.bt_select_image5);
        btImage6 = (Button) view.findViewById(R.id.bt_select_image6);

        chooseImage = (ImageView) view.findViewById(R.id.image_view_pic_add);


        gv = (GridView) view.findViewById(R.id.PhoneImageGrid);

        fetchList= new ArrayList<String>();
        tempList = new ArrayList<String>();


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


      /*  SubmitProperty activity = (SubmitProperty) getActivity();
        ArrayList<String> list = activity.fetchList;
        gv.setAdapter(new ImageAdapter(getActivity(), list));*/


        Intent intent = getActivity().getIntent();
        Log.e("TAG", "TES TEST " + intent);
        if (intent.getExtras()== null){

        }else {


            String id = intent.getExtras().getString("propertyID");
            pID = id;

            GetContacts getContacts = new GetContacts();
            getContacts.execute();
        }
        return  view;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = AllURLs.GET_PROPERTIES_IMAGE_WITH_ID + pID; //"https://www.pk.house/app_webservices/get_properties_images.php?propertyid="+pID;
            Log.e("TAG", "URL  @@ " + url);
            String jsonStr = sh.makeServiceCall(url);
            Log.e("TAG", "Response from url: " + jsonStr);



            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.e("TAG", "RESULT 1" + jsonObj);

                    JSONObject resul = jsonObj.optJSONObject("response"); //for checking reponse is object or array
                    Log.e("TAG", "resso 1243 " + resul);

                    if (resul!=null) { //not null mean response is objectm, null mean respose is array
                        //Toast.makeText(getActivity(), "Email or Password is Invalid", Toast.LENGTH_SHORT).show();
                    }else {

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
                        ArrayList<String> contact = new ArrayList<>();
                        contact.add(imageURL);

                        singlePropertyImageList.addAll(contact);
                    }
                    }
                } catch (final JSONException e) {
                    Log.e("TAG", "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                               /* Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/

                            //Toast.makeText(getActivity(), "No Image Found", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            } else {
                Log.e("TAG", "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
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

          for (int i = 0; i<singlePropertyImageList.size(); i++){

              String url = singlePropertyImageList.get(i);
              Glide
                      .with(getActivity())
                      .load(url)
                      .asBitmap()
                      .into(new SimpleTarget<Bitmap>(300,300) {
                          @Override
                          public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                              Log.e("TAG", "The bitmap: " + resource);
                              //Uri imageURIFromBitmap = getImageUri(getActivity(), resource);
                              String imageURIFromBitmap =  bitmapToUriConverter(resource);
                              //String imageAddress = imageURIFromBitmap.toString();
                              Log.e("TAG", "the image path of server image: " + imageURIFromBitmap);

                              ArrayList<String> contact = new ArrayList<>();
                              contact.add(imageURIFromBitmap);

                              imagefromserverList.addAll(contact);


                          }
                      });
          }



            ImageAdapter singleAdapter = new ImageAdapter(getApplicationContext(), imagefromserverList, 1);
            gv.setAdapter(singleAdapter);


            //Toast.makeText(SingleOwnPropertyImages.this, "Count " + singlePropertyImageList.size(), Toast.LENGTH_SHORT).show();

           // progressBar.setVisibility(View.GONE);


        }
    }

    public Uri getImageUri(Activity a, Bitmap inImage) {
        Log.e("TAG", "TTT123: " + inImage);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(a.getContentResolver(), inImage, "Title", null);
        Log.e("TAG", "TTT: " + path);
        return Uri.parse(path);
    }

    public String bitmapToUriConverter(Bitmap mBitmap) {
        Uri uri = null;
        String path = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            // Calculate inSampleSize
          //  options.inSampleSize = calculateInSampleSize(options, 300, 300);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, 400, 400,
                    true);
            File file = new File(getActivity().getFilesDir(), "Image"
                    + new Random().nextInt() + ".jpeg");
            FileOutputStream out = getActivity().openFileOutput(file.getName(),
                   // Context.MODE_WORLD_READABLE)
                    Context.MODE_PRIVATE);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();
            path = realPath;
            Log.e("TAG", "absolute path: " + realPath);
            File f = new File(realPath);
            uri = Uri.fromFile(f);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return path;
    }


}
