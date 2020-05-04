package com.catalin.account.api

/**
 * Interface of the account service
 */
interface AccountService {
    fun create(request: AccountRequest): Account
}

data class AccountRequest(
        val userId: String,
        val type: String,
        val currency: String
)

data class Account(
        val number: String,
        val userId: String,
        val type: String,
        val currency: String,
        val balance: Double
)