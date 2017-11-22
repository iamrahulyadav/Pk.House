package house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LoginSelector extends AppCompatActivity {

    Button btLoginNormal, btLoginFranchiser;
    RelativeLayout rl_chat_icon;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_selector);

        init();
        butClickListner();
        chatIconClick();
    }

    public void init(){

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(LoginSelector.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);

        Intent i = getIntent();
        if (i.getExtras()!=null){
            value = i.getExtras().getInt("fromcheckbox");
            Log.e("TAG", "THE THE: " + value);
        }

        btLoginNormal = (Button) findViewById(R.id.bt_normal_login);
        btLoginFranchiser = (Button) findViewById(R.id.bt_franchiser_login);

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);

    }

    public void butClickListner(){

        //login for normal
        btLoginNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent normalLogin = new Intent(LoginSelector.this, LoginOrRegister.class);
                normalLogin.putExtra("value", value);
                startActivity(normalLogin);
                finish();

            }
        });//end of normal button handler

        //login for franchiser
        btLoginFranchiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent franchiserLogin = new Intent(LoginSelector.this, LoginFranchiser.class);
                franchiserLogin.putExtra("value", value);
                startActivity(franchiserLogin);
                finish();

            }
        });
    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginSelector.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }

}
