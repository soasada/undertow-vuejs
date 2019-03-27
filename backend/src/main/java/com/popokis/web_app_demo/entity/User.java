package com.popokis.web_app_demo.entity;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface User {
  Long id();
  String username();
  String password();
  LocalDateTime createdAt();
  LocalDateTime updatedAt();
}
