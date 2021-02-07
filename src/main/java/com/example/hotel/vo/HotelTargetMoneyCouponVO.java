package com.example.hotel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 酒店满减金额优惠 eg 满300减100
 */
public class HotelTargetMoneyCouponVO extends CouponVO {

    private Integer hotelId;

    private Integer targetMoney;

    private Integer discountMoney;

    //新增了start和end


  // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;



   //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Integer targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
