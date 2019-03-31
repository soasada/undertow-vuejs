package com.popokis.web_app_demo.mapper.db;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.Furniture;
import com.popokis.web_app_demo.entity.House;
import com.popokis.web_app_demo.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
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
  public User map(ResultSet resultSet) throws SQLException {
    Set<User> users = new HashSet<>();
    Set<House> houses = new HashSet<>();
    Set<Furniture> furnitures = new HashSet<>();

    do {
      users.add(userMapper.map(resultSet));
      House house = houseMapper.map(resultSet);
      Furniture furniture = furnitureMapper.map(resultSet);
      if (Objects.nonNull(house)) houses.add(house);
      if (Objects.nonNull(furniture)) furnitures.add(furniture);
    } while (resultSet.next());

    User user = users.iterator().next();



    return users.iterator().next();
  }
}
