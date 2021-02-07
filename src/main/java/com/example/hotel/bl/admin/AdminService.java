package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.Hotel_Manager;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加账号
     * @param userForm
     * @return
     */
    ResponseVO addManager(UserForm userForm);

    /**
     * 获得所有用户人员信息
     * @return
     */
    List<User> getAllManagers();

    /**
     * 删除用户账号
     * @return
     */
    ResponseVO delAccount(UserForm userForm);

    /**
     * 修改用户信息
     * @return
     */
    ResponseVO changUser(UserForm userForm);

    /**
     * 添加酒店管理员
     * @return
     */
    ResponseVO addHotelManager(Hotel_Manager hotel_manager);

    /**
     * 搜索酒店得到管理员
     * @return
     */
    ResponseVO searchHotel(Hotel_Manager hotel_manager);

    /**
     * 修改酒店管理员
     * @return
     */
    ResponseVO changeHotelManager(HotelVO hotelVO);
}
