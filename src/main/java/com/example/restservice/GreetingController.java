package com.example.restservice;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = LogManager.getLogger(GreetingController.class);
    private static final Logger errorLogger = LogManager.getLogger("COMMON-ERROR");


    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting=null;
        String isSuccess="Y";
        try{
            greeting=new Greeting(counter.incrementAndGet(), String.format(template, name));
            long id=greeting.getId();
            if(id%5==0){
                throw new RuntimeException("出错了，全错了"+id);
            }
        }catch(Exception e){
            isSuccess="N";
            errorLogger.error("Error",e);
        }finally {
            logger.info(isSuccess+","+ greeting.getId()+","+name);
        }
        return greeting;
    }
}