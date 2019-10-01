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

package org.wso2.carbon.identity.tenant.resource.manager.output.event.adapter.email;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.core.util.CryptoException;
import org.wso2.carbon.core.util.CryptoUtil;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterConfiguration;
import org.wso2.carbon.event.output.adapter.core.exception.ConnectionUnavailableException;
import org.wso2.carbon.event.output.adapter.email.EmailEventAdapter;
import org.wso2.carbon.identity.application.common.model.Property;
import org.wso2.carbon.identity.governance.IdentityGovernanceException;
import org.wso2.carbon.identity.governance.IdentityGovernanceService;
import org.wso2.carbon.identity.tenant.resource.manager.internal.TenantResourceManagerDataHolder;

import java.util.Map;

/**
 * EmailEventAdapter that deploy per tenant.
 */
public class TenantAwareEmailEventAdapter extends EmailEventAdapter {
    private static final Log log = LogFactory.getLog(TenantAwareEmailEventAdapter.class);
    private Map<String, String> globalProperties;

    /**
     * Default from address for outgoing messages.
     */
    public TenantAwareEmailEventAdapter(OutputEventAdapterConfiguration eventAdapterConfiguration,
            Map<String, String> globalProperties) {

        super(eventAdapterConfiguration, globalProperties);
        this.globalProperties = globalProperties;
    }

    public void connect() throws ConnectionUnavailableException {

        replaceGlobalPropertiesWithTenantProperties();
        super.connect();

    }

    /**
     * Replace configs from output-event-adapters.xml by tenant wise configurations.
     */
    private void replaceGlobalPropertiesWithTenantProperties() {

        Property[] propertyList = getTenantSpecificAttributeList();
        for (Property property : propertyList) {
            if (globalProperties.containsKey(property.getName()) && !StringUtils.isEmpty(property.getValue())) {
                globalProperties.put(property.getName(), property.getValue());
            }
        }

    }

    /**
     * Get tenant specific configurations from config store.
     */
    private Property[] getTenantSpecificAttributeList() {

        TenantResourceManagerDataHolder tenantResourceManagerDataHolder = TenantResourceManagerDataHolder.getInstance();
        IdentityGovernanceService identityGovernanceService = tenantResourceManagerDataHolder
                .getIdentityGovernanceService();
        try {
            return identityGovernanceService.getConfiguration(globalProperties.keySet().toArray(new String[0]),
                    PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain(true));

        } catch (IdentityGovernanceException e) {
            log.warn("Error occurred when retrieving tenant wise configurations global configurations will be used.",
                    e);
        }
        return new Property[0];
    }

    private String decrypt(String cipherText) throws CryptoException {
        return new String(CryptoUtil.getDefaultCryptoUtil().base64DecodeAndDecrypt(cipherText), Charsets.UTF_8);
    }

}
