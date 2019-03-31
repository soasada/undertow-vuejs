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
  public abstract Long id();

  @JsonProperty("name")
  @Value.Parameter
  public abstract String name();

  @JsonProperty("type")
  @Value.Parameter
  public abstract String type();

  @JsonProperty("houseId")
  @Value.Parameter
  public abstract Long houseId();

  @Nullable
  @JsonProperty("createdAt")
  @Value.Parameter
  public abstract LocalDateTime createdAt();

  @Nullable
  @JsonProperty("updatedAt")
  @Value.Parameter
  public abstract LocalDateTime updatedAt();
}
