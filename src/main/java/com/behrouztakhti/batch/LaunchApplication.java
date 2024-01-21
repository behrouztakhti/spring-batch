package com.behrouztakhti.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * This class is responsible for application startup.
 * @author behrouz.takhti@gmail.com
 */
@SpringBootApplication()
public class LaunchApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LaunchApplication.class, args);
        LOGGER.info("spring-batch application started successfully !");
    }
}
