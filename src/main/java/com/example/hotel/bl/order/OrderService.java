package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 获得指定宾馆所有订单
     * @hotelId
     * @return
     * @author gjbe
     */
    List<Order> getHotelAllOrders(Integer hotelId);

    /**
     * 获得指定宾馆异常订单
     * @hotelId
     * @return
     * @author gjbe
     */
    List<Order> getHotelAbOrders(Integer hotelId);

    /**
     * 入住
     * @param id
     * @return
     * @author gjbe
     */
    ResponseVO checkIn(int id);

    /**
     * 退房
     * @param id
     * @return
     * @author gjbe
     */
    ResponseVO checkOut(int id);

    /**
     * 预计退房时间
     * @param id
     * @return
     * @author gjbe
     */
    ResponseVO updateCheckout(int id,String checkOutDate);



}
