package com.catalin.account.persistence.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : CrudRepository<AccountEntity, Long> {
    override fun findById(id: Long): Optional<AccountEntity>
    fun findByUserIdAndType(userId: String, type: String): List<AccountEntity>
}