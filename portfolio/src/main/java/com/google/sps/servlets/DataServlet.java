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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    // Initialize the message list with harcoded messages
    ArrayList<String> messageList = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    messageList.add("Hello Naa'Oli!"); 
    messageList.add("How are you doing Naa'Oli?");
    messageList.add("Good to see you again Naa'Oli.");
    String message = convertToJson();

    response.setContentType("text/html;");
    response.getWriter().println(message);
  }

  public String convertToJson() {
    // This method returns a string that is a JSON formatted array of messages

    String JSONString = "[";
    
    // Iterates through the message list and adds each message into the string
    for(int i = 0; i < messageList.size(); i++) {
        JSONString += "\"" + messageList.get(i) + "\"";
        if(i != messageList.size() - 1) {
            JSONString += ", ";
        }
    }

    JSONString += "]";
    return JSONString;
  }
}
