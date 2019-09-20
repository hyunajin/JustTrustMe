package com.example.asus.justtrustme;

public class Contact {
    private int contact_no;
    private String contact_number;
    private String contact_name;
    private int mem_no;

    @Override
    public String toString() {
        return "Contact{" +
                "contact_no=" + contact_no +
                ", contact_number='" + contact_number + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", mem_no=" + mem_no +
                '}';
    }

    public int getContact_no() {
        return contact_no;
    }

    public void setContact_no(int contact_no) {
        this.contact_no = contact_no;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public int getMem_no() {
        return mem_no;
    }

    public void setMem_no(int mem_no) {
        this.mem_no = mem_no;
    }
}
