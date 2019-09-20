package com.example.asus.justtrustme;

public class ListViewItem2 {
    private String str_bookmark_start_lat;
    private String str_bookmark_start_lon;
    private String str_bookmark_destination_lat;
    private String str_bookmark_destination_lon;
    private String str_bookmark_start_addr;
    private String str_bookmark_destination_addr;

    public ListViewItem2(String str_bookmark_start_lat, String str_bookmark_start_lon, String str_bookmark_destination_lat, String str_bookmark_destination_lon, String str_bookmark_start_addr, String str_bookmark_destination_addr) {
        this.str_bookmark_start_lat = str_bookmark_start_lat;
        this.str_bookmark_start_lon = str_bookmark_start_lon;
        this.str_bookmark_destination_lat = str_bookmark_destination_lat;
        this.str_bookmark_destination_lon = str_bookmark_destination_lon;
        this.str_bookmark_start_addr = str_bookmark_start_addr;
        this.str_bookmark_destination_addr = str_bookmark_destination_addr;
    }

    public ListViewItem2(String str_bookmark_start_addr, String str_bookmark_destination_addr) {
        this.str_bookmark_start_addr = str_bookmark_start_addr;
        this.str_bookmark_destination_addr = str_bookmark_destination_addr;
    }

    public String getStr_bookmark_start_lat() {
        return str_bookmark_start_lat;
    }

    public String getStr_bookmark_start_lon() {
        return str_bookmark_start_lon;
    }

    public String getStr_bookmark_destination_lat() {
        return str_bookmark_destination_lat;
    }

    public String getStr_bookmark_destination_lon() {
        return str_bookmark_destination_lon;
    }

    public String getStr_bookmark_start_addr() {
        return str_bookmark_start_addr;
    }

    public String getStr_bookmark_destination_addr() {
        return str_bookmark_destination_addr;
    }
}
