package com.heisyenberg.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
  T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
