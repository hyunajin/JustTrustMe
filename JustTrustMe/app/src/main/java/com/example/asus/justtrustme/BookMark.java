package com.example.asus.justtrustme;

public class Bookmark {
    private int bookmark_no;
    private String bookmark_start_lat;
    private String bookmark_start_lon;
    private String bookmark_destination_lat;
    private String bookmark_destination_lon;
    private int bookmark_guidance_cat;
    private String bookmark_start_addr;
    private String bookmark_destination_addr;
    private int mem_no;

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmark_no=" + bookmark_no +
                ", bookmark_start_lat='" + bookmark_start_lat + '\'' +
                ", bookmark_start_lon='" + bookmark_start_lon + '\'' +
                ", bookmark_destination_lat='" + bookmark_destination_lat + '\'' +
                ", bookmark_destination_lon='" + bookmark_destination_lon + '\'' +
                ", bookmark_guidance_cat=" + bookmark_guidance_cat +
                ", bookmark_start_addr='" + bookmark_start_addr + '\'' +
                ", bookmark_destination_addr='" + bookmark_destination_addr + '\'' +
                ", mem_no=" + mem_no +
                '}';
    }

    public int getBookmark_no() {
        return bookmark_no;
    }

    public void setBookmark_no(int bookmark_no) {
        this.bookmark_no = bookmark_no;
    }

    public String getBookmark_start_lat() {
        return bookmark_start_lat;
    }

    public void setBookmark_start_lat(String bookmark_start_lat) {
        this.bookmark_start_lat = bookmark_start_lat;
    }

    public String getBookmark_start_lon() {
        return bookmark_start_lon;
    }

    public void setBookmark_start_lon(String bookmark_start_lon) {
        this.bookmark_start_lon = bookmark_start_lon;
    }

    public String getBookmark_destination_lat() {
        return bookmark_destination_lat;
    }

    public void setBookmark_destination_lat(String bookmark_destination_lat) {
        this.bookmark_destination_lat = bookmark_destination_lat;
    }

    public String getBookmark_destination_lon() {
        return bookmark_destination_lon;
    }

    public void setBookmark_destination_lon(String bookmark_destination_lon) {
        this.bookmark_destination_lon = bookmark_destination_lon;
    }

    public int getBookmark_guidance_cat() {
        return bookmark_guidance_cat;
    }

    public void setBookmark_guidance_cat(int bookmark_guidance_cat) {
        this.bookmark_guidance_cat = bookmark_guidance_cat;
    }

    public String getBookmark_start_addr() {
        return bookmark_start_addr;
    }

    public void setBookmark_start_addr(String bookmark_start_addr) {
        this.bookmark_start_addr = bookmark_start_addr;
    }

    public String getBookmark_destination_addr() {
        return bookmark_destination_addr;
    }

    public void setBookmark_destination_addr(String bookmark_destination_addr) {
        this.bookmark_destination_addr = bookmark_destination_addr;
    }

    public int getMem_no() {
        return mem_no;
    }

    public void setMem_no(int mem_no) {
        this.mem_no = mem_no;
    }
}
