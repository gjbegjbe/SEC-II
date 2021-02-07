const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  creditInfo: state =>state.user.creditInfo,
  userOrderList: state => state.user.userOrderList,
  registeredMemberVisible: state => state.user.registeredMemberVisible,
  //为了显示出来注册组件
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  searchedHotelList: state => state.hotel.searchedHotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  userComment: state =>state.hotel.userComment,
  rankAll: state=>state.hotel.rankAll,
  typeAll: state=>state.hotel.typeAll,
  //admin

  managerList: state => state.admin.managerList,
  clientList: state => state.admin.clientList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  updateAccountVisible: state => state.admin.updateAccountVisible,
  addManagerParams: state => state.admin.addManagerParams,
  updateAccountParams: state => state.admin.updateAccountParams,
  //hotelManager
  unusualOrderList: state => state.hotelManager.unusualOrderList,
  managedOrders:state => state.hotelManager.managedOrders,
  mgrHotelList: state => state.hotelManager.mgrHotelList,
  orderList: state => state.hotelManager.orderList,
  unusualOrderVisible: state => state.hotelManager.unusualOrderVisible,
  addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  couponVisible: state => state.hotelManager.couponVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  activeRoomId: state => state.hotelManager.activeRoomId,
  couponList: state => state.hotelManager.couponList,
  manageRoomVisible: state => state.hotelManager.manageRoomVisible,
  manageHotelVisible: state => state.hotelManager.manageHotelVisible,
  orderVisible: state => state.hotelManager.orderVisible,
  mgrOrderList: state => state.hotelManager.mgrOrderList,
  roomVisible: state => state.hotelManager.roomVisible,
  roomList: state => state.hotelManager.roomList,
  managePriceVisible: state => state.hotelManager.managePriceVisible,
  manageNumVisible: state => state.hotelManager.manageNumVisible,
  checkOutDateVisible:state=> state.hotelManager.checkOutDateVisible,
  //marketer
  marketerHotelId: state => state.marketer.marketerHotelId,
  marketerCouponList: state => state.marketer.marketerCouponList,
  addWebStrategyModalVisible:state => state.marketer.addWebStrategyModalVisible,




  residences: state => state.residences,
  }

  export default getters
