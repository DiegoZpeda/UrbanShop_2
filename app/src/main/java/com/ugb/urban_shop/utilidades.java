package com.ugb.urban_shop;

public class utilidades {
    static String url_consulta= "http://192.168.80.76:5984/db_urban/_design/ropas/_view/ropas";
    static  String url_mto = "http://192.168.80.76:5984/db_urban";

    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}
