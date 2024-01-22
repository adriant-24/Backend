package com.shop.onlineshop;

import com.shop.onlineshop.entity.Address;
import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.service.UserServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class OnlineshopApplicationTests {

	@Autowired
    UserServiceImpl userService;

//	@Test
//	@Disabled
//	void whenUpdateUserInfoEmail_thenEmailIsUpdated() {
//		updateUserInfoEmail(1L);
//	}
//	public void updateUserInfoEmail(Long id){
//		User user = userService.findUserById(id);
//		//user.getUserInfo().setEmail("admin1@shoponline.com");
//		user.setUserInfo(null);
//		//user.setAddresses(null);
//
//		userService.save(user);
//		//List<Address> addresses= userService.findAllAddressesByUserId(id);
//
//		assertThat(userService.findUserById(id).getUserInfo().getEmail()).isEqualTo("admin1@shoponline.com");
//	}

//	@Test
//	@Disabled
//	void whenAddingNewAddress_thenAddressesNumberIncreases(){
//		User user = userService.findByIdJoinFetchAddress(1L);
//		int originalSize = user.getAddresses().size();
//		user = addAddressToUser(user);
//		assertThat(user.getAddresses().size()).isGreaterThan(originalSize);
//	}

//	@Test
//	void whenNewOrderWithItems_thenItemsAreAlsoCreated(){
//		OrderItem item1 = OrderItem.builder()
//				.itemName("Test 1 item")
//				.unitPrice(BigDecimal.valueOf(2.5))
//				//.itemDetail(ItemDetail.builder().itemDescription("test1").build())
//				.build();
//
//		OrderItem item2 = OrderItem.builder()
//				.itemName("Test 2 item")
//				.unitPrice(BigDecimal.valueOf(2.5))
//				//.itemDetail(ItemDetail.builder().itemDescription("test1").build())
//				.build();
//
//		Order order = Order.builder()
//				.isPaid(1)
//				.payType("Master Card")
//				.status("In Delivery")
//				.build();
//		order.addOrderItem(item1);
//		order.addOrderItem(item2);
//
//		Order orderSaved = onlineShopService.saveOrder(order);
//
//		assertThat(onlineShopService.findAllByOrderIdJoinFetchItem(orderSaved.getOrderId()).getOrderItems().size()).isEqualTo(2);
//	}

//	@Test
//	@Disabled
//	void whenGettingItemWithOrders_thenItemHas2Orders(){
//		User user = userService.findByIdJoinFetchAddress(1L);
//		int originalSize = user.getAddresses().size();
//		user = addAddressToUser(user);
//		assertThat(user.getAddresses().size()).isGreaterThan(originalSize);
//	}

//	@Test
//	void addNewAddress(){
//		User user = userService.findByIdUserOnly(1L);
//		Address newAddress = createNewAddress(user);
//		String newCity = userService.findByIdJoinFetchAddress(1L).getAddresses().get(2).getCity();
//		deleteAddress(newAddress);
//		user = userService.findByIdJoinFetchAddress(1L);
//		boolean isAddedAndDeleted = newAddress.getCity().equals(newCity) &&
//                user.getAddresses().stream().noneMatch(a->a.getCity().equals(newCity));
//		assertTrue(isAddedAndDeleted);
//	}
//
//
//	public void deleteAddress(Address address){
//		userService.deleteAddress(address);
//	}
//	private User addAddressToUser(User user) {
//
//		Address address = Address.builder()
//				.country("Romania")
//				.city("Oradea")
//				.phoneNumber("+401234567")
//				.streetAndNumber("Bld. Dacia, nr. 32")
//				.zipCode("200345")
//				.additionalAddress("Bl. 14, Sc.1, Ap. 2")
//				.user(user)
//				.build();
//
//		user.addAddress(address);
//		return userService.save(user);
//	}
//
//	private Address createNewAddress(User user) {
//
//		Address address = Address.builder()
//				.country("Romania")
//				.city("Oradea")
//				.phoneNumber("+401234567")
//				.streetAndNumber("Bld. Dacia, nr. 32")
//				.zipCode("200345")
//				.additionalAddress("Bl. 14, Sc.1, Ap. 2")
//				.user(user)
//				.build();
//		return userService.saveAddress(address);
//	}

}
