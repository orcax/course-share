package org.tjsse.courseshare.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.tjsse.courseshare.service.ProblemService;
import org.tjsse.courseshare.util.Config;

public class ImplProblemService implements ProblemService {

  @Override
  public int splitProblem(String docPath) {
    
    return 0;
  }
  
  private void convertDoc2Html(String docPath) throws FileNotFoundException, IOException {
    HWPFDocument doc = new HWPFDocument(new FileInputStream(docPath));
    WordExtractor we = new WordExtractor(doc);
    Range range = doc.getRange();
    String[] paragraphs = we.getParagraphText();
    for (int i=0;i<paragraphs.length;i++) {
      Paragraph p = range.getParagraph(i);
      CharacterRun run = p.getCharacterRun(0);
      
    }
  }
  
  public static void main(String[] args) {
    ImplProblemService ps = new ImplProblemService();
    try {
      ps.convertDoc2Html(Config.ROOT_PATH + "test1.doc");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
}
