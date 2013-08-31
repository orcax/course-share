package org.tjsse.courseshare.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {
  
  public <E extends Object> E read(Integer id);

  public <E extends Object> E read(Integer id, String[] fields);

  public <E extends Object> List<E> find();
  
  public <E extends Object> List<E> find(String condition);

  public <E extends Object> List<E> find(String condition, String[] fields);

  public List<Map<String, Object>> query(String sql);
  
}
