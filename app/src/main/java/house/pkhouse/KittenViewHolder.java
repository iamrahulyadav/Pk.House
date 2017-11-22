package house.pkhouse;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by User-10 on 27-Sep-17.
 */

public class KittenViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    public KittenViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
