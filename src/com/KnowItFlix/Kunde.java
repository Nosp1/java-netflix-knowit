package com.KnowItFlix;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Kunde {
    Integer id;
    String navn;
    LocalDate date;
    List<SerieType> interestedIn;

    public Kunde(Integer id, String navn, LocalDate date) {
        this.id = id;
        this.navn = navn;
        this.date = date;
        this.interestedIn = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<SerieType> getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(List<SerieType> interestedIn) {
        this.interestedIn = interestedIn;
    }
}
