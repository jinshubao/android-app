package com.example.jinshubao.myapplication.model;

import java.util.List;

public class ImageModel {

    private Boolean error;

    private List<ImageVo> results;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<ImageVo> getResults() {
        return results;
    }

    public void setResults(List<ImageVo> results) {
        this.results = results;
    }

    public static class ImageVo {
        private String desc;//"3-21",
        private String publishedAt;//"2017-03-21T12:19:46.895Z",
        private String url;//"http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-21-17268102_763630507137257_3620762734536163328_n%20-1-.jpg",
        private String who;//"dmj"

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}

