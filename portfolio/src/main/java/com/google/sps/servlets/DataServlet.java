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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */

@WebServlet("/data")
public class DataServlet extends HttpServlet {
  JSONArray messageList = new JSONArray();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Sets the JSON array to the three hard messages
    messageList.clear();
    messageList.add("Hello Naa'Oli!"); 
    messageList.add("How are you doing Naa'Oli?");
    messageList.add("Good to see you again Naa'Oli.");

    // Sends the JSON array to the /data servlet
    response.setContentType("text/html;");
    response.getWriter().println(messageList.toString());
  }

    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get Input from the Form
    String preproccessedText = request.getParameter("user-input");
    if(preproccessedText == null) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // Split String into a List, "\\s*,\\s*" is a regular expression that omits whitespace near the commas
    String[] greetings = preproccessedText.split("\\s*,\\s*");
    
    // Places the greetings in the JSON Array
    messageList.clear();
    for(int i = 0; i < greetings.length; i++) {
      messageList.add(greetings[i]);
    }
    response.setContentType("application/json;");
    response.getWriter().println(messageList.toString());
  }
}