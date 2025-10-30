package br.ufac.sgcmapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class SgcmapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
