package com.popokis.undertow_vuejs.house;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.popokis.undertow_vuejs.furniture.Furniture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

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
}
