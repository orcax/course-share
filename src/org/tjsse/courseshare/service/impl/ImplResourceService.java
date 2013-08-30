package org.tjsse.courseshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.dao.ResourceDao;
import org.tjsse.courseshare.service.ResourceService;

@Service
public class ImplResourceService implements ResourceService{
  
  @Autowired
  private ResourceDao resourceDao;
}
