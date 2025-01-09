package com.heisyenberg.utils;

import com.heisyenberg.configs.DataSourceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import org.aeonbits.owner.ConfigFactory;

public final class DatabaseUtil {
  private static DatabaseUtil INSTANCE;
  private final DataSource dataSource;

  private DatabaseUtil() {
    DataSourceConfig dataSourceConfig = ConfigFactory.create(DataSourceConfig.class);
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(dataSourceConfig.url());
    hikariConfig.setDriverClassName(dataSourceConfig.driverClassName());
    hikariConfig.setUsername(dataSourceConfig.username());
    hikariConfig.setPassword(dataSourceConfig.password());
    dataSource = new HikariDataSource(hikariConfig);
  }

  public static <T> T queryForObject(
      final RowMapper<T> rowMapper, final String sql, final Object... params) {
    List<T> objects = new ArrayList<>();
    executeQuery(
        preparedStatement -> {
          try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
              objects.add(rowMapper.mapRow(resultSet, 1));
            } else {
              throw new SQLException("No data found for statement: " + preparedStatement);
            }
          }
        },
        sql,
        params);
    return objects.get(0);
  }

  public static <T> List<T> queryForList(
      final RowMapper<T> rowMapper, final String sql, final Object... params) {
    List<T> objects = new ArrayList<>();
    executeQuery(
        preparedStatement -> {
          try (ResultSet resultSet = preparedStatement.executeQuery()) {
            int rowNum = 0;
            while (resultSet.next()) {
              objects.add(rowMapper.mapRow(resultSet, rowNum++));
            }
          }
        },
        sql,
        params);
    return objects;
  }

  public static int update(final String sql, final Object... params) {
    AtomicInteger updated = new AtomicInteger();
    executeQuery(preparedStatement -> updated.set(preparedStatement.executeUpdate()), sql, params);
    return updated.get();
  }

  public static void execute(final String sql, final Object... params) {
    executeQuery(PreparedStatement::execute, sql, params);
  }

  private static void executeQuery(
      final StatementHandler statementHandler, final String sql, final Object... params) {
    try (Connection connection = getInstance().dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }
      LoggingUtil.log(LogLevel.INFO, preparedStatement.toString());
      statementHandler.handle(preparedStatement);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static DatabaseUtil getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DatabaseUtil();
    }
    return INSTANCE;
  }

  private interface StatementHandler {
    void handle(final PreparedStatement preparedStatement) throws SQLException;
  }
}
