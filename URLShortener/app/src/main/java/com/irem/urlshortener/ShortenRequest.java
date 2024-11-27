package com.irem.urlshortener;


public class ShortenRequest {

    private String long_url;

    public ShortenRequest(String longUrl) {
        this.long_url = longUrl;
    }

    public String getLongUrl() {
        return long_url;
    }

    public void setLongUrl(String longUrl) {
        this.long_url = longUrl;
    }
}