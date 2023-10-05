package com.example.cloudcounselage;

public class UserHelperClass {
    String Name , email, User_Name, password;

    public UserHelperClass(String val_Name, String val_email, String val_User_Name) {
    }

    public UserHelperClass(String name, String email, String user_Name, String password) {
        Name = name;
        this.email = email;
        User_Name = user_Name;
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
