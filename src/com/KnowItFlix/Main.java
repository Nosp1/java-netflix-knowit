package com.KnowItFlix;

import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("hello");

        Serie serie = new Serie("TrueCrime", SerieType.Animasjon, SerieType.Drama);
        SerieRepository repo = new SerieRepository();
        repo.lagNySerie("Crimpodden", SerieType.Drama,SerieType.Animasjon);
        List<SerieType> type = repo.getSerier().get(0).getType();
        type.forEach(System.out::println);
        repo.fjernSerieType("Crimpodden", "Animasjon");

        System.out.println(repo.getSerier().get(0).getType().get(0));
    }
}
