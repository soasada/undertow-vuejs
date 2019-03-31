package com.popokis.web_app_demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Value.Immutable
@JsonSerialize(as = ImmutableFurniture.class)
@JsonDeserialize(as = ImmutableFurniture.class)
public abstract class Furniture {

  @Nullable
  @JsonProperty("id")
  @Value.Parameter
  public abstract Long fId();

  @JsonProperty("name")
  @Value.Parameter
  public abstract String fName();

  @JsonProperty("type")
  @Value.Parameter
  public abstract String fType();

  @JsonProperty("houseId")
  @Value.Parameter
  public abstract Long fHouseId();

  @Nullable
  @JsonProperty("createdAt")
  @Value.Parameter
  public abstract LocalDateTime fCreatedAt();

  @Nullable
  @JsonProperty("updatedAt")
  @Value.Parameter
  public abstract LocalDateTime fUpdatedAt();
}
