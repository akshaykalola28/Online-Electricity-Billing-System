package example.akshay.onlinebillingsystem;

import java.io.Serializable;

public class Customer implements Serializable {

    public String name, email, username, password, mo_no;
    public int c_no, meter_no;

    public Customer(){}

    public Customer(String name, String email, String username, String password, String mo_no, int c_no, int meter_no){
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.mo_no = mo_no;
        this.c_no = c_no;
        this.meter_no = meter_no;
    }
}
