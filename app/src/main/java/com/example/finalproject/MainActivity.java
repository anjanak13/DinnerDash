package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static double priceOfItem;
    public static final String EXTRA_MESSAGE = "com.example.finalproject.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;
    TextView outputToUser;
    private String replyName;

    //shared preferences object
    private SharedPreferences mPreferences;
    //Name of the shared preferences file
    private String sharedPrefFile = "com.example.android.sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        priceOfItem = 0;

        //Find the textView Item to display Data
        outputToUser = findViewById(R.id.finalOutputToUsr);

        //Creating an object of the shared file to save the data
        mPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        int orientation = getResources().getConfiguration().orientation;
        //if the orientation of the device changes the actionbar is hidden so the user has more space to read interact with
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportActionBar().hide();
        }

        if (savedInstanceState != null){
            outputToUser.setText(savedInstanceState.getString("finalOutput"));
            priceOfItem = savedInstanceState.getDouble("priceOfItem");
        }

    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void burgerOrder(View view) {
        displayToast("You've selected the Twin Drifter");
        outputToUser.setTextSize(20);
        priceOfItem = (9.99);
        outputToUser.setText("Current Item in the basket:\n\nTwin Drifter $9.99");
    }

    public void pizzaOrder(View view) {
        displayToast("You've selected the Pizza-Gedon");
        outputToUser.setTextSize(20);
        priceOfItem = (13.99);
        outputToUser.setText("Current Item in the basket:\n\nPizza-Gedon $13.99");
    }

    public void lasangaOrder(View view) {
        displayToast("You've selected the Remy's Special");
        outputToUser.setTextSize(20);
        priceOfItem = (19.99);
        outputToUser.setText("Current Item in the basket:\n\nRemy's Special $19.99");
    }

    public void onFloatBtnClick(View view) {

        if (priceOfItem == 0){
            displayToast("Please Make a selection to Continue");
        }
        else {
            Intent intent = new Intent(this,SecondActivity.class);
            intent.putExtra(EXTRA_MESSAGE,priceOfItem);
            startActivityForResult(intent,TEXT_REQUEST);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST){
            if (resultCode == RESULT_OK){
                replyName = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                double replyPrice = data.getDoubleExtra(SecondActivity.EXTRA_REPLY_AMOUNT,0);
                int modeToGet = data.getIntExtra(SecondActivity.EXTRA_REPLY_MODE,0);

                outputToUser.setTextSize(18);

                if (modeToGet == 1) {
                    outputToUser.setText("Hi "+replyName + ", your Order has been successfully placed! \n\n$" +String.format("%.2f",replyPrice) + " will be due upon Pickup");
                }
                else if (modeToGet==2){
                    double temp = replyPrice + 2;
                    outputToUser.setText("Hi "+replyName + ", your Order has been successfully placed! \n\n$" +String.format("%.2f",temp) + " will be due upon Delivery");
                }

                //resets the price so that the user can make another selection as needed
                priceOfItem = 0;
            }
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("finalOutput",outputToUser.getText().toString());
        outState.putDouble("priceOfItem", priceOfItem);
    }

}