package com.banking.api.bankingapi.services.auth;

import com.banking.api.bankingapi.dtos.LoginRequest;
import com.banking.api.bankingapi.dtos.LoginResponse;
import com.banking.api.bankingapi.exceptions.ApiException;
import com.banking.api.bankingapi.modules.customer.Customer;
import com.banking.api.bankingapi.security.jwt.JwtServices;
import com.banking.api.bankingapi.services.customers.CustomerServices;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerServices customerServices;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;
    public LoginResponse login(LoginRequest loginRequest)  {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            Customer customer = customerServices.findCustomerByUsername(loginRequest.username());
            return new LoginResponse(jwtServices.generateJwtToken(customer));
        }catch (Exception e){
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
