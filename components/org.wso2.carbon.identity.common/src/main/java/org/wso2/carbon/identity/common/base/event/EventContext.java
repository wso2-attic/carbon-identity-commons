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
import org.wso2.carbon.identity.common.base.event.model.EventHandlerBean;
import org.wso2.carbon.identity.common.base.handler.IdentityEventHandler;
import org.wso2.carbon.identity.common.base.message.MessageContext;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Event message context.
 */
public class EventContext extends MessageContext {

    private Event event;

    private Deque<EventHandlerBean> handlerDeque = new ArrayDeque<>();

    private CommandStack commandStack = new CommandStack();

    public EventContext(Event event) {
        super();
        this.event = event;

    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Deque<EventHandlerBean> getEventHandlerStack() {
        return handlerDeque;
    }

    public void addToEventHandlerStack(IdentityEventHandler handler) {
        handlerDeque.add(new EventHandlerBean(this.event, handler));
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
