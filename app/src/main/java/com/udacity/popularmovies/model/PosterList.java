package com.udacity.popularmovies.model;

import java.util.List;

public class PosterList {

    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public List<Poster> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Poster> getResults() {
        return results;
    }

    public void setResults(List<Poster> results) {
        this.results = results;
    }
}
