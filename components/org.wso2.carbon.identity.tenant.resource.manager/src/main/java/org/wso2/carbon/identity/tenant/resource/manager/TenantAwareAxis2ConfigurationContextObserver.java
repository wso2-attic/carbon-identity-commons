package org.wso2.carbon.identity.tenant.resource.manager;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.databridge.commons.StreamDefinition;
import org.wso2.carbon.event.publisher.core.config.EventPublisherConfiguration;
import org.wso2.carbon.event.stream.core.EventStreamConfiguration;
import org.wso2.carbon.identity.tenant.resource.manager.internal.EmailEventAdapterFactoryDataHolder;
import org.wso2.carbon.utils.AbstractAxis2ConfigurationContextObserver;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import java.util.List;

public class TenantAwareAxis2ConfigurationContextObserver extends AbstractAxis2ConfigurationContextObserver {

    private static final Log log = LogFactory.getLog(TenantAwareAxis2ConfigurationContextObserver.class);

    public void creatingConfigurationContext(int tenantId) {
        log.info("creating configuration context for tenant id: " + tenantId);
        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        List<EventPublisherConfiguration> activeEventPublisherConfigurations = null;
        List<EventStreamConfiguration> eventStreamConfigurationList = null;
        List<StreamDefinition> streamDefinitionList = null;
        try {
            PrivilegedCarbonContext.startTenantFlow();
            PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext.getThreadLocalCarbonContext();
            carbonContext.setTenantId(MultitenantConstants.SUPER_TENANT_ID);
            carbonContext.setTenantDomain(MultitenantConstants.SUPER_TENANT_DOMAIN_NAME);

            activeEventPublisherConfigurations = EmailEventAdapterFactoryDataHolder.getInstance()
                    .getEventPublisherService().getAllActiveEventPublisherConfigurations();

            eventStreamConfigurationList = EmailEventAdapterFactoryDataHolder.getInstance()
                    .getCarbonEventStreamService().getAllEventStreamConfigurations();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrivilegedCarbonContext.endTenantFlow();

        }

        try {
            PrivilegedCarbonContext.startTenantFlow();
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantId(tenantId);
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantDomain(tenantDomain);

            for (EventStreamConfiguration eventStreamConfiguration : eventStreamConfigurationList) {
                if (EmailEventAdapterFactoryDataHolder.getInstance().getCarbonEventStreamService()
                        .getEventStreamConfiguration(eventStreamConfiguration.getStreamDefinition().getStreamId())
                        == null) {
                    EmailEventAdapterFactoryDataHolder.getInstance().getCarbonEventStreamService()
                            .addEventStreamConfig(eventStreamConfiguration);

                }
            }

            for (EventPublisherConfiguration eventPublisherConfiguration : activeEventPublisherConfigurations) {
                if (EmailEventAdapterFactoryDataHolder.getInstance().getCarbonEventPublisherService()
                        .getActiveEventPublisherConfiguration(eventPublisherConfiguration.getEventPublisherName())
                        == null) {
                    EmailEventAdapterFactoryDataHolder.getInstance().getCarbonEventPublisherService()
                            .addEventPublisherConfiguration(eventPublisherConfiguration);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrivilegedCarbonContext.endTenantFlow();

        }
    }

    public void createdConfigurationContext(ConfigurationContext configContext) {
        log.info("created configuration context is called");
    }

    public void terminatingConfigurationContext(ConfigurationContext configCtx) {

        EmailEventAdapterFactoryDataHolder.getInstance().getCarbonOutputEventAdapterService().destroy("email");

    }

}
