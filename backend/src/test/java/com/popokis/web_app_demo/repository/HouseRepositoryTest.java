package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.common.DatabaseTest;
import com.popokis.web_app_demo.entity.House;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HouseRepositoryTest extends DatabaseTest {

  @Test
  void findAllHousesOfUser() {
    List<House> houses = HouseRepository.all(1L);
    assertEquals(2, houses.size());
    assertEquals("soasadaHouseá", houses.get(0).getName());
    assertEquals("soasadaHouse2", houses.get(1).getName());
  }

  @Test
  void createHouse() {
    House house = House.builder().name("goodHouse").userId(1L).build();
    long houseId = HouseRepository.create(house);
    assertEquals(4L, houseId);
  }

  @Test
  void readHouse() {
    House house = HouseRepository.read(1L);
    assertEquals("soasadaHouseá", house.getName());
    assertEquals(1L, house.getUserId());
  }

  @Test
  void updateHouse() {
    int rowsAffected = HouseRepository.update(House.builder().id(1L).name("goodHouse").userId(1L).build());
    assertTrue(rowsAffected > 0);
  }

  @Test
  void deleteHouse() {
    int rowsAffected = HouseRepository.delete(3L);
    assertTrue(rowsAffected > 0);
  }
}