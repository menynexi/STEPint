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
function addRandomGreeting() {
  const greetings = 
    ['You should Read solo leveling', 'You should watch Tower of God', 'Watch Food Wars!!', '#notsponsored'];

  // Pick a random suggestion at random 
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  //pick a 
  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
} 

 function getCommentUsingArrowFunctions() {
  fetch('/data').then(response => response.text()).then((comment) => {
    document.getElementById('comment-container').innerText = comment;
  });
}