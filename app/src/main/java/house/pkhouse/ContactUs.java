package house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    RelativeLayout rl_chat_icon;

    EditText contactUsNAme, contactUsEmail, contactUsPhoe;
    EditText contactUsMessage;
    Button btSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        init();
        chatIconClick();

    }

    public void init(){



        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ContactUs.this ,R.color.colorRed)));
        getSupportActionBar().setTitle("Contact Us");

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);
    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ContactUs.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }

}
