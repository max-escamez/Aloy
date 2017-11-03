package com.aloy.aloy.Models;

/**
 * Created by tldonne on 02/11/2017.
 */

public class SearchResult {

    private String coverUrl;
    private String primaryText;
    private String secondaryText;

    public SearchResult(String coverUrl, String primaryText, String secondaryText) {
        this.coverUrl=coverUrl;
        this.primaryText=primaryText;
        this.secondaryText=secondaryText;

    }

    public String getPrimaryText() {
        return primaryText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
