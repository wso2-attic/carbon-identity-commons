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

import org.wso2.carbon.event.output.adapter.core.MessageType;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapter;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterConfiguration;
import org.wso2.carbon.event.output.adapter.core.Property;
import org.wso2.carbon.event.output.adapter.email.EmailEventAdapterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EmailEventAdapterFactory cannot generate tenant wise adapters hence new factory created.
 * IdentityEmailEventAdapterFactory for identity server.
 */
public class IdentityEmailEventAdapterFactory extends EmailEventAdapterFactory {

    public String getType() {
        return "email-is";
    }

    public List<String> getSupportedMessageFormats() {

        List<String> supportedMessageFormats = new ArrayList<String>();
        supportedMessageFormats.add(MessageType.TEXT);
        supportedMessageFormats.add(MessageType.XML);
        supportedMessageFormats.add(MessageType.JSON);
        return supportedMessageFormats;
    }

    public List<Property> getStaticPropertyList() {
        return null;
    }

    public String getUsageTips() {
        return null;
    }

    public OutputEventAdapter createEventAdapter(OutputEventAdapterConfiguration outputEventAdapterConfiguration,
            Map<String, String> map) {

        return new TenantAwareEmailEventAdapter(outputEventAdapterConfiguration, map);
    }

}
