package org.tjsse.courseshare.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.User;
import org.tjsse.courseshare.dao.ResourceDao;
import org.tjsse.courseshare.dao.UserDao;
import org.tjsse.courseshare.service.ResourceService;

@Service
public class ImplResourceService implements ResourceService{
  
  @Autowired
  private ResourceDao resourceDao;
  @Autowired
  private UserDao userDao;
  
  @Override
  public User getUser() {
    return userDao.read(1);
  }

  @Override
  public List<User> getAllUsers() {
    return userDao.find(null);
  }

  @Override
  public Map<String, Object> getEverything() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("aaa", userDao.read(1));
    map.put("bbb", userDao.find());
    return map;
  }
}
