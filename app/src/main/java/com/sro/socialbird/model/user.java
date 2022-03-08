package com.sro.socialbird.model;

public class user {
    String Id;
    String Name;
    String Email;
    String Phone;
    String Password;
    String ImageURL;


    user() {
    }

    public user(String id, String name, String email, String phone, String password, String imageURL) {
        Id = id;
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
        ImageURL = imageURL;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
