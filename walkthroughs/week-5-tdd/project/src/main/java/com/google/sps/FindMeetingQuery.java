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

package com.google.sps;

import java.util.ArrayList;	
import java.util.Collection;
import java.util.Collections;	
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    if(request.getOptionalAttendees().size() == 0) {
        return getOpenSlots(events, request);	
    }	

    Collection<String> allAttendees = new ArrayList<String>();	
    allAttendees.addAll(request.getAttendees());	
    allAttendees.addAll(request.getOptionalAttendees());	
    MeetingRequest allAttendeesRequest = new MeetingRequest(allAttendees, request.getDuration());	
    Collection<TimeRange> openSlotsForMandatoryAttendees = getOpenSlots(events, request);	
    Collection<TimeRange> openSlotsForAllAttendees = getOpenSlots(events, new MeetingRequest(allAttendees, request.getDuration()));	

    if(openSlotsForAllAttendees.size() == 0 && request.getAttendees().size() > 0) {	
      return openSlotsForMandatoryAttendees;	
    } else {	
      return openSlotsForAllAttendees;	
    }	
  }	

  private Collection<TimeRange> getOpenSlots(Collection<Event> events, MeetingRequest request) {	
    List<TimeRange> openSlots = new ArrayList<TimeRange>();	
    int startTime = TimeRange.START_OF_DAY;	
    int endTime = TimeRange.START_OF_DAY;	
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {	
      return openSlots;	
    }	

    // Going through the events and adding an open slot between events for mandatory attendees	
    for (Event e : events) {	
      // If none of the attendees are attending the event, skip this event	
      if (!anyoneAttending(request.getAttendees(), e)) {	
        continue;	
      }	
      if (startTime < e.getWhen().start() && endTime < e.getWhen().start()) {	
        // Set end time of the open slot before this event as the start time of this event	
        endTime = e.getWhen().start();	
        // Only add the open slot if it can fit the duration of the requested meeting	
        if(endTime - startTime >= request.getDuration()) {	
          openSlots.add(TimeRange.fromStartEnd(startTime, endTime, false));	
        }	
      }	
      // Sets the next start time after this event unless this event is nested into a bigger event	
      if (startTime < e.getWhen().end()) {	
        startTime = e.getWhen().end();	
      }	
    }	


    if(startTime + request.getDuration() < TimeRange.END_OF_DAY) {	
      openSlots.add(TimeRange.fromStartEnd(startTime, TimeRange.END_OF_DAY, true));	
    }	

    return openSlots;	
  }	

  private boolean anyoneAttending(Collection<String> attendees, Event event) {	
    for (String attendee : attendees) {	
      if(isAttending(attendee, event)) {	
        return true;	
      }	
    }	
    return false;	
  }	
  private boolean isAttending(String attendee, Event event) {	
    return event.getAttendees().contains(attendee);	
  }
}
