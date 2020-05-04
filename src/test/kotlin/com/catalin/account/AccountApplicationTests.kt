package com.catalin.account

import com.catalin.account.api.AccountService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountApplicationTests(
		@Autowired val service: AccountService
) {
	@Test
	fun contextLoads() {
		Assertions.assertNotNull(service)
	}

}
