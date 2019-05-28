package com.popokis.undertow_vuejs.house;

import com.popokis.undertow_vuejs.common.HttpTest;
import com.popokis.undertow_vuejs.common.SimpleClient;
import com.popokis.undertow_vuejs.mapper.JsonMapper;
import com.popokis.undertow_vuejs.mapper.JsonMappers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HouseHandlerTest extends HttpTest {

  @Test
  void findAllHousesOfUser() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/user/1/houses");
    List<House> houses = JsonMappers.list(jsonResponse, House.class);
    assertEquals(2, houses.size());
    assertEquals("soasadaHouseá", houses.get(0).getName());
    assertEquals("soasadaHouse2", houses.get(1).getName());
  }

  @Test
  void createHouse() {
    House house = House.builder().name("goodHouse").userId(1L).build();
    String response = SimpleClient.getInstance().post(address + "/houses", JsonMapper.getInstance().toJson(house));
    long houseId = Long.parseLong(response);
    assertEquals(4L, houseId);
  }

  @Test
  void readHouse() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/houses/1");
    House house = JsonMappers.model(jsonResponse, House.class);
    assertEquals("soasadaHouseá", house.getName());
    assertEquals(1L, house.getUserId());
  }

  @Test
  void updateHouse() {
    House updatedHouse = House.builder().id(1L).name("goodHouse").userId(1L).build();
    String response = SimpleClient.getInstance().put(address + "/houses", JsonMapper.getInstance().toJson(updatedHouse));
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }

  @Test
  void deleteHouse() {
    String response = SimpleClient.getInstance().delete(address + "/houses/3");
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }
}