package com.money.transfer.controller;

import com.money.transfer.model.AccountDetails;
import com.money.transfer.service.AccountService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,  produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDetails.class)))),
            @ApiResponse(responseCode = "204", description = "No account found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity fetchAllAccounts() {
        List<AccountDetails> accountDetails = accountService.fetchAllAccounts();
        return !accountDetails.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(accountDetails)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No account found");
    }
}
