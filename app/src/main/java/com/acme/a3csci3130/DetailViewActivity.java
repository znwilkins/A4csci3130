package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DetailViewActivity extends Activity {

    private EditText businessNumField, nameField, addressField;
    private Spinner typeSpinner, provTerrSpinner;
    private MyApplicationData appState;
    private Business receivedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedBusiness = (Business) getIntent().getSerializableExtra("Business");

        appState = ((MyApplicationData) getApplicationContext());

        businessNumField = (EditText) findViewById(R.id.number);
        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.address);
        typeSpinner = (Spinner) findViewById(R.id.type_spin);
        provTerrSpinner = (Spinner) findViewById(R.id.provTerr_spin);

        if (receivedBusiness != null) {
            businessNumField.setText(receivedBusiness.businessNumber);
            nameField.setText(receivedBusiness.name);
            addressField.setText(receivedBusiness.address);
            ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.bus_type_array, android.R.layout.simple_spinner_item);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(typeAdapter);
            typeSpinner.setSelection(getArrayPosition(
                    getResources().getStringArray(R.array.bus_type_array),
                    receivedBusiness.businessType)
            );
            ArrayAdapter<CharSequence> provTerrAdapter = ArrayAdapter.createFromResource(this,
                    R.array.bus_provTerr_array, android.R.layout.simple_spinner_item);
            provTerrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provTerrSpinner.setAdapter(provTerrAdapter);
            provTerrSpinner.setSelection(getArrayPosition(
                    getResources().getStringArray(R.array.bus_provTerr_array),
                    receivedBusiness.provinceTerritory)
            );
        }
        else {
            InfoDialogFragment fragment = new InfoDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("msgid", R.string.retrieve_failure);
            fragment.setArguments(bundle);
            fragment.show(getFragmentManager(), "NULL_BUSINESS_FRAGMENT");
            finish();
        }
    }

    private int getArrayPosition(String[] arr, String desired) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(desired))
                return i;
        }

        return 0;
    }

    public void updateContact(View v) {
        receivedBusiness.businessNumber = businessNumField.getText().toString();
        receivedBusiness.name = nameField.getText().toString();
        receivedBusiness.businessType = typeSpinner.getSelectedItem().toString();
        receivedBusiness.address = addressField.getText().toString();
        receivedBusiness.provinceTerritory = provTerrSpinner.getSelectedItem().toString();

        Task task = appState.firebaseReference.child(receivedBusiness.uid).setValue(receivedBusiness);
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    Log.i("UPDATE_BUSINESS", task.getException().toString());
                    InfoDialogFragment fragment = new InfoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("msgid", R.string.violation);
                    fragment.setArguments(bundle);
                    fragment.show(getFragmentManager(), "FORMAT_VIOLATION_FRAGMENT");
                }
                else
                    finish();
            }
        });
    }

    public void eraseContact(View v) {
        Task task = appState.firebaseReference.child(receivedBusiness.uid).removeValue();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    Log.i("DELETE_BUSINESS", task.getException().toString());
                    InfoDialogFragment fragment = new InfoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("msgid", R.string.delete_failure);
                    fragment.setArguments(bundle);
                    fragment.show(getFragmentManager(), "DELETE_ERROR_FRAGMENT");
                }
                else
                    finish();
            }
        });
    }
}
