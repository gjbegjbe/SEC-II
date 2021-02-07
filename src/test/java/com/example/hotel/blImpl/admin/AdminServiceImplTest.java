package com.example.hotel.blImpl.admin;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.Hotel_Manager;
import com.example.hotel.vo.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminService=new AdminServiceImpl();
    @Test
    @Transactional
    @Rollback
    public void addManager() {
        UserForm userForm1=new UserForm();//用于测试已存在账户的重复添加
        UserForm userForm2=new UserForm();//用于测试不存在的账户的添加
        userForm1.setEmail("1012681@qq.com");
        userForm1.setPassword("123456");
        assertFalse(adminService.addManager(userForm1).getSuccess());
        userForm2.setEmail("test_addManager@.qq.com");
        userForm2.setPassword("123456");
        assertTrue(adminService.addManager(userForm2).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void getAllManagers() {
        assertNotNull(adminService.getAllManagers());
    }


    @Test
    @Transactional
    @Rollback
    public void delAccount() {
        UserForm userForm1=new UserForm();
        userForm1.setEmail("test_addManager@.qq.com");
        assertTrue(adminService.delAccount(userForm1).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void changUser() {
        UserForm userForm1=new UserForm();
        userForm1.setEmail("123@qq.com");
        userForm1.setPassword("123");
        userForm1.setUsername("test");
        userForm1.setPhone("135test");
        userForm1.setUsertype("Client");
        assertTrue(adminService.changUser(userForm1).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void addHotelManager() {
        Hotel_Manager hotel_manager=new Hotel_Manager();
        hotel_manager.setEmail("test_addHotelManager@qq.com");
        hotel_manager.setHotelname("test_addHotelManager酒楼");
        assertEquals("该管理员账户不存在",adminService.addHotelManager(hotel_manager).getMessage());//测试数据库中账户不存在情况
        hotel_manager.setEmail("123@qq.com");
        assertEquals("该账户不是酒店管理员账号，请先授权",adminService.addHotelManager(hotel_manager).getMessage());//测试账号未授权情况
        hotel_manager.setEmail("333@qq.com");
        assertTrue(adminService.addHotelManager(hotel_manager).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void searchHotel() {
        Hotel_Manager hotel_manager=new Hotel_Manager();
        hotel_manager.setHotelname("老七酒楼");
        assertEquals("该酒店不存在",adminService.searchHotel(hotel_manager).getMessage());//测试酒店不存在情况
        hotel_manager.setHotelname("汉庭酒店");
        assertNotNull(adminService.searchHotel(hotel_manager).getContent());
    }

    @Test
    @Transactional
    @Rollback
    public void changeHotelManager() {
        HotelVO hotelVO=new HotelVO();
        hotelVO.setId(1);
        hotelVO.setManagerId(250);
        assertEquals("该管理员账户不存在",adminService.changeHotelManager(hotelVO).getMessage());//测试数据库中账户不存在情况
        hotelVO.setManagerId(5);
        assertEquals("该账户不是酒店管理员账号，请先授权",adminService.changeHotelManager(hotelVO).getMessage());//测试账号未授权情况
        hotelVO.setManagerId(6);
        assertTrue(adminService.changeHotelManager(hotelVO).getSuccess());
    }
}