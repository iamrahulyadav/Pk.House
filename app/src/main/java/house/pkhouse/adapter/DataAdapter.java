package house.pkhouse.adapter;

/**
 * Created by User-10 on 27-Mar-17.
 */

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import house.pkhouse.Constant.AllURLs;
import house.pkhouse.ShowProperties;
import house.pkhouse.SinglePropertyImage;
import house.pkhouse.loader.ImagesLoader;
import house.pkhouse.R;

import static android.R.id.message;

public class DataAdapter extends BaseAdapter {

    private int lastPosition = -1;

    CheckBox check_fav;
    boolean isCheckboxCheck = false;
    String checkId = "#";

    ShareDialog shareDialog;
    final  String appLinke = "https://play.google.com/store/apps/details?id=house.pkhouse";
    String fnNumber;
    static final int MY_PERMISSIONS_PHONE_CALL = 1000;

    private Activity activity;
    private String[] data;
    ArrayList<HashMap<String, String>> contactList;
    private static LayoutInflater inflater=null;
    public ImagesLoader imageLoader;

    String image_ID;
    String propertyId;
    String mTitle, mTitleFull, mPrice, mArea;
    String apkLink;
    Bitmap imageBitmap;
    ListView list;

   // int i = 10;

    public DataAdapter(Activity a, ArrayList<HashMap<String, String>> contactList1) {
        activity = a;
        //data=d;
        contactList = contactList1;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImagesLoader(activity.getApplicationContext());
    }

    public int getCount() {

        return contactList.size();


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
            vi = inflater.inflate(R.layout.row_listview_item, null);

        RelativeLayout rl_list_item = (RelativeLayout)vi.findViewById(R.id.rl_list_item);

        final TextView tv_property_id = (TextView)vi.findViewById(R.id.tv_protperty_id);
        final TextView tv_price = (TextView)vi.findViewById(R.id.tv_price);
        final TextView tv_propertyTitle = (TextView)vi.findViewById(R.id.tv_property_title);
        final TextView tv_propertyTitleFull = (TextView)vi.findViewById(R.id.tv_property_title_full);
        final TextView tv_country = (TextView)vi.findViewById(R.id.tv_country);
        final TextView tv_propertyPropertyLandArea = (TextView)vi.findViewById(R.id.tv_land_area);
        final TextView tv_propertyCity = (TextView)vi.findViewById(R.id.tv_city);
        final TextView tv_propertyLocation = (TextView)vi.findViewById(R.id.tv_location);
        final TextView tv_propertyPhone = (TextView)vi.findViewById(R.id.tv_contact);
        final TextView tv_p_propertyType = (TextView)vi.findViewById(R.id.tv_p_type);
        final TextView tv_protperty_type = (TextView)vi.findViewById(R.id.tv_protperty_type);
        final TextView tv_protperty_status = (TextView)vi.findViewById(R.id.tv_protperty_status);
        final TextView tv_protperty_description = (TextView)vi.findViewById(R.id.tv_protperty_description);

        final TextView tv_propertyEmail = (TextView)vi.findViewById(R.id.tv_email);

        final TextView tv_property_rooms = (TextView)vi.findViewById(R.id.tv_protperty_rooms);
        final TextView tv_property_floors = (TextView)vi.findViewById(R.id.tv_protperty_floors);
        final TextView tv_property_bathrooms = (TextView)vi.findViewById(R.id.tv_protperty_bathrooms);
        final TextView tv_property_status_prpoperty = (TextView)vi.findViewById(R.id.tv_protperty_status_property);
        final TextView tv_image_url = (TextView)vi.findViewById(R.id.tv_url);


       final ImageView image=(ImageView)vi.findViewById(R.id.image);
       // ImageView share_on_fb = (ImageView)vi.findViewById(R.id.share_on_fb);
       // ImageView share_on_tw = (ImageView)vi.findViewById(R.id.share_on_tw);

        RelativeLayout rl_phone = (RelativeLayout)vi.findViewById(R.id.rl_phone);
        check_fav = (CheckBox) vi.findViewById(R.id.check_fav);

        isCheckboxCheck =  check_fav.isChecked();




        final String propertyID = contactList.get(position).get("property_id").toString();
       final String propertyTitle = contactList.get(position).get("property_title").toString();
        final String propertyTitleFull = contactList.get(position).get("property_title").toString();
       final String price = contactList.get(position).get("price").toString();
        final String country = contactList.get(position).get("country").toString();
        final String city = contactList.get(position).get("city").toString();
        final String location = contactList.get(position).get("location").toString();
       final String landArea = contactList.get(position).get("landArea").toString();
       final String contact = contactList.get(position).get("phone").toString();
       final String propertyType = contactList.get(position).get("property_type").toString();
        final String propertystatus =  contactList.get(position).get("status").toString();
        final String description = contactList.get(position).get("property_description").toString();
        final String rooms = contactList.get(position).get("rooms").toString();
        final String bathrooms = contactList.get(position).get("bathrooms").toString();
        final String floors = contactList.get(position).get("floors").toString();
        final String status_property = contactList.get(position).get("status_property").toString();
        final String dealer_email = contactList.get(position).get("dealer_email").toString();

        final String imageURL = contactList.get(position).get("imageurl").trim().toString();



        tv_property_id.setText(propertyID);
        if (propertyTitle.equals("")){

            TextView tvDotDot = (TextView)vi.findViewById(R.id.tv_dot_dot);
            tvDotDot.setText("No Title");
            //tv_propertyTitle.setText("No Title");
        }
        tv_propertyTitle.setText(propertyTitle);
        tv_propertyTitleFull.setText(propertyTitleFull);
        tv_price.setText(price);
        tv_country.setText(country);
        tv_propertyCity.setText(city);
        tv_propertyPhone.setText(contact);
        tv_p_propertyType.setText(propertyType);
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

        //imageLoader.DisplayImage(contactList.get(position).get("imageurl"), image);

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



        rl_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //



                final String title = tv_propertyTitle.getText().toString();
                final String titleFull = tv_propertyTitleFull.getText().toString();
                final String price = tv_price.getText().toString();
                final  String area = tv_propertyPropertyLandArea.getText().toString();
                final String contact = tv_propertyPhone.getText().toString();
                final  String appLinke = AllURLs.APP_URL; //"https://play.google.com/store/apps/details?id=house.pkhouse";
                final String pID = tv_property_id.getText().toString();
                final  String imageURL = tv_image_url.getText().toString();
                propertyId = pID;
                mTitle = title;
                mTitleFull = titleFull;
                mPrice = price;
                mArea = area;
                apkLink = appLinke;

                final String propertyTitle = tv_propertyTitle.getText().toString();
                final String propertyTitleFull = tv_propertyTitleFull.getText().toString();
                final String propertyPrice = tv_price.getText().toString();
                final String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                final String propertyCity = tv_propertyCity.getText().toString();
                final String propertyContact = tv_propertyPhone.getText().toString();
                final String propertyType = tv_protperty_type.getText().toString();
                final String propertyStatus = tv_protperty_status.getText().toString();
                final String propertyDescription = tv_protperty_description.getText().toString();



                Log.e("TAG", "Property Id: " + propertyID);
                Log.e("TAG", "Property TILE: " + propertyTitle);
                Log.e("TAG", "Property TILE Full: " + mTitleFull);
                Log.e("TAG", "Property Price: " + propertyPrice);
                Log.e("TAG", "Property Area: " + propertyLandArea);
                Log.e("TAG", "Property City: " + propertyCity);
                Log.e("TAG", "Property Contact: " + propertyContact);
                Log.e("TAG", "Property Type: " + propertyType);
                Log.e("TAG", "Property Status: " + propertyStatus);
                Log.e("TAG", "Property Description: " + propertyDescription);

                image_ID = pID;




                Intent i = new Intent(activity, SinglePropertyImage.class);
                i.putExtra("ID", propertyID);
                i.putExtra("TITLE", propertyTitle);
                i.putExtra("TITLEFULL", mTitleFull);
                i.putExtra("PRICE", propertyPrice);
                i.putExtra("CITY", propertyCity);
                i.putExtra("PHONE", propertyContact);
                i.putExtra("STATUS", propertyStatus);
                i.putExtra("DESCRIPTION", propertyDescription);
                i.putExtra("TYPE", propertyType);
                i.putExtra("AREA", propertyLandArea);
                i.putExtra("dealer_email", dealer_email);
                activity.startActivity(i);




                //
            }
        });

        return vi;
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