package com.example.f_mkotsovoulou.orderapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.x;

public class MainActivity extends AppCompatActivity {
    double unitPrice;
    double totalPrice;
    EditText quantity;
    CheckBox ham;
    CheckBox cheese;
    CheckBox pepperoni;
    CheckBox bacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unitPrice = 4.5;
        totalPrice = 0.0;
        setContentView(R.layout.activity_main);
        quantity = (EditText) findViewById(R.id.quantity);
        ham = (CheckBox) findViewById(R.id.ham);
        cheese = (CheckBox) findViewById(R.id.cheese);
        pepperoni = (CheckBox) findViewById(R.id.pepperoni);
        bacon = (CheckBox) findViewById(R.id.bacon);


        quantity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    calcPrice();
                }
                return false;
            }
        });

        cheese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    calcPrice();
            }
        });

        ham.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calcPrice();
            }
        });

        bacon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calcPrice();
            }
        });

        pepperoni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calcPrice();
            }
        });
    }


    public void submitOrder(View v){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        TextView message = (TextView) findViewById(R.id.message);
        String quantityText = quantity.getText() + "";
        String emailMessage="I wish to order " + quantityText + " pizza/s! \n";
        if (bacon.isChecked() || ham.isChecked() || pepperoni.isChecked() || cheese.isChecked())
        {
            emailMessage+= " with the following extra topping/s : \n";
            if (bacon.isChecked()) emailMessage += " Bacon \n";
            if (ham.isChecked()) emailMessage += " Ham \n";
            if (cheese.isChecked()) emailMessage += " Cheese \n";
            if (pepperoni.isChecked()) emailMessage += " Pepperoni \n";
        }

        message.setText(emailMessage);
        quantity.setText("");
        cheese.setChecked(false);
        ham.setChecked(false);
        bacon.setChecked(false);
        pepperoni.setChecked(false);

        Log.i("MAIRA", "The button was clicked and the quantity is " + quantityText);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "orders@pizzashop.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Order: " + currentDateandTime);
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void calcPrice() {
        unitPrice = 4.5;

        int quantityInt;
        if ((quantity.getText()+"").equals("")) {
            quantityInt = 1;
            quantity.setText("1");
        } else
            quantityInt = Integer.parseInt(quantity.getText()+ "");
        if (ham.isChecked())
            unitPrice += 0.50;
        if (cheese.isChecked())
            unitPrice += 0.50;
        if (pepperoni.isChecked())
            unitPrice += 0.50;
        if (bacon.isChecked())
            unitPrice += 0.50;


        totalPrice = unitPrice * quantityInt;
        TextView tp = (TextView) findViewById(R.id.totalPrice);
        tp.setText(totalPrice+"");
    }

}
