package com.catalin.account.savings

import com.catalin.account.api.AccountRequest
import com.catalin.account.api.savings.SavingsAccountService
import com.catalin.account.persistence.AccountStore
import com.catalin.account.rules.BusinessRules
import com.catalin.account.api.validator.AccountRequestValidator
import com.catalin.account.api.validator.MSG_ACCOUNT_EXISTS
import com.catalin.account.api.validator.MSG_USER_ID_BLANK
import com.catalin.account.api.validator.MSG_WORKING_TIME
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.server.ResponseStatusException

/**
 * Integration test for the SavingsAccountService.
 * The BusinessRules service is mocked to allow testing at any time.
 */
@SpringBootTest
class SavingsAccountServiceTest @Autowired constructor(
        persistence: AccountStore,
        validator: AccountRequestValidator
) {

    val rules = Mockito.mock(BusinessRules::class.java)
    val service = SavingsAccountService(validator, rules, persistence)

    @BeforeEach
    fun setUp() {
        Mockito.`when`(rules.isWorkingTime()).thenReturn(true)
    }

    @Test
    fun testUserIdProvided() {
        val e = assertThrows<ResponseStatusException> {
            service.create(AccountRequest("", "Savings", "EUR"))
        }
        assertTrue(e.message.contains(MSG_USER_ID_BLANK))
    }

    @Test
    fun `Test with valid data and within business days`() {
        val userId = "u1"
        val type = "Savings"
        val ccy = "EUR"
        val account = service.create(AccountRequest(userId, type, ccy))
        assertTrue(account.number.isNotBlank())
        assertEquals(userId, account.userId)
        assertEquals(type, account.type)
        assertEquals(ccy, account.currency)
    }

    @Test
    fun failOnSecondCreate() {
        service.create(AccountRequest("u2", "Savings", "EUR"))
        val e = assertThrows<ResponseStatusException> {
            service.create(AccountRequest("u2", "Savings", "EUR"))
        }
        assertTrue(e.message.contains(MSG_ACCOUNT_EXISTS))
    }

    @Test
    fun testOutsideBusinessDays() {
        Mockito.`when`(rules.isWorkingTime()).thenReturn(false)
        val e = assertThrows<ResponseStatusException>("Must throw http status exception") {
            service.create(AccountRequest("u3", "Savings", "EUR"))
        }
        assertTrue(e.message.contains(MSG_WORKING_TIME))
    }

}