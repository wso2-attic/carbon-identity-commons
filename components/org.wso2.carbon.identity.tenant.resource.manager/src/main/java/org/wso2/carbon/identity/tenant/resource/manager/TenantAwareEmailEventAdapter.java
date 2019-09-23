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

package org.wso2.carbon.identity.tenant.resource.manager;

import org.apache.commons.io.Charsets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.core.util.CryptoException;
import org.wso2.carbon.core.util.CryptoUtil;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterConfiguration;
import org.wso2.carbon.event.output.adapter.core.exception.ConnectionUnavailableException;
import org.wso2.carbon.event.output.adapter.email.EmailEventAdapter;
import org.wso2.carbon.identity.configuration.mgt.core.ConfigurationManager;
import org.wso2.carbon.identity.configuration.mgt.core.exception.ConfigurationManagementException;
import org.wso2.carbon.identity.configuration.mgt.core.model.Attribute;
import org.wso2.carbon.identity.configuration.mgt.core.model.Resource;
import org.wso2.carbon.identity.tenant.resource.manager.internal.TenantResourceManagerDataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EmailEventAdapter that deploy per tenant.
 */
public class TenantAwareEmailEventAdapter extends EmailEventAdapter {
    private static final Log log = LogFactory.getLog(TenantAwareEmailEventAdapter.class);
    private Map<String, String> globalProperties;
    private final String RESOURCE_TYPE = "mail";
    private final String RESOURCE = "smtp";

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
     *
     */
    private void replaceGlobalPropertiesWithTenantProperties() {

        List<Attribute> attributeList = getTenantSpecificAttributeList();
        for (Attribute attribute : attributeList) {
            String key = RESOURCE_TYPE + "." + RESOURCE + "." + attribute.getKey();
            if (globalProperties.containsKey(key)) {
                globalProperties.put(key, attribute.getValue());
            }
        }

    }

    /**
     * Get tenant specific configurations from config store.
     *
     */
    private List<Attribute> getTenantSpecificAttributeList() {

        TenantResourceManagerDataHolder tenantResourceManagerDataHolder = TenantResourceManagerDataHolder
                .getInstance();
        ConfigurationManager configurationManager = tenantResourceManagerDataHolder.getConfigurationManager();
        try {
            Resource resource = configurationManager.getResource(RESOURCE_TYPE, RESOURCE);
            return resource.getAttributes();
        } catch (ConfigurationManagementException e) {
            log.warn("Error retrieving tenant wise SMTP configurations: "+ e.getMessage() + " global properties will"
                    + " be used for the tenant id: " + CarbonContext.getThreadLocalCarbonContext().getTenantId());
        }
        return new ArrayList<Attribute>(0);
    }

    private String decrypt(String cipherText) throws CryptoException {
        return new String(CryptoUtil.getDefaultCryptoUtil().base64DecodeAndDecrypt(
                cipherText), Charsets.UTF_8);
    }

}
