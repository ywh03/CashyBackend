package com.budgetingapp.cashy;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class CashyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashyApplication.class, args);
	}

}
