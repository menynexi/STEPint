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
* Purpose: recieves HTTP promise response from CommentsServlet.java, places authors and comments into appropriate html container 
*/

function getComment(){
    const responsePromise = fetch('/commentServlet');
    // when server request complete, pass response into handleResponse
    responsePromise.then(handleResponse);
}

function handleResponse(response){
    // receives Java Object (ArrayList reponse)
    const commentListPromise = response.json();
    commentListPromise.then(addCommentsToDom);
}

/** Adds user and reflection to html page **/
function addCommentsToDom(commentList){
    const commentContainer = document.getElementById('comments-container');
    commentContainer.innerHTML = '';
    for (let i = 0; i < commentList.length; i++){
        commentContainer.appendChild(
            createComment(commentList[i].user, commentList[i].reflection));
    }
}

/**
 * The above code is organized to show each individual step, but we can use an
 * ES6 feature called arrow functions to shorten the code. This function
 * combines all of the above code into a single Promise chain. You can use
 * whichever syntax makes the most sense to you.
 **/
function getRandomQuoteUsingArrowFunctions() {
  fetch('/commentServlet').then(response => response.json()).then((commentList) => {
    document.getElementById('comments-container').innerHTML = '';
    for (let i = 0; i < commentList.length; i++){
        commentContainer.appendChild(
            createComment(commentList[i].user, commentList[i].reflection));
    }
  });
}


/** Creates an <li> element containing author: comment. */
function createComment(user, reflection) {
  const liElement = document.createElement('li');
  liElement.innerText = user + "--> " + reflection + "\n\n\n";
  return liElement;
} 