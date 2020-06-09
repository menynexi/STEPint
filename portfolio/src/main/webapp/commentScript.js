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

/**
 * Adds a random suggestion by many to the page.
 */
/*function getComment() {
  console.log('Fetching a comment.');

  // The fetch() function returns a Promise because the request is asynchronous.
  const responsePromise = fetch('/commentServlet');

  // When the request is complete, pass the response into handleResponse().
  responsePromise.then(handleResponse);
}

/**
 * Handles response by converting it to text and passing the result to
 * addQuoteToDom().
 */
/*function handleResponse(response) {
  console.log('Handling the response.');

  // response.text() returns a Promise, because the response is a stream of
  // content and not a simple variable.
  const textPromise = response.text();

  // When the response is converted to text, pass the result into the
  // addQuoteToDom() function.
  textPromise.then(addCommentToDom);
}

/** Adds a random quote to the DOM. */
/*function addCommentToDom(comment) {
  console.log('Adding quote to dom: ' + comment);

  const quoteContainer = document.getElementById('comment-container');
  quoteContainer.innerText = comment;
}*/

function getComments() {
    fetch('/commentServlet').then(response => response.json()).then((comments) => {
        const commentsElement = document.getElementById('comment-container');
        commentsElement.innerHTML = " ";
        commentsElement.appendChild(createList(comments[0]));
        /*for (var i = 0; i < comments.length; i++) {
          commentsElement.appendChild(createList(comments[i]));
        }*/
    });
}

// Makes each comment a list item
function createList(text) {
    const liElement = document.createElement('li');
    liElement.innerText = text;
    return liElement;
}
