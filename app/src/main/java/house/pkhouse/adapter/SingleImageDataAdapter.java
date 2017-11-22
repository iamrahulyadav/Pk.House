package house.pkhouse.adapter;

/**
 * Created by User-10 on 28-Mar-17.
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

import java.util.ArrayList;
import java.util.HashMap;

import house.pkhouse.R;
import house.pkhouse.TouchImageView;
import house.pkhouse.loader.ImagesLoader;


public class SingleImageDataAdapter extends BaseAdapter {

    private int lastPosition = -1;

    private Activity activity;
    private String[] data;
    ArrayList<HashMap<String, String>> contactList;
    private static LayoutInflater inflater=null;
    public ImagesLoader imageLoader;



    public SingleImageDataAdapter(Activity a, ArrayList<HashMap<String, String>> contactList1) {
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
            vi = inflater.inflate(R.layout.dilaog_single_property_images, null);

        TouchImageView image=(TouchImageView)vi.findViewById(R.id.single_image);

        imageLoader.DisplayImage(contactList.get(position).get("imageurl"), image);


        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.down_from_top : R.anim.wave_scaling);
        vi.startAnimation(animation);
        lastPosition = position;



        return vi;
    }


}