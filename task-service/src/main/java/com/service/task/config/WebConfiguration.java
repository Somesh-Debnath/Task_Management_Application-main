
package com.service.task.config;


import com.service.task.client.StudentClient;
import com.service.task.client.TeacherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class WebConfiguration {

    @Autowired
    LoadBalancedExchangeFilterFunction filterFunction;

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
                .builder(WebClientAdapter.forClient(studentWebClient()))
                .blockTimeout(Duration.ofDays(1L))
                .build();
        return factory.createClient(StudentClient.class);
    }

    @Bean
    public WebClient teacherWebClient(){
        return WebClient.builder()
                .baseUrl("http://teacher-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public TeacherClient teacherClient(){
        HttpServiceProxyFactory factory= HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(teacherWebClient()))
                .blockTimeout(Duration.ofDays(1L))
                .build();
        return factory.createClient(TeacherClient.class);
    }


}
