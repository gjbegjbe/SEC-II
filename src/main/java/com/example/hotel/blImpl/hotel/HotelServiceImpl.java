package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final static String UPDATE_ERROR = "修改失败";


    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    private List<HotelVO> hotelVOList;

    private boolean priceSortJudge = false;

    private boolean rateSortJudge = false;

    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(hotelVO.getRate());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void deleteHotel(HotelVO hotelVO) throws ServiceException {
        hotelMapper.deleteHotel(hotelVO.getId());
        hotelMapper.deleterelated_Coupon(hotelVO.getId());
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    //按地址和商圈搜索酒店
    @Override
    public List<HotelVO> searchHotels(String address, String bizRegion) throws ServiceException {
        hotelVOList = retrieveHotels();
        List<HotelVO> searchedList = new ArrayList<HotelVO>();
        for(int i = 0;i<hotelVOList.size();i++){
            HotelVO hotelVO = hotelVOList.get(i);
            if(hotelVO.getAddress()!=null&&hotelVO.getBizRegion()!=null){
                if(hotelVO.getAddress().equals(address)&&hotelVO.getBizRegion().contains(bizRegion)){
                    if(retrieveHotelDetails(hotelVO.getId()).getRooms().size()!=0){
                        searchedList.add(hotelVO);
                    }
                }
            }
        }
        this.hotelVOList = searchedList;
        return hotelVOList;
    }

    //按条件过滤酒店
    @Override
    public List<HotelVO> filterHotels(FilterConditionVO filterConditionVO) throws ServiceException {
        System.out.println(filterConditionVO.getHotelName());
        System.out.println(filterConditionVO.getHotelStars());
        System.out.println(filterConditionVO.getEndPrice());
        System.out.println(filterConditionVO.getRoomTypes());
        System.out.println(filterConditionVO.getRoomNum());
        List<HotelVO> filteredList = new ArrayList<HotelVO>();
        for(int i = 0;i<hotelVOList.size();i++){
            HotelVO hotelVO = hotelVOList.get(i);
            if(filterConditionVO.getHotelName() == null || hotelVO.getName().contains(filterConditionVO.getHotelName())) {
                if (filterConditionVO.getHotelStars() == null||(filterConditionVO.getHotelStars().contains(hotelVO.getHotelStar()))) {//按星级过滤
                    if (filterConditionVO.getStartRate() <= hotelVO.getRate() && filterConditionVO.getEndRate() >= hotelVO.getRate()) {//按评分区间过滤
                        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelVO.getId());
                        List<RoomVO> roomVOS = rooms.stream().map(r -> {
                            RoomVO roomVO = new RoomVO();
                            roomVO.setId(r.getId());
                            roomVO.setPrice(r.getPrice());
                            roomVO.setRoomType(r.getRoomType().toString());
                            roomVO.setCurNum(r.getCurNum());
                            roomVO.setTotal(r.getTotal());
                            return roomVO;
                        }).collect(Collectors.toList());
                        Integer totalNum = 0;//满足条件的总房间数
                        for (int j = 0; j < roomVOS.size(); j++) {
                            RoomVO room = roomVOS.get(j);
                            System.out.println(room.getRoomType());
                            if (filterConditionVO.getRoomTypes() == null||filterConditionVO.getRoomTypes().contains(room.getRoomType())) {//按房间类型过滤
                                if (filterConditionVO.getStartPrice() <= room.getPrice() && filterConditionVO.getEndPrice() >= room.getPrice()) {//按房间价格区间过滤
                                    totalNum += room.getCurNum();
                                    if (totalNum >= filterConditionVO.getRoomNum()) {
                                        filteredList.add(hotelVO);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.hotelVOList = filteredList;
        return hotelVOList;
    }

    //按价格排序酒店
    @Override
    public List<HotelVO> priceSortHotels() throws ServiceException {
        hotelVOList.sort(new Comparator<HotelVO>() {
            @Override
            public int compare(HotelVO o1, HotelVO o2) {
                HotelVO o1WithRooms=retrieveHotelDetails(o1.getId());
                HotelVO o2WithRooms=retrieveHotelDetails(o2.getId());
                List<RoomVO> Room1=o1WithRooms.getRooms();
                List<RoomVO> Room2=o2WithRooms.getRooms();
                Double Price1=getMinPrice(Room1);
                Double Price2=getMinPrice(Room2);
                if(Price1<Price2)
                    return -1;
                else if(Price1>Price2)
                    return 1;
                else
                    return 0;
            }

            public Double getMinPrice(List<RoomVO> roomVOList){
                Double Price=1000000.0;
                for(int i=0;i<roomVOList.size();i++){
                    if(roomVOList.get(i).getPrice()<Price)
                        Price=roomVOList.get(i).getPrice();
                }
                return Price;
            }
        });
        if(priceSortJudge)
            Collections.reverse(hotelVOList);
        priceSortJudge=!priceSortJudge;
        return hotelVOList;
    }

    //按评分排序酒店
    @Override
    public List<HotelVO> rateSortHotels() throws ServiceException {
        hotelVOList.sort(new Comparator<HotelVO>() {
            @Override
            public int compare(HotelVO o1, HotelVO o2) {
                if(o1.getRate()<o2.getRate())
                    return 1;
                else if(o1.getRate()>o2.getRate())
                    return -1;
                else
                    return 0;
            }
        });
        if(rateSortJudge)
            Collections.reverse(hotelVOList);
        rateSortJudge=!rateSortJudge;
        return hotelVOList;
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }
    
    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = orderService.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    /**
     * 查看酒店管理人员的所有酒店
     * @param id
     * @return
     * @author gjbe
     */
    @Override
    public  List<HotelVO> retrieveMgrHotels(@RequestParam int id){
        return hotelMapper.selectMgrHotel(id);
    };

    /**
     * 维护酒店信息
     * @param hotelId,address,bizRegion,description,hotelStar
     * @author gjbe
     */
    @Override
    public ResponseVO updateHotelInfo(Integer hotelId, String name,String address, String bizRegion, String description, String hotelStar,String phoneNum){
        try {
            hotelMapper.updateHotel(hotelId, name,address, bizRegion, description,hotelStar,phoneNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    };

}
