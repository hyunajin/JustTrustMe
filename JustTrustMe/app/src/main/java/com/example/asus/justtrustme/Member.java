package com.example.asus.justtrustme;

public class Member {
    public int mem_no;
    public String mem_name;
    public String mem_email;

    @Override
    public String toString() {
        return "Member{" +
                "mem_no=" + mem_no +
                ", mem_name='" + mem_name + '\'' +
                ", mem_email='" + mem_email + '\'' +
                '}';
    }

    public int getMem_no() {
        return mem_no;
    }

    public void setMem_no(int mem_no) {
        this.mem_no = mem_no;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }
}
