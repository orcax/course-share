package org.tjsse.courseshare.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.bean.Problem;
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
    String docPath = Config.ROOT_PATH + "problem2.doc";
    String htmlPath = Config.ROOT_PATH + "problem2.html";
    if (!problemSetService.convertDoc2Html(docPath, htmlPath)) {
      return "fail";
    }
    int count = problemSetService.splitProblem(htmlPath);
    if (count < 0) {
      return "fail";
    }
    return count + " problems are imported";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public List<Problem> listProblems(
      @RequestParam(value="problem_type", required=false) String problemType, 
      @RequestParam(value="problem_content", required=false) String problemContent) {
    System.out.println("content:" + problemContent);
    if (problemType == null && problemContent == null) {
      return problemSetService.findProblems();
    }

    // Get problems for specific types
    String[] types = null;
    if (problemType != null) {
      StringTokenizer st = new StringTokenizer(problemType.trim(), ",");
      types = new String[st.countTokens()];
      for (int i = 0; i < st.countTokens(); i++) {
        types[i] = mapCnProblemType(st.nextToken().trim());
      }
    }
    List<Problem> p1 = problemSetService.findProblemsByTypes(types);

    // Get problems for specific contents
    String[] contents = null;
    if (problemContent != null) {
      StringTokenizer st = new StringTokenizer(problemContent.trim(), " ");
      contents = new String[st.countTokens()];
      for (int i = 0; i < st.countTokens(); i++) {
        contents[i] = st.nextToken();
      }
    }
    List<Problem> p2 = problemSetService.findProblems(contents);

    // Merge above problems and return
    Set<Integer> ids = new TreeSet<Integer>();
    for (int i = 0; i < p1.size(); i++) {
      ids.add(p1.get(i).getId());
    }
    List<Problem> p3 = new ArrayList<Problem>();
    for (int i = 0; i < p2.size(); i++) {
      if (ids.contains(p2.get(i).getId())) {
        p3.add(p2.get(i));
      }
    }
    return p3;
  }

  private String mapCnProblemType(String en) {
    switch (en) {
    case "type_concept":
      return "概念题";
    case "type_blankfill":
      return "填空题";
    case "type_choice":
      return "选择题";
    case "type_question":
      return "问答题";
    case "type_integrate":
      return "综合题";
    }
    return "";
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
