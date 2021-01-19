/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author apple
 */
public class Venta {

    int idVenta;
    Date fecha;
    int idCliente;
    boolean clienteFrecuente = false;
    double subtotal = 0;
    double total = 0;
    double descuento = 0;
    boolean envio;

    public boolean getEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
        CalcularCuenta();
    }
    
    
    public ArrayList<Producto> productos = new ArrayList<Producto>();

    public void AddProducto(Producto p) {
        for (Producto producto : productos) {
            if (producto.idProducto == p.idProducto) {
                producto.changeCantidad(p.cant + producto.cant);
                CalcularCuenta();
                return;
            }
        }
        productos.add(p);
        CalcularCuenta();
    }

    public void RemoveProducto(int n) {
        productos.remove(n);
    }

    private void CalcularCuenta() {
        subtotal = 0;
        total = 0;
        descuento = 0;
        for (Producto producto : productos) {
            subtotal+=producto.subtotal;
            total+=producto.total;
        }
        descuento=subtotal-total;
        if(clienteFrecuente){
            descuento+=subtotal*0.1;
            total-=subtotal*0.1;
        }
        if(envio){
            total+=100;
        }
    }

    public boolean isClienteFrecuente() {
        return clienteFrecuente;
    }

    public void setClienteFrecuente(boolean clienteFrecuente) {
        this.clienteFrecuente = clienteFrecuente;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDescuento() {
        return descuento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

}
