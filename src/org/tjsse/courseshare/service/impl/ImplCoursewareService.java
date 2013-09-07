package org.tjsse.courseshare.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
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
import org.apache.poi.hwpf.usermodel.PictureType;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.bean.User;
import org.tjsse.courseshare.dao.ResourceDao;
import org.tjsse.courseshare.dao.UserDao;
import org.tjsse.courseshare.service.CoursewareService;
import org.tjsse.courseshare.util.Config;
import org.w3c.dom.Document;

@Service
public class ImplCoursewareService implements CoursewareService {

  @Autowired
  private ResourceDao resourceDao;
  @Autowired
  private UserDao userDao;

  @Override
  public Map<String, Object> getEverything() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("aaa", userDao.read(1));
    map.put("bbb", userDao.find());
    return map;
  }


}
