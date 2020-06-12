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

  private String userNameInput;
  private String reflectionInput;
  private long idGiven;
  private long timestampOfComment;  

  public List<Comment> comments;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    this.userNameInput = request.getParameter(this.USERNAME_PARAMETER);
    this.reflectionInput = request.getParameter(this.REFLECTION_PARAMETER);
    this.timestampOfComment = System.currentTimeMillis();

    createEntitys();
    response.sendRedirect("/comments.html");
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(COMMENT_PARAMETER).addSort(TIMESTAMP_PARAMETER, SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    populateList(results);
    /*this.comments = new ArrayList<>();
    for (Entity commentEntity : results.asIterable()) {
      this.idGiven = commentEntity.getKey().getId();
      this.userNameInput = (String) commentEntity.getProperty(USERNAME_PARAMETER);
      this.reflectionInput = (String) commentEntity.getProperty(REFLECTION_PARAMETER);
      this.timestampOfComment = (long) commentEntity.getProperty(TIMESTAMP_PARAMETER);

      Comment comment = new Comment(idGiven, userNameInput, reflectionInput,timestampOfComment);
      comments.add(comment);
    }*/

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(this.comments));
  }

  public void createEntitys(){
    Entity commentEntity = new Entity(this.COMMENT_PARAMETER);
    commentEntity.setProperty(this.USERNAME_PARAMETER, this.userNameInput);
    commentEntity.setProperty(this.REFLECTION_PARAMETER, this.reflectionInput);
    commentEntity.setProperty(this.TIMESTAMP_PARAMETER, this.timestampOfComment);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
  }

  public void populateList(PreparedQuery results){
    this.comments = new ArrayList<>();
    for (Entity commentEntity : results.asIterable()) {
      this.idGiven = commentEntity.getKey().getId();
      this.userNameInput = (String) commentEntity.getProperty(USERNAME_PARAMETER);
      this.reflectionInput = (String) commentEntity.getProperty(REFLECTION_PARAMETER);
      this.timestampOfComment = (long) commentEntity.getProperty(TIMESTAMP_PARAMETER);

      Comment comment = new Comment(idGiven, userNameInput, reflectionInput,timestampOfComment);
      comments.add(comment);
    }
  }

}
