package org.tjsse.courseshare.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.tjsse.courseshare.bean.Problem;

public class PaperMaker {

  public static final String TITLE = "计算机系统结构试卷";

  public static String makeHtml(List<Problem> problems) {
    Map<String, List<Problem>> sortedProblems = new HashMap<String, List<Problem>>();
    for (Problem p : problems) {
      if (!sortedProblems.containsKey(p.getProblemType())) {
        sortedProblems.put(p.getProblemType(), new ArrayList<Problem>());
      }
      sortedProblems.get(p.getProblemType()).add(p);
    }
    String[] problemTypes = { ProblemType.CONCEPT.getName(),
        ProblemType.BLANKFILL.getName(), ProblemType.CHOICE.getName(),
        ProblemType.QUESTION.getName(), ProblemType.INTEGRATE.getName() };
    StringBuffer content = new StringBuffer("<html><body>");
    content.append("<h2>" + TITLE + "</h2>");

    for (String type : problemTypes) {
      List<Problem> plist = sortedProblems.get(type);
      if (plist == null || plist.size() <= 0)
        continue;
      content.append("<h3>" + type + "</h3>");
      for (int i = 0; i < plist.size(); i++) {
        String pcontent = plist.get(i).getProblemContent();
        pcontent = prependOrder(pcontent, i + 1);
        System.out.println(pcontent);
        content.append(pcontent);
      }
      content.append("<br>");
    }

    content.append("</body></html>");
    return content.toString();
  }

  private static String prependOrder(String html, int order) {
    Document doc = Jsoup.parse(html);
    Elements eles = doc.select("body").get(0).children();
    Element node = new Element(Tag.valueOf("span"), "");
    node.text(order + ". ");
    eles.get(0).prependChild(node);
    return eles.toString();
  }

  public static void main(String[] args) {
//    System.out.println(ProblemType.name2Type("概念题"));
    System.out.println(prependOrder("<p><hr></p>", 1));
  }
}
