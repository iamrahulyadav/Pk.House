package house.pkhouse.adapter;

/**
 * Created by User-10 on 12-May-17.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import house.pkhouse.loader.ImagesLoader;
import house.pkhouse.R;

public class FranchiserAdapter extends BaseAdapter {

    private int lastPosition = -1;

    private Activity activity;
    private String[] data;
    ArrayList<HashMap<String, String>> contactList;
    private static LayoutInflater inflater=null;
    public ImagesLoader imageLoader;

    // int i = 10;

    public FranchiserAdapter(Activity a, ArrayList<HashMap<String, String>> contactList1) {
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
            vi = inflater.inflate(R.layout.frnachiser_list_layout, null);




        TextView tv_companyName = (TextView)vi.findViewById(R.id.tv_compnay_name);
        TextView tv_name = (TextView)vi.findViewById(R.id.tv_name);
        TextView tv_number = (TextView)vi.findViewById(R.id.tv_number);
        TextView tv_cnic = (TextView)vi.findViewById(R.id.tv_cnic);
        TextView tv_area = (TextView)vi.findViewById(R.id.tv_area);
        TextView tv_city = (TextView)vi.findViewById(R.id.tv_city);
        TextView tv_country = (TextView)vi.findViewById(R.id.tv_country);

        ImageView image=(ImageView)vi.findViewById(R.id.image);

        String name = contactList.get(position).get("name").toString();
        String email = contactList.get(position).get("email").toString();
        String companyName = contactList.get(position).get("company_name").toString();
        String areaLocation = contactList.get(position).get("area_location").toString();
        String city = contactList.get(position).get("city").toString();
        String country = contactList.get(position).get("country").toString();
        String number = contactList.get(position).get("number").toString();



        if (city.equals("null")){
            city = "";
        }
        if (country.equals("null")){
            country = "";
        }
        if (areaLocation.equals("null")){
            areaLocation = "";
        }

        tv_companyName.setText(companyName);
        tv_name.setText(name);
        tv_number.setText(number);
        tv_area.setText(areaLocation);
        tv_city.setText(city);
        tv_country.setText(country);

        //imageLoader.DisplayImage(contactList.get(position).get("imageurl"), image);


        Glide.with(activity)
                .load(contactList.get(position).get("imageurl"))
                .placeholder(R.drawable.default_image)
                //.error(R.drawable.)
                .override(200, 200)
                .centerCrop()
                .into(image);



        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.down_from_top : R.anim.wave_scaling);
        vi.startAnimation(animation);
        lastPosition = position;

        //imageLoader.DisplayImage(data[position], image);
        return vi;
    }



}