package com.example.hotel.blImpl.order;

import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class OrderServiceImpl_unitTest {
    @Autowired
    private OrderServiceImpl orderService=new OrderServiceImpl();

    @Test
    @Transactional
    @Rollback
    public void addOrder() {
        OrderVO orderVO=new OrderVO();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        orderVO.setRoomNum(1);
        orderVO.setPeopleNum(1);
        orderVO.setHotelId(2);
        orderVO.setRoomType("Family");
        orderVO.setCreateDate(curdate);
        orderVO.setOrderState("已预订");
        orderVO.setClientName("test");
        orderVO.setPhoneNumber("123456");
        assertFalse(orderService.addOrder(orderVO).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void getAllOrders() {
        assertNotNull(orderService.getAllOrders());
    }

    @Test
    @Transactional
    @Rollback
    public void getUserOrders() {assertNotNull(orderService.getUserOrders(5));
    }

    @Test
    @Transactional
    @Rollback
    public void annulOrder() {
        assertTrue(orderService.annulOrder(1).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void getHotelAllOrders() {
        assertNotNull(orderService.getHotelAllOrders(1));
    }

    @Test
    @Transactional
    @Rollback
    public void getHotelAbOrders() {
        assertNotNull(orderService.getHotelAbOrders(2));
        List<Order> orders=orderService.getHotelAbOrders(2);
        assertEquals("未按时入住",orders.get(0).getOrderState());
    }

    @Test
    public void checkIn() {
        assertTrue(orderService.checkIn(2).getSuccess());
        assertEquals("异常恢复",orderService.getAllOrders().get(1).getOrderState());
    }

    @Test
    public void checkOut() {
        assertTrue(orderService.checkOut(4).getSuccess());
        assertEquals("已退房",orderService.getAllOrders().get(3).getOrderState());
    }

    @Test
    public void updateCheckout() {
        String checkOutDate = "2020-07-01";
        assertTrue(orderService.updateCheckout(1,checkOutDate).getSuccess());
    }
}