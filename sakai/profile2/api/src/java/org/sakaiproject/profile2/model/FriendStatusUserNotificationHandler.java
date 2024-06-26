/**
 * Copyright (c) 2003-2017 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.profile2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.sakaiproject.event.api.Event;
import org.sakaiproject.messaging.api.UserNotificationData;
import org.sakaiproject.messaging.api.AbstractUserNotificationHandler;
import org.sakaiproject.profile2.logic.ProfileConnectionsLogic;
import org.sakaiproject.profile2.logic.ProfileLinkLogic;
import org.sakaiproject.profile2.util.ProfileConstants;
import org.sakaiproject.user.api.User;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FriendStatusUserNotificationHandler extends AbstractUserNotificationHandler {

    @Resource
    private ProfileConnectionsLogic profileConnectionsLogic;

    @Resource
    private ProfileLinkLogic profileLinkLogic;

    @Override
    public List<String> getHandledEvents() {
        return Arrays.asList(ProfileConstants.EVENT_STATUS_UPDATE);
    }

    @Override
    public Optional<List<UserNotificationData>> handleEvent(Event e) {

        String from = e.getUserId();

        String ref = e.getResource();
        String[] pathParts = ref.split("/");

        List<UserNotificationData> bhEvents = new ArrayList<>();

        // Get all the posters friends
        List<User> connections = profileConnectionsLogic.getConnectedUsersForUserInsecurely(from);
        for (User connection : connections) {
            String to = connection.getId();
            String url = profileLinkLogic.getInternalDirectUrlToUserProfile(to, from);
            bhEvents.add(new UserNotificationData(from, to, "", "", url, ProfileConstants.TOOL_ID));
        }

        return Optional.of(bhEvents);
    }
}
