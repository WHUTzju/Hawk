package cn.easy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ServletComponentScan
@EnableTransactionManagement
public class HermesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HermesApplication.class, args);
	}
}
