package com.example.hotel.blImpl.admin;

import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.po.Hotel_ManagerPO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.Hotel_Manager;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Response;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImpl_unitTest {
    @Autowired
    private AdminServiceImpl adminService=new AdminServiceImpl();
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Test
    @Transactional
    @Rollback


    public void addManager() {
        UserForm userForm=new UserForm();//用于测试不存在的账户的添加
        userForm.setEmail("test_addManager@.qq.com");
        userForm.setPassword("123456");
        assertTrue(adminService.addManager(userForm).getSuccess());
        assertFalse(adminService.addManager(userForm).getSuccess());//用于测试已存在的账户的添加
        adminService.delAccount(userForm);
        assertTrue(adminService.addManager(userForm).getSuccess());
    }


    @Test
    @Transactional
    @Rollback
    public void changUser() {
        UserForm userForm1=new UserForm();
        userForm1.setEmail("test_changUser@qq.com");
        userForm1.setPassword("123");
        adminService.addManager(userForm1);//新创建一个用户进行测试
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
        UserForm userForm1=new UserForm();
        assertEquals("该管理员账户不存在",adminService.addHotelManager(hotel_manager).getMessage());//测试数据库中账户不存在情况
        userForm1.setEmail("test_addHotelManager@qq.com");
        userForm1.setPassword("123456");
        adminService.addManager(userForm1);
        userForm1.setUsertype("Client");
        adminService.changUser(userForm1);
        assertEquals("该账户不是酒店管理员账号，请先授权",adminService.addHotelManager(hotel_manager).getMessage());//测试账号未授权情况
        userForm1.setUsertype("HotelManager");
        adminService.changUser(userForm1);
        assertTrue(adminService.addHotelManager(hotel_manager).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void searchHotel() {
        Hotel_Manager hotel_manager=new Hotel_Manager();
        hotel_manager.setHotelname("老七酒楼");
        assertEquals("该酒店不存在",adminService.searchHotel(hotel_manager).getMessage());//测试酒店不存在情况
        ;       hotel_manager.setEmail("333@qq.com");
        adminService.addHotelManager(hotel_manager);//添加该酒楼
        assertNotNull(adminService.searchHotel(hotel_manager).getContent());
    }

    @Test
    @Transactional
    @Rollback
    public void changeHotelManager() {
        HotelVO hotelVO=new HotelVO();
        hotelVO.setId(1);
        hotelVO.setManagerId(7);
        assertEquals("该管理员账户不存在",adminService.changeHotelManager(hotelVO).getMessage());//测试数据库中账户不存在情况
        UserForm userForm=new UserForm();//用于测试不存在的账户的添加
        userForm.setEmail("test_changeHotelManager@.qq.com");
        userForm.setPassword("123456");
        adminService.addManager(userForm);
        assertEquals("该账户不是酒店管理员账号，请先授权",adminService.changeHotelManager(hotelVO).getMessage());//测试账号未授权情况
        userForm.setUsername("test");
        userForm.setPhone("135test");
        userForm.setUsertype("HotelManager");
        adminService.changUser(userForm);
        assertTrue(adminService.changeHotelManager(hotelVO).getSuccess());
    }
}