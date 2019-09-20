package org.wso2.carbon.identity.tenant.resource.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterConfiguration;
import org.wso2.carbon.event.output.adapter.core.exception.ConnectionUnavailableException;
import org.wso2.carbon.event.output.adapter.email.EmailEventAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

public class TenantAwareEmailEventAdapter extends EmailEventAdapter {
    private static final Log log = LogFactory.getLog(TenantAwareEmailEventAdapter.class);
    private static ThreadPoolExecutor threadPoolExecutor;
    private  Session session;
    private OutputEventAdapterConfiguration eventAdapterConfiguration;
    private Map<String, String> globalProperties;
    private int tenantId;
    /**
     * Default from address for outgoing messages.
     */
    private InternetAddress smtpFromAddress = null;
    public TenantAwareEmailEventAdapter(OutputEventAdapterConfiguration eventAdapterConfiguration,
            Map<String, String> globalProperties) {
        super(eventAdapterConfiguration, globalProperties);
        this.globalProperties = globalProperties;
        this.eventAdapterConfiguration = eventAdapterConfiguration;
    }

    public void connect() throws ConnectionUnavailableException {
        tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();

        log.info("Connecting using the TenantAwareEmailEventAdapter for tenant ID: "+ tenantId);

        if (session == null) {

            Map<String, String> propertiesMap = new HashMap<String, String>();
            propertiesMap.put("mail.smtp.user", "resourcesiam5");
            propertiesMap.put("mail.smtp.password", "xxxxx");

            if (tenantId != -1234) {
                replaceGlobalPropertiesWithTenantProperties(globalProperties, propertiesMap);
            }
            super.connect();

        }
    }

    private void replaceGlobalPropertiesWithTenantProperties(Map<String, String> globalProps,
            Map<String, String> tenantProps ){

        for(String key : tenantProps.keySet()){
            if(globalProps.containsKey(key)){
                globalProps.put(key,tenantProps.get(key));
            }

        }

    }
}
