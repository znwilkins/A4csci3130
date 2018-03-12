package com.acme.a3csci3130;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class contains handles to Firebase.
 */
public class MyApplicationData extends Application {

    /** The reference to the database. */
    public DatabaseReference firebaseReference;
    /** An instance of the database. */
    public FirebaseDatabase firebaseDBInstance;

}
