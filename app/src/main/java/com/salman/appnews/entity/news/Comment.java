/*
 * *
 *  * Created by Salman Saleem on 6/21/18 1:44 AM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 6/21/18 1:44 AM
 *
 */

package com.salman.appnews.entity.news;

/**
 * Created by Salman Saleem on 01/03/2017.
 */



        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

