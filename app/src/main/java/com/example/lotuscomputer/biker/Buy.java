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

    EditText search;
    Button searchBtn,backBtn;
    Spinner spinner,spinner2,spinner3,spinner4;
    public static RadioGroup radioGroup;
    public static RadioButton radioButton;
    View rootView;
    ViewFlipper vf;
    List<RowItem> rowItems;
    ListView listView;
    String textFromSpinner_location,textFromSpinner3;
    public static String  radio_string;
    String[] parts;
    ImageView flip_imageView;
    TextView flip3Title,filp3Location,flip3Price,flip3Posted_by,flip3Posted_on,flip3Description,flip3Address,flip3Phone,flip3Email;
    Cursor cursor;
    Button back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.buy,container,false);
        vf = (ViewFlipper)rootView.findViewById(R.id.flipper1);
        search = (EditText)rootView.findViewById(R.id.searchText);
        searchBtn = (Button)rootView.findViewById(R.id.search);
        backBtn = (Button)rootView.findViewById(R.id.backBtn);

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
                String spinner2Selection = parent.getItemAtPosition(position).toString();
                parts = spinner2Selection.split("-");
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
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v == searchBtn){
            rowItems = new ArrayList<>();
            listView = (ListView)rootView.findViewById(R.id.listView);
            CustomAdapter customAdapter = new CustomAdapter(getContext(),rowItems);
            listView.setAdapter(customAdapter);

            String query = "SELECT * FROM " + Sell.db.TABLE_NAME + " WHERE PRICE >= "  +
                    parts[0] +" AND PRICE <= " + parts[1] + " AND LOCATION LIKE \""  +textFromSpinner_location  +
                    "\" AND CATEGORY LIKE \"" + radio_string + "\" AND TITLE LIKE \"" + search.getText().toString() + "\"";
            if(textFromSpinner3.equals("Date"))cursor = Sell.db.getAsRequested(query + "ORDER BY DATETIME(POSTED_ON) DESC");

            if(cursor.getCount() == 0){
                showMessage("Error","Nothing found");
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RowItem item = (RowItem) listView.getItemAtPosition(position);
                    int bike_id = item.getId();
                    Cursor cursor1 = Sell.db.getAsRequested("SELECT * FROM " + Sell.db.TABLE_NAME + " WHERE ID = " + String.valueOf(bike_id) );
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