package com.google.sps.data;

/** an Instance of a comment **/
public final class Comment {
  final private String username;
  final private String reflection;

  public Comment(String username, String reflection) {
    this.username = username;
    this.reflection = reflection;
  }
  
  public String getUsername(){
    return username;
  }

  public String getReflection(){
      return reflection;
  }

} 
