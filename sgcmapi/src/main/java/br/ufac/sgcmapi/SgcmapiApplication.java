package br.ufac.sgcmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SgcmapiApplication {

	public static void main(String[] args) {
		System.out.println("teste");
		SpringApplication.run(SgcmapiApplication.class, args);
	}

	@GetMapping(value = "/")
	public String teste() {
		return "SGCM";
	}

}
