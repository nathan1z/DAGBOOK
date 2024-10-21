package com.example.dagbook.modelo;

public class imageModel {
    private String imageurl;
    public  imageModel(){
    }

    public imageModel(String imagenurl) {
        this.imageurl = imagenurl;
    }

    public String getImagenurl() {
        return imageurl;
    }

    public void setImagenurl(String imagenurl) {
        this.imageurl = imagenurl;
    }
}
