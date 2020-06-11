package com.google.sps.data;

/** an Instance of a comment **/
public final class Comment {
  final private String user;
  final private String reflection;

  public Comment(String user, String reflection) {
    this.user = user;
    this.reflection = reflection;
  }
  
  public getUser(){
    return user;
  }

  public getRelfecltion(){
      return reflection;
  }

} 
