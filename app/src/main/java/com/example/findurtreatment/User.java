package com.example.findurtreatment;

public class User {
    public String name,details,number,add;
    public User(){

    }
    public User(String name,String details,String add,String number)
    {
        this.name=name;
        this.details=details;
        this.number=number;
        this.add=add;

    }
    public String getAdd(){
        return  add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDetails() {
        return details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

