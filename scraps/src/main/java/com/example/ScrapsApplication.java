package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ScrapsApplication implements CommandLineRunner{

	public static void main(String[] args) {
		log.info("starting app");
		SpringApplication.run(ScrapsApplication.class, args);
		log.info("ended app");
	}
	
	@Override
    public void run(String... args) {
        log.info("EXECUTING : command line runner");
 		//arr.doWork();
    }
	


}
