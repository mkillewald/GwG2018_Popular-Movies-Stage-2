package com.udacity.popularmovies.model;

import java.util.List;

public class PosterList {

    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public List<Poster> results = null;

    public List<Poster> getResults() {
        return results;
    }

}
