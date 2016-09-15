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

public class MainActivity extends AppCompatActivity implements BackgroundThread.CallBacks {

    private static final int MESSAGE_CODE = 1;
    private static final int MESSAGE_CODE_1 = 2;
    TextView textViewNumber;
    EditText editTextNumber;
    Button buttonNumber;
    private Handler handlerMainThread;
    BackgroundThread bgThread;

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

        handlerMainThread = new Handler();
        bgThread = new BackgroundThread("ThreadName", this, handlerMainThread);
        bgThread.start();
        bgThread.onLooperPrepared();

        ////////////////////////////////// Looper:HandlerThread ////////////////////////////////////

        buttonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bgThread.sendValueInQueue(editTextNumber.getText().toString());
            }
        });


    }

    // get Value Callbacks
    @Override
    public void setText(String s) {
        textViewNumber.setText(s);
    }
}
