package com.catalin.account.rules

import java.util.*

/**
 * Interface
 */
interface BusinessRules {
    fun isWorkingTime(): Boolean
    fun isValidCurrency(currency: String): Boolean
}