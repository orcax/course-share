package org.tjsse.courseshare.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Range;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.bean.ProblemResource;
import org.tjsse.courseshare.dao.ProblemDao;
import org.tjsse.courseshare.dao.ProblemResourceDao;
import org.tjsse.courseshare.service.ProblemSetService;
import org.tjsse.courseshare.util.Config;
import org.w3c.dom.Document;

@Service
public class ImplProblemSetService implements ProblemSetService {

  @Autowired
  private ProblemResourceDao problemResourceDao;
  @Autowired
  private ProblemDao problemDao;

  /**
   * Data structure for problem detail.
   * 
   * @return Problem object.
   */
  private class ProblemInfo {

    public String currSymbol = COMMENT_SYMBOL;
    public String problemType = "";
    public int difficulty = 0;
    public StringBuffer problemContent = new StringBuffer();
    public StringBuffer keyContent = new StringBuffer();
    public String knowledge = "";

    public Problem toProblem() {
      if (problemContent.length() == 0)
        return null;
      Problem problem = new Problem();
      problem.setDifficulty(difficulty);
      problem.setProblemType(problemType);
      problem.setProblemContent(problemContent.toString());
      problem.setKeyContent(keyContent.toString());
      problem.setKnowledgeId(1);
      return problem;
    }
  }

  /**
   * Convert doc file to html file.
   * 
   * @param docPath
   *          Full path to doc file.
   * @param htmlPath
   *          Full path to html file.
   * @return True if convert successfully.
   */
  @Override
  public boolean convertDoc2Html(String docPath, String htmlPath) {
    // Initialize POI HWPF to do conversion.
    HWPFDocument wordDoc = null;
    WordToHtmlConverter converter = null;
    try {
      wordDoc = new HWPFDocument(new FileInputStream(docPath));
      converter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance()
          .newDocumentBuilder().newDocument());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    // Set PicturesManager that will save pictures in DB and FS.
    converter.setPicturesManager(new PicturesManager() {
      @Override
      public String savePicture(byte[] content, PictureType pictureType,
          String suggestedName, float widthInches, float heightInches) {
        ProblemResource pr = new ProblemResource();
        pr.setType(pictureType.getExtension().toLowerCase());
        pr.setUri(PIC_PATH);
        pr = problemResourceDao.save(pr);
        if (pr == null) {
          return "";
        }
        String picPath = String.format("%s%d.%s", PIC_PATH, pr.getId(), pr.getType());
        String picUrl = String.format("%s%d", PIC_URL, pr.getId());
        return writeFile(content, picPath) ? picUrl : "";
      }
    });
    converter.processDocument(wordDoc);

    // Generate html file.
    Document htmlDoc = converter.getDocument();
    DOMSource domSource = new DOMSource(htmlDoc);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamResult streamResult = new StreamResult(out);
    try {
      Transformer serializer = TransformerFactory.newInstance()
          .newTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING, WORD_ENCODING);
      serializer.setOutputProperty(OutputKeys.INDENT, "yes");
      serializer.setOutputProperty(OutputKeys.METHOD, "html");
      serializer.transform(domSource, streamResult);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    writeFile(new String(out.toByteArray()), htmlPath, HTML_ENCODING);
    return true;
  }

  /**
   * Split html file into several pieces.
   * 
   * @param htmlPath
   *          Full path to html file.
   * @return Number of splitted problems or -1 means failed.
   */
  @Override
  public int splitProblem(String htmlPath) {
    org.jsoup.nodes.Document html = null;
    try {
      html = Jsoup.parse(new File(htmlPath), HTML_ENCODING);
    } catch (IOException e) {
      return -1;
    }
    mergeCss(html);
    Element body = html.getElementsByTag("body").first();
    ProblemInfo pi = new ProblemInfo();
    int count = 0;

    // Traverse each line of doc file
    for (Element e : body.children()) {

      // Save element directly if not p
      if (!e.tagName().equals("p")) {
        saveElement(e, pi);
        continue;
      }

      // Skip empty line
      if (e.childNodeSize() == 0) {
        continue;
      }

      // Save element if first child is not span
      Element span = e.child(0);
      if (!span.tagName().equals("span")) {
        saveElement(e, pi);
        continue;
      }

      // Process for different symbols
      String text = span.text().trim();
      if (text.isEmpty() || text.startsWith(COMMENT_SYMBOL)) {
        continue;
      }
      if (text.startsWith(PROBLEM_TYPE_SYMBOL)) {
        pi.problemType = extractText(e).substring(1);
        pi.currSymbol = PROBLEM_TYPE_SYMBOL;
        continue;
      }
      if (text.startsWith(DIFFICULTY_SYMBOL)) {
        pi.difficulty = Integer.parseInt(extractText(e).substring(1));
        pi.currSymbol = DIFFICULTY_SYMBOL;
        continue;
      }
      if (text.startsWith(KEY_CONTENT_SYMBOL)) {
        pi.currSymbol = KEY_CONTENT_SYMBOL;
        continue;
      }
      if (text.startsWith(KNOWLEDGE_SYMBOL)) {
        pi.knowledge = KNOWLEDGE_SYMBOL;
        continue;
      }
      if (text.startsWith(PROBLEM_CONTENT_SYMBOL)) {
        if (pi.problemContent.length() != 0) {
          // System.out.println(pi.problemContent.toString());
          count += problemDao.save(pi.toProblem()) == null ? 0 : 1;
        }
        pi.problemContent = new StringBuffer();
        pi.keyContent = new StringBuffer();
        pi.currSymbol = PROBLEM_CONTENT_SYMBOL;
        continue;
      }
      saveElement(e, pi);
    }
    if (pi.problemContent.length() != 0) {
      count += problemDao.save(pi.toProblem()) == null ? 0 : 1;
    }
    return count;
  }

  /**
   * Read picture file on disk.
   * 
   * @param prId
   *          Id for ProblemResource object
   * @return Byte array for picture content
   */
  @Override
  public DSPicture readPicture(int prId) {
    ProblemResource pr = problemResourceDao.read(prId);
    if (pr == null) {
      return null;
    }
    String path = String.format("%s%d.%s", pr.getUri(), pr.getId(),
        pr.getType());
    File pic = new File(path);
    if (!pic.exists()) {
      return null;
    }
    byte[] data = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(pic);
      data = new byte[(int) pic.length()];
      fis.read(data);
    } catch (IOException e) {
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        return null;
      }
    }
    DSPicture result = new DSPicture();
    result.setMediaType(pr.getType());
    result.setContent(data);
    return result;
  }

  /**
   * Merge CSS inside style tag into specific elements.
   * 
   * @param html
   *          Document object of full html.
   */
  private void mergeCss(org.jsoup.nodes.Document html) {
    Element style = html.select("style").first();
    String css = style.data().replaceAll("\n", "").trim();
    StringTokenizer st = new StringTokenizer(css, "{}");
    while (st.countTokens() > 1) {
      String selector = st.nextToken();
      String property = st.nextToken();
      Elements elements = html.select(selector);
      for (Element e : elements) {
        e.attr("style", property);
      }
    }
  }

  /**
   * Save element html of current problem to ProblemInfo structure.
   * 
   * @param element
   *          Element object.
   * @param pi
   *          ProblemInfo structure object.
   */
  private void saveElement(Element element, ProblemInfo pi) {
    if (element == null || pi == null || pi.currSymbol == null)
      return;
    switch (pi.currSymbol) {
    case PROBLEM_CONTENT_SYMBOL:
      if (pi.problemContent == null)
        pi.problemContent = new StringBuffer();
      pi.problemContent.append(element.outerHtml());
      break;
    case KEY_CONTENT_SYMBOL:
      if (pi.keyContent == null)
        pi.keyContent = new StringBuffer();
      pi.keyContent.append(element.html());
    case KNOWLEDGE_SYMBOL:
      pi.knowledge = extractText(element);
    }
  }

  /**
   * Extract all text of specific element.
   * 
   * @param element
   * @return
   */
  private String extractText(Element element) {
    Elements elements = element.children();
    StringBuffer sb = new StringBuffer();
    for (Element e : elements) {
      sb.append(e.text().trim());
    }
    return sb.toString();
  }

  private void writeFile(String content, String path, String encoding) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    try {
      File file = new File(path);
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos, encoding));
      bw.write(content);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (bw != null)
          bw.close();
        if (fos != null)
          fos.close();
      } catch (IOException ie) {
      }
    }
  }

  private boolean writeFile(byte[] content, String path) {
    FileOutputStream fos = null;
    boolean success = true;
    try {
      fos = new FileOutputStream(new File(path));
      fos.write(content);
    } catch (IOException e) {
      success = false;
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (IOException e) {
        return false;
      }
    }
    return success;
  }

  public static void main(String[] args) {
    try {
      ImplProblemSetService ics = new ImplProblemSetService();
      // ics.convertDoc2Html(ROOT_PATH + "test1.doc", ROOT_PATH + "test1.html");
      ics.splitProblem(ROOT_PATH + "test1.html");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
