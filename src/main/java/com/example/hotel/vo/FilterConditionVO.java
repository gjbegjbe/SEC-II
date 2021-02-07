package com.example.hotel.vo;

import com.example.hotel.enums.RoomType;

import java.util.ArrayList;
import java.util.List;

public class FilterConditionVO {
    private String hotelName = null;
    private List<String> roomTypes = null;
    private List<String> hotelStars = null;
    private Double startRate = 0.0;
    private Double endRate = 5.0;
    private Double startPrice = 0.0;
    private Double endPrice = 1000000.0;
    private Integer roomNum = 1;

    public String getHotelName(){
        return hotelName;
    }

    public void setHotelName(String hotelName){
        this.hotelName = hotelName;
    }

    public List<String> getRoomTypes(){
        return roomTypes;
    }

    public void setRoomTypes(ArrayList<String> roomTypes){
        this.roomTypes = roomTypes;
    }

    public List<String> getHotelStars() {
        return hotelStars;
    }

    public void setHotelStars(ArrayList<String> hotelStars) {
        this.hotelStars = hotelStars;
    }

    public Double getStartRate() {
        return startRate;
    }

    public void setStartRate(Double startRate) {
        this.startRate = startRate;
    }

    public Double getEndRate() {
        return endRate;
    }

    public void setEndRate(Double endRate) {
        this.endRate = endRate;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }
}
