package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import com.example.hotel.vo.UserInfoVO;
import com.example.hotel.vo.FilterConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    //添加酒店
    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/deleteHotel")
    public ResponseVO deleteHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.deleteHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    //列表获取酒店信息
    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    //按地址和商圈搜索酒店
    @GetMapping("/address")
    public ResponseVO searchHotels(@RequestParam("address") String address,@RequestParam("bizRegion") String bizRegion) throws ServiceException{
        return ResponseVO.buildSuccess(hotelService.searchHotels(address,bizRegion));
    }

    //按条件过滤酒店
    @PostMapping("/filter")
    public ResponseVO filterHotels(@RequestBody FilterConditionVO filterConditionVO) throws ServiceException{
        return ResponseVO.buildSuccess(hotelService.filterHotels(filterConditionVO));
    }

    //按价格排序酒店
    @GetMapping("priceSort")
    public ResponseVO priceSortHotels() throws ServiceException{
        return ResponseVO.buildSuccess(hotelService.priceSortHotels());
    }

    //按评分排序酒店
    @GetMapping("rateSort")
    public ResponseVO rateSortHotels() throws ServiceException{
        return ResponseVO.buildSuccess(hotelService.rateSortHotels());
    }

    //添加房间
    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess();
    }

    //显示酒店详细信息
    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    //显示酒店所有订单
    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.getHotelOrders(hotelId));
    }

    @GetMapping("/{id}/hotelMgr")
    public ResponseVO retrieveMgrHotels(@PathVariable int id) {
        System.out.print(id);
        return ResponseVO.buildSuccess(hotelService.retrieveMgrHotels(id));

    }

    @PostMapping("/{id}/hotelInfo/update")
    public ResponseVO updateHotelInfo(@RequestBody HotelVO hotelVO, @PathVariable Integer id){
        return hotelService.updateHotelInfo(id,hotelVO.getName(),hotelVO.getAddress(),hotelVO.getBizRegion(),hotelVO.getDescription(),hotelVO.getHotelStar(),hotelVO.getPhoneNum());

    }

    @GetMapping("/hotelAllRoom")
    public ResponseVO getHotelAllRoom(@RequestParam Integer hotelId) {
        System.out.print("room!");
        return ResponseVO.buildSuccess(roomService.getHotelAllRoom(hotelId));
    }

    @PostMapping("/{id}/hotelInfo/updatePrice")
    public ResponseVO updatePriceInfo(@RequestBody RoomVO roomVO, @PathVariable int id){
        System.out.println("OOOOOOO");
        return roomService.updateRoomPriceInfo(id,roomVO.getPrice());
    }

    @PostMapping("/{id}/hotelInfo/updateNum")
    public ResponseVO updateNumInfo(@RequestBody RoomVO roomVO, @PathVariable int id){
        System.out.println("OOOOOOO");
        return roomService.updateRoomNumInfo(id,roomVO.getTotal());
    }

    @GetMapping("/{id}/deleteRoom")
    public ResponseVO deleteRoom(@PathVariable int id){
        return roomService.deleteRoom(id);
    }



}
