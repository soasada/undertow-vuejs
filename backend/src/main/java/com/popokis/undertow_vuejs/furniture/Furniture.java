package com.popokis.undertow_vuejs.furniture;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.popokis.popok.sql_db.JdbcMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Furniture.FurnitureBuilder.class)
@AllArgsConstructor(staticName = "create")
public class Furniture {

  @Nullable Long id;
  @NonNull String name;
  @NonNull String type;
  long houseId;
  @EqualsAndHashCode.Exclude @Nullable LocalDateTime createdAt;
  @EqualsAndHashCode.Exclude @Nullable LocalDateTime updatedAt;

  public static JdbcMapper<Furniture> mapper() {
    return new Mapper();
  }

  private static class Mapper implements JdbcMapper<Furniture> {
    @Override
    public Optional<Furniture> map(ResultSetWrappingSqlRowSet resultSet) {
      if (resultSet.getLong("f_id") == 0) return Optional.empty();

      return Optional.of(
          Furniture.create(
              resultSet.getLong("f_id"),
              resultSet.getString("f_name"),
              resultSet.getString("f_type"),
              resultSet.getLong("f_house_id"),
              resultSet.getTimestamp("f_created_at").toLocalDateTime(),
              resultSet.getTimestamp("f_updated_at").toLocalDateTime()
          )
      );
    }
  }
}
