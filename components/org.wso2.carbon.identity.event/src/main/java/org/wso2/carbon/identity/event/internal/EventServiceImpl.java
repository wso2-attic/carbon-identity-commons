/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.event.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.common.base.event.Command;
import org.wso2.carbon.identity.common.base.event.EventContext;
import org.wso2.carbon.identity.common.base.event.model.Event;
import org.wso2.carbon.identity.common.base.event.model.EventHandlerBean;
import org.wso2.carbon.identity.common.base.exception.IdentityException;
import org.wso2.carbon.identity.common.base.handler.IdentityEventHandler;
import org.wso2.carbon.identity.event.AbstractEventHandler;
import org.wso2.carbon.identity.event.EventService;
import org.wso2.carbon.kernel.utils.LambdaExceptionUtils;

import java.util.Deque;
import java.util.List;

/**
 * Event service implementation.
 */
public final class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    private EventDistributionTask eventDistributionTask;

    public EventServiceImpl(List<AbstractEventHandler> handlerList, int threadPoolSize) {
        this.eventDistributionTask = new EventDistributionTask(handlerList, threadPoolSize);
        if (logger.isDebugEnabled()) {
            logger.debug("Starting event distribution task from Notification Management component");
        }
        new Thread(eventDistributionTask).start();
    }

    @Override
    public void handleEvent(EventContext eventContext) throws IdentityException {

        List<AbstractEventHandler> eventHandlerList = EventServiceComponent.EVENT_HANDLER_LIST;

        for (final AbstractEventHandler handler : eventHandlerList) {

            if (handler.canHandle(eventContext)) {
                if (handler.isAsync()) {
//                    eventDistributionTask.addEventToQueue(eventContext);
                } else {
                    eventContext.addToEventHandlerStack(handler);
                    handler.handleEvent(eventContext);
                }
            }
        }
    }


    @Override
    public void rollbackEvent(EventContext eventContext) throws IdentityException {

        Deque<EventHandlerBean> eventHandlerStack = eventContext.getEventHandlerStack();

        eventHandlerStack.forEach(LambdaExceptionUtils.rethrowConsumer(eventHandlerBean -> {

            eventContext.setEvent(eventHandlerBean.getEvent());
            eventHandlerBean.getHandler().rollBack(eventContext);
        }));

    }

    @Override
    public void pushEvent(Event event, EventContext eventContext) throws
            IdentityException {

        List<AbstractEventHandler> eventHandlerList = EventServiceComponent.EVENT_HANDLER_LIST;

        for (final AbstractEventHandler handler : eventHandlerList) {

            if (handler.canHandle(eventContext)) {
                if (handler.isAsync()) {
                    eventDistributionTask.addEventToQueue(new Command(handler, eventContext, event));
                } else {
                    eventContext.getCommandStack().execute(handler, eventContext, eventContext.getEvent());
                }
            }
        }

    }

    @Override
    public void pushEvent(Event event, EventContext eventContext, IdentityEventHandler handler) throws
            IdentityException {

        eventContext.getCommandStack().execute(handler, eventContext, event);

    }
}
