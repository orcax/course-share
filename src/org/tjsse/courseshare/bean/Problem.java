package org.tjsse.courseshare.bean;

public class Problem {

  private Integer id;
  private String problemType;
  private Integer difficulty;
  private String problemContent;
  private String keyContent;
  private Integer knowledgeId;

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

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public String getProblemContent() {
    return problemContent;
  }

  public void setProblemContent(String problemContent) {
    this.problemContent = problemContent;
  }

  public String getKeyContent() {
    return keyContent;
  }

  public void setKeyContent(String keyContent) {
    this.keyContent = keyContent;
  }

  public Integer getKnowledgeId() {
    return knowledgeId;
  }

  public void setKnowledgeId(Integer knowledgeId) {
    this.knowledgeId = knowledgeId;
  }

}