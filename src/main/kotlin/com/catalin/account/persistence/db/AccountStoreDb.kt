package com.catalin.account.persistence.db

import com.catalin.account.api.Account
import com.catalin.account.persistence.AccountMapper
import com.catalin.account.persistence.AccountStore
import org.springframework.stereotype.Component
import java.util.*

/**
 * Database implementation of the AccountStore.
 * Uses AccountMapper object for conversion between entity and public bean.
 */
@Component
class AccountStoreDb(val repository: AccountRepository) : AccountStore {

    /**
     * Get the account wrapped as Optional.
     * @return java.util.Optional to avoid passing null in case the account is not found
     */
    override fun getAccount(userId: String, type: String): Optional<Account> {
        val list = repository.findByUserIdAndType(userId,
                AccountMapper.getAccountCodeByType(type))
        if (list.size == 1) {
            val entity = list[0]
            return Optional.of(AccountMapper.map(entity))
        }
        return Optional.empty()
    }

    /**
     * Create the account and return it as public bean.
     */
    override fun createAccount(userId: String, type: String, currency: String): Account {
        val entity = AccountEntity(userId, AccountMapper.getAccountCodeByType(type), currency)
        repository.save(entity)
        return AccountMapper.map(entity)
    }
}