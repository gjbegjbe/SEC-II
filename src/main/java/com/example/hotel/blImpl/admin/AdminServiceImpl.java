package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.Hotel_Manager;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    AdminMapper adminMapper;
    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.Client);
        String email=user.getEmail();
        int count=adminMapper.judge_exist(email);//判断个数，如果已存在，添加失败
        if(user.getPassword().length()<6){
            return ResponseVO.buildFailure("账户密码不能小于六位");
        }
        if(count==1){
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        else {
            adminMapper.addManager(user);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    public ResponseVO delAccount(UserForm userForm){
        String email=userForm.getEmail();
        System.out.println(email);
        adminMapper.delAccount(email);
        return ResponseVO.buildSuccess(true);
    }

    public ResponseVO changUser(UserForm userForm){
        User user = new User();
        user.setEmail(userForm.getEmail());
        if(userForm.getUsername()!=null) {
            user.setUserName(userForm.getUsername());
        }
        if(userForm.getPassword()!=null) {
            user.setPassword(userForm.getPassword());
        }
        if(userForm.getUsertype().equals("Manager")) {
            user.setUserType(UserType.Manager);
        }
        if(userForm.getUsertype().equals("Client")) {
            user.setUserType(UserType.Client);
        }
        if(userForm.getUsertype().equals("HotelManager")) {
            user.setUserType(UserType.HotelManager);
        }
        if(userForm.getUsertype().equals("Marketer")) {
            user.setUserType(UserType.Marketer);
        }
        if(userForm.getPhone()!=null) {
            user.setPhoneNumber(userForm.getPhone());
        }
        adminMapper.update_user_info(user);
        return ResponseVO.buildSuccess(true);
    }

    public ResponseVO addHotelManager(Hotel_Manager hotel_manager){
        int count=adminMapper.judge_exist(hotel_manager.getEmail());//判断个数，如果不存在，添加失败
        if(count==0) {
            return ResponseVO.buildFailure("该管理员账户不存在");
        }
        else{
            String userType=adminMapper.judgeHotelManager(hotel_manager.getEmail());
            if(!userType.equals("HotelManager")){
                return ResponseVO.buildFailure("该账户不是酒店管理员账号，请先授权");
            }
            int id=adminMapper.findManagerId(hotel_manager.getEmail());
            Hotel hotel=new Hotel();
            hotel.setHotelName(hotel_manager.getHotelname());
            hotel.setManagerId(id);
            adminMapper.addHotelManager(hotel);
            return ResponseVO.buildSuccess(true);
        }
    }

    public ResponseVO searchHotel(Hotel_Manager hotel_manager){
        int count=adminMapper.judge_existHotel(hotel_manager.getHotelname());
        if(count==0) {
            return ResponseVO.buildFailure("该酒店不存在");
        }
        else{
            return ResponseVO.buildSuccess(adminMapper.getAllHotel(hotel_manager.getHotelname()));
        }
    }

    public ResponseVO changeHotelManager(HotelVO hotelVO){
        int count=adminMapper.judge_existManager(hotelVO.getManagerId());//判断个数，如果不存在，添加失败
        System.out.println(count);
        if(count==0) {
            return ResponseVO.buildFailure("该管理员账户不存在");
        }
        else{
            String userType=adminMapper.judgeHotelManager_1(hotelVO.getManagerId());
            System.out.println(userType);
            if(!userType.equals("HotelManager")){
                return ResponseVO.buildFailure("该账户不是酒店管理员账号，请先授权");
            }
            Hotel hotel=new Hotel();
            hotel.setId(hotelVO.getId());
            hotel.setManagerId(hotelVO.getManagerId());
            adminMapper.update_HotelManager(hotel);
            return ResponseVO.buildSuccess(true);
        }
    }
}
