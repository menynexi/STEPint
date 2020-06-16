// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.sps.data.Comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  

/** This servet is responsible for the comment data **/
@WebServlet("/commentServlet")
public class CommentServlet extends HttpServlet {
  private static final String USERNAME_PARAMETER = "username";
  private static final String REFLECTION_PARAMETER = "reflection";
  private static final String COMMENT_PARAMETER = "Comment";
  private static final String DATE_TIME_PARAMETER = "timestamp";
  private static final String MAXCOMMENT_PARAMETER = "max-comment";
  private static final String APPLICATION_JSON_PARAMETER = "application/json;";
  private static final String COMMENT_HTML_PARAMETER = "/comments.html";
  private static final String COMMENT_FORM = "comment-form";
  private static final String MAX_FORM = "max-form";

  private String userNameInput;
  private String reflectionInput;
  private String dateTime;
  private long idGiven; 
  private int maxComment = 5; 

  public List<Comment> comments;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    userNameInput = request.getParameter(this.USERNAME_PARAMETER);
    reflectionInput = request.getParameter(this.REFLECTION_PARAMETER);
    dateTime = getCurrentTimeString();

    if (request.getParameter(COMMENT_FORM) != null && !request.getParameter(COMMENT_FORM).isEmpty()){
        storeEntities(createEntities());

    } else if (request.getParameter(MAX_FORM) != null && !request.getParameter(MAX_FORM).isEmpty()) {
      setMaxComment(request, request.getParameter(MAXCOMMENT_PARAMETER));
    }

    response.sendRedirect(COMMENT_HTML_PARAMETER);
  }

  public Entity createEntities(){
    Entity commentEntity = new Entity(COMMENT_PARAMETER);
    commentEntity.setProperty(USERNAME_PARAMETER, userNameInput);
    commentEntity.setProperty(REFLECTION_PARAMETER, reflectionInput);
    commentEntity.setProperty(DATE_TIME_PARAMETER, dateTime);
    return commentEntity;
  }

  public void storeEntities(Entity commentEntity) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
  }

  public void setMaxComment(HttpServletRequest request, String maxComment) {
    try {
        this.maxComment = Integer.parseInt(maxComment);
    } catch (NumberFormatException e) {
        throw new NumberFormatException("Max comments cannot be converted to int: " + maxComment);
    }
  }

  public int getMaxComment() {
      return maxComment;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(COMMENT_PARAMETER).addSort(DATE_TIME_PARAMETER, SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    displayMaxComments(results, getMaxComment());
    String json = new Gson().toJson(comments);
    response.setContentType(APPLICATION_JSON_PARAMETER);
    response.getWriter().println(json);
  }
  
  public void displayMaxComments(PreparedQuery results, int maxComment) {
    int commentEntityIndex = 0;  
    comments = new ArrayList<>(); 
    for(Entity commentEntity : results.asIterable()){
      if(commentEntityIndex == maxComment){ 
          break;
        }
      comments.add(createCommentFromEntity(commentEntity));
      commentEntityIndex++;
    }
  }

  public String getCurrentTimeString() {  
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
   LocalDateTime now = LocalDateTime.now();
   return(dtf.format(now).toString());
  }  

  public Comment createCommentFromEntity(Entity commentEntity) {
      return new Comment(
        commentEntity.getKey().getId(), 
        commentEntity.getProperty(USERNAME_PARAMETER).toString(), 
        commentEntity.getProperty(REFLECTION_PARAMETER).toString(),
        commentEntity.getProperty(DATE_TIME_PARAMETER).toString()
      );
  }
}
