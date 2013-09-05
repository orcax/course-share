package org.tjsse.courseshare.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tjsse.courseshare.bean.User;
import org.tjsse.courseshare.service.ResourceService;

@Controller
@RequestMapping("/courseware")
public class CoursewareController {
  
  @Autowired
  private ResourceService resourceService;
  
  @RequestMapping(value="", method=RequestMethod.GET)
  public String index() {
    return "courseware";
  }
  
  @RequestMapping(value="/download/{resourceId}", method=RequestMethod.GET)
  public void download() {
    
  }
  
  @RequestMapping("/test")
  @ResponseBody
  public Map<String, Object> test() {
    return resourceService.getEverything();
  }
 
}
