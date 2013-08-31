package org.tjsse.courseshare.bean;

public class Problem {

  private Integer id;
  private String problemType;
  private String problemMediaType;
  private String problemFileFormat;
  private String problemUrl;
  private Integer difficulty;
  private String keyMediaType;
  private String keyFileFormat;
  private String keyUrl;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProblemType() {
    return problemType;
  }

  public void setProblemType(String problemType) {
    this.problemType = problemType;
  }

  public String getProblemMediaType() {
    return problemMediaType;
  }

  public void setProblemMediaType(String problemMediaType) {
    this.problemMediaType = problemMediaType;
  }

  public String getProblemFileFormat() {
    return problemFileFormat;
  }

  public void setProblemFileFormat(String problemFileFormat) {
    this.problemFileFormat = problemFileFormat;
  }

  public String getProblemUrl() {
    return problemUrl;
  }

  public void setProblemUrl(String problemUrl) {
    this.problemUrl = problemUrl;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public String getKeyMediaType() {
    return keyMediaType;
  }

  public void setKeyMediaType(String keyMediaType) {
    this.keyMediaType = keyMediaType;
  }

  public String getKeyFileFormat() {
    return keyFileFormat;
  }

  public void setKeyFileFormat(String keyFileFormat) {
    this.keyFileFormat = keyFileFormat;
  }

  public String getKeyUrl() {
    return keyUrl;
  }

  public void setKeyUrl(String keyUrl) {
    this.keyUrl = keyUrl;
  }

}
