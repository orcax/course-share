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
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.service.ProblemSetService;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

@Controller
@RequestMapping("/problemset")
public class ProblemsetController {

  @Autowired
  private ProblemSetService problemSetService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("problemset", "libType", LibType.PROBLEMSET);
  }

  @RequestMapping(value = "/import/{problems}", method = RequestMethod.GET)
  @ResponseBody
  public String importProblems(@PathVariable String problems) {
    if(problems == null)
      return "0 problems are imported.";
    StringTokenizer st = new StringTokenizer(problems, ",");
    int count = 0;
    while(st.hasMoreTokens()) {
      String p = st.nextToken().trim();
      if(p.isEmpty())
        continue;
      String docPath = Config.ROOT_PATH + p + ".doc";
      String htmlPath = Config.ROOT_PATH + p + ".html";
      if (!problemSetService.convertDoc2Html(docPath, htmlPath))
        continue;
      int c = problemSetService.splitProblem(htmlPath);
      if(c > 0)
        count += c;
    }
    return count + " problems are imported";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public List<Problem> listProblems(
      @RequestParam(value = "problem_type", required = false) String problemType,
      @RequestParam(value = "difficulty", required = false) String difficulty,
      @RequestParam(value = "problem_content", required = false) String problemContent,
      @RequestParam(value = "knowledge", required = false) String knowledge) {
    System.out.println("type:" + problemType);
    System.out.println("difficulty:" + difficulty);
    System.out.println("content:" + problemContent);
    System.out.println("knowledge:" + knowledge);

    // Get problems for specific types
    List<String> types = new ArrayList<String>();
    String[] pts = null;
    if (problemType != null) {
      StringTokenizer st = new StringTokenizer(problemType, ",");
      while (st.hasMoreTokens()) {
        String t = mapCnProblemType(st.nextToken().trim());
        if (!t.isEmpty())
          types.add(t);
      }
      if (!types.isEmpty())
        pts = types.toArray(new String[types.size()]);
    }

    // Get problems for specific difficulty
    List<Integer> diffs = new ArrayList<Integer>();
    Integer[] pds = null;
    if (difficulty != null) {
      StringTokenizer st = new StringTokenizer(difficulty, ",");
      while (st.hasMoreTokens()) {
        Integer d = Integer.parseInt(st.nextToken().trim());
        diffs.add(d);
      }
      if (!diffs.isEmpty())
        pds = diffs.toArray(new Integer[diffs.size()]);
    }

    // Get problems for specific contents
    List<String> contents = new ArrayList<String>();
    String[] pcs = null;
    if (problemContent != null) {
      StringTokenizer st = new StringTokenizer(problemContent);
      while (st.hasMoreTokens()) {
        String c = st.nextToken().trim();
        if (!c.isEmpty())
          contents.add(c);
      }
      if (!contents.isEmpty())
        pcs = contents.toArray(new String[contents.size()]);
    }

    // Get problems for specific knowledge
    List<String> knows = new ArrayList<String>();
    String[] pks = null;
    if (knowledge != null) {
      StringTokenizer st = new StringTokenizer(knowledge);
      while (st.hasMoreTokens()) {
        String k = st.nextToken().trim();
        if (!k.isEmpty())
          knows.add(k);
      }
      if (!knows.isEmpty())
        pks = knows.toArray(new String[knows.size()]);
    }
    System.out.println("pks:" + pks);

    return problemSetService.findProblems(pts, pds, pcs, pks);
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


  private String mapCnProblemType(String en) {
    if ("type_concept".equals(en))
      return "概念题";
    if ("type_blankfill".equals(en))
      return "填空题";
    if ("type_choice".equals(en))
      return "选择题";
    if ("type_question".equals(en))
      return "问答题";
    if ("type_integrate".equals(en))
      return "综合题";
    return "";
  }

  }
