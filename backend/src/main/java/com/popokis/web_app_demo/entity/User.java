package com.popokis.web_app_demo.entity;

import java.time.LocalDateTime;

public final class User {

  private final Long id;
  private final String username;
  private final String password;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  private User(Long id, String username, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static User of(Long id, String username, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new User(id, username, password, createdAt, updatedAt);
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
