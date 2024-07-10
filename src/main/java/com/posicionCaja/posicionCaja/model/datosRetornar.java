package com.posicionCaja.posicionCaja.model;

public class datosRetornar {
    private int paginaFirma;
    private int[] coordenadas;
    private int[] tamanyo;

    // Constructor por defecto
    public datosRetornar() {
    }

    // Constructor
    public datosRetornar(int paginaFirma, int[] coordenadas, int[] tamanyo) {
        this.paginaFirma = paginaFirma;
        this.coordenadas = coordenadas;
        this.tamanyo = tamanyo;
    }

    // Getters & Setters
    public int getPaginaFirma() {
        return paginaFirma;
    }

    public void setPaginaFirma(int paginaFirma) {
        this.paginaFirma = paginaFirma;
    }

    public int[] getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(int[] coordenadas) {
        this.coordenadas = coordenadas;
    }

    public int[] getTamanyo() {
        return tamanyo;
    }

    public void setTamanyo(int[] tamanyo) {
        this.tamanyo = tamanyo;
    }
    
}
