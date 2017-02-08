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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.common.base.event.EventContext;
import org.wso2.carbon.identity.common.base.event.model.Event;
import org.wso2.carbon.identity.common.base.exception.IdentityRuntimeException;
import org.wso2.carbon.identity.common.base.handler.AbstractMessageHandler;
import org.wso2.carbon.identity.common.base.handler.IdentityEventHandler;
import org.wso2.carbon.identity.common.base.handler.InitConfig;
import org.wso2.carbon.identity.common.base.message.MessageContext;
import org.wso2.carbon.identity.event.internal.ConfigParser;
import org.wso2.carbon.identity.event.model.ModuleConfig;
import org.wso2.carbon.identity.event.model.Subscription;

import java.util.List;

/**
 * Abstract Event Handler class.
 */
public abstract class AbstractEventHandler extends AbstractMessageHandler implements IdentityEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventHandler.class);
    protected ModuleConfig moduleConfig;

    public boolean canHandle(MessageContext messageContext) throws IdentityRuntimeException {

        if (!(messageContext instanceof EventContext)) {
            return false;
        }
        Event event = ((EventContext) messageContext).getEvent();
        String eventName = event.getEventName();
        String moduleName = this.getName();
        ConfigParser notificationMgtConfigBuilder;
        try {
            notificationMgtConfigBuilder = ConfigParser.getInstance();
        } catch (EventException e) {
            logger.error("Error while retrieving event mgt config builder", e);
            return false;
        }
        List<Subscription> subscriptionList = null;
        //TODO see whether this can be removed.
        ModuleConfig moduleConfig = notificationMgtConfigBuilder.getModuleConfigurations(moduleName);
        if (moduleConfig != null) {
            subscriptionList = moduleConfig.getSubscriptions();
        }

        if (subscriptionList != null) {
            return subscriptionList.stream()
                                   .anyMatch(sub -> sub.getSubscriptionName().equals(eventName));
        }

        return false;
    }

    /**
     *
     * @param eventContext
     * @throws EventException
     */
    @Override
    public abstract void handleEvent(EventContext eventContext) throws EventException;

    @Override
    public void init(InitConfig configuration) throws IdentityRuntimeException {

        if (configuration instanceof ModuleConfig) {
            this.moduleConfig = (ModuleConfig) configuration;
        }
    }

}
