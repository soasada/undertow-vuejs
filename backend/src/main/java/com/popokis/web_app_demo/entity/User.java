package com.popokis.web_app_demo.entity;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public abstract class User {
  public abstract Long uId();
  public abstract String uUsername();
  public abstract String uPassword();
  public abstract LocalDateTime uCreatedAt();
  public abstract LocalDateTime uUpdatedAt();
}
