package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.Hotel_Manager;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){
        System.out.println(userForm.getPassword());
        return adminService.addManager(userForm);
    }

    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @PostMapping("/delAccount")
    public ResponseVO delAccount(@RequestBody UserForm userForm){
        return adminService.delAccount(userForm);
    }

    @PostMapping("/changeUser")
    public ResponseVO changeUser(@RequestBody UserForm userForm){
        return adminService.changUser(userForm);
    }

    @PostMapping("/addHotelManager")
    public ResponseVO addHotelManager(@RequestBody Hotel_Manager hotel_manager){
        return adminService.addHotelManager(hotel_manager);
    }

    @PostMapping("/searchHotel")
    public ResponseVO searchHotel(@RequestBody Hotel_Manager hotel_manager){
        return adminService.searchHotel(hotel_manager);
    }

    @PostMapping("/changeHotelManager")
    public ResponseVO changeHotelManager(@RequestBody HotelVO hotelVO){
        System.out.println(hotelVO.getManagerId());
        return adminService.changeHotelManager(hotelVO);
    }
}
