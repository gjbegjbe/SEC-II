package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    @GetMapping("/managedOrders")
    public ResponseVO getHotelAllOrders(@RequestParam Integer hotelId) {
        System.out.println("getOrder");
        System.out.println(hotelId);

        System.out.println("用户："+orderService.getHotelAllOrders(hotelId).get(0).getUserId());
        return ResponseVO.buildSuccess(orderService.getHotelAllOrders(hotelId));
    }

    @GetMapping("/abnormalOrdersOfTheDay")
    public ResponseVO getHotelAbOrders(@RequestParam Integer hotelId) {
        System.out.println("getabOrder");
        System.out.println(hotelId);
        //System.out.println("用户："+orderService.getHotelAllOrders(hotelId).get(0).getUserId());
        return ResponseVO.buildSuccess(orderService.getHotelAbOrders(hotelId));
    }

    //异常订单恢复
    @GetMapping("/{id}/checkin")
    public ResponseVO checkIn(@PathVariable int id){return  orderService.checkIn(id);}

    @GetMapping("/{id}/checkout")
    public ResponseVO checkOut(@PathVariable int id){return  orderService.checkOut(id);}

    @PostMapping("{id}/orderInfo/updateCheckout")
    public ResponseVO updateCheckout(@RequestBody OrderVO orderVO, @PathVariable int id){
        System.out.println("O212321OOOOOO");
        System.out.println(orderVO.getCheckOutDate());
        return orderService.updateCheckout(id,orderVO.getCheckOutDate());
    }


}
