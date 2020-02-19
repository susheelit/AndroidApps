package com.aob.aobsalesman.activities.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review_Data implements Serializable
{

    @SerializedName("date")
    private String date;

    @SerializedName("review_id")
    private String review_id;

    @SerializedName("comments")
    private String comments;

    @SerializedName("project_id")
    private String project_id;

    @SerializedName("name")
    private String name;

    @SerializedName("time")
    private String time;

    @SerializedName("email")
    private String email;

    @SerializedName("review_status")
    private String review_status;

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getReview_id ()
    {
        return review_id;
    }

    public void setReview_id (String review_id)
    {
        this.review_id = review_id;
    }

    public String getComments ()
    {
        return comments;
    }

    public void setComments (String comments)
    {
        this.comments = comments;
    }

    public String getProject_id ()
    {
        return project_id;
    }

    public void setProject_id (String project_id)
    {
        this.project_id = project_id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getReview_status ()
    {
        return review_status;
    }

    public void setReview_status (String email)
    {
        this.review_status = review_status;
    }

}
