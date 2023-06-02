package com.example.blogkulinarnymobileapp.Models;

public class User {

    public User() {
    }

    public User(int id, String login, String password, String mail, boolean isAccepted, Ranks rank, String imageURL) {
        Id = id;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.isAccepted = isAccepted;
        this.rank = rank;
        this.imageURL = imageURL;
    }

    public int Id;

    public String login;

    public String password;

    public String mail;

    public boolean isAccepted;

    public Ranks rank;

    public String imageURL;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogin() {
        return login;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Ranks getRank() {
        return rank;
    }

    public void setRank(Ranks rank) {
        this.rank = rank;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getVerificationToken() {
        return VerificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        VerificationToken = verificationToken;
    }

    public String getPasswordResetToken() {
        return PasswordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        PasswordResetToken = passwordResetToken;
    }

    // mailer
    public String VerificationToken;

    //public DateTime VerifiedAt;

    public String PasswordResetToken;

    public void setlogin(String login) {
        this.login = login;
    }

    //public DateTime ResetTokenExpires;

    //Relationships
}
