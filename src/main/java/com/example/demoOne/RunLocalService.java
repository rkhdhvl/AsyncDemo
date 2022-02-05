package com.example.demoOne;

import com.example.demoOne.error_handling.ResponseEntityErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class RunLocalService {

    @Autowired
    private  AsyncService asyncService;

    @Autowired
    private ConfigurableApplicationContext context;


    @GetMapping("/check")
    public ResponseEntity<String> sayHello() {
        try {
            runApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Hello", HttpStatus.OK);
    }

    public void runApp() throws Exception {
        Instant start = Instant.now();

        List<CompletableFuture<Object>> allFutures = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            allFutures.add(asyncService.callMsgService()
                    .whenComplete((object,action)->{
                        System.out.println(object.toString());
                    })
                    .exceptionally(throwable -> {
                        if(throwable.getCause() instanceof ResponseEntityErrorException)
                        {
                            ResponseEntityErrorException errorException = (ResponseEntityErrorException) throwable.getCause();
                            System.out.println(finalI + " " + errorException.getErrorResponse().getStatusCode());
                        }
                        return "Error";
                    })
                    );
        }

       CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<Object> allFuture : allFutures) {
            System.out.println("response: " + allFuture.get().toString());
        }

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Total time: " + timeElapsed + " ms");

        System.exit(SpringApplication.exit(context));
    }


}
