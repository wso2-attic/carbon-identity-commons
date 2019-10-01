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

import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterService;
import org.wso2.carbon.event.publisher.core.EventPublisherService;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.identity.configuration.mgt.core.ConfigurationManager;
import org.wso2.carbon.identity.governance.IdentityGovernanceService;

/**
 * DataHolder for Tenant Resource Manager.
 */
public class TenantResourceManagerDataHolder {

    private static volatile TenantResourceManagerDataHolder instance = new TenantResourceManagerDataHolder();
    private ConfigurationManager configurationManager;

    private TenantResourceManagerDataHolder() {
    }

    public static TenantResourceManagerDataHolder getInstance() {
        return instance;
    }

    private EventPublisherService eventPublisherService = null;
    private OutputEventAdapterService carbonOutputEventAdapterService = null;
    private EventPublisherService carbonEventPublisherService = null;
    private EventStreamService carbonEventStreamService = null;
    private IdentityGovernanceService identityGovernanceService = null;

    public void setEventPublisherService(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }

    public EventPublisherService getEventPublisherService() {
        return eventPublisherService;
    }

    public OutputEventAdapterService getCarbonOutputEventAdapterService() {
        return carbonOutputEventAdapterService;
    }

    public void setCarbonOutputEventAdapterService(OutputEventAdapterService carbonOutputEventAdapterService) {
        this.carbonOutputEventAdapterService = carbonOutputEventAdapterService;
    }

    public EventPublisherService getCarbonEventPublisherService() {
        return carbonEventPublisherService;
    }

    public void setCarbonEventPublisherService(EventPublisherService carbonEventPublisherService) {
        this.carbonEventPublisherService = carbonEventPublisherService;
    }

    public void setCarbonEventStreamService(EventStreamService carbonEventStreamService) {
        this.carbonEventStreamService = carbonEventStreamService;
    }

    public EventStreamService getCarbonEventStreamService() {
        return carbonEventStreamService;
    }

    public void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public void setIdentityGovernanceService(IdentityGovernanceService identityGovernanceService) {
        this.identityGovernanceService = identityGovernanceService;
    }

    public IdentityGovernanceService getIdentityGovernanceService() {
        return identityGovernanceService;
    }
}
