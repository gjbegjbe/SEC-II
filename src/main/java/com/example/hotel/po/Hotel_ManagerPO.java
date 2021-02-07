package com.example.hotel.po;

public class Hotel_ManagerPO {
    private Integer id;

    private String hotelName;

    private Integer manager_Id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelname) {
        this.hotelName = hotelname;
    }

    public Integer getManagerId() {
        return manager_Id;
    }

    public void setManagerId(Integer managerId) {
        this.manager_Id = managerId;
    }
}
