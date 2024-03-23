package com.PFA2.EduHousing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
/*@ComponentScan(basePackages = {"com.PFA2.EduHousing", "org.springdoc"})*/
public class EduHousingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduHousingApplication.class, args);
	}

}
