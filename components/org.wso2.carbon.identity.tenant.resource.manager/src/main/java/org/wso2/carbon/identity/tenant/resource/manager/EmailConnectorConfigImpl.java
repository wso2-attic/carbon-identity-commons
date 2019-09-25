package org.wso2.carbon.identity.tenant.resource.manager;


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
