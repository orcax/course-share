package org.tjsse.courseshare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tjsse.courseshare.bean.User;
import org.tjsse.courseshare.service.ResourceService;

@Controller
@RequestMapping("/courseware")
public class CoursewareController {
  
  @Autowired
  private ResourceService resourceService;
  
  @RequestMapping("/index")
  public String index() {
    return "courseware";
  }
  
  @RequestMapping("/test")
  @ResponseBody
  public List<String> test() {
    List<String> list = new ArrayList<String>();
    list.add("aaaaa");
    list.add("bbbbbb");
    return list;
  }
}
