/*
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.identity.tenant.resource.manager.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterFactory;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterService;
import org.wso2.carbon.event.publisher.core.EventPublisherService;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.identity.configuration.mgt.core.ConfigurationManager;
import org.wso2.carbon.identity.governance.IdentityGovernanceService;
import org.wso2.carbon.identity.governance.common.IdentityConnectorConfig;
import org.wso2.carbon.identity.tenant.resource.manager.connector.EmailConnectorConfigImpl;
import org.wso2.carbon.identity.tenant.resource.manager.output.event.adapter.email.IdentityEmailEventAdapterFactory;
import org.wso2.carbon.identity.tenant.resource.manager.TenantAwareAxis2ConfigurationContextObserver;
import org.wso2.carbon.utils.AbstractAxis2ConfigurationContextObserver;
import org.wso2.carbon.utils.Axis2ConfigurationContextObserver;

/**
 * @scr.component component.name="org.wso2.carbon.event.email.poc" immediate="true"
 * @scr.reference name="eventPublisherService.service"
 * interface="org.wso2.carbon.event.publisher.core.EventPublisherService" cardinality="1..1"
 * policy="dynamic" bind="setEventPublisherService" unbind="unsetEventPublisherService"
 * @scr.reference name="outputEventAdapterService"
 * interface="org.wso2.carbon.event.output.adapter.core.OutputEventAdapterService" cardinality="1..1"
 * policy="dynamic" bind="setCarbonOutputEventAdapterService" unbind="unsetCarbonOutputEventAdapterService"
 * @scr.reference name="eventPublisherService"
 * interface="org.wso2.carbon.event.publisher.core.EventPublisherService" cardinality="1..1"
 * policy="dynamic" bind="setCarbonEventPublisherService" unbind="unsetCarbonEventPublisherService"
 * @scr.reference name="eventStreamService"
 * interface="org.wso2.carbon.event.stream.core.EventStreamService" cardinality="1..1"
 * policy="dynamic" bind="setCarbonEventStreamService" unbind="unsetCarbonEventStreamService"
 * @scr.reference name="configurationDAO"
 * interface="org.wso2.carbon.identity.configuration.mgt.core.ConfigurationManager" cardinality="1..1"
 * policy="dynamic" bind="setConfigurationManager" unbind="unsetConfigurationManager"
 * @scr.reference name="IdentityGovernanceService
 * interface="org.wso2.carbon.identity.governance.IdentityGovernanceService" cardinality="1..1"
 * policy="dynamic" bind="setIdentityGovernanceService" unbind="unsetIdentityGovernanceService"
 */
public class TenantResourceManagerServiceDS extends AbstractAxis2ConfigurationContextObserver {

    private static final Log log = LogFactory.getLog(TenantResourceManagerServiceDS.class);

    /**
     * Register IdentityEmailEventAdapterFactory as an OSGI service.
     *
     * @param context OSGI service component context.
     */
    protected void activate(ComponentContext context) {

        try {
            IdentityEmailEventAdapterFactory identityEmailEventAdapterFactory = new IdentityEmailEventAdapterFactory();
            context.getBundleContext()
                    .registerService(OutputEventAdapterFactory.class.getName(), identityEmailEventAdapterFactory, null);
            TenantAwareAxis2ConfigurationContextObserver tenantAwareAxis2ConfigurationContextObserver = new TenantAwareAxis2ConfigurationContextObserver();

            context.getBundleContext().registerService(Axis2ConfigurationContextObserver.class.getName(),
                    tenantAwareAxis2ConfigurationContextObserver, null);
            context.getBundleContext()
                    .registerService(IdentityConnectorConfig.class.getName(), new EmailConnectorConfigImpl(), null);
            if (log.isDebugEnabled()) {
                log.debug("Successfully deployed the tenant resource manager service.");
            }
        } catch (RuntimeException e) {
            log.error("Can not create the tenant resource manager service.", e);
        }
    }

    protected void setEventPublisherService(EventPublisherService eventPublisherService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the Event Publisher Service");
        }
        TenantResourceManagerDataHolder.getInstance().setEventPublisherService(eventPublisherService);
    }

    protected void unsetEventPublisherService(EventPublisherService eventPublisherService) {
        if (log.isDebugEnabled()) {
            log.debug("UnSetting the Event Publisher Service");
        }
        TenantResourceManagerDataHolder.getInstance().setEventPublisherService(null);
    }

    protected void setCarbonOutputEventAdapterService(OutputEventAdapterService carbonOutputEventAdapterService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonOutputEventAdapter Service");
        }
        TenantResourceManagerDataHolder.getInstance()
                .setCarbonOutputEventAdapterService(carbonOutputEventAdapterService);

    }

    protected void unsetCarbonOutputEventAdapterService(OutputEventAdapterService carbonOutputEventAdapterService) {
        if (log.isDebugEnabled()) {
            log.debug("Un Setting the CarbonOutputEventAdapter Service");
        }
        TenantResourceManagerDataHolder.getInstance().setCarbonOutputEventAdapterService(null);
    }

    protected void setCarbonEventPublisherService(EventPublisherService carbonEventPublisherService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonEventPublisherService");
        }
        TenantResourceManagerDataHolder.getInstance().setCarbonEventPublisherService(carbonEventPublisherService);
    }

    protected void unsetCarbonEventPublisherService(EventPublisherService carbonEventPublisherService) {
        if (log.isDebugEnabled()) {
            log.debug("Un Setting the CarbonEventPublisherService Service");
        }
        TenantResourceManagerDataHolder.getInstance().setCarbonEventPublisherService(null);
    }

    protected void setCarbonEventStreamService(EventStreamService carbonEventStreamService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the EventStreamService");
        }
        TenantResourceManagerDataHolder.getInstance().setCarbonEventStreamService(carbonEventStreamService);

    }

    protected void unsetCarbonEventStreamService(EventStreamService carbonEventStreamService) {
        if (log.isDebugEnabled()) {
            log.debug("Un Setting the EventStreamService");
        }
        TenantResourceManagerDataHolder.getInstance().setCarbonEventStreamService(null);
    }

    protected void setConfigurationManager(ConfigurationManager configurationManager) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonEventPublisherService");
        }
        TenantResourceManagerDataHolder.getInstance().setConfigurationManager(configurationManager);

    }

    protected void unsetConfigurationManager(ConfigurationManager configurationManager) {
        if (log.isDebugEnabled()) {
            log.debug("Un Setting theCarbonEventPublisherService Service");
        }
        TenantResourceManagerDataHolder.getInstance().setConfigurationManager(null);
    }

    protected void setIdentityGovernanceService(IdentityGovernanceService identityGovernanceService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonEventPublisherService");
        }
        TenantResourceManagerDataHolder.getInstance().setIdentityGovernanceService(identityGovernanceService);

    }

    protected void unsetIdentityGovernanceService(IdentityGovernanceService identityGovernanceService) {
        if (log.isDebugEnabled()) {
            log.debug("Un Setting theCarbonEventPublisherService Service");
        }
        TenantResourceManagerDataHolder.getInstance().setIdentityGovernanceService(null);
    }

}
