package org.tjsse.courseshare.service;

import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.util.Config;

public interface ProblemSetService {
  
  public static final String ROOT_PATH = Config.ROOT_PATH;
  public static final String PIC_PATH = ROOT_PATH + "pics/";
  public static final String PIC_URL = "/course-share/problemset/picture/";

  public static final String WORD_ENCODING = "utf-8";
  public static final String HTML_ENCODING = "utf-8";
  public static final String COMMENT_SYMBOL = "//";
  public static final String PROBLEM_TYPE_SYMBOL = "$";
  public static final String DIFFICULTY_SYMBOL = "&";
  public static final String PROBLEM_CONTENT_SYMBOL = "@1";
  public static final String KEY_CONTENT_SYMBOL = "@2";
  public static final String KNOWLEDGE_SYMBOL = "@3";

  public boolean convertDoc2Html(String docPath, String htmlPath);

  public int splitProblem(String htmlPath);
  
  public DSPicture readPicture(int id);
}
