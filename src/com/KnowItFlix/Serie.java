package com.KnowItFlix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Serie {
    private Integer serieId;
    private String title;
    private List<SerieType> type;

    public Serie( String title, SerieType... type) {
        this.title = title;
        this.type = new ArrayList<>();
        this.type.addAll(Arrays.asList(type));
    }


    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SerieType> getType() {
        return type;
    }

    public void setType(List<SerieType> type) {
        this.type = type;
    }
}