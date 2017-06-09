package com.example.lotuscomputer.biker;

/**
 * Created by Lotus Computer on 30-May-17.
 */

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class Buy extends Fragment implements AdapterView.OnClickListener{

    public static DatabaseHelper db;
    EditText search,searchText;
    Button searchBtn,backBtn,searchFirstBtn,moreOptionsBtn,backFlip;
    Spinner spinner,spinner2,spinner3,spinner4;
    public static RadioGroup radioGroup;
    public static RadioButton radioButton;
    View rootView;
    ViewFlipper vf;
    List<RowItem> rowItems;
    public static List<RowItem> rowItemsFirst;
    ListView listView;
    public static ListView listViewFirst;
    String textFromSpinner_location,textFromSpinner3,spinner2Selection;
    public static String  radio_string;
    String[] parts;
    ImageView flip_imageView,flip_imageViewFirst;
    TextView flip3Title,filp3Location,flip3Price,flip3Posted_by,flip3Posted_on,flip3Description,flip3Address,flip3Phone,flip3Email;
    TextView flip3TitleFirst,filp3LocationFirst,flip3PriceFirst,flip3Posted_byFirst,flip3Posted_onFirst,flip3DescriptionFirst,flip3AddressFirst,flip3PhoneFirst,flip3EmailFirst;
    Cursor cursor;
    Button back,backFirst;
    public static CustomAdapter customAdapterFirst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.buy,container,false);
        vf = (ViewFlipper)rootView.findViewById(R.id.flipper1);
        search = (EditText)rootView.findViewById(R.id.searchText);
        searchBtn = (Button)rootView.findViewById(R.id.search);
        backBtn = (Button)rootView.findViewById(R.id.backBtn);
        backFlip = (Button)rootView.findViewById(R.id.backflip);
        backFlip.setOnClickListener(this);
        db = new DatabaseHelper(getContext());
        firstFlip();

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.location_name,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner)rootView.findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textFromSpinner_location = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.price_range,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 = (Spinner)rootView.findViewById(R.id.spinner2);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner2Selection = parent.getItemAtPosition(position).toString();
                if(!spinner2Selection.equals("none"))parts = spinner2Selection.split("-");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3 = (Spinner)rootView.findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textFromSpinner3 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radioGroup = (RadioGroup)rootView.findViewById(R.id.radio_group);
        String[] radio_arrays = getResources().getStringArray(R.array.category);
        for(int i = 0; i < radio_arrays.length; i++){
            String item = radio_arrays[i];
            final RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(item);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radio_string = radioButton.getText().toString();
                    Toast.makeText(getContext(),radio_string,Toast.LENGTH_SHORT).show();
                }
            });
            radioGroup.addView(radioButton);
        }


        searchBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        flip3Title = (TextView)rootView.findViewById(R.id.filp_3_title);
        filp3Location = (TextView)rootView.findViewById(R.id.filp_3_location);
        flip3Price = (TextView)rootView.findViewById(R.id.filp_3_price);
        flip3Posted_by = (TextView)rootView.findViewById(R.id.filp_3_posted_by);
        flip3Posted_on = (TextView)rootView.findViewById(R.id.filp_3_posted_on);
        flip3Description = (TextView)rootView.findViewById(R.id.filp_3_description);
        flip_imageView = (ImageView)rootView.findViewById(R.id.flip_imageView);
        flip3Address = (TextView)rootView.findViewById(R.id.filp_3_address);
        flip3Phone = (TextView)rootView.findViewById(R.id.filp_3_phone);
        flip3Email = (TextView)rootView.findViewById(R.id.filp_3_email);
        back = (Button)rootView.findViewById(R.id.back);
        back.setOnClickListener(this);

        flip3TitleFirst = (TextView)rootView.findViewById(R.id.filp_3_titleFirst);
        filp3LocationFirst = (TextView)rootView.findViewById(R.id.filp_3_locationFirst);
        flip3PriceFirst = (TextView)rootView.findViewById(R.id.filp_3_priceFirst);
        flip3Posted_byFirst = (TextView)rootView.findViewById(R.id.filp_3_posted_byFirst);
        flip3Posted_onFirst = (TextView)rootView.findViewById(R.id.filp_3_posted_onFirst);
        flip3DescriptionFirst = (TextView)rootView.findViewById(R.id.filp_3_descriptionFirst);
        flip_imageViewFirst = (ImageView)rootView.findViewById(R.id.flip_imageViewFirst);
        flip3AddressFirst = (TextView)rootView.findViewById(R.id.filp_3_addressFirst);
        flip3PhoneFirst = (TextView)rootView.findViewById(R.id.filp_3_phoneFirst);
        flip3EmailFirst = (TextView)rootView.findViewById(R.id.filp_3_emailFirst);
        backFirst = (Button)rootView.findViewById(R.id.backFirst);
        backFirst.setOnClickListener(this);

        return rootView;
    }

    public void firstFlip(){
        searchText = (EditText)rootView.findViewById(R.id.searchTextFirst);
        searchFirstBtn = (Button)rootView.findViewById(R.id.searchFirst);
        searchFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.getText().toString().equals("")){
                    showMessage("Error","Search field empty");
                }
                else {
                    rowItemsFirst.clear();
                    CustomAdapter customAdapter = new CustomAdapter(getContext(),rowItemsFirst);
                    listViewFirst.setAdapter(customAdapter);
                    String query = "SELECT * FROM " + db.TABLE_NAME + " WHERE TITLE LIKE \"" + searchText.getText().toString() +
                            "\" ORDER BY DATE(POSTED_ON) DESC";
                    Cursor cursor1 = db.getAsRequested(query);
                    if(cursor1.getCount() == 0){
                        showMessage("Error","Nothing found");
                    }
                    while (cursor1.moveToNext()){
                        String title = cursor1.getString(0);
                        String price = cursor1.getString(2);
                        byte[] image = cursor1.getBlob(9);
                        int id = cursor1.getInt(11);
                        rowItemsFirst.add(new RowItem(title,price,image,id));
                    }
                }
            }
        });
        moreOptionsBtn = (Button)rootView.findViewById(R.id.moreOptions);
        moreOptionsBtn.setOnClickListener(this);
        listViewFirst = (ListView)rootView.findViewById(R.id.listViewFirst);
        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem item = (RowItem) listViewFirst.getItemAtPosition(position);
                int bike_id = item.getId();
                Cursor cursor1 = db.getAsRequested("SELECT * FROM " + db.TABLE_NAME + " WHERE ID = " + String.valueOf(bike_id) );
                while(cursor1.moveToNext()){
                    flip3TitleFirst.setText("Title: " + cursor1.getString(0));
                    filp3LocationFirst.setText("Location: " + cursor1.getString(1));
                    flip3PriceFirst.setText("Price: " + cursor1.getString(2));
                    flip3Posted_byFirst.setText("Posted_by: " + cursor1.getString(3));
                    flip3Posted_onFirst.setText("Posted_on: " + cursor1.getString(4));
                    flip3DescriptionFirst.setText("Description: " + cursor1.getString(5));
                    flip3AddressFirst.setText("Address: " + cursor1.getString(6));
                    flip3PhoneFirst.setText("Phone: " + cursor1.getString(7));
                    flip3EmailFirst.setText("Email: " + cursor1.getString(8));
                    byte[] image = cursor1.getBlob(9);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    flip_imageViewFirst.setImageBitmap(bitmap);
                }
                vf.showNext();
            }
        });
        rowItemsFirst = new ArrayList<>();
        customAdapterFirst = new CustomAdapter(getContext(),rowItemsFirst);
        listViewFirst.setAdapter(customAdapterFirst);

        String query = "SELECT * FROM " + db.TABLE_NAME + " ORDER BY TITLE ASC";
        Cursor cursor2 = db.getAsRequested(query);
        if(cursor2.getCount() == 0){
            showMessage("Error","Nothing found");
        }
        rowItemsFirst.clear();
        while (cursor2.moveToNext()){
            String title = cursor2.getString(0);
            String price = cursor2.getString(2);
            byte[] image = cursor2.getBlob(9);
            int id = cursor2.getInt(11);
            rowItemsFirst.add(new RowItem(title,price,image,id));
        }

    }

    @Override
    public void onClick(View v) {
        if(v == moreOptionsBtn){
            vf.showNext();
            vf.showNext();
        }
        if(v == backFlip){
            vf.showPrevious();
            vf.showPrevious();
        }
        if(v == backFirst){
            vf.showPrevious();
        }
        if(v == searchBtn){
            if(search.getText().toString().equals("") || textFromSpinner_location.equals("none") || radio_string.equals("none") || spinner2Selection.equals("none")){
                showMessage("Error","Empty field");
            }
            else{
                rowItems = new ArrayList<>();
                listView = (ListView)rootView.findViewById(R.id.listView);
                CustomAdapter customAdapter = new CustomAdapter(getContext(),rowItems);
                listView.setAdapter(customAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RowItem item = (RowItem) listView.getItemAtPosition(position);
                        int bike_id = item.getId();
                        Cursor cursor1 = db.getAsRequested("SELECT * FROM " + db.TABLE_NAME + " WHERE ID = " + String.valueOf(bike_id) );
                        while(cursor1.moveToNext()) {
                            flip3Title.setText("Title: " + cursor1.getString(0));
                            filp3Location.setText("Location: " + cursor1.getString(1));
                            flip3Price.setText("Price: " + cursor1.getString(2));
                            flip3Posted_by.setText("Posted_by: " + cursor1.getString(3));
                            flip3Posted_on.setText("Posted_on: " + cursor1.getString(4));
                            flip3Description.setText("Description: " + cursor1.getString(5));
                            flip3Address.setText("Address: " + cursor1.getString(6));
                            flip3Phone.setText("Phone: " + cursor1.getString(7));
                            flip3Email.setText("Email: " + cursor1.getString(8));
                            byte[] image = cursor1.getBlob(9);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            flip_imageView.setImageBitmap(bitmap);
                        }
                        vf.showNext();

                    }
                });

                String query = "SELECT * FROM " + "bikers_info_6" + " WHERE PRICE >= "  +
                        parts[0] +" AND PRICE <= " + parts[1] + " AND LOCATION LIKE \""  +textFromSpinner_location  +
                        "\" AND CATEGORY LIKE \"" + radio_string + "\" AND TITLE LIKE \"" + search.getText().toString() + "\"";
                if(textFromSpinner3.equals("Date"))cursor = db.getAsRequested(query + " ORDER BY DATE(POSTED_ON) DESC");
                if(textFromSpinner3.equals("Name"))cursor = db.getAsRequested(query + " ORDER BY TITLE ASC");
                if(textFromSpinner3.equals("Price"))cursor = db.getAsRequested(query + " ORDER BY PRICE ASC");
                if(cursor.getCount() == 0){
                    showMessage("Error","Nothing found");
                }
                rowItems.clear();
                while(cursor.moveToNext()){
                    String title = cursor.getString(0);
                    String price = cursor.getString(2);
                    byte[] image = cursor.getBlob(9);
                    int id = cursor.getInt(11);
                    rowItems.add(new RowItem(title,price,image,id));
                }
                customAdapter.notifyDataSetChanged();
                vf.showNext();
            }


        }
        if(v == backBtn){
            vf.showPrevious();
        }
        if(v == back){
            vf.showPrevious();
        }

    }
    /*public void viewData()
    {
        Cursor res = db.getAsrwquested("Select * from " + DatabaseHelper.TABLE_NAME);
        if (res.getCount() == 0)
        {
            showMessage("Error","Nothing found");
        }
        else
        {

            while(res.moveToNext())
            {
                buffer.append("Title: "+ res.getString(0) + "\n");
                buffer.append("Location: " + res.getString(1) + "\n");
                buffer.append("Price: " + res.getString(2) + "\n");
                buffer.append("Posted_by: " + res.getString(3) + "\n");
                buffer.append("Posted_on: " + res.getString(4) + "\n");
                buffer.append("Description: " + res.getString(5) + "\n");
                buffer.append("Address: " + res.getString(6) + "\n");
                buffer.append("Phone: " + res.getString(7) + "\n");
                buffer.append("Email: " + res.getString(8) + "\n");
            }
            showMessage("Data",buffer.toString());
        }
    }*/

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}