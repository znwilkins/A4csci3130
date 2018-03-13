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

/**
 * This class describes an activity that displays information about
 * a specific business. This business can be updated or deleted.
 */
public class DetailViewActivity extends Activity {

    private EditText businessNumField, nameField, addressField;
    private Spinner typeSpinner, provTerrSpinner;
    private MyApplicationData appState;
    public Business receivedBusiness;

    /**
     * This method sets up fields of the activity, and makes calls
     * to finish setting up the activity display.
     * @param savedInstanceState not used
     */
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

        if (receivedBusiness != null)
            displayBusinessInfo();
        else
            showErrorDialogAndFinish();
    }

    /**
     * This method displays an error dialog, and exits the activity.
     * This should only be called in erroneous situations.
     */
    private void showErrorDialogAndFinish() {
        InfoDialogFragment fragment = new InfoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("msgid", R.string.retrieve_failure);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "NULL_BUSINESS_FRAGMENT");
        finish();
    }

    /**
     * This method changes the fields to display the current business information.
     */
    private void displayBusinessInfo() {
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

    /**
     * This method determines an array position that matches a given String.
     * @param arr the array of Strings
     * @param desired the desired String
     * @return the index of the desired String, if it is present, else 0
     */
    private int getArrayPosition(String[] arr, String desired) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(desired))
                return i;
        }

        return 0;
    }

    /**
     * This method updates the displayed business using the values that a user has input.
     * @param v the view, not used
     */
    public void updateBusiness(View v) {
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

    /**
     * This method deletes the displayed business.
     * @param v the view, not used
     */
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
