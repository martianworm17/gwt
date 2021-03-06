/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * This startup script is used when we run superdevmode from an app server.
 */
(function($wnd, $doc){
  // document.head does not exist in IE8
  var $head = $doc.head || $doc.getElementsByTagName('head')[0];
  // Compute some codeserver urls so as the user does not need bookmarklets
  var hostName = $wnd.location.hostname;
  var serverUrl = 'http://' + hostName + ':__SUPERDEV_PORT__';
  var nocacheUrl = serverUrl + '/__MODULE_NAME__/__MODULE_NAME__.recompile.nocache.js';

  // Insert the superdevmode nocache script in the first position of the head
  var devModeScript = $doc.createElement('script');
  devModeScript.src = nocacheUrl;

  // Everybody except IE8 does fire an error event
  // This means that we do not detect a non running SDM with IE8.
  if (devModeScript.addEventListener) {
    var callback = function() {
      if (!$wnd.__gwt_sdm__recompiler || !$wnd.__gwt_sdm__recompiler.loaded) {
        $wnd.alert("Could not load application from super dev mode. " +
            "Make sure your super dev mode server is running and hit reload.");
      }
    };
    devModeScript.addEventListener("error", callback, true);
  }

  //defer app script insertion until the body is ready
  setTimeout(function(){
    $head.insertBefore(devModeScript, $head.firstElementChild || $head.children[0]);
  }, 1);
})(window, document);
