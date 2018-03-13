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
 * This class describes an activity where a user can create a business.
 */
public class CreateBusinessActivity extends Activity {

    private EditText businessNumField, nameField, addressField;
    private Spinner typeSpinner, provTerrSpinner;
    private MyApplicationData appState;
    public Business business;

    /**
     * This method sets up fields of the activity, and makes calls
     * to finish setting up the activity display.
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_activity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        setUpViews();
    }

    /**
     * This method associates fields with views, and gives input options
     * for the type and prov/terr spinners.
     */
    private void setUpViews() {
        businessNumField = (EditText) findViewById(R.id.number);
        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.address);

        typeSpinner = (Spinner) findViewById(R.id.type_spin);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.bus_type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        provTerrSpinner = (Spinner) findViewById(R.id.provTerr_spin);
        ArrayAdapter<CharSequence> provTerrAdapter = ArrayAdapter.createFromResource(this,
                R.array.bus_provTerr_array, android.R.layout.simple_spinner_item);
        provTerrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provTerrSpinner.setAdapter(provTerrAdapter);
    }

    /**
     * This method is called when the Create Business button is pressed.
     * It updates Firebase with the information that the user has input.
     * @param v not used
     */
    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String uid = appState.firebaseReference.push().getKey();
        String businessNum = businessNumField.getText().toString();
        String name = nameField.getText().toString();
        String businessType = typeSpinner.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String provTerr = provTerrSpinner.getSelectedItem().toString();
        business = new Business(uid, businessNum, name, businessType, address, provTerr);

        Task task = appState.firebaseReference.child(uid).setValue(business);
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    Log.i("CREATE_BUSINESS", task.getException().toString());
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
}
