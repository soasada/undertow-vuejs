package com.popokis.web_app_demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableHouse.class)
@JsonDeserialize(as = ImmutableHouse.class)
public abstract class House {

  @Nullable
  @JsonProperty("id")
  @Value.Parameter
  public abstract Long hId();

  @JsonProperty("name")
  @Value.Parameter
  public abstract String hName();

  @JsonProperty("userId")
  @Value.Parameter
  public abstract Long hUserId();

  @Nullable
  @JsonProperty("createdAt")
  @Value.Parameter
  public abstract LocalDateTime hCreatedAt();

  @Nullable
  @JsonProperty("updatedAt")
  @Value.Parameter
  public abstract LocalDateTime hUpdatedAt();

  @Nullable
  @JsonProperty("furniture")
  @Value.Parameter
  public abstract List<Furniture> furniture();
}
