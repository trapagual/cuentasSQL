package es.perroverde.ejemplos.cuentassql;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alejandro on 7/11/15.
 */
public class AlertFragment  extends DialogFragment {

    public static AlertFragment newInstance(int title, int message) {
        AlertFragment frag = new AlertFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        int message = getArguments().getInt("message");

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_dialog_alert_red)
                .setTitle(title)
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((AltaActivity) getActivity()).doPositiveClick();
                            }
                        }
                )
                /* en esta no necesito el boton de cancelar
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((AltaActivity)getActivity()).doNegativeClick();
                            }
                        }
                ) */
                .create();
    }



}
