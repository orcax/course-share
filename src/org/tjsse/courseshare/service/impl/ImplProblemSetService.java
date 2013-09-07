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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Range;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.service.ProblemSetService;
import org.tjsse.courseshare.util.Config;
import org.w3c.dom.Document;

public class ImplProblemSetService implements ProblemSetService {

  /**
   * Convert doc file into html file.
   * 
   * @param docPath
   *          Full path to doc file.
   * @param htmlPath
   *          Full path to html file.
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParserConfigurationException
   * @throws TransformerFactoryConfigurationError
   * @throws TransformerException
   */
  private void convertDoc2Html(String docPath, String htmlPath)
      throws FileNotFoundException, IOException, ParserConfigurationException,
      TransformerFactoryConfigurationError, TransformerException {
    HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(docPath));
    WordToHtmlConverter converter = new WordToHtmlConverter(
        DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
    converter.setPicturesManager(new PicturesManager() {
      @Override
      public String savePicture(byte[] content, PictureType pictureType,
          String suggestedName, float widthInches, float heightInches) {
        return Config.ROOT_PATH + suggestedName;
      }
    });
    converter.processDocument(wordDoc);
    Document htmlDoc = converter.getDocument();
    DOMSource domSource = new DOMSource(htmlDoc);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamResult streamResult = new StreamResult(out);
    Transformer serializer = TransformerFactory.newInstance().newTransformer();
    serializer.setOutputProperty(OutputKeys.ENCODING, Config.WORD_ENCODING);
    serializer.setOutputProperty(OutputKeys.INDENT, "yes");
    serializer.setOutputProperty(OutputKeys.METHOD, "html");
    serializer.transform(domSource, streamResult);
    out.close();
    writeFile(new String(out.toByteArray()), htmlPath);
  }

  /**
   * Split html file into several pieces.
   * 
   * @param htmlPath
   *          Full path to html file.
   * @throws IOException
   */
  private void splitProblem(String htmlPath) throws IOException {
    org.jsoup.nodes.Document html = Jsoup.parse(new File(htmlPath),
        Config.HTML_ENCODING);
    replaceCss(html);
    Element body = html.getElementsByTag("body").first();
    ProblemInfo pi = new ProblemInfo();

    // Traverse each line of doc file
    for (Element e : body.children()) {

      // Save element if not p
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

      // Judge if is symbol
      String text = span.text().trim();
      if (text.isEmpty() || text.startsWith(Config.COMMENT_SYMBOL)) {
        continue;
      }
      switch (text) {
      case Config.PROBLEM_TYPE_SYMBOL:
        pi.problemType = extractText(e).substring(1);
        pi.currSymbol = Config.PROBLEM_TYPE_SYMBOL;
        break;
      case Config.DIFFICULTY_SYMBOL:
        pi.difficulty = Integer.parseInt(extractText(e).substring(1));
        pi.currSymbol = Config.DIFFICULTY_SYMBOL;
        break;
      case Config.KEY_CONTENT_SYMBOL:
        pi.currSymbol = Config.KEY_CONTENT_SYMBOL;
        break;
      case Config.KNOWLEDGE_SYMBOL:
        pi.knowledge = Config.KNOWLEDGE_SYMBOL;
        break;
      case Config.PROBLEM_CONTENT_SYMBOL:
        if (pi.problemContent != null && pi.problemContent.length() != 0) {
          saveProblem(pi.toProblem());
        }
        pi.problemContent = new StringBuffer();
        pi.keyContent = new StringBuffer();
        pi.currSymbol = Config.PROBLEM_CONTENT_SYMBOL;
        break;
      default:
        saveElement(e, pi);
      }
    }
    if (pi.problemContent.length() != 0) {
      saveProblem(pi.toProblem());
    }
  }

  private void replaceCss(org.jsoup.nodes.Document html) {
    Element style = html.select("style").first();
    String css = style.data().replaceAll("\n", "").trim();
    StringTokenizer st = new StringTokenizer(css, "{}");
    Map<String, String> map = new HashMap<String, String>();
    while (st.countTokens() > 1) {
      String selector = st.nextToken();
      String property = st.nextToken();
      Elements elements = html.select(selector);
      for(Element e: elements) {
        e.attr("style", property);
      }
    }
  }

  private void saveElement(org.jsoup.nodes.Element element, ProblemInfo pi) {
    if (element == null || pi == null || pi.currSymbol == null)
      return;
    switch (pi.currSymbol) {
    case Config.PROBLEM_CONTENT_SYMBOL:
      if (pi.problemContent == null)
        pi.problemContent = new StringBuffer();
      pi.problemContent.append(element.html());
      break;
    case Config.KEY_CONTENT_SYMBOL:
      if (pi.keyContent == null)
        pi.keyContent = new StringBuffer();
      pi.keyContent.append(element.html());
    case Config.KNOWLEDGE_SYMBOL:
      pi.knowledge = extractText(element);
    }
  }

  private void saveProblem(Problem problem) {
     System.out.println(problem.getProblemContent());
  }

  private String extractText(org.jsoup.nodes.Element element) {
    org.jsoup.select.Elements elements = element.children();
    StringBuffer sb = new StringBuffer();
    for (org.jsoup.nodes.Element e : elements) {
      sb.append(e.text().trim());
    }
    return sb.toString();
  }

  private void writeFile(String content, String path) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    try {
      File file = new File(path);
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos, Config.HTML_ENCODING));
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

  public static void main(String[] args) {
    try {
      ImplProblemSetService ics = new ImplProblemSetService();
      ics.convertDoc2Html(Config.ROOT_PATH + "test1.doc", Config.ROOT_PATH
          + "test1.html");
      ics.splitProblem(Config.ROOT_PATH + "test1.html");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class ProblemInfo {
  public String currSymbol = Config.COMMENT_SYMBOL;
  public String problemType = "";
  public int difficulty = 0;
  public StringBuffer problemContent = null;
  public StringBuffer keyContent = null;
  public String knowledge = "";

  public Problem toProblem() {
    if (problemContent.length() == 0)
      return null;
    Problem problem = new Problem();
    problem.setDifficulty(difficulty);
    problem.setProblemType(problemType);
    problem.setProblemContent(problemContent.toString());
    problem.setKeyContent(keyContent.toString());
    return problem;
  }
}
