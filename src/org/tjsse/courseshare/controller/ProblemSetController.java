package org.tjsse.courseshare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("problemset")
public class ProblemSetController {

  @RequestMapping(value="", method=RequestMethod.GET)
  public String index() {
    return "problemset";
  }
}
