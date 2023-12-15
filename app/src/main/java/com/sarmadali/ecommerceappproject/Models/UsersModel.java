package com.sarmadali.ecommerceappproject.Models;

public class UsersModel {

    String profilePic, userName, eMail, passWord, confirmPassword, userId;

    //constructor

    public UsersModel(String profilePic, String userName, String eMail, String passWord, String confirmPassword, String userId) {

        this.profilePic = profilePic;
        this.userName = userName;
        this.eMail = eMail;
        this.passWord = passWord;
        this.confirmPassword = confirmPassword;
        this.userId = userId;
    }
    //registration construction

    public UsersModel(String profilePic, String userName, String eMail, String passWord, String confirmPassword) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.eMail = eMail;
        this.passWord = passWord;
        this.confirmPassword = confirmPassword;
    }


    //getter and setter

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
