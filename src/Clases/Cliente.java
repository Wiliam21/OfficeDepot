
package Clases;

import java.sql.Date;

/**
 *
 * @author apple
 */
public class Cliente {
    int idCliente;
    String nombre;

    public int getIdCliente() {
        return idCliente;
    }
    String apellido;
    Date clienteDesde;
    String direccion;

    public Cliente(int idCliente, String nombre, String apellido, Date clienteDesde, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clienteDesde = clienteDesde;
        this.direccion = direccion;
    }
    
    public String getFullName(){
        return nombre+" "+apellido;
    }
}
