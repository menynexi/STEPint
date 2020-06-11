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
  
  private String USERNAME_INPUT;
  private String REFLECTION_INPUT;
  private long ID;
  private long TIMESTAMP_OF_COMMENT;  

  public ArrayList<Comment> comments = new ArrayList<Comment>();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    this.USERNAME_INPUT = request.getParameter(this.USERNAME_PARAMETER);
    this.REFLECTION_INPUT = request.getParameter(this.REFLECTION_PARAMETER);
    this.TIMESTAMP_OF_COMMENT = System.currentTimeMillis();

    Entity taskEntity = new Entity(this.COMMENT_PARAMETER);
    taskEntity.setProperty(this.USERNAME_PARAMETER, this.USERNAME_INPUT);
    taskEntity.setProperty(this.REFLECTION_PARAMETER, this.REFLECTION_INPUT);
    taskEntity.setProperty(this.TIMESTAMP_PARAMETER, this.TIMESTAMP_OF_COMMENT);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);

    response.sendRedirect("/comments.html");
  }

}
