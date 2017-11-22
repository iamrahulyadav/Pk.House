package house.pkhouse.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import house.pkhouse.R;

/**
 * Created by User-10 on 17-Jul-17.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> fetchList;
    private static LayoutInflater inflater=null;
    int mIndicator;
    int rotatevalue = 0;
    int currentPos = -1;
    int prePosition = -2;

    String arrValue = "";

    HashMap<Integer, Matrix> mImageTransforms = new HashMap<Integer,Matrix>();
    Matrix mIdentityMatrix = new Matrix();

    public ImageAdapter(Context c, ArrayList<String> fetchList1, int indicator) {
        mContext = c;
        fetchList = fetchList1;
        mIndicator = indicator;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {

        return fetchList.size();

    }

    public Object getItem(int position) {
        return null;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        View vi=convertView;




        if(convertView==null)

            vi = inflater.inflate(R.layout.grid_image_list, null);

        final ImageView image = (ImageView)vi.findViewById(R.id.image);
        ImageView cross = (ImageView)vi.findViewById(R.id.cross);

       // Bitmap b = BitmapFactory.decodeFile(fetchList.get(position));
        //Log.e("TAG", "IMAGE BITMAP: " + b);

//            image.setImageBitmap(BitmapFactory.decodeFile(fetchList.get(position)));

        final BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = false;
       // options.inSampleSize = -10;

        //options.inPurgeable = true;
        options.outHeight = 40;
        options.outWidth = 40;
        options.inSampleSize = -50;



        if (mIndicator == 1){

            Glide.with(mContext)
                    .load(fetchList.get(position))
                    .placeholder(R.drawable.default_image)
                    //.error(R.drawable.)
                    .override(400, 400)
                    .centerCrop()
                    .into(image);

        }
        else {



            Bitmap bm = BitmapFactory.decodeFile(fetchList.get(position), options);
            image.setImageBitmap(bm);

        }
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchList.remove(position);
                notifyDataSetChanged();
            }
        });

        Log.e("TAG", "Indicator is : " + mIndicator);
        if (mIndicator == 1){
            //do something if image is from server
        }
        else {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle("Roate Image");
                        alertDialog.setMessage("Rote Image to right, left or down Using Given Buttons");
                        alertDialog.setPositiveButton("To Left", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                performRotation(image, position, 270, options);
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setNegativeButton("To Right", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                performRotation(image, position, 90, options);
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setNeutralButton("To Down", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                performRotation(image, position, 180, options);
                                dialogInterface.dismiss();
                            }
                        });

                        alertDialog.show();


                }
            });
        }

        return vi;

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    public Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
    }

    private void rotate(ImageView imgview, float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(2000);
        rotateAnim.setFillAfter(true);
        imgview.startAnimation(rotateAnim);
    }

/*    private String getOrientation(String uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String orientation = "landscape";
        try{
            String image = new File(uri).getAbsolutePath();
            BitmapFactory.decodeFile(image, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            if (imageHeight > imageWidth){
                orientation = "portrait";
            }
        }catch (Exception e){
            //Do nothing
        }
        return orientation;
    }*/

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 512, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
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

    public void performRotation(ImageView imageView, int position, float angle, BitmapFactory.Options options){
        Bitmap bmp = BitmapFactory.decodeFile(fetchList.get(position), options);
        Log.e("TAg", "befor bitmap: " + bmp);
        Bitmap bb = RotateBitmap(bmp, angle);
        imageView.setImageBitmap(bb);
        Log.e("TAg", "after bitmap: " + bb);
        Uri ii = getImageUri(mContext, bb);
        Log.e("TAg", "urii: " + ii);
        String presentPath = getRealPathFromURI(ii, mContext);
        Log.e("TAg", "prev path: " + fetchList.get(position).toString());
        Log.e("TAg", "current path: " + presentPath);

        fetchList.set(position, presentPath);

    }



}