package example.akshay.onlinebillingsystem;

import java.io.Serializable;

public class User implements Serializable {

    public String name;
    public String email;
    public String username;
    public String password;
    public String mo_no;

    public User(){}

    public User(String name, String email, String username, String password, String mo_no){
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.mo_no = mo_no;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
