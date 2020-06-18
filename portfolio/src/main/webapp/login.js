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

/**
 * Fetches a random quote from the server and adds it to the DOM.
 */
function getLogin() {
  console.log('Fetching login page');

  // The fetch() function returns a Promise because the request is asynchronous.
  const responsePromise = fetch('/login');

  // When the request is complete, pass the response into handleResponse().
  responsePromise.then(handleResponse);
}

/**
 * Handles response by converting it to text and passing the result to
 * addQuoteToDom().
 */
function handleResponse() {
  console.log('Handling login response.');

  // response.text() returns a Promise, because the response is a stream of
  // content and not a simple variable.
  const textPromise = response.text();

  // When the response is converted to text, pass the result into the
  // addQuoteToDom() function.
  textPromise.then(addLoginToDom);
}

/** Adds a random quote to the DOM. */
function addLoginToDom(login) {
  console.log('Adding login to dom: ');

  const authInfo = document.getElementById('login-container');
  authInfo.innerHTML = authInfo;
}

function login(){
    const responsePromise = fetch('/login');
    responsePromise.then(response => response.text())
    .then(authInfo => {
        document.getElementById("login-container").innerHTML = authInfo;
    });
}