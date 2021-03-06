package es.perroverde.ejemplos.cuentassql;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import es.perroverde.ejemplos.cuentassql.modelo.IngresoGasto;

public class AltaActivity extends FragmentActivity {

    private Spinner sp;
    private TextView tvIngresoGasto;
    private TextView tvTitulo;
    private ArrayList<String> lista;
    private Context mContext;

    private Button btnFecha;
    private Button btnHora;

    private static Button btnFechaS;
    private static Button btnHoraS;

    private int tipo;


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            btnFechaS.setText(day + "-" + (month + 1) + "-" + year);
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            btnHoraS.setText(hourOfDay + ":" + minute);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);

        // esto es porque el ArrayAdapter necesita un Context, no vale meter this
        mContext = this;

        // recogemos el intent para sacarle el extra
        Intent intent = getIntent();
        String strTipo = intent.getStringExtra("tipo");
        tipo = new Integer(strTipo).intValue();


        // cambiar el titulo de la actividad
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        if (tipo == 0) {
            tvTitulo.setText("Nuevo Ingreso");
        } else {
            tvTitulo.setText("Nuevo Gasto");
        }

        // Rellenar el spinner
        sp = (Spinner)findViewById(R.id.spnCateg);

        CuentasDBHelper db = new CuentasDBHelper(this);

        lista = db.getAllCateg(tipo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        sp.setAdapter(adapter);

        // fecha y hora
        btnFecha = (Button)findViewById(R.id.btnFecha);
        btnHora = (Button)findViewById(R.id.btnHora);
        btnFechaS = (Button)findViewById(R.id.btnFecha);
        btnHoraS = (Button)findViewById(R.id.btnHora);

        // al arrancar mostrar la fecha y hora actuales en los botones
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        btnFecha.setText(day + "-" + (month + 1) + "-" + year);
        btnHora.setText(hour + ":" + minute);


        // recoger los datos y rellenar en la base de datos
        // se hace en doAltaIngresoGasto()

        db.close();
    } // fin onCreate

    // onClick callback para los botones de mostrar los dialogos de fecha y hora
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void doAltaIngresoGasto(View v) {
        // rellenar el objeto del modelo a partir de los datos del formulario
        IngresoGasto ig = new IngresoGasto();


        CuentasDBHelper db = new CuentasDBHelper(this);
        boolean res = db.altaIngresoGasto(tipo);
        if (res) {
            if (tipo == 0) { // ingreso
                Toast.makeText(this, "Ingreso grabado Ok", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(this, "Gasto grabado Ok", Toast.LENGTH_SHORT);
            }
        } else {
                showDialog();
        }
        // al escribir hay que cerrar la b.d.
        db.close();
    }


    public void doPositiveClick() {
        // no hago nada a ver que pasa
        // lo cierra correctamente OK
    }

    void showDialog() {
        DialogFragment newFragment = AlertFragment.newInstance(
                R.string.alert_dialog_title,
                R.string.alert_dialog_message);
        newFragment.show(getFragmentManager(), "¡Cuidado!");
    }

}
