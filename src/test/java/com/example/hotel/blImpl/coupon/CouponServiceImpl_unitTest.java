package com.example.hotel.blImpl.coupon;

import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import com.example.hotel.vo.OrderVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CouponServiceImpl_unitTest {
    @Autowired
    private TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    @Autowired
    private TimeCouponStrategyImpl timeCouponStrategy;

    @Autowired
    private TargetRoomCouponStrategyImpl targetRoomCouponStrategy;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponServiceImpl couponService=new CouponServiceImpl(targetMoneyCouponStrategy,timeCouponStrategy, targetRoomCouponStrategy, couponMapper);

    @Test
    @Transactional
    @Rollback
    public void getMatchOrderCoupon() {
        OrderVO orderVO=new OrderVO();
        orderVO.setHotelId(2);
        assertNotNull(couponService.getHotelAllCoupon(2));

    }

    @Test
    @Transactional
    @Rollback
    public void getHotelAllCoupon() {
        assertNotNull(couponService.getHotelAllCoupon(2));
    }

    @Test
    public void addHotelTargetMoneyCoupon() {
        HotelTargetMoneyCouponVO coupon = new HotelTargetMoneyCouponVO();
        coupon.setDescription("12");
        coupon.setTargetMoney(100);
        coupon.setHotelId(2);
        coupon.setDiscountMoney(2);
        coupon.setName("2222");
        coupon.setType(1);
        LocalDateTime startTime;
        LocalDateTime endTime;
        startTime=LocalDateTime.of(2020, 9, 22, 15, 40, 00);
        endTime=LocalDateTime.of(2020, 9, 24, 15, 40, 00);
        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);
        coupon.setStatus(1);
        assertNotNull(couponService.addHotelTargetMoneyCoupon(coupon));

    }
}