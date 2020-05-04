package com.catalin.account.rules

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

/**
 * Loads business rules from application.properties
 */
@Component
class SimpleBusinessRules(
        @Value("\${working.days}") daysConfig: String,
        @Value("\${working.hours}") hoursConfig: String,
        @Value("\${currency.list}") ccyConfig: String
) : BusinessRules {

    val log = LoggerFactory.getLogger(SimpleBusinessRules::class.java)

    // init with configured values
    val workingDays = daysConfig.split(",").map { it.toInt() }
    val workingHours = hoursConfig.split(",").map { it.toInt() }
    val currencyList = ccyConfig.split(",")

    // Print business rules from configuration
    init {
        log.info("Configured working days: $workingDays")
        log.info("Configured working hours: $workingHours")
        log.info("Valid currencies: $currencyList")
    }

    /**
     * Check current time against business rules
     */
    override fun isWorkingTime(): Boolean {
        return checkWorkingTime(Date())
    }

    /**
     * Check given time against business rules
     * @param time
     */
    fun checkWorkingTime(time: Date): Boolean {
        val cal = Calendar.getInstance()
        cal.time = time
        val dayOfWeek = cal[Calendar.DAY_OF_WEEK]
        if (!workingDays.contains(dayOfWeek)) {
            return false
        }
        val hour = cal[Calendar.HOUR_OF_DAY]
        if (!workingHours.contains(hour)) {
            return false
        }
        return true
    }

    /**
     * Check given currency against business rules
     * @param time
     */
    override fun isValidCurrency(currency: String): Boolean {
        return currencyList.contains(currency)
    }
}