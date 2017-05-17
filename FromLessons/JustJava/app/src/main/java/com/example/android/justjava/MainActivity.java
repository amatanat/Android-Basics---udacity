package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG= "MainActivity";

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        boolean hasWhippedCreme = false;
        boolean hasChocolate = false;
        String name = "";


        CheckBox checkBoxCreme = (CheckBox) findViewById(R.id.whipped_cream_checkBox);
        if (checkBoxCreme != null)
            hasWhippedCreme = checkBoxCreme.isChecked();

        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkBox);
        if (checkBoxChocolate != null)
            hasChocolate = checkBoxChocolate.isChecked();

        EditText editTextName = (EditText) findViewById(R.id.name_edittext);
        if (editTextName != null){
            name = editTextName.getText().toString();
        }
        int price = calculatePrice(hasWhippedCreme, hasChocolate);

        // sending order in email using email app in device
       /* Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,  "JustJava order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCreme, hasChocolate, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/

        displayMessage(createOrderSummary(price, hasWhippedCreme, hasChocolate, name));
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (quantityTextView != null)
        quantityTextView.setText("" + number);
    }


    public void increment(View view){
        if (quantity < 100){
            quantity++;
        }else{
            Toast.makeText(this, getString(R.string.buy_less_coffee), Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view){
        if (quantity > 1){
            quantity--;
        } else{
            Toast.makeText(this, getString(R.string.buy_more_coffee), Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        if (orderSummaryTextView != null)
            orderSummaryTextView.setText(message);
    }

    private int calculatePrice(boolean hasWhippedCreme, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCreme){
            basePrice = basePrice + 1;
        }

        if (hasChocolate){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(int price, boolean hasWhippedCreme, boolean hasChocolate, String name){
        String priceMessage = getString(R.string.name, name);
        priceMessage += "\n" + getString(R.string.add_whipped_creme) + " " + hasWhippedCreme;
        priceMessage += "\n" + getString(R.string.add_chocolate) + " " + hasChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + " " + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
       return priceMessage;
    }
}
