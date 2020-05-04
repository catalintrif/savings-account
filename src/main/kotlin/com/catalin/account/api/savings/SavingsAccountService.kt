package com.catalin.account.api.savings

import com.catalin.account.api.Account
import com.catalin.account.api.AccountRequest
import com.catalin.account.api.AccountService
import com.catalin.account.persistence.AccountMapper
import com.catalin.account.persistence.AccountStore
import com.catalin.account.rules.BusinessRules
import com.catalin.account.api.validator.AccountRequestValidator
import com.catalin.account.api.validator.MSG_ACCOUNT_EXISTS
import com.catalin.account.api.validator.MSG_WORKING_TIME
import com.catalin.account.api.validator.MSG_WRONG_TYPE
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * REST service that handles savings account creation.
 * Assumes userId passed in AccountRequest already exists.
 * The account can only be opened from Monday to Friday within working hours
 * and the user can have only one savings account.
 */
@RestController
@RequestMapping("/api")
@Transactional
class SavingsAccountService @Autowired constructor(
        var validator: AccountRequestValidator,
        var businessRules: BusinessRules,
        var persistence: AccountStore
) : AccountService {
    private val log = LoggerFactory.getLogger(SavingsAccountService::class.java)

    /**
     * Creates the savings account
     * @return the account as JSON
     * @throws ResponseStatusException if validation fails
     */
    @PostMapping("savings")
    override fun create(@RequestBody request: AccountRequest): Account {
        log.info("Request: $request")
        // assume user exists
        if (AccountMapper.getAccountCodeByType(request.type).isEmpty()) {
            log.error(MSG_WRONG_TYPE)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_WRONG_TYPE)
        }
        val validationError = validator.validateRequest(request)
        validationError.ifPresent { message ->
            log.error(message)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, message)
        }

        // check if a savings account already exists
        val existing = persistence.getAccount(request.userId, request.type)
        existing.ifPresent {
            log.error(MSG_ACCOUNT_EXISTS)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ACCOUNT_EXISTS)
        }
        if (!businessRules.isWorkingTime()) {
            log.error(MSG_WORKING_TIME)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_WORKING_TIME)
        }
        val account = persistence.createAccount(request.userId, request.type, request.currency)
        log.info("Created $account")
        return account
    }

}