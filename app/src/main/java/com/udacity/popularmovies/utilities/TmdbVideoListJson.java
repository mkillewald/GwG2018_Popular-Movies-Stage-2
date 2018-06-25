package com.udacity.popularmovies.utilities;

import com.google.gson.Gson;
import com.udacity.popularmovies.model.Video;
import com.udacity.popularmovies.model.VideoList;

import java.util.ArrayList;
import java.util.List;

public class TmdbVideoListJson {
    List<Video> mVideos;

    public TmdbVideoListJson() {
        mVideos = new ArrayList<>();
    }

    public static List<Video> parse(String json) {
        Gson gson = new Gson();
        VideoList videoList = gson.fromJson(json, VideoList.class);

        return videoList.getResults();
    }
}
