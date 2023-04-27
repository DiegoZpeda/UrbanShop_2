package com.ugb.urban_shop;

public class utilidades {
    static String url_consulta= "http://localhost:5984/db_urban/_design/ropas/_view/ropas";
    static  String url_mto = "http://localhost:5984/db_urban/";

    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}
