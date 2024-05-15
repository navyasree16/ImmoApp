package com.example.praktikum2;


public class User {


    public String vor_name;
    public String nach_name;
    public String email;
    public String telefon_nr;

    public User(){

    }


    public User(String vor_name, String nach_name, String email, String telefon_nr){

        this.vor_name = vor_name;
        this.nach_name = nach_name;
        this.email = email;
        this.telefon_nr = telefon_nr;
    }



    public String getVor_name() {
        return vor_name;
    }

    public String getNach_name() {
        return nach_name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon_nr() {
        return telefon_nr;
    }

    public void setVor_name(String vor_name) {
        this.vor_name = vor_name;
    }

    public void setNach_name(String nach_name) {
        this.nach_name = nach_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon_nr(String telefon_nr) {
        this.telefon_nr = telefon_nr;
    }



}

