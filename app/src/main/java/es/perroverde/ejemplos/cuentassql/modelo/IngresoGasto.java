package es.perroverde.ejemplos.cuentassql.modelo;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alejandro on 8/11/15.
 */
public class IngresoGasto {
    /*
    String SQL = "INSERT INTO `movimientos` VALUES " +
            "(1,'Compra bicicleta','2015-08-01 18:37:43',1,5,2,'Bicicleta para cumpleaños del Decatlon',125.45),
    */

    private int id;
    private String nombre;
    private Date fechahora;
    private int idCat;
    private int idTipo;
    private int idCuenta;
    private String descripcion;
    private Double importe;

    public IngresoGasto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    /**
     *  admite una fecha y una hora para guardar un Date
     * @param fecha formato "dd-MM-YYYY"
     * @param hora formato "HH:mm"
     */
    public void setFechahora(String fecha, String hora) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.forLanguageTag("ES"));
        String fh = fecha + " " + hora;
        try {
            Date date = format.parse(fh);
            this.setFechahora(date);
        } catch (ParseException e) {
            Log.e("SetFechaHora", "Formato de fecha incorrecto: "+e.getLocalizedMessage());
        }
    }


    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getIngresoGasto() {
        if (idTipo==0) return "Ingreso";
        else return "Gasto";
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return getIngresoGasto() + " " + nombre + sdf.format(getFechahora()) + " " + getImporte();
    }
}
