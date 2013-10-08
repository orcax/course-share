package org.tjsse.courseshare.test;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

public class OtherTester {
  public static void main(String[] args) {
//    System.out.println(LibType.SUBJECT);
//    LibType lt = LibType.FLASH;
//    System.out.println(lt.isEqual(LibType.FLASH, "active"));
//    System.out.println(System.getProperty("file.encoding"));
    Document doc = Jsoup.parse("<p><span>1</span></p><p><span>2</span></p>");
    Elements eles = doc.select("body").get(0).children();
    Element node = new Element(Tag.valueOf("span"), "");
    node.val("aaaa");
    eles.get(0).prependChild(node);
    System.out.println(eles);
  }
}
