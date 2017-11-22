package house.pkhouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.LeadingMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/**
 * Created by User-10 on 16-Mar-17.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;*/

import co.geeksters.googleplaceautocomplete.lib.CustomAutoCompleteTextView;


public class SubmitThirldPage extends Fragment {

    public static EditText m_ed_location, m_ed_phoneNumber, m_ed_Name, m_ed_email;
    public static Spinner sp_country, sp_city;
    public static LinearLayout m_llEmail;
    public static LinearLayout m_ll_account_type;
    public static LinearLayout m_ll_et_client_no;
    public static LinearLayout m_ll_account_type_client_no;

    public static RelativeLayout m_ll_contact_et;
    public static RelativeLayout m_rl_contact_tv;
    public static TextView m_tv_fithline;

    public static  Spinner m_sp_selec_country_for_phone;
    public static Spinner m_sp_account_type;
    public static TextView m_tv_country_code;
    public static EditText m_et_mobile_code, m_register_phone;
    public static EditText m_et_client_no;

    public static RelativeLayout m_rl_et_client;

    public static  ProgressBar m_bar;

    public static String m_et_location  = null;

    public static  CustomAutoCompleteTextView m_customAutoCompleteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.submit_third_page, container, false);


        m_customAutoCompleteTextView = (CustomAutoCompleteTextView)view.findViewById(R.id.atv_auto_places);


        m_ed_location = (EditText) view.findViewById(R.id.m_ed_location);
        m_ed_phoneNumber = (EditText) view.findViewById(R.id.m_ed_phone_number);
        m_ed_Name = (EditText) view.findViewById(R.id.m_ed_name);
        m_ed_email = (EditText) view.findViewById(R.id.m_ed_email);
        sp_country = (Spinner) view.findViewById(R.id.sp_chose_country);
        sp_city = (Spinner) view.findViewById(R.id.sp_chose_city);
        m_llEmail = (LinearLayout) view.findViewById(R.id.ll_email);
        m_ll_account_type = (LinearLayout) view.findViewById(R.id.ll_account_type);
        m_ll_et_client_no = (LinearLayout) view.findViewById(R.id.ll_et_client_no);
        m_ll_account_type_client_no = (LinearLayout) view.findViewById(R.id.ll_account_type_client_no);

        m_rl_contact_tv = (RelativeLayout)view.findViewById(R.id.lr_contact_text);
        m_tv_fithline = (TextView)view.findViewById(R.id.tv_fithstraight_line);
        m_ll_contact_et = (RelativeLayout) view.findViewById(R.id.rl_contact_ed);

        m_sp_selec_country_for_phone = (Spinner) view.findViewById(R.id.sp_select_country);
        m_sp_account_type = (Spinner) view.findViewById(R.id.sp_account_type);
        m_tv_country_code = (TextView) view.findViewById(R.id.tv_country_code);
        m_et_mobile_code = (EditText) view.findViewById(R.id.et_mobile_code);
        m_register_phone = (EditText) view.findViewById(R.id.register_phone);
        m_et_client_no = (EditText) view.findViewById(R.id.et_client_no);
        m_rl_et_client = (RelativeLayout) view.findViewById(R.id.rl_et_client);


        m_bar = (ProgressBar) view.findViewById(R.id.progressBar);

        spinnerValuesOnSelection();
        startingWithZeroRestriction();
        mobileSpinnerValuesOnSelection();
        spinerAccountTypeSelection();
        startingWithZeroRestrictionCientNumber();




//        customAutoCompleteTextView.googlePlace.getCountry(); //Return the country name
      //  customAutoCompleteTextView.googlePlace.getCity(); //Return the city
       // m_et_location =  customAutoCompleteTextView.googlePlace.getDescription(); //Return the description (city + region + country)

        return view;
    }


    public void spinnerValuesOnSelection(){
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    m_sp_selec_country_for_phone.setSelection(0);

                }
                if (position==1){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.chose_a_city, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(adapter);

                    m_sp_selec_country_for_phone.setSelection(1);

                }

                if (position==2){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.chose_a_city_uae, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(adapter);

                    m_sp_selec_country_for_phone.setSelection(2);

                }

                if (position==3){


                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.chose_a_city_oman, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(adapter);

                    m_sp_selec_country_for_phone.setSelection(3);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//end of spinner values on Selection

    //spiner select for mobile
    public void mobileSpinnerValuesOnSelection(){
        m_sp_selec_country_for_phone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){

                    m_tv_country_code.setText("");


                }

                if (position==1){

                    m_tv_country_code.setText("92");


                }

                if (position==2){



                    m_tv_country_code.setText("971");


                }

                if (position==3){


                    m_tv_country_code.setText("968");


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//end of spinner values on Selection for mobile



    public void startingWithZeroRestriction(){
        m_ed_phoneNumber.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                String x = s.toString();
                if(x.startsWith("0"))
                {
                    Toast.makeText(getActivity(), "Pleae enter number starting with 92 format", Toast.LENGTH_LONG).show();
                    m_ed_phoneNumber.setText("");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });//end for login editText

    }

    //zro restriction for client numnber
    public void startingWithZeroRestrictionCientNumber(){
        m_et_client_no.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                String x = s.toString();
                if(x.startsWith("0"))
                {

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }

                if (x.startsWith("1")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("2")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("3")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("4")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("5")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("6")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("7")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
                if (x.startsWith("8")){

                    Toast.makeText(getActivity(), "Pleae enter number starting with 92", Toast.LENGTH_SHORT).show();
                    m_et_client_no.setText("");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });//end for login editText

    }

    public void spinerAccountTypeSelection(){

      m_sp_account_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


              if (m_sp_account_type.getSelectedItemId()==0){
                  m_ll_et_client_no.setVisibility(View.GONE);
              }

              if (m_sp_account_type.getSelectedItemId()==1){
                  m_ll_et_client_no.setVisibility(View.VISIBLE);
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
    }

}