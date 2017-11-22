package house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


public class AboutUs extends AppCompatActivity {

    RelativeLayout rl_chat_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        init();
        chatIconClick();
    }

    public void init(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(AboutUs.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_about_us);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);
    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AboutUs.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }
}
