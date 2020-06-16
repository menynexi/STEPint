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

/** This servet is responsible for the comment data **/
@WebServlet("/commentServlet")
public class CommentServlet extends HttpServlet {
  private static final String USERNAME_PARAMETER = "username";
  private static final String REFLECTION_PARAMETER = "reflection";
  private static final String COMMENT_PARAMETER = "Comment";
  private static final String TIMESTAMP_PARAMETER = "timestamp";
  private static final String MAXCOMMENT_PARAMETER = "max-comment";
  private static final String APPLICATION_JSON_PARAMETER = "application/json;";
  private static final String COMMENT_HTML_PARAMETER = "/comments.html";
  private static final String COMMENT_FORM = "comment-form";
  private static final String MAX_FORM = "max-form";

  private String userNameInput;
  private String reflectionInput;
  private long idGiven;
  private long timestampOfComment; 
  private int maxComment = 5; 

  public List<Comment> comments;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    this.userNameInput = request.getParameter(this.USERNAME_PARAMETER);
    this.reflectionInput = request.getParameter(this.REFLECTION_PARAMETER);
    this.timestampOfComment = System.currentTimeMillis();

    if(request.getParameter(COMMENT_FORM) != null && !request.getParameter(COMMENT_FORM).isEmpty()){
        createEntitys();
    }
    else if(request.getParameter(MAX_FORM) != null && !request.getParameter(MAX_FORM).isEmpty()) {
      setMaxComment(request, request.getParameter(MAXCOMMENT_PARAMETER));
    }

    response.sendRedirect(COMMENT_HTML_PARAMETER);
  }

  public void createEntitys(){
    Entity commentEntity = new Entity(COMMENT_PARAMETER);
    commentEntity.setProperty(USERNAME_PARAMETER, userNameInput);
    commentEntity.setProperty(REFLECTION_PARAMETER, reflectionInput);
    commentEntity.setProperty(TIMESTAMP_PARAMETER, timestampOfComment);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
  }

  public void setMaxComment(HttpServletRequest request, String maxComment){
    try {
        this.maxComment = Integer.parseInt(maxComment);
    } catch (NumberFormatException e) {
        throw new NumberFormatException("Max comments cannot be converted to int: " + maxComment);
    }
  }

  public int getMaxComment(){
      return maxComment;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(COMMENT_PARAMETER).addSort(TIMESTAMP_PARAMETER, SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    displayMaxComments(results, getMaxComment());
    String json = new Gson().toJson(comments);
    response.setContentType(APPLICATION_JSON_PARAMETER);
    response.getWriter().println(json);
  }
  
  public void displayMaxComments(PreparedQuery results, int maxComment){
    comments = new ArrayList<>();
    for(Entity commentEntity : results.asIterable()){
      if(maxComment == 0){break;}
      comments.add(createCommentFromEntity(commentEntity));
      maxComment--;
    }
  }

  public Comment createCommentFromEntity(Entity commentEntity){
      Comment comment = new Comment(
        commentEntity.getKey().getId(), 
        (String) commentEntity.getProperty(USERNAME_PARAMETER), 
        (String) commentEntity.getProperty(REFLECTION_PARAMETER),
        (long) commentEntity.getProperty(TIMESTAMP_PARAMETER)
      );
    return comment;
  }
}
