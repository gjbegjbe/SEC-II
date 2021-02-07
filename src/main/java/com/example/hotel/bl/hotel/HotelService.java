package com.example.hotel.bl.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.FilterConditionVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.omg.PortableInterceptor.HOLDING;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    void addHotel(HotelVO hotelVO) throws ServiceException;


    /**
     * 删除酒店
     * @param hotelVO
     * @throws
     */
    void deleteHotel(HotelVO hotelVO) throws ServiceException;

    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 按地址和商圈搜索酒店
     * @param address
     * @param bizRegion
     * @return
     * @author lzh
     */
    List<HotelVO> searchHotels(String address,String bizRegion) throws ServiceException;

    /**
     * 按条件过滤酒店
     * @param filterConditionVO
     * @return
     * @author lzh
     */
    List<HotelVO> filterHotels(FilterConditionVO filterConditionVO) throws ServiceException;

    /**
     * 按价格排序酒店
     * @return
     * @author lzh
     */
    List<HotelVO> priceSortHotels() throws ServiceException;

    /**
     * 按评分排序酒店
     * @return
     * @author lzh
     */
    List<HotelVO> rateSortHotels() throws ServiceException;

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 查看酒店管理人员的所有酒店
     * @param id
     * @return
     * @author gjbe
     */
    List<HotelVO> retrieveMgrHotels(int id);

    /**
     * 维护酒店信息
     * @param hotelId,address,bizRegion,description,hotelStar
     * @author gjbe
     */
    ResponseVO updateHotelInfo(Integer hotelId, String name,String address, String bizRegion, String description, String hotelStar,String phoneNum);


}
