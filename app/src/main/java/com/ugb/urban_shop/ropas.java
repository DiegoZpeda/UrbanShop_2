package com.ugb.urban_shop;

public class ropas {
    String idropas;
    String codigo;
    String descripcion;
    String marca;
    String presentacion;
    String precio;
    String urlImg;

    public ropas (String idropas, String codigo, String descripcion, String marca, String presentacion,String precio, String urlImg){
        this.idropas = idropas;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.precio = precio;
        this.urlImg = urlImg;
    }
    public String getIdropas() {
        return idropas;
    }

    public void setIdropas(String idropas) {
        this.idropas = idropas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
