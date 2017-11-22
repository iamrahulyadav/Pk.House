package house.pkhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User-10 on 16-Mar-17.
 */
public class SubmitFirstPage extends Fragment {


    public static EditText ed_protpertyTitle, ed_propertyPrice, ed_noOfRooms, ed_ofBathrooms, ed_floors, ed_description, ed_structureOfProperty, ed_landAread;
    public static EditText ed_flooringStructer, ed_wallsStructure, ed_doorsStructure, ed_windowsStructure, ed_electricalStructure;

    public static Spinner sp_propertyType, sp_propertyFor, sp_landArea, sp_propertyAvail;

    public static LinearLayout ll_propertyInerNumber, ll_property_flooring, ll_walls_door, ll_windwos_electrical;


    public static RadioGroup radioGroupPeropertyType;
    public static RadioButton radioButtonHome, radioButtonPlots, radioButtonCommercial;
    public static RelativeLayout relativlyoutForHouse, relativeLayoutForPlots, relativeLayoutForCommercial;
    public static TextView tvHomeHouse, tvHomeFlat, tvHomeUperportion, tvHomeLowerportion, tvHomeFarmhouse, tvHomeRoom, tvHomepenthoue;
    public static ImageView ivHomeHouse, ivHomeFlat, ivHomeUperportion, ivHomeLowerportion, ivHomeFarmhouse, ivHomeRoom, ivHomepenthoue;
    public static TextView tvPlotResidential, tvPlotCommercial, tvPlotAgricultural, tvPlotIndustrial, tvPlotfile, tvPlotForm;
    public static ImageView ivPlotResidentail, ivPlotCommercial, ivPlotAgritulturea, ivPlotIndustrial, ivPlotFile, ivPlotForm;
    public static TextView tvCommercialOffice, tvCommercialShop, tvCommercialWarehouse, tvCommercialFactory, tvCommercialBuilding, tvCommercialOther;
    public static ImageView ivCommercialOffice, ivCommercialShop, ivCommercialWerehouse, ivCommercialFactory, ivCommercialBuilding, ivCommercialOther;

    public static int radioPosition = -1;
    public static String radioValue = "no";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.submit_first_page, container, false);

        ed_protpertyTitle = (EditText) view.findViewById(R.id.et_property_title);
        ed_propertyPrice = (EditText) view.findViewById(R.id.ed_price);
        ed_noOfRooms = (EditText) view.findViewById(R.id.ed_rooms);
        ed_ofBathrooms = (EditText) view.findViewById(R.id.ed_no_of_bathrooms);
        ed_floors = (EditText) view.findViewById(R.id.ed_no_of_floors);
        ed_description = (EditText) view.findViewById(R.id.et_description);

        ed_landAread = (EditText) view.findViewById(R.id.ed_land_area);


        sp_propertyType = (Spinner) view.findViewById(R.id.sp_property_type);
        sp_propertyFor = (Spinner) view.findViewById(R.id.sp_property_for);
        sp_landArea = (Spinner) view.findViewById(R.id.sp_land_area);
        sp_propertyAvail = (Spinner) view.findViewById(R.id.sp_property_avail);

        ll_propertyInerNumber = (LinearLayout) view.findViewById(R.id.ll_propertyInerNumber);

        radioGroupPeropertyType = (RadioGroup) view.findViewById(R.id.radio_group_property_type);
        radioButtonHome = (RadioButton) view.findViewById(R.id.radio_house);
        radioButtonPlots = (RadioButton) view.findViewById(R.id.radio_plots);
        radioButtonCommercial = (RadioButton) view.findViewById(R.id.radio_commercial);

        relativlyoutForHouse = (RelativeLayout) view.findViewById(R.id.ll_house_flat);
        relativeLayoutForPlots = (RelativeLayout) view.findViewById(R.id.ll_residentail_commercial_plot);
        relativeLayoutForCommercial = (RelativeLayout) view.findViewById(R.id.ll_ofice_shop);

        tvHomeHouse = (TextView) view.findViewById(R.id.house);
        tvHomeFlat = (TextView) view.findViewById(R.id.flate);
        tvHomeUperportion = (TextView) view.findViewById(R.id.uperportion);
        tvHomeLowerportion = (TextView) view.findViewById(R.id.lowerportion);
        tvHomeFarmhouse = (TextView) view.findViewById(R.id.farmhouse);
        tvHomeRoom = (TextView) view.findViewById(R.id.room);
        tvHomepenthoue = (TextView) view.findViewById(R.id.penthoue);

        tvPlotResidential = (TextView) view.findViewById(R.id.residentail);
        tvPlotCommercial = (TextView) view.findViewById(R.id.commercial);
        tvPlotAgricultural = (TextView) view.findViewById(R.id.agriculture);
        tvPlotIndustrial = (TextView) view.findViewById(R.id.industrial);
        tvPlotfile = (TextView) view.findViewById(R.id.file);
        tvPlotForm = (TextView) view.findViewById(R.id.form);

        tvCommercialOffice = (TextView) view.findViewById(R.id.office);
        tvCommercialShop = (TextView) view.findViewById(R.id.shop);
        tvCommercialWarehouse = (TextView) view.findViewById(R.id.werehouse);
        tvCommercialFactory = (TextView) view.findViewById(R.id.factory);
        tvCommercialBuilding = (TextView) view.findViewById(R.id.building);
        tvCommercialOther = (TextView) view.findViewById(R.id.other);


        ivHomeHouse = (ImageView) view.findViewById(R.id.housetick);
        ivHomeFlat = (ImageView) view.findViewById(R.id.flattick);
        ivHomeUperportion = (ImageView) view.findViewById(R.id.uperportiontick);
        ivHomeLowerportion = (ImageView) view.findViewById(R.id.lowerportiontick);
        ivHomeFarmhouse = (ImageView) view.findViewById(R.id.farmhousetick);
        ivHomeRoom = (ImageView) view.findViewById(R.id.roomtick);
        ivHomepenthoue = (ImageView) view.findViewById(R.id.penthouetick);

        ivPlotResidentail = (ImageView) view.findViewById(R.id.residentailtick);
        ivPlotCommercial = (ImageView) view.findViewById(R.id.commercialtick);
        ivPlotAgritulturea = (ImageView) view.findViewById(R.id.ariculturaltick);
        ivPlotIndustrial = (ImageView) view.findViewById(R.id.industrialtick);
        ivPlotFile = (ImageView) view.findViewById(R.id.filetick);
        ivPlotForm = (ImageView) view.findViewById(R.id.formtick);

        ivCommercialOffice = (ImageView) view.findViewById(R.id.officetick);
        ivCommercialShop = (ImageView) view.findViewById(R.id.shoptick);
        ivCommercialWerehouse = (ImageView) view.findViewById(R.id.werehousetick);
        ivCommercialFactory = (ImageView) view.findViewById(R.id.factorytick);
        ivCommercialBuilding = (ImageView) view.findViewById(R.id.buildingtick);
        ivCommercialOther = (ImageView) view.findViewById(R.id.othertick);


        boolean isSelected = false;




        Intent intent = getActivity().getIntent();
        Log.e("TAG", "TES TEST " + intent);
        if (intent.getExtras()== null){

        }else {



            String   propertyTitle = intent.getExtras().getString("propertyTitle", null);
            String  propertyPrice = intent.getExtras().getString("propertyPrice", null);
            String propertyDescription = intent.getExtras().getString("propertyDescription", null);
            String propertyLandArea = intent.getExtras().getString("propertyLandArea", null);
            String  propertyCity = intent.getExtras().getString("propertyCity", null);
            String  propertyContact = intent.getExtras().getString("propertyContact", null);
            String propertyType  =intent.getExtras().getString("propertyType", null);
            String propertyStatus = intent.getExtras().getString("propertyStatus", null);
            String propertyRooms = intent.getExtras().getString("propertyRooms", null);
            String propertyFloors = intent.getExtras().getString("propertyFloors", null);
            String propertyBathrooms = intent.getExtras().getString("propertyBathrooms", null);
            String propertyStatus_prpoperty = intent.getExtras().getString("propertyStatus_prpoperty", null);

            Log.e("TAG", "TES TEST " + propertyTitle);
            Log.e("TAG", "Land Area Shoaib" + propertyLandArea);
            Log.e("TAG", "City Shoaib" + propertyCity);
            Log.e("TAG", "Contact Shoaib " + propertyContact);
            Log.e("TAG", "Type Shoaib " + propertyType);
            Log.e("TAG", "Status Shoaib " + propertyStatus);
            Log.e("TAG", "Status double " + propertyStatus_prpoperty);



            ed_protpertyTitle.setText(propertyTitle);
            ed_propertyPrice.setText("");
            ed_description.setText(propertyDescription);
            ed_noOfRooms.setText(propertyRooms);
            ed_ofBathrooms.setText(propertyBathrooms);
            ed_floors.setText(propertyFloors);
            ed_propertyPrice.setText(propertyPrice);

            if (propertyStatus!=null) {


            if (propertyStatus.equals("Sale")){

                sp_propertyFor.setSelection(1);
            }
            if (propertyStatus.equals("Rent")){
                sp_propertyFor.setSelection(2);
            }

                String[] area = propertyLandArea.split(" ");
                String digitArea = area[0];
                String textArea = area[1];
                Log.e("TAG", "digitArea = " + digitArea);
                Log.e("TAG", "TextArea = " + textArea);

                ed_landAread.setText(digitArea);

                if (textArea.equals("Marla")){
                    sp_landArea.setSelection(1);
                }
                if (textArea.equals("Kanal")){
                    sp_landArea.setSelection(2);
                }
                if (textArea.equals("Marabba")){
                    sp_landArea.setSelection(3);
                }
                if (textArea.equals("Acre")){
                    sp_landArea.setSelection(4);
                }
                if (textArea.equals("Sq.Ft")){
                    sp_landArea.setSelection(5);
                }
                if (textArea.equals("Yard")){
                    sp_landArea.setSelection(6);
                }



            }

     /*       if (!propertyLandArea.equals((null))) {

                String[] larea = propertyLandArea.split(" ");
                String areaNumber = larea[0].toString();
                String areaString = larea[1].toString();
                Log.e("TAG", "TTT: " + areaNumber);
                Log.e("TAG", "TTT 13 : " + areaString);

                ed_landAread.setText(areaNumber);
                if (areaString.equals("Marla")) {
                    sp_landArea.setSelection(1);
                }
                if (areaString.equals("Kanal")) {
                    sp_landArea.setSelection(2);
                }
                if (areaString.equals("Marabba")) {
                    sp_landArea.setSelection(3);
                }
                if (areaString.equals("Acre")) {
                    sp_landArea.setSelection(4);
                }
                if (areaString.equals("Sq.Ft")) {
                    sp_landArea.setSelection(5);
                }
                if (areaString.equals("Yard")) {
                    sp_landArea.setSelection(6);
                }
                //spinnerObject.setSelection(INDEX_OF_CATEGORY2)
            }*/



        }


        /*ll_property_flooring = (LinearLayout)  view.findViewById(R.id.property_flooring);
        ll_walls_door = (LinearLayout)  view.findViewById(R.id.walls_doors);
        ll_windwos_electrical = (LinearLayout)  view.findViewById(R.id.ll_window_electrical);

*/
        checkingPropertytype();



        ed_protpertyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_propertyPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_noOfRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_ofBathrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_floors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ed_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*
        ed_structureOfProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_flooringStructer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_wallsStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_doorsStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_windowsStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ed_electricalStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        ll_propertyInerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_property_flooring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_walls_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_windwos_electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/

        sp_propertyFor.getSelectedItem().toString();




        radioButtonHomeClickListener();
        radioButtonPlotsClickListener();
        radioButtonCommercailClickListener();

        seSelectedListener();
        radioGroupCheckChangeListener();
        tvHomeClickListener();
        tvFlatClickListener();
        tvUperPortionClickListener();
        tvLowerPortionClickListener();
        tvRoomClickListener();
        tvFarmHoueClickListener();
        tvPentHouseClickListener();

        tvResedentailPlotClickListener();
        tvCommercialPlotClickListener();
        tvAgriculturalPlotClickListener();
        tvIndustrailPlotClickListener();
        tvFilePlotClickListener();
        tvFormPlotClickListener();

        tvCommercialOfficeClickListener();
        tvCommercialShopClickListener();
        tvCommercialWerehouseClickListener();
        tvCommercialFactoryClickListener();
        tvCommercialBuildClickListener();
        tvCommercialOtherClickListener();

        Log.e("TAG", "RadioPostion: " + radioPosition);

        return view;
    }


    public void checkingPropertytype(){



        sp_propertyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_propertyType.getSelectedItem().equals("Land") || sp_propertyType.getSelectedItem().equals("Plot")){

                    ll_propertyInerNumber.setVisibility(View.GONE);

                  /*  ll_property_flooring.setVisibility(View.GONE);
                    ll_walls_door.setVisibility(View.GONE);
                    ll_windwos_electrical.setVisibility(View.GONE);*/

                }

                if (sp_propertyType.getSelectedItem().equals("House")
                        || sp_propertyType.getSelectedItem().equals("Flat")
                        || sp_propertyType.getSelectedItem().equals("Commercial")
                        || sp_propertyType.getSelectedItem().equals("Shop")){

                    ll_propertyInerNumber.setVisibility(View.VISIBLE);


                    /*ll_property_flooring.setVisibility(View.VISIBLE);
                    ll_walls_door.setVisibility(View.VISIBLE);
                    ll_windwos_electrical.setVisibility(View.VISIBLE);*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


    }


    public void radioButtonHomeClickListener(){


        radioButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_propertyInerNumber.setVisibility(View.VISIBLE);

                radioPosition = 0;
                Log.e("TAG", "RadioPostion: " + radioPosition);
                radioValue = "Homes";

                relativlyoutForHouse.setVisibility(View.VISIBLE);
                relativeLayoutForPlots.setVisibility(View.GONE);
                relativeLayoutForCommercial.setVisibility(View.GONE);

                tvHomeHouse.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivHomeHouse.setVisibility(View.VISIBLE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.GONE);
            }
        });


    }

    public void radioButtonPlotsClickListener(){


        radioButtonPlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_propertyInerNumber.setVisibility(View.GONE);

                radioPosition = 1;
                Log.e("TAG", "RadioPostion: " + radioPosition);
                radioValue = "Plots";

                relativlyoutForHouse.setVisibility(View.GONE);
                relativeLayoutForPlots.setVisibility(View.VISIBLE);
                relativeLayoutForCommercial.setVisibility(View.GONE);

                tvPlotResidential.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvPlotCommercial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotAgricultural.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotIndustrial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotForm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));



                ivPlotResidentail.setVisibility(View.VISIBLE);
                ivPlotCommercial.setVisibility(View.GONE);
                ivPlotAgritulturea.setVisibility(View.GONE);
                ivPlotIndustrial.setVisibility(View.GONE);
                ivPlotFile.setVisibility(View.GONE);
                ivPlotForm.setVisibility(View.GONE);
            }
        });


    }

    public void radioButtonCommercailClickListener(){


        radioButtonCommercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_propertyInerNumber.setVisibility(View.VISIBLE);

                radioPosition = 2;
                Log.e("TAG", "RadioPostion: " + radioPosition);
                radioValue = "Commercial";

                relativlyoutForHouse.setVisibility(View.GONE);
                relativeLayoutForPlots.setVisibility(View.GONE);
                relativeLayoutForCommercial.setVisibility(View.VISIBLE);

                tvCommercialOffice.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvCommercialShop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialWarehouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialBuilding.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));



                ivCommercialOffice.setVisibility(View.VISIBLE);
                ivCommercialShop.setVisibility(View.GONE);
                ivCommercialWerehouse.setVisibility(View.GONE);
                ivCommercialFactory.setVisibility(View.GONE);
                ivCommercialBuilding.setVisibility(View.GONE);
                ivCommercialOther.setVisibility(View.GONE);
            }
        });


    }

    public void radioGroupCheckChangeListener(){

        radioGroupPeropertyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                seSelectedListener();

            }
        });
    }

    public void seSelectedListener() {

        int selectedRadio = radioGroupPeropertyType.getCheckedRadioButtonId();
        View radioButton = radioGroupPeropertyType.findViewById(selectedRadio);
        int indx = radioGroupPeropertyType.indexOfChild(radioButton);
        Log.e("TAG", "selected Radio ID: " + indx);
    }

    public void tvHomeClickListener(){

        tvHomeHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivHomeHouse.setVisibility(View.VISIBLE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.GONE);
            }
        });


    }

    public void tvFlatClickListener(){

        tvHomeFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFlat.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));


                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));


                ivHomeHouse.setVisibility(View.GONE);
                ivHomeFlat.setVisibility(View.VISIBLE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.GONE);
            }
        });


    }

    public void tvUperPortionClickListener(){

        tvHomeUperportion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));


                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));


                ivHomeHouse.setVisibility(View.GONE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.VISIBLE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.GONE);
            }
        });

    }

    public void tvLowerPortionClickListener(){

        tvHomeLowerportion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));


                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));


                ivHomeHouse.setVisibility(View.GONE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.VISIBLE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.GONE);
            }
        });

    }

    public void tvFarmHoueClickListener(){

        tvHomeFarmhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivHomeHouse.setVisibility(View.GONE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.VISIBLE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.GONE);
            }
        });

    }

    public void tvRoomClickListener(){

        tvHomeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvHomepenthoue.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivHomeHouse.setVisibility(View.GONE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.VISIBLE);
                ivHomepenthoue.setVisibility(View.GONE);

            }
        });
    }


    public void tvPentHouseClickListener(){

        tvHomepenthoue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvHomeHouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFlat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeUperportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeLowerportion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeFarmhouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomeRoom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvHomepenthoue.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));


                tvHomeHouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFlat.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeUperportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeLowerportion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeFarmhouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomeRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvHomepenthoue.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));

                ivHomeHouse.setVisibility(View.GONE);
                ivHomeFlat.setVisibility(View.GONE);
                ivHomeUperportion.setVisibility(View.GONE);
                ivHomeLowerportion.setVisibility(View.GONE);
                ivHomeFarmhouse.setVisibility(View.GONE);
                ivHomeRoom.setVisibility(View.GONE);
                ivHomepenthoue.setVisibility(View.VISIBLE);
            }
        });
    }

    public void tvResedentailPlotClickListener(){

        tvPlotResidential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPlotResidential.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvPlotCommercial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotAgricultural.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotIndustrial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotForm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));



                ivPlotResidentail.setVisibility(View.VISIBLE);
                ivPlotCommercial.setVisibility(View.GONE);
                ivPlotAgritulturea.setVisibility(View.GONE);
                ivPlotIndustrial.setVisibility(View.GONE);
                ivPlotFile.setVisibility(View.GONE);
                ivPlotForm.setVisibility(View.GONE);
            }
        });
    }

    public void tvCommercialPlotClickListener(){

        tvPlotCommercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPlotResidential.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotCommercial.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvPlotAgricultural.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotIndustrial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotForm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivPlotResidentail.setVisibility(View.GONE);
                ivPlotCommercial.setVisibility(View.VISIBLE);
                ivPlotAgritulturea.setVisibility(View.GONE);
                ivPlotIndustrial.setVisibility(View.GONE);
                ivPlotFile.setVisibility(View.GONE);
                ivPlotForm.setVisibility(View.GONE);
            }
        });
    }

    public void tvAgriculturalPlotClickListener(){

        tvPlotAgricultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPlotResidential.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotCommercial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotAgricultural.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvPlotIndustrial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotForm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivPlotResidentail.setVisibility(View.GONE);
                ivPlotCommercial.setVisibility(View.GONE);
                ivPlotAgritulturea.setVisibility(View.VISIBLE);
                ivPlotIndustrial.setVisibility(View.GONE);
                ivPlotFile.setVisibility(View.GONE);
                ivPlotForm.setVisibility(View.GONE);
            }
        });
    }

    public void tvIndustrailPlotClickListener(){

        tvPlotIndustrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPlotResidential.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotCommercial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotAgricultural.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotIndustrial.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvPlotfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotForm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));


                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivPlotResidentail.setVisibility(View.GONE);
                ivPlotCommercial.setVisibility(View.GONE);
                ivPlotAgritulturea.setVisibility(View.GONE);
                ivPlotIndustrial.setVisibility(View.VISIBLE);
                ivPlotFile.setVisibility(View.GONE);
                ivPlotForm.setVisibility(View.GONE);
            }
        });
    }

    public void tvFilePlotClickListener(){

        tvPlotfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPlotResidential.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotCommercial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotAgricultural.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotIndustrial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotfile.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvPlotForm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivPlotResidentail.setVisibility(View.GONE);
                ivPlotCommercial.setVisibility(View.GONE);
                ivPlotAgritulturea.setVisibility(View.GONE);
                ivPlotIndustrial.setVisibility(View.GONE);
                ivPlotFile.setVisibility(View.VISIBLE);
                ivPlotForm.setVisibility(View.GONE);
            }
        });
    }


    public void tvFormPlotClickListener(){

        tvPlotForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPlotResidential.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotCommercial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotAgricultural.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotIndustrial.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotfile.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvPlotForm.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));

                tvPlotResidential.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotCommercial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotAgricultural.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotIndustrial.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotfile.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvPlotForm.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));

                ivPlotResidentail.setVisibility(View.GONE);
                ivPlotCommercial.setVisibility(View.GONE);
                ivPlotAgritulturea.setVisibility(View.GONE);
                ivPlotIndustrial.setVisibility(View.GONE);
                ivPlotFile.setVisibility(View.GONE);
                ivPlotForm.setVisibility(View.VISIBLE);
            }
        });
    }

    public void tvCommercialOfficeClickListener(){

        tvCommercialOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCommercialOffice.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvCommercialShop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialWarehouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialBuilding.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));


                ivCommercialOffice.setVisibility(View.VISIBLE);
                ivCommercialShop.setVisibility(View.GONE);
                ivCommercialWerehouse.setVisibility(View.GONE);
                ivCommercialFactory.setVisibility(View.GONE);
                ivCommercialBuilding.setVisibility(View.GONE);
                ivCommercialOther.setVisibility(View.GONE);
            }
        });
    }


    public void tvCommercialShopClickListener(){

        tvCommercialShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCommercialOffice.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialShop.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvCommercialWarehouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialBuilding.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivCommercialOffice.setVisibility(View.GONE);
                ivCommercialShop.setVisibility(View.VISIBLE);
                ivCommercialWerehouse.setVisibility(View.GONE);
                ivCommercialFactory.setVisibility(View.GONE);
                ivCommercialBuilding.setVisibility(View.GONE);
                ivCommercialOther.setVisibility(View.GONE);
            }
        });
    }

    public void tvCommercialWerehouseClickListener(){

        tvCommercialWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCommercialOffice.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialShop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialWarehouse.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvCommercialFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialBuilding.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivCommercialOffice.setVisibility(View.GONE);
                ivCommercialShop.setVisibility(View.GONE);
                ivCommercialWerehouse.setVisibility(View.VISIBLE);
                ivCommercialFactory.setVisibility(View.GONE);
                ivCommercialBuilding.setVisibility(View.GONE);
                ivCommercialOther.setVisibility(View.GONE);
            }
        });
    }

    public void tvCommercialFactoryClickListener(){

        tvCommercialFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCommercialOffice.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialShop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialWarehouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialFactory.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvCommercialBuilding.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));

                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivCommercialOffice.setVisibility(View.GONE);
                ivCommercialShop.setVisibility(View.GONE);
                ivCommercialWerehouse.setVisibility(View.GONE);
                ivCommercialFactory.setVisibility(View.VISIBLE);
                ivCommercialBuilding.setVisibility(View.GONE);
                ivCommercialOther.setVisibility(View.GONE);
            }
        });
    }

    public void tvCommercialBuildClickListener(){

        tvCommercialBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCommercialOffice.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialShop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialWarehouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialBuilding.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                tvCommercialOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));


                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                ivCommercialOffice.setVisibility(View.GONE);
                ivCommercialShop.setVisibility(View.GONE);
                ivCommercialWerehouse.setVisibility(View.GONE);
                ivCommercialFactory.setVisibility(View.GONE);
                ivCommercialBuilding.setVisibility(View.VISIBLE);
                ivCommercialOther.setVisibility(View.GONE);
            }
        });
    }

    public void tvCommercialOtherClickListener(){

        tvCommercialOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvCommercialOffice.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialShop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialWarehouse.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialBuilding.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner));
                tvCommercialOther.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));


                tvCommercialOffice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialShop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialWarehouse.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialFactory.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialBuilding.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tvCommercialOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));

                ivCommercialOffice.setVisibility(View.GONE);
                ivCommercialShop.setVisibility(View.GONE);
                ivCommercialWerehouse.setVisibility(View.GONE);
                ivCommercialFactory.setVisibility(View.GONE);
                ivCommercialBuilding.setVisibility(View.GONE);
                ivCommercialOther.setVisibility(View.VISIBLE);
            }
        });
    }

    public static String getSelectedValues(){

        String selectedString = "no";

        if (radioPosition==-1){

            selectedString = "no";
        }

        if (radioPosition==0){

            if (ivHomeHouse.getVisibility()==View.VISIBLE){

                selectedString = "House";
            }
            else if (ivHomeFlat.getVisibility()==View.VISIBLE){
                selectedString = "Flat";
            }
            else if (ivHomeUperportion.getVisibility()==View.VISIBLE){
                selectedString = "Upper Portion";
            }
            else if (ivHomeLowerportion.getVisibility()==View.VISIBLE){
                selectedString = "LowerPortion";
            }
            else if (ivHomeFarmhouse.getVisibility()==View.VISIBLE){
                selectedString = "Farm House";
            }
            else if (ivHomeRoom.getVisibility()==View.VISIBLE){
                selectedString = "Room";
            }
            else if (ivHomepenthoue.getVisibility()==View.VISIBLE){
                selectedString = "Penthoue";
            }
        }
        if (radioPosition==1){

            if (ivPlotResidentail.getVisibility()==View.VISIBLE){

                selectedString = "Residentail Plot";
            }
            else if (ivPlotCommercial.getVisibility()==View.VISIBLE){
                selectedString = "Commercial Plot";
            }
            else if (ivPlotAgritulturea.getVisibility()==View.VISIBLE){
                selectedString = "Agricultural Land";
            }
            else if (ivPlotIndustrial.getVisibility()==View.VISIBLE){
                selectedString = "Industrial Land";
            }
            else if (ivPlotFile.getVisibility()==View.VISIBLE){
                selectedString = "Plot File";
            }
            else if (ivPlotForm.getVisibility()==View.VISIBLE){
                selectedString = "Plot Form";
            }

        }

        if (radioPosition==2){

            if (ivCommercialOffice.getVisibility()==View.VISIBLE){

                selectedString = "Office";
            }
            else if (ivCommercialShop.getVisibility()==View.VISIBLE){
                selectedString = "Shop";
            }
            else if (ivCommercialWerehouse.getVisibility()==View.VISIBLE){
                selectedString = "Werehouse";
            }
            else if (ivCommercialFactory.getVisibility()==View.VISIBLE){
                selectedString = "Factory";
            }
            else if (ivCommercialBuilding.getVisibility()==View.VISIBLE){
                selectedString = "Building";
            }
            else if (ivCommercialOther.getVisibility()==View.VISIBLE){
                selectedString = "Other";
            }

        }


        return selectedString;

    }

}
