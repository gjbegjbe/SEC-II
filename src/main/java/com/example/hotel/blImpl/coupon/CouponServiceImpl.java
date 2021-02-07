package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {


    private final  TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    private final  TimeCouponStrategyImpl timeCouponStrategy;
    private final  TargetRoomCouponStrategyImpl targetRoomCouponStrategy;
    private final CouponMapper couponMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             TargetRoomCouponStrategyImpl targetRoomCouponStrategy, CouponMapper couponMapper) {
        this.targetRoomCouponStrategy = targetRoomCouponStrategy;
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(targetRoomCouponStrategy);
    }




    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) throws ParseException {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());//获取该酒店的所有优惠券

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {//遍历酒店所有优惠券
            for (CouponMatchStrategy strategy : strategyList) {//遍历三种判断逻辑
                if (this.timeCouponStrategy.isMatch(orderVO, hotelCoupons.get(i))&&strategy.isMatch(orderVO, hotelCoupons.get(i))) {//满足条件
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }

        List<Coupon> webCoupons = getHotelAllCoupon(-1);//获取网站的所有优惠券

        for (int i = 0; i < webCoupons.size(); i++) {//遍历网站所有优惠券
                if (this.timeCouponStrategy.isMatch(orderVO, webCoupons.get(i))&&(webCoupons.get(i).getCouponType()==4 || this.targetRoomCouponStrategy.isMatch(orderVO, webCoupons.get(i)))) {//满足条件
                    availAbleCoupons.add(webCoupons.get(i));
                }

        }

        return availAbleCoupons;
    }


    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }


    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStartTime(couponVO.getStartTime());
        coupon.setEndTime(couponVO.getEndTime());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }
    public CouponVO addHotelMultiplyRoomsCoupon(HotelMultiplyRoomsCouponVO couponVO){
        Coupon coupon = new Coupon();
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setRoomNum(couponVO.getRoomNum());
        coupon.setStartTime(couponVO.getStartTime());
        coupon.setEndTime(couponVO.getEndTime());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStatus(1);
        coupon.setCouponType(2);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }
    public CouponVO addHotelFestivalCoupon(HotelFestivalCouponVO couponVO){
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        //这里要转换类型
        coupon.setStartTime(couponVO.getStartTime());
        coupon.setEndTime(couponVO.getEndTime());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStatus(1);
        coupon.setCouponType(4);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }
}