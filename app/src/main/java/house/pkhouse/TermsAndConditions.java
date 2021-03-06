package house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class TermsAndConditions extends AppCompatActivity {

    RelativeLayout rl_chat_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(TermsAndConditions.this ,R.color.colorRed)));

        getSupportActionBar().setTitle(R.string.tv_pk_house);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

        chatIconClick();

    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(TermsAndConditions.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }
}
