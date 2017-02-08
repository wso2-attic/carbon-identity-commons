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

package org.wso2.carbon.identity.event;

import org.wso2.carbon.identity.common.base.event.EventContext;
import org.wso2.carbon.identity.common.base.exception.IdentityException;
import org.wso2.carbon.identity.common.base.handler.IdentityEventHandler;
import org.wso2.carbon.identity.common.base.handler.InitConfig;

/**
 * This handler can be used when even handling returns a result.
 *
 * @param <T> Result type.
 */
public abstract class ResultReturningHandler<T extends Object> implements IdentityEventHandler {

    private T result;

    public T getResult() {
        return result;
    }

    public abstract T handleEventWithResult(EventContext eventContext) throws IdentityException;

    @Override
    public void handleEvent(EventContext eventContext) throws IdentityException {
        result = handleEventWithResult(eventContext);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void configure(InitConfig initConfig) throws IdentityException {

    }
}
