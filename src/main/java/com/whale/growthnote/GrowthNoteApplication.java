package com.whale.growthnote;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class GrowthNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrowthNoteApplication.class, args);
    }
}
