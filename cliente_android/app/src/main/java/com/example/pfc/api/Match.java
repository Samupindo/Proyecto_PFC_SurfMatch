package com.example.pfc.api;
public class Match {
    private String alias_sesion;
    private String fecha_hora_match;
    private double altura_real;
    private int periodo_real;
    private String leido_en_zona;

    // Getters
    public String getAlias_sesion() { return alias_sesion; }
    public String getFecha_hora_match() { return fecha_hora_match; }
    public double getAltura_real() { return altura_real; }
    public int getPeriodo_real() { return periodo_real; }
    public String getLeido_en_zona() { return leido_en_zona; }
}