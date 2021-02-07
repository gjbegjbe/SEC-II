package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;

import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String UPDATE_ERROR = "修改失败";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    RoomMapper roomMapper ;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        try{
            Order annul=orderMapper.getOrderById(orderid);
            Integer curNum=annul.getRoomNum();
            curNum=-1*curNum;
            Integer hotelId=annul.getHotelId();
            String roomType=annul.getRoomType();
            hotelService.updateRoomInfo(hotelId,roomType,curNum);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curTime = new Date();//获取当前时间
            Date latestTime = sdf.parse(annul.getCheckInDate()+" 23:59:59");//获取最晚入住时间
            System.out.println(curTime);
            System.out.println(latestTime);
            System.out.println(latestTime.getTime());
            System.out.println(curTime.getTime());
            if(latestTime.getTime()-curTime.getTime()<21600000){//时间差小于六小时
                double credit=annul.getPrice()/2;
                System.out.println(credit);
                accountService.minusCredit(annul.getUserId(),credit);
            }
            orderMapper.annulOrder(orderid);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseVO.buildSuccess(true);

    }

    @Override
    public List<Order> getHotelAllOrders(Integer hotelId){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String today = sf.format(date);
        List<Integer> LateId=orderMapper.getLateUserId(today);
        List<Double> LateCredit=orderMapper.getLateCredit(today);
        for(int i=0;i<LateId.size();i++){
            int userId=LateId.get(i);
            double credit=LateCredit.get(i);
            accountMapper.minusCredit(userId,credit);

        }
        orderMapper.updateToday(today);



        System.out.println(hotelId);
        return orderMapper.getHotelOrders(hotelId);
    }

    @Override
    public List<Order> getHotelAbOrders(Integer hotelId){
        System.out.println(hotelId);
        return orderMapper.getHotelAbOrders(hotelId);
    }

    @Override
    public ResponseVO checkIn(int id){
        Order order=orderMapper.getOrderById(id);
        double creditValue=order.getPrice();
        int userId=order.getUserId();
        accountMapper.updateCredit(userId,creditValue);

        orderMapper.checkIn(id);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO checkOut(int id){
        try{
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String outdate = sf.format(date);
            orderMapper.updateOutTime(id,outdate);

            Order out=orderMapper.getOrderById(id);
            Integer curNum=out.getRoomNum();
            curNum=-1*curNum;
            Integer hotelId=out.getHotelId();
            String roomType=out.getRoomType();
            hotelService.updateRoomInfo(hotelId,roomType,curNum);
            orderMapper.checkOut(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseVO.buildSuccess(true);
    }

    /**
     * 预计退房时间
     * @param id
     * @return
     * @author gjbe
     */
    @Override
    public ResponseVO updateCheckout(int id,String checkOutDate){
        Order order=orderMapper.getOrderById(id);
        double creditValue=order.getPrice();
        int userId=order.getUserId();
        accountMapper.updateCredit(userId,creditValue);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String checkInDate = sf.format(date);
        try {
            orderMapper.updateCheckout(id,checkOutDate,checkInDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    };
}
