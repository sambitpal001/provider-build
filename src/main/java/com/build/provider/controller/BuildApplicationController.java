package com.build.provider.controller;

import com.build.provider.service.BuildApplicationAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@Controller
public class BuildApplicationController {
    private BuildApplicationAsyncService buildApplicationAsyncService;

    @Autowired
    public void setAsyncService(BuildApplicationAsyncService buildApplicationAsyncService) {
        this.buildApplicationAsyncService = buildApplicationAsyncService;
    }

    public void createBuild() {
        try
        {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"C:\\Users\\pals2\\IdeaProjects\\EN1-128-130-contractTest\\After-Deploy\\reference-producer\" && mvn clean install -Dpact.verifier.publishResults=true");
           // ProcessBuilder builder = new ProcessBuilder(
             //     "cmd.exe", "/c", "cd \"C:\\Users\\pals2\\IdeaProjects\\EN1-128-130-contractTest\\After-Deploy\\reference-acl-service-master\" && mvn install -Pcontract-tests -Dpact.provider.version=101 -Dpact.tag=dev -Dpact.verifier.publishResults=true -Dpact.broker.host=localhost -Dpact.broker.port=8500");
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
    @PostMapping("/builds/new")
    private ResponseEntity asyncMethod(@RequestBody(required = false) String str) {
        buildApplicationAsyncService.process(
                new Runnable() {
                    public void run() {
                        System.out.println("Asynchronous task");
                        createBuild();
                    }
                }

        );
        // returns immediately
        return ResponseEntity.ok("ok");
    }
}
