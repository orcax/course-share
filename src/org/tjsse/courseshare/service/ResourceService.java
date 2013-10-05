package org.tjsse.courseshare.service;

import java.util.List;
import java.util.Map;

import org.tjsse.courseshare.bean.User;

public interface ResourceService {
  
  public User getUser();
  
  public List<User> getAllUsers();
  
  public Map<String, Object> getEverything();
}
