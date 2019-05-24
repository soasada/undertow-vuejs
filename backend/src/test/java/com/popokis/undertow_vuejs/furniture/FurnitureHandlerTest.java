package com.popokis.undertow_vuejs.furniture;

import com.popokis.undertow_vuejs.common.HttpTest;
import com.popokis.undertow_vuejs.http.SimpleClient;
import com.popokis.undertow_vuejs.mapper.JsonMapper;
import com.popokis.undertow_vuejs.mapper.JsonMappers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FurnitureHandlerTest extends HttpTest {

  @Test
  void findAllFurnitureOfHouse() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/house/1/furniture");
    List<Furniture> furniture = JsonMappers.list(jsonResponse, Furniture.class);
    assertEquals(2, furniture.size());
    assertEquals("table", furniture.get(0).getName());
    assertEquals("chair", furniture.get(1).getName());
    assertEquals("wood", furniture.get(0).getType());
    assertEquals("wood", furniture.get(1).getType());
  }

  @Test
  void createFurniture() {
    Furniture furniture = Furniture.builder().name("goodFurniture").type("iron").houseId(1L).build();
    String response = SimpleClient.getInstance().post(address + "/furniture", JsonMapper.getInstance().toJson(furniture));
    long furnitureId = Long.parseLong(response);
    assertEquals(6L, furnitureId);
  }

  @Test
  void readFurniture() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/furniture/1");
    Furniture furniture = JsonMappers.model(jsonResponse, Furniture.class);
    assertEquals("table", furniture.getName());
    assertEquals("wood", furniture.getType());
    assertEquals(1L, furniture.getHouseId());
  }

  @Test
  void updateFurniture() {
    Furniture updatedFurniture = Furniture.builder().id(1L).name("goodFurniture").type("iron").houseId(1L).build();
    String response = SimpleClient.getInstance().put(address + "/furniture", JsonMapper.getInstance().toJson(updatedFurniture));
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }

  @Test
  void deleteFurniture() {
    String response = SimpleClient.getInstance().delete(address + "/furniture/5");
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }
}