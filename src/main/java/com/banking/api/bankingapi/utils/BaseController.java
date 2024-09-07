package com.banking.api.bankingapi.utils;

import com.banking.api.bankingapi.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public abstract class BaseController {
    public String getCurrentCustomerUsername()  {
        JwtClaims details = (JwtClaims) SecurityContextHolder.getContext().getAuthentication().getDetails();
        try {
            return details.getSubject();
        } catch (MalformedClaimException e) {
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
