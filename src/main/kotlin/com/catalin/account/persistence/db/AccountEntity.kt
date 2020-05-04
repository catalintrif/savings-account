package com.catalin.account.persistence.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class AccountEntity(
        val userId: String,
        val type: String,
        val currency: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    var balance: Double = 0.0

    override fun toString(): String {
        return "AccountEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                '}'
    }
}