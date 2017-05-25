package com.example.jferroal.happycard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int countOfCoffee = 0;
    private SparseBooleanArray choosenTopping = new SparseBooleanArray(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        String message = this.concatMessage();
        this.displayMessage(message);
        String[] addresses = {"jferroal@gmail.com"};
        this.sendToEmail(addresses, "StarFucks Coffee order", message);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.topping_option_a:
                if (checked) {
                    this.choosenTopping.put(R.string.text_topping_option_a, true);
                } else {
                    this.choosenTopping.put(R.string.text_topping_option_a, false);
                }
                break;
            case R.id.topping_option_b:
                if (checked) {
                    this.choosenTopping.put(R.string.text_topping_option_b, true);
                } else {
                    this.choosenTopping.put(R.string.text_topping_option_b, false);
                }
                break;
            case R.id.topping_option_c:
                if (checked) {
                    this.choosenTopping.put(R.string.text_topping_option_c, true);
                } else {
                    this.choosenTopping.put(R.string.text_topping_option_c, false);
                }
                break;
            default:
                break;
        }
    }

    public void increment(View view) {
        this.countOfCoffee += 1;
        this.display(this.countOfCoffee);
    }

    public void decrement(View view) {
        if (this.countOfCoffee < 1) return;
        this.countOfCoffee -= 1;
        this.display(this.countOfCoffee);
    }

    // This method displays the given quantity value on the screen.
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_quantity);
        String message = "" + number;
        quantityTextView.setText(message);
    }

    private void displayMessage(String message) {
        TextView messageTextView = (TextView) findViewById(R.id.text_message);
        messageTextView.setText(message);
    }
    private String concatMessage() {
        int totalPrice = this.countOfCoffee * 5;
        String message = "Name: Jferroal\nTotal: $" + totalPrice + "\nAdd Toppings:\n";
        for (int i = 0; i < this.choosenTopping.size(); i++) {
            int key = choosenTopping.keyAt(i);
            // get the object by the key.
            Boolean isChecked = choosenTopping.get(key);
            switch (key) {
                case R.string.text_topping_option_a:
                    if (isChecked) {
                        message = message.concat("Topping A\n");
                    }
                    break;
                case R.string.text_topping_option_b:
                    if (isChecked) {
                        message = message.concat("Topping B\n");
                    }
                    break;
                case R.string.text_topping_option_c:
                    if (isChecked) {
                        message = message.concat("Topping C\n");
                    }
                    break;
                default:
                    break;
            }
        }
        if (this.countOfCoffee > 0 && this.countOfCoffee < 2) {
            message = message.concat("Have a nice day");
        } else if (this.countOfCoffee > 1) {
            message = message.concat("Share with your friends");
        }
        return message;
    }

    private void sendToEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}