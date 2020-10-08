package com.build.provider.service;

import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BuildApplicationAsyncService {

    private ExecutorService executorService;

    @PostConstruct
    private void create() {
        System.out.println("SSAAMMBBITT>>>>Started after Spring boot application !");
        executorService = Executors.newSingleThreadExecutor();
    }

    public void process(Runnable operation) {
        // no result operation
        executorService.submit(operation);
    }

    @PreDestroy
    private void destroy() {
        executorService.shutdown();
    }
}