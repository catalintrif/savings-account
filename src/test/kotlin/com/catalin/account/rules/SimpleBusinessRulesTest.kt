package com.catalin.account.rules

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class SimpleBusinessRulesTest (
        @Autowired val rules: SimpleBusinessRules
) {

    @Test
    fun `Test isWorkingTime() returns true only Monday to Friday 9h00 to 16h59`() {
        val workingDays = (Calendar.MONDAY..Calendar.FRIDAY).toList()
        val workingHours = (9..16).toList()
        val cal = Calendar.getInstance()
        val dayOfWeek = cal[Calendar.DAY_OF_WEEK]
        val hour = cal[Calendar.HOUR_OF_DAY]
        val expected = workingDays.contains(dayOfWeek) && workingHours.contains(hour)
        println("Expected: $expected")
        assertEquals(expected, rules.isWorkingTime())
    }

    @Test
    fun `checkWorkingTime() should return true on Monday and false on Saturday`() {
        val cal = Calendar.getInstance()
        cal[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        cal[Calendar.HOUR_OF_DAY] = 10
        assertTrue(rules.checkWorkingTime(cal.time))
        cal[Calendar.DAY_OF_WEEK] = Calendar.SATURDAY
        assertFalse(rules.checkWorkingTime(cal.time))
    }

    @Test
    fun `USD should be a valid currency and GBP should not`() {
        assertTrue(rules.isValidCurrency("USD"))
        assertFalse(rules.isValidCurrency("GBP"))
    }
}