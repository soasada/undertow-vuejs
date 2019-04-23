package com.popokis.web_app_demo.exception;

import java.util.Objects;

public final class Exceptions {

  private Exceptions() {}

  public static Throwable rootCause(Throwable throwable) {
    return Objects.nonNull(throwable.getCause()) ? rootCause(throwable.getCause()) : throwable;
  }
}
