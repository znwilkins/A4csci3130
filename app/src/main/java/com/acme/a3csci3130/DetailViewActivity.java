package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DetailViewActivity extends Activity {

    private EditText numField, nameField;
    Business receivedPersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedPersonInfo = (Business)getIntent().getSerializableExtra("Business");

        numField = (EditText) findViewById(R.id.name);
        nameField = (EditText) findViewById(R.id.email);

        if(receivedPersonInfo != null){
            numField.setText(receivedPersonInfo.businessNumber);
            nameField.setText(receivedPersonInfo.name);
        }
    }

    public void updateContact(View v) {
        //TODO: Update contact functionality
    }

    public void eraseContact(View v) {
        //TODO: Erase contact functionality
    }
}
