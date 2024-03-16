package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.twoactivites.extra.REPLY";
    public static final String EXTRA_REPLY_AMOUNT = "com.example.twoactivites.extra.REPLY_AMOUNT";
    public static final String EXTRA_REPLY_MODE = "com.example.twoactivites.extra.REPLY_MODE";
    EditText customerName, phoneNumber, customerAddress;
    TextView netTotalTextView,pstTextView,gstTextView,totalTextView;
    double provincialTax, governmentTax, totalToPay;
    int radioBtnCheck = 1;
    boolean nameChecked, phoneChecked, addressChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        Intent intent = getIntent();
        final double sPriceToPay = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGE,0);

        provincialTax = (sPriceToPay*(0.07));
        governmentTax = (sPriceToPay*(0.05));
        totalToPay = provincialTax + governmentTax +sPriceToPay;

        //Creating Objects for the Input Edit Texts.
        customerName = findViewById(R.id.nameInput);
        //Creating an object for the Input of Phone number.
        //phoneNumber = findViewById(R.id.phoneNumberInput);
        phoneNumber = findViewById(R.id.telephoneInput);
        //Creating an object for the Input of the Address
        customerAddress = findViewById(R.id.addressInput);

        netTotalTextView = findViewById(R.id.netTotal);
        pstTextView = findViewById(R.id.psTAX);
        gstTextView = findViewById(R.id.gsTAX);
        totalTextView = findViewById(R.id.totalOutput);

        netTotalTextView.setText("Item Price: $" + String.format("%.2f", sPriceToPay));
        pstTextView.setText("PST (7%):   $"+String.format("%.2f", provincialTax));
        gstTextView.setText("GST (5%):   $"+String.format("%.2f", governmentTax));
        totalTextView.setText("Total: $"+String.format("%.2f", totalToPay));

        //Checking to see if there has been data saved and then Restoring it
        if (savedInstanceState != null){
            radioBtnCheck = savedInstanceState.getInt("radioStatePos");
            totalToPay = savedInstanceState.getDouble("totalPriceToPay");
            totalTextView.setText(savedInstanceState.getString("totalPrice"));
        }
    }

    public void onRadioButtonClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.pickUpBtn:
                if (checked){
                    radioBtnCheck = 1;
                    totalTextView.setText("Total: $"+String.format("%.2f", totalToPay));
                }
                break;
            case R.id.deliveryBtn:
                if (checked){
                    radioBtnCheck = 2;
                    totalTextView.setText("Total: $"+String.format("%.2f", totalToPay+2));
                }
                break;
        }

    }

    public void onOrderClick(View view) {

        String nameOfCustomer = customerName.getText().toString();

        nameChecked = true;
        phoneChecked = true;
        addressChecked = true;

        //Checks if the name field is empty
        if (TextUtils.isEmpty(customerName.getText().toString())){
            nameChecked = false;

        }
        else if (TextUtils.isEmpty(phoneNumber.getText().toString())){
            phoneChecked = false;

        }
        else if (TextUtils.isEmpty(customerAddress.getText().toString())){
            addressChecked = false;
            Toast.makeText(SecondActivity.this,"Address empty!",Toast.LENGTH_SHORT).show();
        }

        if ((phoneChecked == true) && (nameChecked == true) && (addressChecked == true)){
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY,nameOfCustomer);
            replyIntent.putExtra(EXTRA_REPLY_AMOUNT,totalToPay);
            replyIntent.putExtra(EXTRA_REPLY_MODE,radioBtnCheck);
            setResult(RESULT_OK,replyIntent);
            finish();
        }
        else {
            Toast.makeText(SecondActivity.this,"Personal Details Cannot be Empty!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("totalPrice",totalTextView.getText().toString());
        outState.putInt("radioStatePos",radioBtnCheck);
        outState.putDouble("totalPriceToPay",totalToPay);

    }

}