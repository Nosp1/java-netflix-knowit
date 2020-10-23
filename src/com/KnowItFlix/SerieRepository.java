package com.KnowItFlix;

import static com.KnowItFlix.SerieType.valueOf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SerieRepository {
    AtomicInteger atomicInteger = new AtomicInteger();
    private List<Serie> serier;
    private List<Kunde> kunder;
    private HashMap<SerieType, Serie> serietypeMap;

    public SerieRepository() {
        this.serier = new ArrayList<>();
        this.serietypeMap = new HashMap<>();
        this.kunder = new ArrayList<>();
    }

    public Kunde hentKunde(Integer id) {
        return getFirst(id)
            .get();
    }

    public List<Kunde> hentKunderSomLikerSerie(Integer id) {
        Serie show = serier.stream()
            .filter(serie -> serie.getSerieId()
                .equals(id))
            .findFirst()
            .get();
        return kunder.stream()
            .filter(kunde ->
                kunde.getInterestedIn()
                    .stream()
                    .anyMatch(serieType ->
                        show.getType()
                            .contains(serieType)
                    ))
            .collect(Collectors.toList());
    }

    public List<Serie> hentSerierMedSammeType(SerieType serieType) {
        return serier.stream()
            .sorted(Comparator.comparing(Serie::getTitle))
            .filter(k -> k.getType()
                .contains(serieType))
            .collect(Collectors.toList());
    }

    public Integer lagNySerie(String serieNavn, SerieType... serieTyper) {
        Integer id = atomicInteger.incrementAndGet();
        Serie serie = new Serie(serieNavn, serieTyper);
        serie.setSerieId(id);
        this.serier.add(serie);
        serie.getType()
            .forEach((type) -> serietypeMap.put(type, serie));
        return id;
    }

    public Integer leggTilNyKunde(String navn, LocalDate date) {
        Integer id = atomicInteger.incrementAndGet();
        kunder.add(new Kunde(id, navn, date));
        return id;
    }

    public void leggTilTypeForKunde(Integer id, SerieType... serieType) {
        getFirst(id)
            .ifPresent(kunde -> kunde.getInterestedIn()
                .addAll(
                    Arrays.asList(serieType)));
    }

    public void fjernTypeTilKunde(Integer id, SerieType... serieType) {
        getFirst(id)
            .ifPresent(kunde -> kunde.getInterestedIn()
                .removeAll(Arrays.asList(serieType)));
    }

    private Optional<Kunde> getFirst(Integer id) {
        return kunder.stream()
            .filter(kunde -> kunde.getId()
                .equals(id))
            .findFirst();
    }

    public void leggTilType(String serienavn, SerieType... serieType) {
        serier
            .stream()
            .filter(serie ->
                serie.getTitle()
                    .equals(serienavn))
            .findFirst()
            .ifPresent(serie ->
                serie.getType()
                    .addAll(Arrays.asList((serieType))));
    }

    public void fjernSerieType(String serienavn, String serieType) {

        serier.stream()
            .filter(serie -> serie.getTitle()
                .equals(serienavn))
            .findFirst()
            .ifPresent(serie -> serie.getType()
                .removeIf(type -> type.equals(valueOf(serieType))));

    }

    public List<Serie> getSerier() {
        return serier;
    }

    public void setSerier(List<Serie> serier) {
        this.serier = serier;
    }

    public HashMap<SerieType, Serie> getSerietypeMap() {
        return serietypeMap;
    }

    public void setSerietypeMap(HashMap<SerieType, Serie> serietypeMap) {
        this.serietypeMap = serietypeMap;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    public List<Kunde> getKunder() {
        return kunder;
    }

    public void setKunder(List<Kunde> kunder) {
        this.kunder = kunder;
    }

}
