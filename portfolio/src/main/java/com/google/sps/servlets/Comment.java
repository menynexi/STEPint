package com.google.sps.data;

/** an Instance of a comment **/
public final class Comment {
  private final String username;
  private final String reflection;
  private final long timeStamp;
  private final long id;

  public Comment(long id, String username, String reflection, long timeStamp) {
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

  public long getTimeStamp() {
    return this.timeStamp;
  }

} 
