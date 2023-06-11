
package com.service.teacher.config;

import com.service.teacher.client.StudentClient;
import com.service.teacher.client.TaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebConfiguration {

    @Autowired
    LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient taskWebClient(){
        return WebClient.builder()
                .baseUrl("http://task-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public TaskClient taskClient(){
        HttpServiceProxyFactory factory= HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(taskWebClient())).build();
        return factory.createClient(TaskClient.class);
    }

    @Bean
    public WebClient studentWebClient(){
        return WebClient.builder()
                .baseUrl("http://student-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public StudentClient studentClient(){
        HttpServiceProxyFactory factory= HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(studentWebClient())).build();
        return factory.createClient(StudentClient.class);
    }


}
