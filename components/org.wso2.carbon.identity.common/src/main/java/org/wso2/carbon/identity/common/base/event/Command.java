/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.common.base.event;

import org.wso2.carbon.identity.common.base.event.model.Event;
import org.wso2.carbon.identity.common.base.exception.IdentityException;
import org.wso2.carbon.identity.common.base.handler.IdentityEventHandler;

/**
 * Command structure for event handling.
 */
public class Command {

    private IdentityEventHandler identityEventHandler;

    private EventContext eventContext;

    private Event event;


    public Command(IdentityEventHandler identityEventHandler, EventContext eventContext, Event event) {
        this.identityEventHandler = identityEventHandler;
        this.eventContext = eventContext;
        this.event = event;
    }

    public void rollback() throws IdentityException {
        identityEventHandler.rollBack(eventContext, event);
    }

    public EventContext getEventContext() {
        return eventContext;
    }

    public IdentityEventHandler getIdentityEventHandler() {
        return identityEventHandler;
    }

    public Event getEvent() {
        return event;

    }
}
