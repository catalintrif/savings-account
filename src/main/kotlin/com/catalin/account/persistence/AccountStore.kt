package com.catalin.account.persistence

import com.catalin.account.api.Account
import java.util.*

/**
 * Account persistence interface.
 * Could be implemented by multiple persistence implementations (DB, file, Redis, etc)
 */
interface AccountStore {
    fun getAccount(userId: String, type: String): Optional<Account>
    fun createAccount(userId: String, type: String, currency: String): Account
}