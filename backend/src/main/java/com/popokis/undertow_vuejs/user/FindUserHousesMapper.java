package com.popokis.undertow_vuejs.user;

import com.popokis.popok.sql_db.JdbcMapper;
import com.popokis.undertow_vuejs.furniture.Furniture;
import com.popokis.undertow_vuejs.furniture.FurnitureMapper;
import com.popokis.undertow_vuejs.house.House;
import com.popokis.undertow_vuejs.house.HouseMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class FindUserHousesMapper implements JdbcMapper<User> {

  private final JdbcMapper<User> userMapper;
  private final JdbcMapper<House> houseMapper;
  private final JdbcMapper<Furniture> furnitureMapper;

  public FindUserHousesMapper() {
    this.userMapper = new UserMapper();
    this.houseMapper = new HouseMapper();
    this.furnitureMapper = new FurnitureMapper();
  }

  @Override
  public Optional<User> map(ResultSetWrappingSqlRowSet resultSet) {
    Set<User> users = new HashSet<>();
    Set<House> houses = new HashSet<>();
    Set<Furniture> furnitures = new HashSet<>();

    do {
      Optional<User> optionalUser = userMapper.map(resultSet);
      optionalUser.ifPresent(users::add);
      Optional<House> optionalHouse = houseMapper.map(resultSet);
      optionalHouse.ifPresent(houses::add);
      Optional<Furniture> optionalFurniture = furnitureMapper.map(resultSet);
      optionalFurniture.ifPresent(furnitures::add);
    } while (resultSet.next());

    User user = users.iterator().next();
    List<House> userHouses = new ArrayList<>();

    for (House house : houses) {
      List<Furniture> furnitureList = new ArrayList<>();
      for (Furniture furniture : furnitures) {
        if (house.getId().longValue() == furniture.getHouseId()) furnitureList.add(furniture);
      }
      userHouses.add(house.toBuilder().furniture(furnitureList).build());
    }

    return Optional.of(user.toBuilder().houses(userHouses).build());
  }
}
