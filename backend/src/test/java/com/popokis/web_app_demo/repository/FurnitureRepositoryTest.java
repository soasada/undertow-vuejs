package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.Furniture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FurnitureRepositoryTest {

  @BeforeEach
  void setUp() {
    BootstrapDatabase.setUp();
  }

  @AfterEach
  void tearDown() {
    BootstrapDatabase.setDown();
  }

  @Test
  void findAllFurnitureOfHouse() {
    List<Furniture> furniture = FurnitureRepository.all(1L);
    assertEquals(2, furniture.size());
    assertEquals("table", furniture.get(0).getName());
    assertEquals("chair", furniture.get(1).getName());
    assertEquals("wood", furniture.get(0).getType());
    assertEquals("wood", furniture.get(1).getType());
  }

  @Test
  void createFurniture() {
    Furniture furniture = Furniture.builder().name("goodFurniture").type("iron").houseId(1L).build();
    long furnitureId = FurnitureRepository.create(furniture);
    assertEquals(6L, furnitureId);
  }

  @Test
  void readFurniture() {
    Furniture furniture = FurnitureRepository.read(1L);
    assertEquals("table", furniture.getName());
    assertEquals("wood", furniture.getType());
    assertEquals(1L, furniture.getHouseId());
  }

  @Test
  void updateFurniture() {
    int rowsAffected = FurnitureRepository.update(Furniture.builder().id(1L).name("goodFurniture").type("iron").houseId(1L).build());
    assertTrue(rowsAffected > 0);
  }

  @Test
  void deleteFurniture() {
    int rowsAffected = FurnitureRepository.delete(5L);
    assertTrue(rowsAffected > 0);
  }
}