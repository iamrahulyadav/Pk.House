package house.pkhouse.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.zopim.android.sdk.data.DataSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import house.pkhouse.BigImage;
import house.pkhouse.ClientSSLSocketFactory;
import house.pkhouse.Constant.AllURLs;
import house.pkhouse.Interfaces.KittenClickListener;
import house.pkhouse.Manifest;
import house.pkhouse.R;
import house.pkhouse.TouchImageView;
import house.pkhouse.loader.ImagesLoader;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by User-10 on 19-Jun-17.
 */

public class RecyclerAdapterSinglerImage extends RecyclerView.Adapter<RecyclerAdapterSinglerImage.MyViewHolder> {


    private int lastPosition = -1;

    private Activity activity;
    private String[] data;
    ArrayList<HashMap<String, String>> contactList;
    private static LayoutInflater inflater=null;
    public ImagesLoader imageLoader;

    ProgressBar  progressBar;

    private  Bitmap image = null;
    boolean mExpanded;

    public RecyclerAdapterSinglerImage(Activity a, ArrayList<HashMap<String, String>> contactList1) {
        activity = a;
        //data=d;
        this.contactList = contactList1;

        imageLoader=new ImagesLoader(activity.getApplicationContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dilaog_single_property_images, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {




      //  imageLoader.DisplayImage(contactList.get(position).get("imageurl"), holder.imageView);

        Glide.with(activity)
                .load(contactList.get(position).get("imageurl"))
                .placeholder(R.drawable.default_image)
                //.error(R.drawable.)
                .override(200, 200)
                .centerCrop()
                .into(holder.imageView);




        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.down_from_top : R.anim.wave_scaling);
        holder.imageView.startAnimation(animation);
        lastPosition = position;



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               final String url = contactList.get(position).get("imageurl");
                String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );

               final String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));

                Glide
                        .with(activity)
                        .load(url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(300,300) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                showSingleImage(url, resource);

                            }
                        });




                Toast.makeText(activity, "Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {

        return contactList.size();


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TouchImageView imageView;
        ViewGroup transitionsContainer;
        TouchImageView imageView1;
        TextView single_image;

        public MyViewHolder(View view) {
            super(view);

            imageView = (TouchImageView)view.findViewById(R.id.single_image);
            transitionsContainer = (ViewGroup) view.findViewById(R.id.transitions_container);
            imageView1 = (TouchImageView) transitionsContainer.findViewById(R.id.image);
           // single_image = (TextView) view.findViewById(R.id.single_image);



        }
    }

    public void downloadImage(String imageURL, String fileName){
        HttpsURLConnection connection = null;

        try
        {


            URL url = new URL(imageURL);


            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(new ClientSSLSocketFactory(connection.getSSLSocketFactory()));
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);

            /*URL url = new URL(imageURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();*/

            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
            String filename=fileName+".jpg";
            Log.i("Local filename:",""+filename);
            File file = new File(SDCardRoot,filename);
            if(file.createNewFile())
            {
                file.createNewFile();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = connection.getInputStream();
            int totalSize = connection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ( (bufferLength = inputStream.read(buffer)) > 0 )
            {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
            }
            fileOutput.close();
            //if(downloadedSize==totalSize) filepath=file.getPath();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            //filepath=null;
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    public void downloadFile(String uRl, String fileName) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/pk_house");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        File file = new File("/pk_house");
        file = new File(file.getAbsolutePath());
        String dir = file.getParent();
        Log.e("TAG", "File Directory path: " + file);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("pk.hosue")
                .setDescription("Pk.House Property Image")
                .setDestinationInExternalPublicDir(dir, fileName+".jpg");

        mgr.enqueue(request);


    }

    public void saveImage(String imageUrl){


                boolean success = (new File("/sdcard/Pk.House")).mkdir();
                if (!success)
                {
                    Log.w("directory not created", "directory not created");
                }

                try
                {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);

                    String data1 = String.valueOf(String.format("/sdcard/Pk.House/%d.jpg",System.currentTimeMillis()));

                    FileOutputStream stream = new FileOutputStream(data1);

                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();

                    Toast.makeText(getApplicationContext(), "Downloading Completed", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

       // shareeditflag

    }


    public void showSingleImage(final String imageUrl, Bitmap bitmap){


       final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.single_property_image);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
      RelativeLayout rr_dialog = (RelativeLayout)dialog.findViewById(R.id.rr_dialog);

   /*     final Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
        rr_dialog.startAnimation(myAnim);*/

        TouchImageView singleImagea = (TouchImageView) dialog.findViewById(R.id.im_single);
        Button bt_image_save = (Button) dialog.findViewById(R.id.bt_image_save);


        singleImagea.setImageBitmap(bitmap);



        bt_image_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadFile(imageUrl, "123");
                saveImage(imageUrl);
                Toast.makeText(activity, "Donwloading to device...", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();


    }

}
