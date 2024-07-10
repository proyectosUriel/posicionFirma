package com.posicionCaja.posicionCaja.model;

public class datosRecibir {
    private String docpdf;
    private String textoBuscar;
    private String posicionTexto;
    private int padding;
    private int altura;
    private int anchura;

    // Constructor por defecto
    public datosRecibir() {
    }

    // Constructor
    public datosRecibir(String docpdf, String textoBuscar, String posicionTexto, int padding, int altura, int anchura) {
        this.docpdf = docpdf;
        this.textoBuscar = textoBuscar;
        this.posicionTexto = posicionTexto;
        this.padding = padding;
        this.altura = altura;
        this.anchura = anchura;
    }

    // Getters & Setters
    public String getDocpdf() {
        return docpdf;
    }

    public void setDocpdf(String docpdf) {
        this.docpdf = docpdf;
    }

    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }

    public String getPosicionTexto() {
        return posicionTexto;
    }

    public void setPosicionTexto(String posicionTexto) {
        this.posicionTexto = posicionTexto;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }

}
