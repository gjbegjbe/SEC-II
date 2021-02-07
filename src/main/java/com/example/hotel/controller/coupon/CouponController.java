package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {

        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);

        System.out.print(hotelTargetMoneyCouponVO.getStartTime());

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelMultiplyRooms")
    public ResponseVO addHotelMultiplyRoomsCoupon(@RequestBody HotelMultiplyRoomsCouponVO hotelMultiplyRoomsCouponVO) {

        CouponVO couponVO = couponService.addHotelMultiplyRoomsCoupon(hotelMultiplyRoomsCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }
    @PostMapping("/hotelFestival")
    public ResponseVO addHotelFestivalCoupon(@RequestBody HotelFestivalCouponVO hotelFestivalCouponVO) {

        CouponVO couponVO = couponService.addHotelFestivalCoupon(hotelFestivalCouponVO);
        return ResponseVO.buildSuccess(couponVO);

    }



    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        System.out.print("coupon!");
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) throws ParseException {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


}
