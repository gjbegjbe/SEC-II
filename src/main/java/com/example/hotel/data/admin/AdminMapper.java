package com.example.hotel.data.admin;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.Hotel_ManagerPO;
import com.example.hotel.po.User;
import com.example.hotel.vo.Hotel_Manager;
import com.example.hotel.vo.UserForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface AdminMapper {

    int addManager(User user);

    List<User> getAllManagers();

    void delAccount(String email);

    int judge_exist(String email);

    void update_user_info(User user);

    void addHotelManager(Hotel hotel);

    String judgeHotelManager(String email);

    int findManagerId(String email);

    int judge_existHotel(String hotelName);

    List<Hotel_ManagerPO> getAllHotel(String hotelName);

    int judge_existManager(int id);

    String judgeHotelManager_1(int id);

    void update_HotelManager(Hotel hotel);

}
