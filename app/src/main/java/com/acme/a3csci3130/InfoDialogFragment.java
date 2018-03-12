package com.acme.a3csci3130;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * This class describes a dialog box that does nothing except
 * display a specified message.
 */
public class InfoDialogFragment extends DialogFragment {

    /**
     * This method retrieves a given message ID, and creates a fragment
     * with the associated message.
     * @param savedInstanceState not used
     * @return a fragment containing a specified message
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int message = (Integer) getArguments().get("msgid");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                });
        return builder.create();
    }

}
