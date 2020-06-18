package com.google.sps.data;

/** an Instance of a comment **/
public final class Comment {
  private final String username;
  private final String reflection;
  private final String timeStamp;
  private final long id;
  private int maxComment;

  public Comment(long id, String username, String reflection, String timeStamp) {
    this.id = id;
    this.username = username;
    this.reflection = reflection;
    this.timeStamp = timeStamp;
  }

  public String getUsername() {
    return this.username;
  }

  public String getReflection() {
    return this.reflection;
  }

  public long getId() {
    return this.id;
  }

  public String getTimeStamp() {
    return this.timeStamp;
  }

  public void setMaxComment(int maxComment) {
      this.maxComment = maxComment;
  }

  public int getMaxComment() {
      return this.maxComment;
  }
} 
