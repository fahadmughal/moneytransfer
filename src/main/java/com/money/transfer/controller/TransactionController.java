package com.money.transfer.controller;

import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.model.AccountDetails;
import com.money.transfer.model.TransactionDetails;
import com.money.transfer.service.TransactionService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(schema = @Schema(implementation = TransactionDetails.class))),
            @ApiResponse(responseCode = "204", description = "No account found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity performTxn(@Valid  @RequestBody TransactionRequestDto transactionRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.performTxn(transactionRequestDto));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,  produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDetails.class))))
    })
    public ResponseEntity fetchAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.fetchAllTransactions());
    }
}
