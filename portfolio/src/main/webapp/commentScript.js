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

/*
* Purpose: recieves HTTP promise response from CommentsServlet.java, 
* places authors and comments into appropriate html container 
*/
var COMMENT_SERVLET = '/commentServlet';
var CONTAINER_SERVLET = 'comments-container';
var LI_PARAMETER = 'li';
var SPAN_PARAMETER = 'span';
var BUTTON_PARAMETER = 'button';
var DELETE_PARAMETER = 'Delete';
var COMMENT_PARAMETER = 'Comment';
var CLICK_PARAMETER = 'click';
var ID_PARAMETER = 'id';
var DELETE_SERVLET = '/delete-comment';
var POST_PARAMETER = 'post';
var ARROW_PARAMETER = '--> ';
var SPACE1_PARAMETER = '\n';
var SPACE3_PARAMETER = '\n\n\n';

function getComment(){
    const responsePromise = fetch(COMMENT_SERVLET);
    responsePromise.then(handleResponse);
}

function handleResponse(response){
    const commentListPromise = response.json();
    commentListPromise.then(addCommentsToDom);
}

/** Adds user and reflection to html page **/
function addCommentsToDom(commentList){
    const commentContainer = document.getElementById(CONTAINER_SERVLET);
    commentContainer.innerHTML = '';
    for (let i = 0; i < commentList.length; i++) {
        commentContainer.appendChild(
            createComment(commentList[i]));
    }
}

/** Creates an <li> element containing comments. */
function createComment(comment) {
  const liElement = document.createElement(LI_PARAMETER);
  liElement.className = COMMENT_PARAMETER;

  const commentElement = addTextToListElement(comment);
  const deleteButtonElement = addDeleteToListElement(liElement);
  deleteButtonElement.addEventListener(CLICK_PARAMETER, () => {
    deleteComment(comment);

    // Remove the task from the DOM.
    liElement.remove();
  });

  liElement.appendChild(commentElement);
  liElement.appendChild(deleteButtonElement);
  return liElement;
} 

function addTextToListElement(comment) {
  const commentElement = document.createElement(SPAN_PARAMETER);
  commentElement.innerText = comment.username + ARROW_PARAMETER + comment.reflection + SPACE1_PARAMETER + comment.timeStamp + SPACE3_PARAMETER;
  return commentElement;
}

function addDeleteToListElement(liElement){
  const deleteButtonElement = document.createElement(BUTTON_PARAMETER); 
  deleteButtonElement.innerText = DELETE_PARAMETER;
  return deleteButtonElement;
}

/** Tells the server to delete the task. */
function deleteComment(comment) {
  const params = new URLSearchParams();
  params.append(ID_PARAMETER, comment.id);
  fetch(DELETE_SERVLET, {method: POST_PARAMETER, body: params});
}
