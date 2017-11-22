package house.pkhouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class FindYourDreamProperties extends AppCompatActivity {

    Spinner sp_choseCity, sp_propertyType, sp_propertyStatu, sp_choseCountry, sp_propertyArea, sp_select_option;
    EditText et_minPrice, et_maxPrice, et_propertyArea;
    Button bt_search;

    String selectedCity, selectedPropertyType, selectedPropertyStatus, selectedMinPrice, selectedMaxPrice;
    String selectedCountry, propertyArea, interPropertyType;
    String spTextArea;
    String location;

    private DatePickerDialog datePictkerDialog;
    private SimpleDateFormat dateFormatter;

    private CustomAutoCompleteTextView customAutoCompleteTextView;

    RelativeLayout rl_chat_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_dream_properties);

        init();
        searchButtonClickHandler();
        spinnerValuesOnSelection();
        spinerPropertyTypeSelectListner();
        chatIconClick();


    }//end of onCreate

    public void init(){

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(FindYourDreamProperties.this ,R.color.colorRed)));
        getSupportActionBar().setTitle(R.string.tv_pk_house);
        sp_choseCity = (Spinner) findViewById(R.id.sp_chose_city);
        sp_propertyType = (Spinner) findViewById(R.id.sp_property_type);
        sp_select_option = (Spinner) findViewById(R.id.sp_property_selec_option);
        sp_propertyStatu = (Spinner) findViewById(R.id.sp_property_status);
        sp_choseCountry = (Spinner) findViewById(R.id.sp_chose_country);
        sp_propertyArea = (Spinner) findViewById(R.id.sp_area);
        et_propertyArea = (EditText) findViewById(R.id.parea) ;
        et_minPrice = (EditText) findViewById(R.id.et_min_price);
        et_maxPrice = (EditText) findViewById(R.id.et_max_price);
        bt_search = (Button) findViewById(R.id.bt_search);

        customAutoCompleteTextView = (CustomAutoCompleteTextView) findViewById(R.id.atv_auto_places);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        rl_chat_icon = (RelativeLayout) findViewById(R.id.rl_live_chat);



    }//end of init



    public void spinnerValuesOnSelection(){
        sp_choseCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            FindYourDreamProperties.this, R.array.chose_a_city, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_choseCity.setAdapter(adapter);


                }

                if (position==2){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            FindYourDreamProperties.this, R.array.chose_a_city_uae, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_choseCity.setAdapter(adapter);


                }

                if (position==3){


                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            FindYourDreamProperties.this, R.array.chose_a_city_oman, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_choseCity.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end of spinner values on Selection


    ////

    public void spinerPropertyTypeSelectListner(){
        sp_propertyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            FindYourDreamProperties.this, R.array.for_house, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_select_option.setAdapter(adapter);


                }

                if (position==2){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            FindYourDreamProperties.this, R.array.for_plot, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_select_option.setAdapter(adapter);


                }

                if (position==3){


                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            FindYourDreamProperties.this, R.array.for_commercial, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_select_option.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end of spinner values on Selection
    ///

    public void searchButtonClickHandler(){

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent(FindYourDreamProperties.this, showProperties.class);
                startActivity(intent);*/

                boolean spChecker =  validatingSpinner();
                if (spChecker){
                    Toast.makeText(FindYourDreamProperties.this, "Please Wait data is loading....", Toast.LENGTH_SHORT).show();





                    Intent intent = new Intent(FindYourDreamProperties.this, ShowProperties.class);
                    intent.putExtra("country", selectedCountry);
                    intent.putExtra("city", selectedCity);
                    intent.putExtra("ptype", selectedPropertyType);
                    intent.putExtra("piner", interPropertyType);
                    intent.putExtra("pstatus", selectedPropertyStatus);
                    intent.putExtra("parea", spTextArea);
                    intent.putExtra("location", location);
                    intent.putExtra("minprice", selectedMinPrice);
                    intent.putExtra("maxprice", selectedMaxPrice);
                    startActivity(intent);


                }else {
                    //Toast.makeText(FindYourDreamProperties.this, "Look Like You Miss Something", Toast.LENGTH_SHORT).show();



                }
            }
        });




    }

    public boolean validatingSpinner(){
        boolean isSpinnerEmpty;
        int spPositionCity = sp_choseCity.getSelectedItemPosition();
        int spPositionCountry = sp_choseCountry.getSelectedItemPosition();
        int spPositionPropertyType = sp_propertyType.getSelectedItemPosition();
        int spPositionPropertyStatus = sp_propertyStatu.getSelectedItemPosition();

        if (spPositionCountry==0){

            Toast.makeText(this, "Please Chose Country", Toast.LENGTH_SHORT).show();
            isSpinnerEmpty = false;

        }
         /* else if (spPositionPropertyType == 0){

            Toast.makeText(this, "Plese Chose Property type", Toast.LENGTH_SHORT).show();
            isSpinnerEmpty = false;
        }
      else if(spPositionPropertyType==0){

            Toast.makeText(this, "Please Chose Property Type", Toast.LENGTH_SHORT).show();
            isSpinnerEmpty = false;

        }else if(spPositionPropertyStatus==0){

            Toast.makeText(this, "Please Chose Property Status", Toast.LENGTH_SHORT).show();
            isSpinnerEmpty = false;

        }*/
        else {



            selectedCountry = sp_choseCountry.getSelectedItem().toString();
            selectedCity = sp_choseCity.getSelectedItem().toString();

            selectedPropertyType = sp_propertyType.getSelectedItem().toString();
            selectedPropertyStatus = sp_propertyStatu.getSelectedItem().toString();
            selectedMinPrice = et_minPrice.getText().toString();
            selectedMaxPrice = et_maxPrice.getText().toString();

            interPropertyType = sp_select_option.getSelectedItem().toString();
            if (interPropertyType.equals("Select Option")){
                interPropertyType = "";
            }

            String spArea = sp_propertyArea.getSelectedItem().toString();
            propertyArea  =  et_propertyArea.getText().toString();

            spTextArea = propertyArea + " " + spArea;

            location = customAutoCompleteTextView.getText().toString();

            if (selectedPropertyType.equals("Property Type")){

                selectedPropertyType = "";
            }


            if (selectedPropertyStatus.equals("Property For")){
                selectedPropertyStatus = "";
            }
            if (selectedPropertyStatus.equals("Buy")){
                selectedPropertyStatus = "Sale";
            }

            if (propertyArea.length()==0){
                spTextArea = "";
            }
            if (sp_choseCity.getSelectedItemId()==0){
                selectedCity = "default";
            }



            Log.e("TAGE", "TEST Country: " + selectedCountry);
            Log.e("TAGE", "TEST City: " + selectedCity);
            Log.e("TAGE", "TEST Property Type: " + selectedPropertyType);
            Log.e("TAGE", "TEST Property Inter: " + interPropertyType);
            Log.e("TAGE", "TEST Property Status: " + selectedPropertyStatus);
            Log.e("TAGE", "TEST Area: " + spTextArea);
            Log.e("TAGE", "TEST Min Price: " + selectedMinPrice);
            Log.e("TAGE", "TEST Max Price: " + selectedMaxPrice);
            Log.e("TAGE", "Location: " + location);






            isSpinnerEmpty = true;

        }

        return isSpinnerEmpty;

    }

    public void chatIconClick(){

        rl_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FindYourDreamProperties.this, LiveSupport.class);
                startActivity(i);
            }
        });
    }

}
