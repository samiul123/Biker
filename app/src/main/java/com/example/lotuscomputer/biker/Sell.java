package com.example.lotuscomputer.biker;

/**
 * Created by Lotus Computer on 30-May-17.
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
import static com.example.lotuscomputer.biker.Buy.customAdapterFirst;
import static com.example.lotuscomputer.biker.Buy.db;
import static com.example.lotuscomputer.biker.Buy.listViewFirst;
import static com.example.lotuscomputer.biker.Buy.rowItemsFirst;

public class Sell extends Fragment {

    TextView name,title,description,price,location,contact_info,address,phone,email;
    Button post_ad,cancel,choose;
    EditText editName,editTitle,editDescription,editPrice,editAddress,editPhone,editEmail;
    Spinner spinner,spinner_item;
    ImageView imageView;
    String locationFromSpinner,categoryFromSpinner;
    DateFormat datef = new SimpleDateFormat("EEE, MMM d, ''yy");
    String date = datef.format(Calendar.getInstance().getTime());
    private static final int SELECTED_PICTURE = 1;
    public static Cursor cursorSell;
    String categorySPinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sell, container, false);

        //initializing database


        name = (TextView)rootView.findViewById(R.id.name_id);
        title = (TextView)rootView.findViewById(R.id.title_id);
        description = (TextView)rootView.findViewById(R.id.description_id);
        price = (TextView)rootView.findViewById(R.id.price_id);
        location = (TextView)rootView.findViewById(R.id.location_id);
        contact_info = (TextView)rootView.findViewById(R.id.contact_id);
        address = (TextView)rootView.findViewById(R.id.address_id);
        phone = (TextView)rootView.findViewById(R.id.phone_id);
        email = (TextView)rootView.findViewById(R.id.email_id);


        editName = (EditText)rootView.findViewById(R.id.nameText);
        editTitle = (EditText)rootView.findViewById(R.id.titleText);
        editDescription = (EditText)rootView.findViewById(R.id.description_text);
        editPrice = (EditText)rootView.findViewById(R.id.price_text);
        editAddress = (EditText)rootView.findViewById(R.id.address_text);
        editPhone = (EditText)rootView.findViewById(R.id.phone_text);
        editEmail = (EditText)rootView.findViewById(R.id.email_text);

        //creating array adapter for spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.location_name,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner)rootView.findViewById(R.id.spinner4);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationFromSpinner = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),locationFromSpinner,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(getContext(),R.array.category,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item = (Spinner)rootView.findViewById(R.id.spinner_item);
        spinner_item.setAdapter(arrayAdapter1);
        spinner_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryFromSpinner = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView = (ImageView)rootView.findViewById(R.id.imageView2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("111111111111111111111111111111111111111111111111");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                System.out.println("111111111111111111111111111111111111111111111111");
                startActivityForResult(intent, SELECTED_PICTURE);
                System.out.println("111111111111111111111111111111111111111111111111");
            }
        });
        post_ad = (Button) rootView.findViewById(R.id.postAdd_id);
        post_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.getText().toString().equals("") && editTitle.getText().toString().equals("") && editDescription.getText().toString().equals("") && editPhone.getText().toString().equals("") && editAddress.getText().toString().equals("") && editEmail.getText().toString().equals("") && editPrice.getText().toString().equals("") && categoryFromSpinner.equals("none") && locationFromSpinner.equals("none")){
                    showMessage("Error","Empty fields");
                }
                else{
                    boolean is_inserted = db.insertData(editTitle.getText().toString(),locationFromSpinner,Integer.valueOf(editPrice.getText().toString()),editName.getText().toString(),date,editDescription.getText().toString(),editAddress.getText().toString(),editPhone.getText().toString(),editEmail.getText().toString(),imageViewToByte(imageView),categoryFromSpinner);
                    if(is_inserted)showMessage("Success","Your ad has been posted");
                    else showMessage("Failure","Your ad has not been posted");
                    editName.setText(null);
                    editTitle.setText(null);
                    editPrice.setText(null);
                    editEmail.setText(null);
                    editPhone.setText(null);
                    editAddress.setText(null);
                    editDescription.setText(null);
                    cursorSell = db.getAsRequested("SELECT * FROM " + db.TABLE_NAME + " ORDER BY TITLE ASC");
                    rowItemsFirst.clear();
                    while(cursorSell.moveToNext()){
                        String title = cursorSell.getString(0);
                        String price = cursorSell.getString(2);
                        byte[] image = cursorSell.getBlob(9);
                        int id = cursorSell.getInt(11);
                        rowItemsFirst.add(new RowItem(title,price,image,id));
                    }
                    customAdapterFirst = new CustomAdapter(getContext(),rowItemsFirst);
                    listViewFirst.setAdapter(customAdapterFirst);

                }


            }
        });
        cancel = (Button) rootView.findViewById(R.id.cancel_id);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Failure","You canceled your ads");
            }
        });
        return rootView;
    }
    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("111111111111111111111111111111111111111111111111");
        if(requestCode == SELECTED_PICTURE && resultCode == RESULT_OK && data != null){
            System.out.println("111111111111111111111111111111111111111111111111");
            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(uri,projection,null,null,null);
            cursor.moveToFirst();

            int column_index = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(column_index);
            cursor.close();

            Bitmap selectedImage  = BitmapFactory.decodeFile(filePath);

            //Drawable d = new BitmapDrawable(selectedImage);
            imageView.setImageBitmap(selectedImage);
        }

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}