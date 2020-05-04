package com.catalin.account.persistence

import com.catalin.account.api.Account
import com.catalin.account.persistence.db.AccountEntity

/**
 * Object for conversion between entity and public bean.
 * Contains a two way mapping between public account types and internal representations.
 */
object AccountMapper {

    /**
     * Map the provided JPA entity to the Account bean.
     * Composes an account number for public use.
     */
    fun map(entity: AccountEntity): Account {
        return Account(entity.type + "1" + entity.currency, entity.userId,
                getAccountTypeByCode(entity.type), entity.currency, entity.balance)
    }

    private val accountTypeCodes = mapOf("Savings" to "S")
    private val accountTypes = mapOf("S" to "Savings")

    fun getAccountTypeByCode(code: String): String {
        return accountTypes[code].orEmpty()
    }

    fun getAccountCodeByType(type: String): String {
        return accountTypeCodes[type].orEmpty()
    }
}