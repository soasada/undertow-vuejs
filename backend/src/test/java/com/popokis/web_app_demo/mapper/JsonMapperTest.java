package com.popokis.web_app_demo.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.popokis.web_app_demo.entity.Furniture;
import com.popokis.web_app_demo.entity.House;
import com.popokis.web_app_demo.entity.ImmutableFurniture;
import com.popokis.web_app_demo.entity.ImmutableHouse;
import com.popokis.web_app_demo.entity.ImmutableUser;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonMapperTest {

  @Test
  void userJsonSerialization() throws JsonProcessingException {
    User user = ImmutableUser.of(1L, "soasada", "TEST", LocalDateTime.now(), LocalDateTime.now(), null);
    String jsonUser = JsonMapper.getInstance().mapper().writeValueAsString(user);
    assertFalse(jsonUser.contains("houses"));
  }

  @Test
  void userJsonDeserialization() throws IOException {
    String jsonUser = "{\"id\":1,\"username\":\"soasada\",\"password\":\"TEST\",\"createdAt\":\"2019-03-31T13:24:07.23841\",\"updatedAt\":\"2019-03-31T13:24:07.239629\"}";
    User user = JsonMapper.getInstance().mapper().readValue(jsonUser, User.class);
    assertEquals(1L, user.uId());
    assertEquals("soasada", user.uUsername());
  }

  @Test
  void houseJsonSerialization() throws JsonProcessingException {
    House house = ImmutableHouse.of(1L, "soasadaHouse", 1L, LocalDateTime.now(), LocalDateTime.now(), null);
    String jsonHouse = JsonMapper.getInstance().mapper().writeValueAsString(house);
    assertFalse(jsonHouse.contains("furniture"));
  }

  @Test
  void houseJsonDeserialization() throws IOException {
    String jsonHouse = "{\"id\":1,\"name\":\"soasadaHouse\",\"userId\":1,\"createdAt\":\"2019-03-31T21:08:14.622456\",\"updatedAt\":\"2019-03-31T21:08:14.623265\"}";
    House house = JsonMapper.getInstance().mapper().readValue(jsonHouse, House.class);
    assertEquals(1L, house.hId());
    assertEquals("soasadaHouse", house.hName());
  }

  @Test
  void furnitureJsonSerialization() throws JsonProcessingException {
    Furniture furniture = ImmutableFurniture.of(1L, "table", "wood", 1L, LocalDateTime.now(), LocalDateTime.now());
    String jsonFurniture = JsonMapper.getInstance().mapper().writeValueAsString(furniture);
    assertTrue(jsonFurniture.contains("type"));
  }

  @Test
  void furnitureJsonDeserialization() throws IOException {
    String jsonFurniture = "{\"id\":1,\"name\":\"table\",\"type\":\"wood\",\"houseId\":1,\"createdAt\":\"2019-03-31T21:11:17.722026\",\"updatedAt\":\"2019-03-31T21:11:17.722806\"}";
    Furniture furniture = JsonMapper.getInstance().mapper().readValue(jsonFurniture, Furniture.class);
    assertEquals(1L, furniture.fId());
    assertEquals("table", furniture.fName());
    assertEquals("wood", furniture.fType());
  }
}