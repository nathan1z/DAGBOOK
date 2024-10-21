package com.example.dagbook.modelo;

import java.io.Serializable;

public class Contrato implements Serializable {
    private Persona titular;
    private String ciTitular;
    private String ciImgUri;
    private String contratoImgUri;

    public Contrato() {
    }

    public Contrato(Persona titular, String ciTitular, String ciImgUri, String contratoImgUri) {
        this.titular = titular;
        this.ciTitular = ciTitular;
        this.ciImgUri = ciImgUri;
        this.contratoImgUri = contratoImgUri;
    }

    public Persona getTitular() {
        return titular;
    }

    public void setTitular(Persona titular) {
        this.titular = titular;
    }

    public String getCiTitular() {
        return ciTitular;
    }

    public void setCiTitular(String ciTitular) {
        this.ciTitular = ciTitular;
    }

    public String getCiImgUri() {
        return ciImgUri;
    }

    public void setCiImgUri(String ciImgUri) {
        this.ciImgUri = ciImgUri;
    }

    public String getContratoImgUri() {
        return contratoImgUri;
    }

    public void setContratoImgUri(String contratoImgUri) {
        this.contratoImgUri = contratoImgUri;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "titular=" + titular +
                ", ciTitular='" + ciTitular + '\'' +
                ", ciImgUri='" + ciImgUri + '\'' +
                ", contratoImgUri='" + contratoImgUri + '\'' +
                '}';
    }
}
