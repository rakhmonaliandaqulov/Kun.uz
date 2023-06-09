package com.example;

import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println(JwtUtil.encode(4, ProfileRole.MODERATOR));
	}

}
