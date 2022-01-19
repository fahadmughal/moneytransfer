package com.money.transfer.controller;

import com.money.transfer.dto.CustomerProfileDto;
import com.money.transfer.model.CustomerProfile;
import com.money.transfer.service.CustomerService;
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
@RequestMapping(value = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,  produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerProfileDto.class)))),
            @ApiResponse(responseCode = "204", description = "No account found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<List<CustomerProfileDto>> fetchAllAccounts() {
        List<CustomerProfileDto> customerProfileList = customerService.findAllCustomer();
        return !customerProfileList.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(customerProfileList)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerProfileList);
    }
}
