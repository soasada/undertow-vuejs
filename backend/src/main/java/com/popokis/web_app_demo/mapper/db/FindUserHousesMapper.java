package com.popokis.web_app_demo.mapper.db;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.Furniture;
import com.popokis.web_app_demo.entity.House;
import com.popokis.web_app_demo.entity.User;
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
