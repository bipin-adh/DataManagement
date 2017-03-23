package com.example.datamanagement.model;

/**
 * Created by b1p1n on 1/26/2017.
 */
public class Contact {

    String name,email,password;

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }



    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return this.email;
    }



    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return this.password;
    }


    public Contact () {

    }
}
