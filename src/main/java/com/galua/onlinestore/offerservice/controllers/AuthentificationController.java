package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.dto.AuthenticationRequestDto;
import com.galua.onlinestore.offerservice.entities.Customers;
import com.galua.onlinestore.offerservice.security.jwt.InvalidJwtAuthenticationException;
import com.galua.onlinestore.offerservice.security.jwt.JwtTokenProvider;
import com.galua.onlinestore.offerservice.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth/")
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomersService customersService;

    @Autowired
    public AuthentificationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomersService customersService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customersService = customersService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String userEmail = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, requestDto.getPassword()));
            Customers customer = customersService.getCustomerByEmail(userEmail);

            if (userEmail == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            String token = jwtTokenProvider.createToken(userEmail);

            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        catch (InvalidJwtAuthenticationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    /*
    @GetMapping("find")
    public ResponseEntity findCustomerByToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(jwtTokenProvider.validateToken(token)){
            String email = jwtTokenProvider.getUsername(token);
            Customers customer = customersService.getCustomerByEmail(email);
            return new ResponseEntity(customer.getId(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }*/

    @GetMapping("find")
    public ResponseEntity findCustomerByToken(@RequestBody String token){
        String email = jwtTokenProvider.getUsername(token);
        Customers customer = customersService.getCustomerByEmail(email);
        return new ResponseEntity(customer.getId(), HttpStatus.OK);
    }
}
