package com.popokis.undertow_vuejs.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.popokis.popok.sql_db.JdbcMapper;
import com.popokis.undertow_vuejs.house.House;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import javax.annotation.Nullable;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

  public String hashPassword() {
    try {
      byte[] salt = "S3cur·ñeSalt87GG".getBytes();
      KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
      return Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  public static JdbcMapper<User> mapper() {
    return new Mapper();
  }

  private static class Mapper implements JdbcMapper<User> {
    @Override
    public Optional<User> map(ResultSetWrappingSqlRowSet resultSet) {
      if (resultSet.getLong("u_id") == 0) return Optional.empty();

      return Optional.of(
          User.create(
              resultSet.getLong("u_id"),
              resultSet.getString("u_username"),
              resultSet.getString("u_password"),
              resultSet.getTimestamp("u_created_at").toLocalDateTime(),
              resultSet.getTimestamp("u_updated_at").toLocalDateTime(),
              List.of()
          )
      );
    }
  }
}
