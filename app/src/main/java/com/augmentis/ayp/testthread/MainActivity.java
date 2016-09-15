package com.augmentis.ayp.testthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MESSAGE_CODE = 1;
    private static final int MESSAGE_CODE_1 = 2;
    TextView textViewNumber;
    EditText editTextNumber;
    Button buttonNumber;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = (EditText) findViewById(R.id.edit_text_number);
        buttonNumber = (Button) findViewById(R.id.button_number);
        textViewNumber = (TextView) findViewById(R.id.text_view_number);

        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewNumber.setText(editTextNumber.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ////////////////////////////////// Handler //////////////////////////////////////

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_CODE){
                    textViewNumber.setText(msg.obj.toString());
                }
                else {
                    textViewNumber.setText("Finish");
                }
            }
        };

        buttonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s = editTextNumber.getText().toString();
                        int inputNumber = Integer.valueOf(s);
                        while(true){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = handler.obtainMessage(MESSAGE_CODE, inputNumber);
                            msg.sendToTarget();
                            inputNumber--;

                            if(inputNumber < 0) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message msg1 = handler.obtainMessage(MESSAGE_CODE_1, inputNumber);
                                msg1.sendToTarget();
                                break;
                            }
                        }

                    }
                }).start();

            }
        });
    }


}
