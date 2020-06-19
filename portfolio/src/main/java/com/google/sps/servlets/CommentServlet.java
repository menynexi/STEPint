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
@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
  private static final String USERNAME_PARAMETER = "username";
  private static final String REFLECTION_PARAMETER = "reflection";
  private static final String COMMENT_PARAMETER = "Comment";
  private static final String TIMESTAMP_PARAMETER = "timestamp";

  private String userNameInput;
  private String reflectionInput;
  private long idGiven;
  private long timestampOfComment;  

  public ArrayList<Comment> comments = new ArrayList<Comment>();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
<<<<<<< HEAD
    this.userNameInput = request.getParameter(this.USERNAME_PARAMETER);
    this.reflectionInput = request.getParameter(this.REFLECTION_PARAMETER);
    this.timestampOfComment = System.currentTimeMillis();

    createEntitys();
=======
    if (request.getParameter(this.COMMENT_FORM) != null && !request.getParameter(this.COMMENT_FORM).isEmpty()) {
      storeEntities(createEntities(request));
    } else if (request.getParameter(this.MAX_FORM) != null && !request.getParameter(this.MAX_FORM).isEmpty()) {
      setMaxComment(request, request.getParameter(this.MAXCOMMENT_PARAMETER));
    }

    response.sendRedirect(this.COMMENT_HTML_PARAMETER);
  }

  public Entity createEntities(HttpServletRequest request){
    Entity commentEntity = new Entity(this.COMMENT_PARAMETER);
    System.out.println(request.getParameter(this.USERNAME_PARAMETER));
    System.out.println(request.getParameter(this.REFLECTION_PARAMETER));
    System.out.println(DATE_TIME_FORMATTER.format(LocalDateTime.now()).toString());

    commentEntity.setProperty(this.USERNAME_PARAMETER, request.getParameter(this.USERNAME_PARAMETER));
    commentEntity.setProperty(this.REFLECTION_PARAMETER, request.getParameter(this.REFLECTION_PARAMETER));
    commentEntity.setProperty(this.DATE_TIME_PARAMETER, DATE_TIME_FORMATTER.format(LocalDateTime.now()).toString());
    return commentEntity;
  }
>>>>>>> cea69e1... fixed maxComment bug on the public server

    response.sendRedirect("/comments.html");
  }

<<<<<<< HEAD
  public void createEntitys(){
    Entity commentEntity = new Entity(this.COMMENT_PARAMETER);
    commentEntity.setProperty(this.USERNAME_PARAMETER, this.userNameInput);
    commentEntity.setProperty(this.REFLECTION_PARAMETER, this.reflectionInput);
    commentEntity.setProperty(this.TIMESTAMP_PARAMETER, this.timestampOfComment);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
  }

=======
  public void setMaxComment(HttpServletRequest request, String maxComment) {
    try {
      this.maxComment = Integer.parseInt(maxComment);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Max comments cannot be converted to int: " + maxComment);
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(this.COMMENT_PARAMETER).addSort(this.DATE_TIME_PARAMETER, SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    displayMaxComments(results);
    String json = new Gson().toJson(this.comments);
    response.setContentType(this.APPLICATION_JSON_PARAMETER);
    response.getWriter().println(json);
  }
  
  public void displayMaxComments(PreparedQuery results) {
    int commentEntityIndex = 0;  
    this.comments = new ArrayList<>(); 
    for(Entity commentEntity : results.asIterable()){
      if(commentEntityIndex == this.maxComment){ 
          break;
        }
      comments.add(createCommentFromEntity(commentEntity));
      commentEntityIndex++;
    }
  }

  public Comment createCommentFromEntity(Entity commentEntity) {

      System.out.println(commentEntity.getProperty(this.USERNAME_PARAMETER));

      return new Comment(
        commentEntity.getKey().getId(), 
        commentEntity.getProperty(this.USERNAME_PARAMETER).toString(), 
        commentEntity.getProperty(this.REFLECTION_PARAMETER).toString(),
        commentEntity.getProperty(this.DATE_TIME_PARAMETER).toString()
      );
  }
>>>>>>> cea69e1... fixed maxComment bug on the public server
}
