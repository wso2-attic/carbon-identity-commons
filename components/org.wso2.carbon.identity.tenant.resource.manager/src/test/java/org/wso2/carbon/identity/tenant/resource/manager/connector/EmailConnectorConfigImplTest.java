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

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.testng.Assert.*;

public class EmailConnectorConfigImplTest {

    private EmailConnectorConfigImpl emailConnectorConfig;
    private static final String CATEGORY = "Tenant Wise Output Adapter Configurations";
    private static final String FRIENDLY_NAME = "Email Adapter Configuration";

    @BeforeTest
    public void Init() {
        emailConnectorConfig = new EmailConnectorConfigImpl();
    }

    @Test
    public void testGetName() {
        Assert.assertEquals(emailConnectorConfig.getName(), "email-output-adapter-configuration");
    }

    @Test
    public void testGetFriendlyName() {
        Assert.assertEquals(emailConnectorConfig.getFriendlyName(), FRIENDLY_NAME);
    }

    @Test
    public void testGetCategory() {
        Assert.assertEquals(emailConnectorConfig.getCategory(),CATEGORY);
    }

    @Test
    public void testGetSubCategory() {
        Assert.assertEquals(emailConnectorConfig.getSubCategory(),"DEFAULT");
    }


    @Test
    public void testGetPropertyNameMapping() {

        Map<String, String> nameMappingExpected = new HashMap<>();
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_FROM,
                "FROM Email Address");
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_USER,
                "SMTP Server User Name");
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_PASSWORD,
                "SMTP Server Password");
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_HOST,
                "SMTP Server Host");
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_PORT,
                "SMTP Server Port");
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_START_TLS,
                "Enable Start TLS");
        nameMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_AUTH, "Enable SMTP Sever Auth");

        Assert.assertEquals(emailConnectorConfig.getPropertyNameMapping(), nameMappingExpected, "Maps are not equal");
    }

    @Test
    public void testGetPropertyDescriptionMapping() {

        Map<String, String> descriptionMappingExpected = new HashMap<>();
        descriptionMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_FROM,
                "Provide the email address of the SMTP account. Example: abcd@gmail.com");
        descriptionMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_USER,
                "Provide the username of the SMTP account. Example: abcd");
        descriptionMappingExpected.put(EmailConnectorConstants.EMAIL_SMTP_PASSWORD, "Provide the password of the SMTP account.");

        Assert.assertEquals(emailConnectorConfig.getPropertyDescriptionMapping(),descriptionMappingExpected, "Maps are not equal");
    }

    @Test
    public void testGetPropertyNames() {

        List<String> propertiesExpected = new ArrayList<>();
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_FROM);
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_USER);
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_PASSWORD);
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_HOST);
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_PORT);
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_START_TLS);
        propertiesExpected.add(EmailConnectorConstants.EMAIL_SMTP_AUTH);

        Assert.assertEquals(emailConnectorConfig.getPropertyNames(),
                propertiesExpected.toArray(new String[propertiesExpected.size()]), "property names are not equal");
    }

    @Test
    public void testGetDefaultPropertyValues() throws Exception {

        Map<String, String> defaultProperties = new HashMap<>();
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_FROM, "");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_USER,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_PASSWORD,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_HOST,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_PORT,"");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_START_TLS,"true");
        defaultProperties.put(EmailConnectorConstants.EMAIL_SMTP_AUTH,"true");

        Properties propertiesExpected = new Properties();
        propertiesExpected.putAll(defaultProperties);

        Assert.assertEquals(emailConnectorConfig.getDefaultPropertyValues(""), propertiesExpected, "Default "
                + "Properties not equal");
    }

}