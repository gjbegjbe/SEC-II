package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(@Param("userid") int userid);

    int annulOrder(@Param("orderid") int orderid);

    Order getOrderById(@Param("orderid") int orderid);

    List<Order> getHotelOrders(@Param("hotelId") Integer hotelId);

    List<Order> getHotelAbOrders(@Param("hotelId") Integer hotelId);


    int checkIn(@Param("id") int id);

    int checkOut(@Param("id") int id);

    int updateOutTime(@Param("id") int id,@Param("checkOutDate") String outdate);

    int updateToday(@Param("today")String today);

    int getValue(@Param("id")int id);

    int getUserId(@Param("id")int id);

    List<Integer> getLateUserId(@Param("today")String today);

    List<Double> getLateCredit(@Param("today")String today);

    int updateCheckout(@Param("id")int id,@Param("checkOutDate")String checkOutDate,@Param("checkInDate")String checkInDate);

}
