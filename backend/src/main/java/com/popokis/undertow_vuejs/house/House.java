package com.popokis.undertow_vuejs.house;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.popokis.popok.sql_db.JdbcMapper;
import com.popokis.undertow_vuejs.furniture.Furniture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = House.HouseBuilder.class)
@AllArgsConstructor(staticName = "create")
public class House {

  @Nullable Long id;
  @NonNull String name;
  long userId;
  @EqualsAndHashCode.Exclude @Nullable LocalDateTime createdAt;
  @EqualsAndHashCode.Exclude @Nullable LocalDateTime updatedAt;
  @Nullable List<Furniture> furniture;

  public static JdbcMapper<House> mapper() {
    return new Mapper();
  }

  private static class Mapper implements JdbcMapper<House> {
    @Override
    public Optional<House> map(ResultSetWrappingSqlRowSet resultSet) {
      if (resultSet.getLong("h_id") == 0) return Optional.empty();

      return Optional.of(
          House.create(
              resultSet.getLong("h_id"),
              resultSet.getString("h_name"),
              resultSet.getLong("h_user_id"),
              resultSet.getTimestamp("h_created_at").toLocalDateTime(),
              resultSet.getTimestamp("h_updated_at").toLocalDateTime(),
              List.of()
          )
      );
    }
  }
}
