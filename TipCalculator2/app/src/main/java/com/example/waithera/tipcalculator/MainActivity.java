package com.example.waithera.tipcalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;//For edit text event handle
import android.text.TextWatcher;//Edittext listener
import android.widget.EditText;//for bill amount input
import android.widget.SeekBar;//for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener;//SeekBar listener
import android.widget.TextView;//For displaying text

import org.w3c.dom.Text;

import java.text.NumberFormat;//for currency formatting

public class MainActivity extends AppCompatActivity{

    private static final NumberFormat currencyFormat=
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat=
            NumberFormat.getPercentInstance();

    private double billAmount=0.0;//bill amount entered by user
    private double percent=0.15; //initial tip percentage
    private TextView amountTextView;//shows formatted bill amount
    private TextView percentTextView;//Shows tip percentage
    private TextView tipTextView;//shows calculated tip percentage
    private TextView totalTextView;//shows calculated total bill amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to programmatically manipulated Textviews

        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView=(TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));//set text to 0
        totalTextView.setText(currencyFormat.format(0));//set text to 0

        //set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //set percentSeekBar's onSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }
        //Calculate and display tip and total amounts
        private void calculate(){
        //format percent and display percentTextView
        percentTextView.setText(percentFormat.format(percent));

        //Calculate tip and total
        double tip=billAmount* percent;
        double total=billAmount+tip;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }
    //listener object for the SeekBar's progress changed events
        private final OnSeekBarChangeListener seekBarListener=
                new OnSeekBarChangeListener(){
        //update percent, then call calculate
        @Override
                public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
            percent=progress/100.0;//set percent based on progress
            calculate();
        }
        @Override
                    public void onStartTrackingTouch(SeekBar seekBar){}
         @Override
                    public void onStopTrackingTouch(SeekBar seekBar){}
    };

    //listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher=new TextWatcher(){
        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {
            try {//get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            } catch (NumberFormatException e) {//if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate();//update the tip and total textviews
        }
            @Override
                    public void afterTextChanged(Editable s){}
            @Override
                    public void beforeTextChanged(
                            CharSequence s,int start,int count,int after
                    ){}

    };
    }



