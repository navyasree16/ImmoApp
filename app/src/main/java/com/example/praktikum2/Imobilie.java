package com.example.praktikum2;

public class Imobilie {

    private String ID;
    private String imobilien_name;
    private String standort;
    private String flaeche;
    private String preis;
    private String zimmer;
    private String beschreibung;
    private String image;
    private String konatkt;



    public Imobilie(){
    }

    public Imobilie(String ID, String imobilien_name, String standort, String flaeche, String preis, String zimmer, String beschreibung, String image, String konatkt){

        this.ID = ID;
        this.imobilien_name = imobilien_name;
        this.standort = standort;
        this.flaeche = flaeche;
        this.preis = preis;
        this.zimmer = zimmer;
        this.beschreibung = beschreibung;
        this.image = image;
        this.konatkt = konatkt;
    }

    public String getKonatkt() {
        return konatkt;
    }

    public void setKonatkt(String konatkt) {
        this.konatkt = konatkt;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImobilien_name() {
        return imobilien_name;
    }

    public String getStandort() {
        return standort;
    }

    public String getFlaeche() {
        return flaeche;
    }

    public String getPreis() {
        return preis;
    }

    public String getZimmer() {
        return zimmer;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getImage() {
        return image;
    }

    public void setImobilien_name(String imobilien_name) {
        this.imobilien_name = imobilien_name;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public void setFlaeche(String flaeche) {
        this.flaeche = flaeche;
    }

    public void setPreis(String preis) {
        this.preis = preis;
    }

    public void setZimmer(String zimmer) {
        this.zimmer = zimmer;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
