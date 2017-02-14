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

package org.wso2.carbon.identity.common.base.handler;

import org.wso2.carbon.identity.common.base.event.EventContext;
import org.wso2.carbon.identity.common.base.event.model.Event;
import org.wso2.carbon.identity.common.base.exception.IdentityException;

/**
 * Interface for identity event handlers.
 */
public interface IdentityEventHandler {

    /**
     * Specify whether the handler is synchronous or asynchronous.
     * @return True if async.
     */
    default boolean isAsync() {
        return false;
    }

    /**
     * Handle logic for subscribed events.
     *
     * @param eventContext EventContext of the operaion.
     * @param event Event to be handled.
     * @throws IdentityException
     */
    void handle(EventContext eventContext, Event event) throws IdentityException;

    /**
     * Rollback logic of the handle operation to execute during faulty conditions.
     *
     * @param eventContext EventContext of the operation.
     * @param event Event to rollback.
     * @throws IdentityException
     */
    default void onFault(EventContext eventContext, Event event) throws IdentityException {

    }

    /**
     * Configure handler.
     *
     * @param initConfig Configuration related to the handler.
     * @throws IdentityException If an errors while configuring the handler.
     */
    void configure(InitConfig initConfig) throws IdentityException;

    /**
     * Returns handler name.
     *
     * @return Handler name.
     */
    String getName();
}
