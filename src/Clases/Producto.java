/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author apple
 */
public class Producto {

    int idProducto;
    String nombre;
    int stock;
    int cant;
    double precio, descuento, total, subtotal;

    public Producto(int idProducto, String nombre, int cant, double precio, double descuento, double total, double subtotal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cant = cant;
        this.precio = precio;
        this.descuento = descuento;
        this.total = total;
        this.subtotal = subtotal;
    }

    public void changeCantidad(int cant) {
        this.cant = cant;
        subtotal = cant * precio;
        total = subtotal - subtotal * descuento;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto() {
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public int getCant() {
        return cant;
    }

    public double getPrecio() {
        return precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public Producto(int id, String nombre, int cant, double precio, double desc) {
        this.idProducto = id;
        this.nombre = nombre;
        this.cant = cant;
        this.precio = precio;
        this.descuento = desc;
        total = cant * precio - desc;
    }

    public String getInsertQuery() {
        String query = nombre + " " + stock + " " + precio;
        return query;
    }
}
