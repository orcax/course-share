package org.tjsse.courseshare.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tjsse.courseshare.dao.BaseDao;

@Repository
public class JdbcBaseDao implements BaseDao {

  private JdbcTemplate jdbcTemplate;
  
  @Autowired
  protected void setJdbcTemplate(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }
}
