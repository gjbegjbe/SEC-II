package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final static String UPDATE_ERROR = "修改失败";
    private final static String UPDATE_NUMERROR = "有人预订，修改失败";


    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        roomMapper.insertRoom(hotelRoom);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelRoom> getHotelAllRoom(Integer hotelId){
        List<HotelRoom> hotelRooms = roomMapper.selectRoomsByHotelId(hotelId);
        return hotelRooms;
    }

    @Override
    public ResponseVO updateRoomPriceInfo(int id, double price){
        try {
            roomMapper.updatePrice(id,price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    };
    @Override
    public  ResponseVO updateRoomNumInfo(int id, Integer num){
        try {
            HotelRoom room =roomMapper.getRoomById(id);
            int curNum=room.getCurNum();
            int total=room.getTotal();

            if(num<(total-curNum)){
                return ResponseVO.buildFailure(UPDATE_NUMERROR);
            }
            roomMapper.updateNum(id,num);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO deleteRoom(int id){
        try{
            roomMapper.deleteRoom(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseVO.buildSuccess(true);
    };



}
