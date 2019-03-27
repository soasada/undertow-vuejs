package com.popokis.web_app_demo.entity;

import java.time.LocalDateTime;

public final class House {

  private final Long id;
  private final String name;
  private final Long userId;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public House(Long id, String name, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.userId = userId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getUserId() {
    return userId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
