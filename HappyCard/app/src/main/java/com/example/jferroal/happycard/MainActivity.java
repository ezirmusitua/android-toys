package com.example.jferroal.happycard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity{
    private int countOfCoffee = 0;
    private int priceOfCoffee = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This method is called when the order button is clicked.
    public void submitOrder(View view) {
        this.display(this.countOfCoffee);
        this.displayPrice(this.countOfCoffee * this.priceOfCoffee);
    }

    public void increment(View view) {
        this.countOfCoffee += 1;
        this.display(this.countOfCoffee);
        this.displayPrice(this.countOfCoffee * this.priceOfCoffee);
    }

    public void decrement(View view) {
        if (this.countOfCoffee < 1) return;
        this.countOfCoffee -= 1;
        this.display(this.countOfCoffee);
        this.displayPrice(this.countOfCoffee * this.priceOfCoffee);
    }

    // This method displays the given quantity value on the screen.
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_quantity);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.text_total_price);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}