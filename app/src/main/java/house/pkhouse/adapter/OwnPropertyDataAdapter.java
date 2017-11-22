package house.pkhouse.adapter;

/**
 * Created by User-10 on 15-Jun-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import house.pkhouse.R;
import house.pkhouse.loader.ImagesLoader;

public class OwnPropertyDataAdapter extends BaseAdapter {

    private int lastPosition = -1;

    ShareDialog shareDialog;
    final  String appLinke = "https://play.google.com/store/apps/details?id=house.pkhouse";
    String fnNumber;
    static final int MY_PERMISSIONS_PHONE_CALL = 1000;

    private Activity activity;
    private String[] data;
    ArrayList<HashMap<String, String>> contactList;
    private static LayoutInflater inflater=null;
    public ImagesLoader imageLoader;

    // int i = 10;

    public OwnPropertyDataAdapter(Activity a, ArrayList<HashMap<String, String>> contactList1) {
        activity = a;
        //data=d;
        contactList = contactList1;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImagesLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return contactList.size();
        //return  i;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.view_own_property_layout, null);

        TextView tv_property_id = (TextView)vi.findViewById(R.id.tv_protperty_id);
        TextView tv_price = (TextView)vi.findViewById(R.id.tv_price);
        TextView tv_propertyTitle = (TextView)vi.findViewById(R.id.tv_property_title);
        TextView tv_propertyTitleFull = (TextView)vi.findViewById(R.id.tv_property_title_full);
        TextView tv_propertyFullPrice = (TextView)vi.findViewById(R.id.tv_full_price);
        TextView tv_country = (TextView)vi.findViewById(R.id.tv_country);
        TextView tv_propertyPropertyLandArea = (TextView)vi.findViewById(R.id.tv_land_area);
        TextView tv_propertyCity = (TextView)vi.findViewById(R.id.tv_city);
        TextView tv_propertyLocation = (TextView)vi.findViewById(R.id.tv_location);
        TextView tv_propertyPhone = (TextView)vi.findViewById(R.id.tv_contact);
        TextView tv_protperty_type = (TextView)vi.findViewById(R.id.tv_protperty_type);
        TextView tv_protperty_status = (TextView)vi.findViewById(R.id.tv_protperty_status);
        TextView tv_protperty_description = (TextView)vi.findViewById(R.id.tv_protperty_description);

        TextView tv_propertyEmail = (TextView)vi.findViewById(R.id.tv_email);

        TextView tv_property_rooms = (TextView)vi.findViewById(R.id.tv_protperty_rooms);
        TextView tv_property_floors = (TextView)vi.findViewById(R.id.tv_protperty_floors);
        TextView tv_property_bathrooms = (TextView)vi.findViewById(R.id.tv_protperty_bathrooms);
        TextView tv_property_status_prpoperty = (TextView)vi.findViewById(R.id.tv_protperty_status_property);

        TextView tv_image_url = (TextView)vi.findViewById(R.id.tv_url);

        final ImageView image=(ImageView)vi.findViewById(R.id.image);
        // ImageView share_on_fb = (ImageView)vi.findViewById(R.id.share_on_fb);
        // ImageView share_on_tw = (ImageView)vi.findViewById(R.id.share_on_tw);

        RelativeLayout rl_phone = (RelativeLayout)vi.findViewById(R.id.rl_phone);



        final String propertyID = contactList.get(position).get("property_id").toString();
        final String propertyTitle = contactList.get(position).get("property_title").toString();
        final String propertyTitleFull = contactList.get(position).get("property_title").toString();
        final String price = contactList.get(position).get("price").toString();
        final String fullprice = contactList.get(position).get("fullprice").toString();
        String country = contactList.get(position).get("country").toString();
        String city = contactList.get(position).get("city").toString();
        String location = contactList.get(position).get("location").toString();
        final String landArea = contactList.get(position).get("landArea").toString();
        final String contact = contactList.get(position).get("phone").toString();
        String propertyType = contactList.get(position).get("property_type").toString();
        String propertystatus =  contactList.get(position).get("status").toString();
        String description = contactList.get(position).get("property_description").toString();
        String rooms = contactList.get(position).get("rooms").toString();
        String bathrooms = contactList.get(position).get("bathrooms").toString();
        String floors = contactList.get(position).get("floors").toString();
        String status_property = contactList.get(position).get("status_property").toString();
        String dealer_email = contactList.get(position).get("dealer_email").toString();

        String imageURL = contactList.get(position).get("imageurl").trim().toString();



        tv_property_id.setText(propertyID);
        tv_propertyTitle.setText(propertyTitle);
        tv_propertyTitleFull.setText(propertyTitleFull);
        tv_price.setText(price);
        tv_propertyFullPrice.setText(fullprice);
        tv_country.setText(country);
        tv_propertyCity.setText(city);
        tv_propertyPhone.setText(contact);
        tv_propertyPropertyLandArea.setText(landArea);
        tv_protperty_type.setText(propertyType);
        tv_protperty_status.setText(propertystatus);
        tv_protperty_description.setText(description);
        tv_propertyLocation.setText(location);

        tv_propertyEmail.setText(dealer_email);

        tv_property_rooms.setText(rooms);
        tv_property_bathrooms.setText(bathrooms);
        tv_property_floors.setText(floors);
        tv_property_status_prpoperty.setText(status_property);

        tv_image_url.setText(imageURL);

      //  imageLoader.DisplayImage(contactList.get(position).get("imageurl"), image);



        Glide.with(activity)
                .load(contactList.get(position).get("imageurl"))
                .placeholder(R.drawable.default_image)
                //.error(R.drawable.)
                .override(200, 200)
                .centerCrop()
                .into(image);





        //Picasso.with(activity).load(contactList.get(position).get("imageurl")).into(image);


        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.down_from_top : R.anim.wave_scaling);
        vi.startAnimation(animation);
        lastPosition = position;


/*
        //facebook share button click lister
        share_on_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog = new ShareDialog(activity);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://www.pk.house/property-detail/"+propertyID))
                            .build();
                    shareDialog.show(linkContent);
                }

                //Toast.makeText(activity, "thtahthath", Toast.LENGTH_SHORT).show();
            }
        });//end for facebook share

        //twitter share button click lister
        share_on_tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap image1 = ((BitmapDrawable)image.getDrawable()).getBitmap();



                Uri path = getImageUri(activity, image1);

                Intent tweetIntent = new Intent(Intent.ACTION_SEND);
                tweetIntent.putExtra(Intent.EXTRA_TEXT, "Ttitle: "+propertyTitle+ "\nPrice "+price + "\nArea: " + landArea + "\nDownlaod App For Detail: " + appLinke);
                tweetIntent.putExtra(Intent.EXTRA_STREAM, path);
                tweetIntent.setType("text/plain");

                PackageManager packManager = activity.getPackageManager();
                List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

                boolean resolved = false;
                for(ResolveInfo resolveInfo: resolvedInfoList){
                    if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")){
                        tweetIntent.setClassName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name );
                        resolved = true;
                        break;
                    }
                }
                if(resolved){
                    activity.startActivity(tweetIntent);
                }else{
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://twitter.com/intent/tweet?text="+urlEncode("HELLO")));
                    activity.startActivity(i);
                    Toast.makeText(activity, "Twitter app isn't found", Toast.LENGTH_LONG).show();
                }

            }
        });//end of twitter

        //call phone funtion
        rl_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone1 = contact.substring(0, 2);
                String[] a = contact.split("92");
                String right = a[0].toString();
                String left = a[1].toString();
                String number = "0"+left;
                Log.e("TAG", "phone Number Is: " + phone1);
                Log.e("TAG", "phone Number Is: " + right);
                Log.e("TAG", "phone Number Is: " + left);
                Log.e("TAG", "phone Number Is: " + number);

                fnNumber = number;
                call(fnNumber);
            }
        });//end for call button

*/

        //imageLoader.DisplayImage(data[position], image);
        return vi;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            Log.wtf("TAG", "UTF-8 should always be supported", e);
            return "";
        }
    }


    public void call(String mn){


        int permissionCheck = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_PHONE_CALL);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mn));
            activity.startActivity(callIntent);
        }
    }

}