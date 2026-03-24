package com.example.pfc.api;

public class SesionIdeal {
    private int id;
    private String alias;
    private String fecha_referencia;
    private String swell_minimo;
    private String swell_maximo;
    private String marea;
    private String direccion_viento;
    private String estado_viento;
    private int surfista;

    public SesionIdeal() {}

    public SesionIdeal(String alias, String fecha_referencia, String swell_minimo,
                       String swell_maximo, String marea, String direccion_viento,
                       String estado_viento, int surfista) {
        this.alias = alias;
        this.fecha_referencia = fecha_referencia;
        this.swell_minimo = swell_minimo;
        this.swell_maximo = swell_maximo;
        this.marea = marea;
        this.direccion_viento = direccion_viento;
        this.estado_viento = estado_viento;
        this.surfista = surfista;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFecha_referencia() {
        return fecha_referencia;
    }

    public void setFecha_referencia(String fecha_referencia) {
        this.fecha_referencia = fecha_referencia;
    }

    public String getSwell_minimo() {
        return swell_minimo;
    }

    public void setSwell_minimo(String swell_minimo) {
        this.swell_minimo = swell_minimo;
    }

    public String getMarea() {
        return marea;
    }

    public void setMarea(String marea) {
        this.marea = marea;
    }

    public String getSwell_maximo() {
        return swell_maximo;
    }

    public void setSwell_maximo(String swell_maximo) {
        this.swell_maximo = swell_maximo;
    }

    public String getDireccion_viento() {
        return direccion_viento;
    }

    public void setDireccion_viento(String direccion_viento) {
        this.direccion_viento = direccion_viento;
    }

    public String getEstado_viento() {
        return estado_viento;
    }

    public void setEstado_viento(String estado_viento) {
        this.estado_viento = estado_viento;
    }

    public int getSurfista() {
        return surfista;
    }

    public void setSurfista(int surfista) {
        this.surfista = surfista;
    }

    public int getId() { return id; }
    public String getAlias() { return alias; }
}