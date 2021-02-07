package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    void deleteHotel(int id);

    void deleterelated_Coupon(int id);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    /**
     *
     * @param id
     * @return
     * @author gjbe
     */
    List<HotelVO> selectMgrHotel(int id);

    /**
     * 更新酒店信息
     * @author gjbe
     */
    int updateHotel(@Param("id") Integer hotelId, @Param("name")String name,@Param("address") String address,@Param("bizRegion") String bizRegion,
                    @Param("hotelDescription") String description,@Param("hotelStar")String hotelStar,@Param("phoneNum")String phoneNum);

}
