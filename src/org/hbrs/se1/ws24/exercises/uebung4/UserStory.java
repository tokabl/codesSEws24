package org.hbrs.se1.ws24.exercises.uebung4;

import java.io.Serializable;

public class UserStory implements Comparable<UserStory>, Serializable {

    private int id;
    private String titel;
    private String Akzeptanzkriterium;
    private int aufwand;
    private int risiko;
    private int mehrwert;
    private double prio;
    private int strafe;
    private String Projekt;

    public UserStory(int id, String titel, String Akzeptanzkriterium, int aufwand, int risiko, int mehrwert,double prio , int strafe, String Projekt) {
        this.id = id;
        if (titel == null || titel.trim().isEmpty()) {
            throw new IllegalArgumentException("Titel darf nicht null oder leer sein");
        }
        this.titel = titel;
        if ( Akzeptanzkriterium == null || Akzeptanzkriterium.trim().isEmpty()) {
            throw new IllegalArgumentException("Akzeptanzkriterium kann nicht null sein");
        }
        this.Akzeptanzkriterium = Akzeptanzkriterium;
        this.aufwand = aufwand;
        this.risiko = risiko;
        this.mehrwert = mehrwert;
        this.prio = prio;
        this.strafe = strafe;
        this.Projekt = Projekt;
    }


    public int getId() {
        return id;
    }


    public String getTitel() {
        return titel;
    }


    public void setTitel(String titel) {
        if (titel == null || titel.trim().isEmpty()) {
            throw new IllegalArgumentException("Titel darf nicht null oder leer sein");
        }
        this.titel = titel;
    }


    public String getAkzeptanzkriterium() {
        return Akzeptanzkriterium;
    }


    public void setAkzeptanzkriterium(String Akzeptanzkriterium) {
        this.Akzeptanzkriterium = Akzeptanzkriterium;
    }


    public int getRisiko() {
        return risiko;

    }


    public void setRisiko(int risiko) {
        if (risiko < 0 ) {
            throw new IllegalArgumentException("Risiko kann nicht negativ sein");
        }
        this.risiko = risiko;
    }



    public int getAufwand() {
        return aufwand;
    }


    public void setAufwand(int aufwand) {
        if (aufwand < 0) {
            throw new IllegalArgumentException("Aufwand kann nicht negativ sein");
        }
        this.aufwand = aufwand;
    }


    public int getMehrwert() {
        return mehrwert;
    }


    public void setMehrwert(int mehrwert) {
        if (mehrwert < 0) {
            throw new IllegalArgumentException("Mehrwert kann nicht negativ sein");
        }
        this.mehrwert = mehrwert;
    }


    public double getPrio() {
        return prio;
    }


    public void setPrio(double prio) {
        if (prio < 0) {
            throw new IllegalArgumentException("Prio kann nicht negativ sein");
        }
        this.prio = prio;
    }


    public int getStrafe() {
        return strafe;
    }


    public void setStrafe(int strafe) {
        if (strafe < 0) {
            throw new IllegalArgumentException("Strafe kann nicht negativ sein");
        }
        this.strafe = strafe;
    }


    public String getProjekt() {
        return Projekt;
    }


    public void setProjekt(String Projekt) {
        this.Projekt = Projekt;
    }

    private void calculatePrio() {
        this.prio = (double) (mehrwert + strafe) / (aufwand + risiko);
    }


    @Override
    public int compareTo(UserStory other) {
        return Double.compare(this.prio, other.prio);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Titel: " + titel +
                ", Akzeptanzkriterium: " + Akzeptanzkriterium+
                ", Aufwand: " + aufwand +
                ", Risiko: " + risiko +
                ", Mehrwert: " + mehrwert +
                ", Prio: " + prio +
                ", Strafe: " + strafe +
                ", Projekt: " + Projekt;
    }














}
