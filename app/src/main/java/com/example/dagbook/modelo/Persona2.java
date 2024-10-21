package com.example.dagbook.modelo;

public class Persona2 {
    private String name,phone,address,mail;

    public Persona2() {
    }

    public Persona2(String name, String phone, String address, String mail) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
