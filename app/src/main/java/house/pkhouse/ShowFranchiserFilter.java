package house.pkhouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import co.geeksters.googleplaceautocomplete.lib.CustomAutoCompleteTextView;

public class ShowFranchiserFilter extends AppCompatActivity {

    Spinner sp_choseCity, sp_choseCountry;
    EditText et_mobile;
    private CustomAutoCompleteTextView et_area;
    Button bt_search;

    String selectedCity;
    String selectedCountry;
    String etArea, etMobile;

    RelativeLayout rl_chat_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_franchiser_filter);

        init();
        searchButtonClickHandler();
        spinnerValuesOnSelection();
        chatIconClick();


    }//end of onCreate

    public void init(){

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ShowFranchiserFilter.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
        sp_choseCity = (Spinner) findViewById(R.id.sp_chose_city);
        sp_choseCountry = (Spinner) findViewById(R.id.sp_chose_country);

        et_area = (CustomAutoCompleteTextView) findViewById(R.id.et_area);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        bt_search = (Button) findViewById(R.id.bt_search);



        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);



    }//end of init



    public void spinnerValuesOnSelection(){
        sp_choseCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            ShowFranchiserFilter.this, R.array.chose_a_city, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_choseCity.setAdapter(adapter);
                    sp_choseCity.setSelection(0);


                }

                if (position==2){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            ShowFranchiserFilter.this, R.array.chose_a_city_uae, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_choseCity.setAdapter(adapter);


                }

                if (position==3){


                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            ShowFranchiserFilter.this, R.array.chose_a_city_oman, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_choseCity.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end of spinner values on Selection

    public void searchButtonClickHandler(){

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent(FindYourDreamProperties.this, showProperties.class);
                startActivity(intent);*/

             validatingSpinner();



                    Intent intent = new Intent(ShowFranchiserFilter.this, ShowFranchisers.class);
                    intent.putExtra("country", selectedCountry);
                    intent.putExtra("city", selectedCity);
                    intent.putExtra("area", etArea);
                    intent.putExtra("mobile", etMobile);
                    startActivity(intent);



            }
        });




    }

    public boolean validatingSpinner(){
        boolean isSpinnerEmpty;
        int spPositionCity = sp_choseCity.getSelectedItemPosition();
        int spPositionCountry = sp_choseCountry.getSelectedItemPosition();





            selectedCountry = sp_choseCountry.getSelectedItem().toString();
            selectedCity = sp_choseCity.getSelectedItem().toString();



            etArea = et_area.getText().toString();
            etMobile = et_mobile.getText().toString();

            if (selectedCountry.equals("Chose a Country")){
                selectedCountry = "";
            }
            if (sp_choseCity.getSelectedItemId()==0){
                selectedCity = "";
            }



            Log.e("TAGE", "TEST Country: " + selectedCountry);
            Log.e("TAGE", "TEST City: " + selectedCity);
            Log.e("TAGE", "TEST Area: " + etArea);
            Log.e("TAGE", "TEST Mobile: " + etMobile);

            isSpinnerEmpty = true;



        return isSpinnerEmpty;

    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ShowFranchiserFilter.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }

}
