package es.perroverde.ejemplos.cuentassql;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity {


    private TextView tvMes;
    private TextView tvIngresos;
    private TextView tvGastos;
    private TextView tvSaldo;
    private Button btnIngreso;
    private Button btnGasto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creacion de la bd. si hace falta
        CuentasDBHelper dbh = new CuentasDBHelper(getBaseContext());

        // obtener b.d. escribible para que la cree automaticamente si no existe
        SQLiteDatabase db = dbh.getWritableDatabase();
        // cerramos la b.d. en modo escritura
        db.close();

        // buscar el mes y los resumenes para la tabla resumen
        Date ahora = new Date();
        String mesFecha = dbh.getMesFecha(ahora);

        // buscar la suma de ingresos del mes
        Long resIngresos = dbh.getResIG(ahora, 0);

        // buscar la suma de gastos del mes
        Long resGastos = dbh.getResIG(ahora, 1);

        // calcular el saldo
        Long saldo = resIngresos - resGastos;

        // para formatear las salidas numericas
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        // String numberAsString = decimalFormat.format(number);

        TextView tvMes = (TextView)findViewById(R.id.tvMes);
        TextView tvIngresos = (TextView)findViewById(R.id.tvIngresos);
        TextView tvGastos = (TextView)findViewById(R.id.tvGastos);
        TextView tvSaldo = (TextView)findViewById(R.id.tvSaldo);

        // actualizar interfaz
        tvMes.setText(mesFecha);
        tvIngresos.setText(decimalFormat.format(resIngresos));
        tvGastos.setText(decimalFormat.format(resGastos));
        tvSaldo.setText(decimalFormat.format(saldo));
        if (saldo > 0 ) {
            tvSaldo.setTextColor(ContextCompat.getColor(this, R.color.verdeOK));
        } else {
            tvSaldo.setTextColor(ContextCompat.getColor(this, R.color.rojoFeria));
        }

        // botones
        btnIngreso = (Button)findViewById(R.id.btnIngreso);
        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AltaActivity.class);
                intent.putExtra("tipo", Integer.toString(0)); // 0= tipo ingreso
                startActivity(intent);
            }
        });

        btnGasto = (Button)findViewById(R.id.btnGasto);
        btnGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AltaActivity.class);
                intent.putExtra("tipo", Integer.toString(1)); // 1= tipo gasto
                startActivity(intent);
            }
        });




    }

}
