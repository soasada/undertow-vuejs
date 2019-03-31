package com.popokis.web_app_demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableUser.class)
@JsonDeserialize(as = ImmutableUser.class)
public abstract class User {

  @Nullable
  @JsonProperty("id")
  @Value.Parameter
  public abstract Long uId();

  @JsonProperty("username")
  @Value.Parameter
  public abstract String uUsername();

  @JsonProperty("password")
  @Value.Parameter
  public abstract String uPassword();

  @Nullable
  @JsonProperty("createdAt")
  @Value.Parameter
  public abstract LocalDateTime uCreatedAt();

  @Nullable
  @JsonProperty("updatedAt")
  @Value.Parameter
  public abstract LocalDateTime uUpdatedAt();

  @Nullable
  @JsonProperty("houses")
  @Value.Parameter
  public abstract List<House> houses();
}
