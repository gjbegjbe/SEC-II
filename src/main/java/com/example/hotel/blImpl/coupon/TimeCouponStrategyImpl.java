package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足优惠时间，此为必要条件
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//将优惠时间和入住时间都格式化成年月日形式
        Date checkInDate = sdf.parse(orderVO.getCheckInDate());
        Date startTime = sdf.parse(coupon.getStartTime().toString().substring(0,10));//优惠时间原本格式为年月日时分秒
        Date endTime = sdf.parse(coupon.getEndTime().toString().substring(0,10));
        try{
            if(checkInDate.getTime()>=startTime.getTime()&&checkInDate.getTime()<=endTime.getTime()){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
