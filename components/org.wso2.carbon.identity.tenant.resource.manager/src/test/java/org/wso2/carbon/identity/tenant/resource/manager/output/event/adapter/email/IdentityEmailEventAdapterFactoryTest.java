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

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class IdentityEmailEventAdapterFactoryTest {

    private IdentityEmailEventAdapterFactory identityEmailEventAdapterFactory;

    @BeforeTest
    public void Init() {
        identityEmailEventAdapterFactory = new IdentityEmailEventAdapterFactory();
    }

    @Test
    public void testGetType() {

        Assert.assertEquals(identityEmailEventAdapterFactory.getType(), "email-is");
    }

    @Test
    public void testCreateEventAdapter() {

        OutputEventAdapterConfiguration outputEventAdapterConfiguration = new OutputEventAdapterConfiguration();

        Map<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("mail.smtp.user", "iam123");
        propertiesMap.put("mail.smtp.port", "587");
        propertiesMap.put("maxThread", "100");
        propertiesMap.put("keepAliveTimeInMillis", "20000");
        propertiesMap.put("mail.smtp.password", "xxxxxxx");
        propertiesMap.put("mail.smtp.from", "iam123@gmail.com");
        propertiesMap.put("mail.smtp.starttls.enable", "true");
        propertiesMap.put("mail.smtp.auth", "true");
        propertiesMap.put("mail.jobQueueSize", "10000");
        propertiesMap.put("mail.smtp.host", "smtp.gmail.com");
        propertiesMap.put("minThread", "8");

        Assert.assertNotNull(
                identityEmailEventAdapterFactory.createEventAdapter(outputEventAdapterConfiguration, propertiesMap));
    }
}
