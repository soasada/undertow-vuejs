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
  public abstract Long id();

  @JsonProperty("username")
  @Value.Parameter
  public abstract String username();

  @JsonProperty("password")
  @Value.Parameter
  public abstract String password();

  @Nullable
  @JsonProperty("createdAt")
  @Value.Parameter
  public abstract LocalDateTime createdAt();

  @Nullable
  @JsonProperty("updatedAt")
  @Value.Parameter
  public abstract LocalDateTime updatedAt();

  @Nullable
  @JsonProperty("houses")
  @Value.Parameter
  public abstract List<House> houses();
}
