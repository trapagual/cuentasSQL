package es.perroverde.ejemplos.cuentassql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SGEN0290 on 02/11/2015.
 */
public class CuentasDBHelper extends SQLiteOpenHelper {


    private static int version = 1;
    private static String name = "CuentasDB";
    private static SQLiteDatabase.CursorFactory factory = null;

    public CuentasDBHelper(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(),"La Base de datos no existe: vamos a crearla");
        String sqlCreacionMovimientos =
                "CREATE TABLE movimientos (" +
                        "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "    nombre TEXT," +
                        "    fecha DATETIME default (current_timestamp)," +
                        "    id_tipo INTEGER default 0," +
                        "    id_categ integer default 0," +
                        "    id_cuenta integer default 0," +
                        "    descripcion TEXT," +
                        "	 importe REAL default 0)";
        String sqlCreacionCategorias =
                "CREATE TABLE categorias (" +
                        "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "    nombre TEXT, " +
                        "	 id_tipo integer default 0)";
        String sqlCreacionTipos =
                "CREATE TABLE tipos (" +
                        "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "    nombre TEXT )";
        String sqlCreacionCuentas =
                "CREATE TABLE cuentas (" +
                        "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "    nombre TEXT )";
        // ejecutar batch creacion tablas
        db.execSQL(sqlCreacionMovimientos);
        Log.i(this.getClass().toString(), "Tabla Movimientos creada");

        db.execSQL(sqlCreacionCategorias);
        Log.i(this.getClass().toString(), "Tabla Categorias creada");

        db.execSQL(sqlCreacionTipos);
        Log.i(this.getClass().toString(), "Tabla Tipos creada");

        db.execSQL(sqlCreacionCuentas);
        Log.i(this.getClass().toString(), "Tabla Cuentas creada");


        // ejecutar inserciones categorias
        db.execSQL("INSERT INTO categorias VALUES(1,'Otros ingresos',0)");
        db.execSQL("INSERT INTO categorias VALUES(2,'Consultas',0)");
        db.execSQL("INSERT INTO categorias VALUES(3,'Libros',1)");
        db.execSQL("INSERT INTO categorias VALUES(4,'Compra',1)");
        db.execSQL("INSERT INTO categorias VALUES(5,'Juguetes',1)");
        db.execSQL("INSERT INTO categorias VALUES(6,'Comida',1)");
        db.execSQL("INSERT INTO categorias VALUES(7,'Bebida',1)");
        db.execSQL("INSERT INTO categorias VALUES(8,'Consultas',1)");
        db.execSQL("INSERT INTO categorias VALUES(9,'Transporte',1)");
        db.execSQL("INSERT INTO categorias VALUES(10,'Varios',1)");
        Log.i(this.getClass().toString(), "Insertadas Categorias");

        // ejecutar inserciones cuentas
        db.execSQL("INSERT INTO cuentas VALUES(1,'Efectivo')");
        db.execSQL("INSERT INTO cuentas VALUES(2,'Tarjeta Débito')");
        db.execSQL("INSERT INTO cuentas VALUES(3,'Tarjeta ECI')");
        db.execSQL("INSERT INTO cuentas VALUES(4,'Domiciliación')");
        db.execSQL("INSERT INTO cuentas VALUES(5,'Transferencia')");
        Log.i(this.getClass().toString(), "Insertadas Cuentas");

        // ejecutar inserciones tipos
        db.execSQL("INSERT INTO tipos VALUES(0,'Ingreso')");
        db.execSQL("INSERT INTO tipos VALUES(1,'Gasto')");

        // ejecutar inserciones movimientos
        db.execSQL(
                "INSERT INTO `movimientos` VALUES " +
                        "(1,'Compra bicicleta','2015-08-01 18:37:43',1,5,2,'Bicicleta para cumpleaños del Decatlon',125.45), " +
                        "(2,'Tabaco','2015-09-01 18:39:09',1,10,1,NULL,21.95), " +
                        "(3,'Befeater','2015-10-01 18:39:47',1,7,1,NULL,12.05), " +
                        "(4,'Mari Rosi','2015-11-01 18:41:43',0,7,1,'Consulta pasada a hoy desde el viernes pasado',60.0), " +
                        "(5,'Antonio','2015-11-01 18:42:09',0,7,1,'Martes a las 12',60.0), " +
                        "(6,'Pilar','2015-11-01 18:42:35',0,7,1,'Viernes a las 11',60.0), " +
                        "(7,'Fernando','2015-11-01 18:43:06',0,7,1,'Todo el mes de Octubre',250.0), " +
                        "(8,'Copas','2015-11-05 11:30:32',1,10,1,NULL,43.25), " +
                        "(9,'Miguel Angel','2015-08-05 11:33:36',0,2,1,'Lunes 15: 16',65.0), " +
                        "(10,'Antonio','2015-09-05 11:34:28',0,2,1,'Jueves 7 a 8',62.3), " +
                        "(11,'Juan','2015-10-05 11:35:09',0,2,1,'Martes a las 12',60.0)"
        );
        Log.i(this.getClass().toString(), "Insertados Movimientos");



        Log.i(this.getClass().toString(), "Fin creacion b.d.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(this.getClass().toString(), "Actualización b.d. nada que hacer");
    }

    // obtener el mes de la fecha
    public String getMesFecha(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // esta funcion devuelve los meses empezando en cero Enero=0, Diciembre=11
        int month = cal.get(Calendar.MONTH);

        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        return meses[month];
    }



    /** Obtener la suma de ingresos o gastos de la b.d.
     * @param  ahora la fecha a partir de la cual calcular el mes
     * @param tipo 1 si gastos, 0 si ingresos
     * @return Long la suma de ingresos o gastos
     */
    public Long getResIG(Date ahora, int tipo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ahora);
        // esta funcion devuelve los meses empezando en cero Enero=0, Diciembre=11
        int month = cal.get(Calendar.MONTH);
        Log.i("getResIG", "Calendar.MONTH me ha devuelto: "+month);
        String strMes = "";
        Long suma = 0L;
        // ojo
        //select * from cuentas where strftime('%m',fecha) = '09';
        if (month < 9 ) strMes="0"+(month+1);
        else strMes=""+(month+1);

        Log.i("getResIG", "El mes en cadena es: "+strMes);

        String query = "select sum(importe) from movimientos where id_tipo="+tipo+" and strftime('%m',fecha) = '"+strMes+"'";
        // obtener b.d. en solo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        // ejecuto consulta
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            suma = cursor.getLong(0);
        }

        // en lectura no se cierra la b.d.
        return suma;
    }

    /**
     * Obtiene una lista de nombres de categorias ordenadas alfabeticamente
     * @param tipo 0=ingresos, 1=gastos
     * @return Una lista de nombres
     */
    public ArrayList<String> getAllCateg(int tipo) {
        ArrayList<String> lista = new ArrayList<String>();

        String query = "select _id, nombre from categorias where id_tipo = "+tipo+" order by nombre";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(1));
            } while (cursor.moveToNext());
            return lista;
        } else {
            return null;
        }
    }

    /**
     * Graba un ingreso o un gasto, segun el parametro tipo
     * @param tipo si 0, es un ingreso, si no, es un gasto
     * @return true si ha completado correctamente la operacion, false si no
     */
    public boolean altaIngresoGasto(int tipo) {
        return false;
    }

}
