package com.sro.socialbird.model;

public class Post {
    String postText;
    String postImgURL;
    String senderID;
    String senderName;
    String postID;
    int like;
    int comments;

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }


    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    long time;

    Post() {
    }

    public Post(String postText, String senderID, long time) {
        this.postText = postText;
        this.senderID = senderID;
        this.time = time;

    }

    public Post(String postText, String postImgURL, String senderID, long time) {
        this.postText = postText;
        this.postImgURL = postImgURL;
        this.senderID = senderID;
        this.time = time;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImgURL() {
        return postImgURL;
    }

    public void setPostImgURL(String postImgURL) {
        this.postImgURL = postImgURL;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
