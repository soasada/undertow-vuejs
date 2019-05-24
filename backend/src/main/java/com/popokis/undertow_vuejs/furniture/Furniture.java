package com.popokis.undertow_vuejs.furniture;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

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
}
