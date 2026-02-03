package com.unibague.magno;

import com.unibague.magno.config.TestSecurityConfig;
import com.unibague.magno.infrastructure.configuration.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Import(TestSecurityConfig.class)
class MagnoBackApplicationTests {

	@Test
	void contextLoads() {
	}

}
