package com.catalin.account.api.validator

import com.catalin.account.api.AccountRequest
import com.catalin.account.rules.BusinessRules
import org.springframework.stereotype.Component
import java.util.*

// error messages
const val MSG_WRONG_TYPE = "Account type is invalid"
const val MSG_USER_ID_BLANK = "User ID is blank"
const val MSG_CCY_BLANK = "Currency is blank"
const val MSG_CCY_INVALID = "Currency is invalid"
const val MSG_WORKING_TIME = "Account can only be opened within business hours"
const val MSG_ACCOUNT_EXISTS = "Account already exists"

/**
 * Account request validator
 */
@Component
class AccountRequestValidator(
        val rules: BusinessRules
) {
    fun validateRequest(account: AccountRequest): Optional<String> {
        if (account.userId.isEmpty()) {
            return Optional.of(MSG_USER_ID_BLANK)
        }
        if (account.currency.isEmpty()) {
            return Optional.of(MSG_CCY_BLANK)
        }
        if (!rules.isValidCurrency(account.currency)) {
            return Optional.of(MSG_CCY_INVALID)
        }
        return Optional.empty()
    }
}