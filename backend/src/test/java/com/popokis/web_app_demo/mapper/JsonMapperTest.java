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
    String jsonUser = "{\"username\":\"soasada\",\"password\":\"TEST\"}";
    User user = JsonMapper.getInstance().mapper().readValue(jsonUser, User.class);
    assertEquals("soasada", user.username());
    assertEquals("TEST", user.password());
  }

  @Test
  void houseJsonSerialization() throws JsonProcessingException {
    House house = ImmutableHouse.of(1L, "soasadaHouse", 1L, LocalDateTime.now(), LocalDateTime.now(), null);
    String jsonHouse = JsonMapper.getInstance().mapper().writeValueAsString(house);
    assertFalse(jsonHouse.contains("furniture"));
  }

  @Test
  void houseJsonDeserialization() throws IOException {
    String jsonHouse = "{\"name\":\"soasadaHouse\",\"userId\":1}";
    House house = JsonMapper.getInstance().mapper().readValue(jsonHouse, House.class);
    assertEquals("soasadaHouse", house.name());
    assertEquals(1L, house.userId());
  }

  @Test
  void furnitureJsonSerialization() throws JsonProcessingException {
    Furniture furniture = ImmutableFurniture.of(1L, "table", "wood", 1L, LocalDateTime.now(), LocalDateTime.now());
    String jsonFurniture = JsonMapper.getInstance().mapper().writeValueAsString(furniture);
    assertTrue(jsonFurniture.contains("type"));
  }

  @Test
  void furnitureJsonDeserialization() throws IOException {
    String jsonFurniture = "{\"name\":\"table\",\"type\":\"wood\",\"houseId\":1}";
    Furniture furniture = JsonMapper.getInstance().mapper().readValue(jsonFurniture, Furniture.class);
    assertEquals("table", furniture.name());
    assertEquals("wood", furniture.type());
    assertEquals(1L, furniture.houseId());
  }
}