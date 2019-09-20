package org.wso2.carbon.identity.tenant.resource.manager.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterFactory;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterService;
import org.wso2.carbon.event.publisher.core.EventPublisherService;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.identity.tenant.resource.manager.TenantAwareAxis2ConfigurationContextObserver;
import org.wso2.carbon.identity.tenant.resource.manager.TenantEmailEventAdapterFactory;
import org.wso2.carbon.utils.AbstractAxis2ConfigurationContextObserver;
import org.wso2.carbon.utils.Axis2ConfigurationContextObserver;

/**
 * @scr.component component.name="org.wso2.carbon.event.email.poc" immediate="true"
 * @scr.reference name="eventPublisherService.service"
 * interface="org.wso2.carbon.event.publisher.core.EventPublisherService" cardinality="1..1"
 * policy="dynamic" bind="setEventPublisherService" unbind="unsetEventPublisherService"
 * @scr.reference name="outputEventAdapterService"
 * interface="org.wso2.carbon.event.output.adapter.core.OutputEventAdapterService" cardinality="1..1"
 * policy="dynamic" bind="setCarbonOutputEventAdapterService" unbind="unsetCarbonOutputEventAdapterService"
 * @scr.reference name="eventPublisherService"
 * interface="org.wso2.carbon.event.publisher.core.EventPublisherService" cardinality="1..1"
 * policy="dynamic" bind="setCarbonEventPublisherService" unbind="unsetCarbonEventPublisherService"
 * @scr.reference name="eventStreamService"
 * interface="org.wso2.carbon.event.stream.core.EventStreamService" cardinality="1..1"
 * policy="dynamic" bind="setCarbonEventStreamService" unbind="unsetCarbonEventStreamService"
 */

public class EmailEventAdapterFactoryServiceDS extends AbstractAxis2ConfigurationContextObserver {

    private static final Log log = LogFactory.getLog(EmailEventAdapterFactoryServiceDS.class);

    /**
     * initialize the email service here service here.
     *
     * @param context
     */
    protected void activate(ComponentContext context) {

        try {
            TenantEmailEventAdapterFactory tenantEmailEventAdapterFactory = new TenantEmailEventAdapterFactory();
            context.getBundleContext().registerService(OutputEventAdapterFactory.class.getName(),
                    tenantEmailEventAdapterFactory, null);

            TenantAwareAxis2ConfigurationContextObserver tenantAwareAxis2ConfigurationContextObserver =
                    new TenantAwareAxis2ConfigurationContextObserver();

            context.getBundleContext().registerService(Axis2ConfigurationContextObserver.class.getName(),
                    tenantAwareAxis2ConfigurationContextObserver, null);
            if (log.isDebugEnabled()) {
                log.debug("Successfully deployed the  org.wso2.carbon.event.email.pocservice");
            }
        } catch (RuntimeException e) {
            log.error("Can not create the org.wso2.carbon.event.email.poc service ", e);
        }
    }

    protected void setEventPublisherService(EventPublisherService eventPublisherService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the Event Publisher Service");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setEventPublisherService(eventPublisherService);
    }

    protected void unsetEventPublisherService(EventPublisherService eventPublisherService) {
        if (log.isDebugEnabled()) {
            log.debug("UnSetting the Event Publisher Service");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setEventPublisherService(null);
    }

    protected void setCarbonOutputEventAdapterService(OutputEventAdapterService carbonOutputEventAdapterService){
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonOutputEventAdapter Service");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setCarbonOutputEventAdapterService(carbonOutputEventAdapterService);

    }

    protected void unsetCarbonOutputEventAdapterService(OutputEventAdapterService carbonOutputEventAdapterService){
        if (log.isDebugEnabled()) {
            log.debug("Un Setting the CarbonOutputEventAdapter Service");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setCarbonOutputEventAdapterService(null);

    }


    protected void setCarbonEventPublisherService(EventPublisherService carbonEventPublisherService){
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonEventPublisherService");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setCarbonEventPublisherService(carbonEventPublisherService);

    }

    protected void unsetCarbonEventPublisherService(EventPublisherService carbonEventPublisherService){
        if (log.isDebugEnabled()) {
            log.debug("Un Setting theCarbonEventPublisherService Service");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setCarbonEventPublisherService(null);

    }

    protected void setCarbonEventStreamService(EventStreamService carbonEventStreamService){
        if (log.isDebugEnabled()) {
            log.debug("Setting the CarbonEventPublisherService");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setCarbonEventStreamService(carbonEventStreamService);

    }

    protected void unsetCarbonEventStreamService(EventStreamService carbonEventStreamService){
        if (log.isDebugEnabled()) {
            log.debug("Un Setting theCarbonEventPublisherService Service");
        }
        EmailEventAdapterFactoryDataHolder.getInstance().setCarbonEventStreamService(null);

    }

}
