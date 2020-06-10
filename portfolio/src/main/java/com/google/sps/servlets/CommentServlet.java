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

/** This servet is responsible for the comment data **/
@WebServlet("/commentServlet")
public class CommentServlet extends HttpServlet {

  public ArrayList<Comment> comments = new ArrayList<Comment>();

  /* responds with a json string*/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Convert the ArrayLists to JSON

    Gson gson = new Gson();
    String json = gson.toJson(comments);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  /* when the submit button on the values page is hit, doPost requests the text in the author and commment field and stores them*/ 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
    // Get the input from the comments form
    String nameStr = request.getParameter("user");
    String reflectionStr = request.getParameter("reflection");
    comments.add(new Comment(nameStr, reflectionStr));

    // redirect back to comments page
    response.sendRedirect("/comments.html");
  }
}
