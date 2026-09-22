package com.ibm.digicusthub.controller;

import com.ibm.digicusthub.dto.CustomerCheckRequest;
import com.ibm.digicusthub.service.CustomerDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerDetailsController {

    private final CustomerDetailsService customerDetailsService;

    public CustomerDetailsController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Validate user by email and phone number and save
     * @param customerCheckRequest
     * @return response message
     */
    @PostMapping("/validate-user")
    public ResponseEntity<?> validateUser(@RequestBody CustomerCheckRequest customerCheckRequest) {
        customerDetailsService.validateUserUniqueness(customerCheckRequest);
        return new ResponseEntity<>("User Validated Successfully", HttpStatus.OK);
    }


    /**
     * Update customer details
     * @param customerId
     * @param customerCheckRequest
     * @return response message
     */
    @PutMapping("/update-customer/{customerId}" )
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerCheckRequest customerCheckRequest) {
        customerDetailsService.updateCustomerDetails(customerId, customerCheckRequest);
        return new ResponseEntity<>("Customer Updated Successfully", HttpStatus.OK);
    }

    @PutMapping("/documents-upload/{customerId}")
    public ResponseEntity<?> uploadDocuments(@PathVariable Long customerId,@RequestParam Map<String, MultipartFile> files){
        customerDetailsService.uploadCustomerDocuments(customerId,files);
        return new ResponseEntity<>("Documents Uploaded Successfully", HttpStatus.OK);
        }

}
