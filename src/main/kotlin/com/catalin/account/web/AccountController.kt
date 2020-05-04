package com.catalin.account.web

import com.catalin.account.api.AccountRequest
import com.catalin.account.api.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException

/**
 * Simple web controller for handling html form submit
 */
@Controller
class AccountController(
        @Autowired val service: AccountService
) {

    @GetMapping("/")
    fun createForm(): String {
        return "/create.html"
    }

    @PostMapping("/save")
    fun save(request: AccountRequest): ResponseEntity<String> {
        val account = try { service.create(request) } catch (e: ResponseStatusException) {
            return ResponseEntity(e.reason, e.status)
        }
        return ResponseEntity("Created: $account", HttpStatus.OK)
    }
}