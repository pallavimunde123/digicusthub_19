package com.ibm.digicusthub.service;

import com.ibm.digicusthub.dto.CustomerCheckRequest;
import com.ibm.digicusthub.entity.CustomerDetailsEntity;
import com.ibm.digicusthub.entity.OtpDetailsEntity;
import com.ibm.digicusthub.enums.VerificationTypeEnum;
import com.ibm.digicusthub.exception.UserExistsException;
import com.ibm.digicusthub.repository.CustomerDetailsRepository;

import com.ibm.digicusthub.repository.OtpDetailsRepository;
import com.ibm.digicusthub.utility.AppUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerDetailsService {

    private final CustomerDetailsRepository customerDetailsRepository;
    private final OtpDetailsService otpDetailsService;

    public CustomerDetailsService(CustomerDetailsRepository customerDetailsRepository, OtpDetailsService otpDetailsService) {
        this.customerDetailsRepository = customerDetailsRepository;
        this.otpDetailsService = otpDetailsService;
    }

    /**
     * Service to validate the customer exists or not by email and phone number
     * @param customerCheckRequest
     */
    public void validateUserUniqueness(CustomerCheckRequest customerCheckRequest){
        String email = customerCheckRequest.getEmail();
        String mobNo = customerCheckRequest.getMobNo();
        String panCard =customerCheckRequest.getPanCard();
        boolean emailExists = false;
        boolean mobNoExists = false;
        boolean panExists = false;

        if(email != null  && !email.isBlank()){
            emailExists = customerDetailsRepository.existsByEmail(email);
        }

        if(mobNo != null  && !mobNo.isBlank()){
            mobNoExists = customerDetailsRepository.existsByMobNo(mobNo);
        }

        if(panCard != null && !panCard.isBlank()){
            panExists = customerDetailsRepository.existsByPanCardDetails(panCard);
        }

        String message = Stream.of(
                        emailExists ? "Email" : null,
                        mobNoExists ? "Mobile Number" : null,
                        panExists ? "PAN Card" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        if (!message.isEmpty()) {
            throw new UserExistsException(message + " already exists!");
        }

        CustomerDetailsEntity customerDetailsEntity = new CustomerDetailsEntity();
        customerDetailsEntity.setEmail(email);
        customerDetailsEntity.setMobNo(mobNo);
        customerDetailsEntity.setPanCardDetails(panCard);

       CustomerDetailsEntity savedCustomer = customerDetailsRepository.save(customerDetailsEntity);
       otpDetailsService.sendAndSaveOtpData(savedCustomer);

    }


    /**
     * Service to update customer details
     * @param customerId
     * @param customerCheckRequest
     */
    public void updateCustomerDetails(Long customerId, CustomerCheckRequest customerCheckRequest) {
        CustomerDetailsEntity existingCustomer = customerDetailsRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        existingCustomer.setFirstName(customerCheckRequest.getFirstName());
        existingCustomer.setMiddleName(customerCheckRequest.getMiddleName());
        existingCustomer.setLastName(customerCheckRequest.getLastName());
        existingCustomer.setGender(customerCheckRequest.getGender());
        existingCustomer.setDob(customerCheckRequest.getDob());

        customerDetailsRepository.save(existingCustomer);
    }

//    public void uploadCustomerDocuments(Long customerId, Map<String, MultipartFile> files) {
//        CustomerDetailsEntity existingCustomer = customerDetailsRepository.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
//        try {
//            for(Map.Entry<String, MultipartFile> entry : files.entrySet()) {
//                String key = entry.getKey();
//                MultipartFile file = entry.getValue();
//
//                switch (key.toLowerCase()){
//                    case "aadhardocs":
//                        existingCustomer.setAadharDocs(file.getBytes());
//                        break;
//                    case "pancarddocs":
//                        existingCustomer.setPancardDocs(file.getBytes());
//                        break;
//                    case "addressdocs":
//                        existingCustomer.setAddressDocs(file.getBytes());
//                        break;
//                    case "signaturedocs":
//                        existingCustomer.setSignature(file.getBytes());
//                        break;
//
//                    default:
//                        throw new RuntimeException("Invalid document type: " + key);
//                }
//
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        customerDetailsRepository.save(existingCustomer);
//    }

    /**
     * Service to upload customer documents
     * @param customerId
     * @param files
     */
    public void uploadCustomerDocuments(Long customerId, Map<String, MultipartFile> files) {

        CustomerDetailsEntity customer = customerDetailsRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        Map<String, Consumer<byte[]>> documentSetters = Map.of(
                "aadhardocs", customer::setAadharDocs,
                "pancarddocs", customer::setPancardDocs,
                "addressdocs", customer::setAddressDocs,
                "signaturedocs", customer::setSignature
        );

        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            String docType = entry.getKey().toLowerCase();
            MultipartFile file = entry.getValue();

            Consumer<byte[]> setter = documentSetters.get(docType);
            if (setter == null) {
                throw new RuntimeException("Invalid document type: " + entry.getKey());
            }

            try {
                setter.accept(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file for document type: " + docType, e);
            }
        }

        customerDetailsRepository.save(customer);
    }



}
