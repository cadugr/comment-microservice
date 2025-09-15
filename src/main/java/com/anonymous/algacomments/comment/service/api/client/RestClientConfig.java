package com.anonymous.algacomments.comment.service.api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public ModerationServiceClient moderationServiceClient(RestClientFactory restClientFactory) {
        RestClient restClient = restClientFactory.sensorMonitoringRestClient();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return proxyFactory.createClient(ModerationServiceClient.class);
    }
}
