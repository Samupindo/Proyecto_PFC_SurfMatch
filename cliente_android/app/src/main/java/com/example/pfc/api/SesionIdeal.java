package com.example.pfc.api;

public class SesionIdeal {
    private String alias;
    private String fecha_referencia;

    // Nuestros nuevos campos estrella
    private String tamano;
    private String periodo;

    private String marea;
    private String direccion_viento;
    private String estado_viento;
    private int surfista;

    public SesionIdeal(String alias, String fecha_referencia, String tamano, String periodo,
                       String marea, String direccion_viento, String estado_viento, int surfista) {
        this.alias = alias;
        this.fecha_referencia = fecha_referencia;
        this.tamano = tamano;
        this.periodo = periodo;
        this.marea = marea;
        this.direccion_viento = direccion_viento;
        this.estado_viento = estado_viento;
        this.surfista = surfista;
    }

    // Getters para que Retrofit pueda leer los datos y enviarlos a Django
    public String getAlias() { return alias; }
    public String getFecha_referencia() { return fecha_referencia; }
    public String getTamano() { return tamano; }
    public String getPeriodo() { return periodo; }
    public String getMarea() { return marea; }
    public String getDireccion_viento() { return direccion_viento; }
    public String getEstado_viento() { return estado_viento; }
    public int getSurfista() { return surfista; }
}