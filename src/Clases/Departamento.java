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
public class Departamento {
    int idDepartamento;
    String nombre;

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public Departamento(int idDepartamento, String nombre) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
    }
}
