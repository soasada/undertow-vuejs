package com.popokis.web_app_demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@JsonDeserialize(builder = User.UserBuilder.class)
@AllArgsConstructor(staticName = "create")
public class User {

  @Nullable Long id;
  @NonNull String username;
  @NonNull String password;
  @EqualsAndHashCode.Exclude @Nullable LocalDateTime createdAt;
  @EqualsAndHashCode.Exclude @Nullable LocalDateTime updatedAt;
  @Nullable List<House> houses;
}
