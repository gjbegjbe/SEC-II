package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
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
public class AccountServiceImpl_unitTest {
    @Autowired
    private AccountServiceImpl accountService=new AccountServiceImpl();

    @Test
    @Transactional
    @Rollback
    public void registerAccount() {
        UserVO userVO = new UserVO();
        userVO.setId(67);
        userVO.setEmail("58467@qq.com");
        userVO.setPassword("123456");
        userVO.setUserName("58467");
        userVO.setPhoneNumber("12345678919");
        userVO.setCredit(100);
        userVO.setUserType(UserType.Client);
        accountService.registerAccount(userVO);
        assertEquals("58467",userVO.getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void login() {
        UserForm userForm = new UserForm();
        userForm.setEmail("1012681@qq.com");
        userForm.setPassword("123456");
        assertNotNull(accountService.login(userForm));
    }

    @Test
    @Transactional
    @Rollback
    public void getUserInfo() {
        assertEquals("1012681@qq.com",accountService.getUserInfo(4).getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void updateUserInfo() {
        accountService.updateUserInfo(5,"123456","客户1","12345678900");
        assertEquals("12345678900",accountService.getUserInfo(5).getPhoneNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void minusCredit() throws ServiceException {
        accountService.minusCredit(6,100.0);
        assertTrue(accountService.getUserInfo(6).getCredit()==0);
    }
}