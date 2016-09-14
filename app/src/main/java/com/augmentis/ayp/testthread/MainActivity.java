package com.augmentis.ayp.testthread;

import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewNumber;
    EditText editTextNumber;
    Button buttonNumber;

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

        buttonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TestAsyncTask testAsyncTask = new TestAsyncTask();
                testAsyncTask.execute(editTextNumber.getText().toString());

            }
        });
    }

    private class TestAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            textViewNumber.setText(values[0].toString());
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = strings[0];
            int inputNumber = Integer.valueOf(s);
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(inputNumber);
                inputNumber--;
                if(inputNumber<0){
                    break;
                }
            }

            return "Finish";
        }

        @Override
        protected void onPostExecute(String s) {
            textViewNumber.setText(s);
        }
    }

}
