package com.popokis.undertow_vuejs.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.popokis.undertow_vuejs.furniture.Furniture;
import com.popokis.undertow_vuejs.house.House;
import com.popokis.undertow_vuejs.user.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonMapperTest {

  @Test
  void userJsonSerialization() throws JsonProcessingException {
    User user = User.create(1L, "soasada", "TEST", LocalDateTime.now(), LocalDateTime.now(), null);
    String jsonUser = JsonMapper.getInstance().mapper().writeValueAsString(user);
    assertFalse(jsonUser.contains("houses"));
  }

  @Test
  void userJsonDeserialization() throws IOException {
    String jsonUser = "{\"username\":\"soasada\",\"password\":\"TEST\"}";
    User user = JsonMapper.getInstance().mapper().readValue(jsonUser, User.class);
    assertEquals("soasada", user.getUsername());
    assertEquals("TEST", user.getPassword());
  }

  @Test
  void houseJsonSerialization() throws JsonProcessingException {
    House house = House.create(1L, "soasadaHouseá", 1L, LocalDateTime.now(), LocalDateTime.now(), null);
    String jsonHouse = JsonMapper.getInstance().mapper().writeValueAsString(house);
    assertFalse(jsonHouse.contains("furniture"));
  }

  @Test
  void houseJsonDeserialization() throws IOException {
    String jsonHouse = "{\"name\":\"soasadaHouseá\",\"userId\":1}";
    House house = JsonMapper.getInstance().mapper().readValue(jsonHouse, House.class);
    assertEquals("soasadaHouseá", house.getName());
    assertEquals(1L, house.getUserId());
  }

  @Test
  void furnitureJsonSerialization() throws JsonProcessingException {
    Furniture furniture = Furniture.create(1L, "table", "wood", 1L, LocalDateTime.now(), LocalDateTime.now());
    String jsonFurniture = JsonMapper.getInstance().mapper().writeValueAsString(furniture);
    assertTrue(jsonFurniture.contains("type"));
  }

  @Test
  void furnitureJsonDeserialization() throws IOException {
    String jsonFurniture = "{\"name\":\"table\",\"type\":\"wood\",\"houseId\":1}";
    Furniture furniture = JsonMapper.getInstance().mapper().readValue(jsonFurniture, Furniture.class);
    assertEquals("table", furniture.getName());
    assertEquals("wood", furniture.getType());
    assertEquals(1L, furniture.getHouseId());
  }
}