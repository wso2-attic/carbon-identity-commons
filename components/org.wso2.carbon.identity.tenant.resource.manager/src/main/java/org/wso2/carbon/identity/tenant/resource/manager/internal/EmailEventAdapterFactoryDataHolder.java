package org.wso2.carbon.identity.tenant.resource.manager.internal;

import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterService;
import org.wso2.carbon.event.publisher.core.EventPublisherService;
import org.wso2.carbon.event.stream.core.EventStreamService;

public class EmailEventAdapterFactoryDataHolder {

    private static volatile EmailEventAdapterFactoryDataHolder instance = new EmailEventAdapterFactoryDataHolder();

    private  EmailEventAdapterFactoryDataHolder(){


    }

    public static EmailEventAdapterFactoryDataHolder getInstance() {
        return instance;
    }

    private EventPublisherService eventPublisherService = null;
    private OutputEventAdapterService carbonOutputEventAdapterService = null;
    private EventPublisherService carbonEventPublisherService = null;
    private EventStreamService carbonEventStreamService = null;

    public void setEventPublisherService(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }

    public EventPublisherService getEventPublisherService() {
        return eventPublisherService;
    }

    public OutputEventAdapterService getCarbonOutputEventAdapterService() {
        return carbonOutputEventAdapterService;
    }

    public void setCarbonOutputEventAdapterService(OutputEventAdapterService carbonOutputEventAdapterService) {
        this.carbonOutputEventAdapterService = carbonOutputEventAdapterService;
    }

    public EventPublisherService getCarbonEventPublisherService() {
        return carbonEventPublisherService;
    }

    public void setCarbonEventPublisherService(EventPublisherService carbonEventPublisherService) {
        this.carbonEventPublisherService = carbonEventPublisherService;
    }

    public void setCarbonEventStreamService(EventStreamService carbonEventStreamService) {
        this.carbonEventStreamService = carbonEventStreamService;
    }

    public  EventStreamService  getCarbonEventStreamService(){
        return carbonEventStreamService;
    }
}
