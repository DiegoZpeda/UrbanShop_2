package com.ugb.urban_shop;

public class ropas {
    String rev;
    String idUnico;
    String id;
    String codigo;
    String descripcion;
    String marca;
    String presentacion;
    String precio;
    String urlImg;

    public ropas (String id,String rev, String idUnico, String codigo, String descripcion, String marca, String presentacion,String precio, String urlImg){
        this.id = id;
        this.rev = rev;
        this.idUnico = idUnico;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.precio = precio;
        this.urlImg = urlImg;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRev() {return rev;}
    public void setRev(String rev) {this.rev = rev;}
    public String getIdUnico() {return idUnico;}
    public void setIdUnico(String idUnico) {this.idUnico = idUnico;}

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
