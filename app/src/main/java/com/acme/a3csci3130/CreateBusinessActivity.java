package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CreateBusinessActivity extends Activity {

    private Button submitButton;
    private EditText businessNumField, nameField, addressField;
    private Spinner typeSpinner, provTerrSpinner;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_activity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
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

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String uid = appState.firebaseReference.push().getKey();
        String businessNum = businessNumField.getText().toString();
        String name = nameField.getText().toString();
        String businessType = typeSpinner.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String provTerr = provTerrSpinner.getSelectedItem().toString();
        Business business = new Business(uid, businessNum, name, businessType, address, provTerr);

        Task task = appState.firebaseReference.child(uid).setValue(business);
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    Log.i("CREATE_BUSINESS", task.getException().toString());
                    Context context = getApplicationContext();
                    String msg = "Violates formatting rules!";

                    //TODO This is too small, bigger?
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                    finish();
            }
        });
    }
}
