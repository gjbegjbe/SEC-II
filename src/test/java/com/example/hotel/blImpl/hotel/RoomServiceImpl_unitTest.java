package com.example.hotel.blImpl.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest

public class RoomServiceImpl_unitTest {
    @Autowired
    RoomServiceImpl roomService=new RoomServiceImpl();

    @Test
    public void retrieveHotelRoomInfo() {
        assertNotNull(roomService.retrieveHotelRoomInfo(2));
    }

    @Test
    public void insertRoomInfo() {
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setId(41);
        hotelRoom.setHotelId(8);
        hotelRoom.setTotal(100);
        hotelRoom.setCurNum(100);
        roomService.insertRoomInfo(hotelRoom);
        assertEquals(1,roomService.getRoomCurNum(8,"Family"));
    }

    @Test
    public void updateRoomInfo() {
        roomService.updateRoomInfo(8,"Family",1);
        assertEquals(0,roomService.getRoomCurNum(8,"Family"));
    }

    @Test
    public void getRoomCurNum() {
        assertEquals(0,roomService.getRoomCurNum(8,"Family"));
    }

    @Test
    public void getHotelAllRoom() {
        assertNotNull(roomService.getHotelAllRoom(1));
    }

    @Test
    public void updateRoomPriceInfo() {
        roomService.updateRoomPriceInfo(42,100);
        assertEquals(0,roomService.getRoomCurNum(8,"Family"));
    }

    @Test
    public void updateRoomNumInfo() {
        roomService.updateRoomNumInfo(42,100);
        assertEquals(0,roomService.getRoomCurNum(8,"Family"));
    }


}