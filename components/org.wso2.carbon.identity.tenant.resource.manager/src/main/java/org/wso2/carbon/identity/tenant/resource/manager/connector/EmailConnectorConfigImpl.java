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

package org.wso2.carbon.identity.tenant.resource.manager.connector;


import org.wso2.carbon.identity.governance.IdentityGovernanceException;
import org.wso2.carbon.identity.governance.common.IdentityConnectorConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EmailConnectorConfigImpl implements IdentityConnectorConfig {

    private static String connectorName = "email-output-adapter-configuration";

    @Override
    public String getName() {
        return connectorName;
    }

    @Override
    public String getFriendlyName() {
        return "Email Adapter Configuration";
    }

    @Override
    public String getCategory() {
        return "Tenant Wise Output Adapter Configurations";
    }

    @Override
    public String getSubCategory() {
        return "DEFAULT";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Map<String, String> getPropertyNameMapping() {
        Map<String, String> nameMapping = new HashMap<>();
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_FROM,
                "FROM Email Address");
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_USER,
                "SMTP Server User Name");
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_PASSWORD,
                "SMTP Server Password");
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_HOST,
                "SMTP Server Host");
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_PORT,
                "SMTP Server Port");
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_START_TLS,
                "Enable Start TLS");
        nameMapping.put(EmailConnectorConstants.EMAIL_SMTP_AUTH, "Enable SMTP Sever Auth");
        return nameMapping;
    }

    @Override
    public Map<String, String> getPropertyDescriptionMapping() {
        Map<String, String> descriptionMapping = new HashMap<>();
        descriptionMapping.put(EmailConnectorConstants.EMAIL_SMTP_FROM,
                "Provide the email address of the SMTP account. Example: abcd@gmail.com");
        descriptionMapping.put(EmailConnectorConstants.EMAIL_SMTP_USER,
                "Provide the username of the SMTP account. Example: abcd");
        descriptionMapping.put(EmailConnectorConstants.EMAIL_SMTP_PASSWORD, "Provide the password of the SMTP account.");
        return descriptionMapping;
    }

    @Override
    public String[] getPropertyNames() {

        List<String> properties = new ArrayList<>();
        properties.add(EmailConnectorConstants.EMAIL_SMTP_FROM);
        properties.add(EmailConnectorConstants.EMAIL_SMTP_USER);
        properties.add(EmailConnectorConstants.EMAIL_SMTP_PASSWORD);
        properties.add(EmailConnectorConstants.EMAIL_SMTP_HOST);
        properties.add(EmailConnectorConstants.EMAIL_SMTP_PORT);
        properties.add(EmailConnectorConstants.EMAIL_SMTP_START_TLS);
        properties.add(EmailConnectorConstants.EMAIL_SMTP_AUTH);

        return properties.toArray(new String[properties.size()]);
    }

    @Override
    public Properties getDefaultPropertyValues(String tenantDomain) throws IdentityGovernanceException {

        Map<String, String> defaultProperties = new HashMap<>();
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_FROM, "");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_USER,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_PASSWORD,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_HOST,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_PORT,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_START_TLS,"true");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_AUTH,"true");

        Properties properties = new Properties();
        properties.putAll(defaultProperties);
        return properties;
    }

    @Override
    public Map<String, String> getDefaultPropertyValues(String[] propertyNames, String tenantDomain) throws IdentityGovernanceException {
        return null;
    }

}
