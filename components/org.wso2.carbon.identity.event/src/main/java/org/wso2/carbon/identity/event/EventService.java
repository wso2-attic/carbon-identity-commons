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

package org.wso2.carbon.identity.event;

import org.wso2.carbon.identity.common.base.event.EventContext;
import org.wso2.carbon.identity.common.base.event.model.Event;
import org.wso2.carbon.identity.common.base.exception.IdentityException;
import org.wso2.carbon.identity.common.base.handler.IdentityEventHandler;

/**
 * Represents an event service.
 */
public interface EventService {

    /**
     * Executes handle logic for a given event and message context.
     *
     * @param event Event.
     * @param eventContext EventContext.
     * @throws IdentityException If an error occurs during event handling.
     */
    void pushEvent(Event event, EventContext eventContext) throws
            IdentityException;

    /**
     * Executes handle logic for a given event and message context.
     *
     * @param event Event.
     * @param eventContext EventContext.
     * @param handler IdentityEventHandler.
     * @throws IdentityException If an error occurs during event handling.
     */
    void pushEvent(Event event, EventContext eventContext, IdentityEventHandler handler) throws IdentityException;

}
