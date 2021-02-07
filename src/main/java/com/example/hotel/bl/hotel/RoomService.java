package com.example.hotel.bl.hotel;

import com.example.hotel.po.Coupon;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;
import com.sun.org.apache.regexp.internal.RE;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    /**
     * 添加酒店客房信息
     * @param hotelRoom
     */
    void insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房房间数量
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    /**
     * 获取指定酒店的房间
     * @param  hotelId
     * @return
     * @author gjbe
     */
    List<HotelRoom> getHotelAllRoom(Integer hotelId);

    /**
     * 更改房间价格
     * @param  id
     * @author gjbe
     */
    ResponseVO updateRoomPriceInfo(int id, double price);

    /**
     * 更改房间数量
     * @param  id
     * @author gjbe
     */
    ResponseVO updateRoomNumInfo(int id, Integer num);

    /**
     * 删除房间
     * @param  id
     * @author gjbe
     */
    ResponseVO deleteRoom(int id);
}
