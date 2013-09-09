package org.tjsse.courseshare.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.service.ProblemSetService;
import org.tjsse.courseshare.util.Config;

@Controller
@RequestMapping("/problemset")
public class ProblemSetController {

  @Autowired
  private ProblemSetService problemSetService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index() {
    return "problemset";
  }

  @RequestMapping(value = "/import", method = RequestMethod.GET)
  @ResponseBody
  public String importProblems() {
    String docPath = Config.ROOT_PATH + "test1.doc";
    String htmlPath = Config.ROOT_PATH + "test1.html";
    if (!problemSetService.convertDoc2Html(docPath, htmlPath)) {
      return "fail";
    }
    int count = problemSetService.splitProblem(htmlPath);
    if (count < 0) {
      return "fail";
    }
    return count + " problems are imported";
  }

  @RequestMapping(value = "/picture/{picId}", method = RequestMethod.GET)
  public void picture(@PathVariable Integer picId, HttpServletResponse resp) {
    DSPicture pic = problemSetService.readPicture(picId);
    resp.setContentType("image/" + pic.getMediaType());
    try {
      OutputStream os = resp.getOutputStream();
      os.write(pic.getContent());
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
}
