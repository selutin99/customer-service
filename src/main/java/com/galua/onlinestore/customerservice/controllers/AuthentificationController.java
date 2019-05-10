package com.galua.onlinestore.customerservice.controllers;

import com.galua.onlinestore.customerservice.dto.AuthenticationRequestDto;
import com.galua.onlinestore.customerservice.entities.Customers;
import com.galua.onlinestore.customerservice.security.jwt.InvalidJwtAuthenticationException;
import com.galua.onlinestore.customerservice.security.jwt.JwtTokenProvider;
import com.galua.onlinestore.customerservice.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("customer")
    public ResponseEntity<Integer> findCustomerByToken(HttpServletRequest request){
        try {
            String email = request.getUserPrincipal().getName();
            int id = customersService.getCustomerByEmail(email).getId();
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
