package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.House;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseRepositoryTest {

  @BeforeEach
  void setUp() {
    BootstrapDatabase.setUp();
  }

  @AfterEach
  void tearDown() {
    BootstrapDatabase.setDown();
  }

  @Test
  void findAllHousesOfUser() {
    List<House> houses = HouseRepository.all(1L);
    assertEquals(2, houses.size());
    assertEquals("soasadaHouseá", houses.get(0).getName());
    assertEquals("soasadaHouse2", houses.get(1).getName());
  }
}