package com.example.hotel.blImpl.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.FilterConditionVO;
import com.example.hotel.vo.HotelVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import java.util.ArrayList;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelServiceImpl_unitTest {
    @Autowired
    private HotelServiceImpl hotelService=new HotelServiceImpl();

    @Test
    @Transactional
    @Rollback
    public void addHotel() throws ServiceException {
        HotelVO hotelVO=new HotelVO();
        hotelVO.setDescription("欢迎光临");
        hotelVO.setAddress("鼓楼");
        hotelVO.setName("南大招待所");
        hotelVO.setPhoneNum("1212");
        hotelVO.setManagerId(6);
        hotelVO.setRate(3.3);
        hotelVO.setBizRegion("西单");
        hotelVO.setHotelStar("Three");
        assertEquals("西单",hotelVO.getBizRegion());
    }

    @Test
    @Transactional
    @Rollback
    public void updateRoomInfo() {
        hotelService.updateRoomInfo(8, "Family", 1);
        assertNotEquals(1,hotelService.getRoomCurNum(8,"Family"));
    }

    @Test
    @Transactional
    @Rollback
    public void getRoomCurNum() {
        assertEquals(1,hotelService.getRoomCurNum(8,"Family"));
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveHotels() {
        assertNotNull(hotelService.retrieveHotels());
    }

    @Test
    @Transactional
    @Rollback
    public void searchHotels() throws ServiceException {
        assertNotNull(hotelService.searchHotels("南京","新街口"));
    }

    @Test
    @Transactional
    @Rollback
    public void filterHotels() throws ServiceException {
        FilterConditionVO filterConditionVO = new FilterConditionVO();
        ArrayList<String> roomTypes = new ArrayList<>();
        roomTypes.add("大床房");
        roomTypes.add("家庭房");
        filterConditionVO.setRoomTypes(roomTypes);
        ArrayList<String> hotelStars = new ArrayList<>();
        hotelStars.add("Five");
        hotelStars.add("Four");
        filterConditionVO.setHotelStars(hotelStars);
        filterConditionVO.setStartRate(2.0);
        hotelService.searchHotels("南京","新街口");
        assertEquals("如家酒店(新街口中心店)",hotelService.filterHotels(filterConditionVO).get(0).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void priceSortHotels() throws ServiceException{
        hotelService.searchHotels("南京","新街口");
        assertNotNull(hotelService.priceSortHotels());
    }

    @Test
    @Transactional
    @Rollback
    public void rateSortHotels() throws ServiceException{
        hotelService.searchHotels("南京","新街口");
        assertNotNull(hotelService.rateSortHotels());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveHotelDetails() {
        HotelVO hotelVO=new HotelVO();
        hotelVO=hotelService.retrieveHotelDetails(2);
        assertEquals("如家酒店(新街口中心店)",hotelVO.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void getHotelOrders() {
        assertNotNull(hotelService.getHotelOrders(2));
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveMgrHotels() {
        assertNotNull(hotelService.retrieveMgrHotels(6));
    }

    @Test
    @Transactional
    @Rollback
    public void updateHotelInfo() {
        assertTrue(hotelService.updateHotelInfo(12,"南大酒店","南大","XiDan",
                "奥利给","Three","123456").getSuccess());
        assertEquals(hotelService.retrieveHotelDetails(12).getName(),"南大酒店");
    }
}