package com.thofactorauth.controller;

import com.thofactorauth.exception.CustomErrorResponse;
import com.thofactorauth.model.table.User_Data;
import com.thofactorauth.service.User_DataService;
import com.thofactorauth.service.impl.User_DataServiceImpl;
import com.thofactorauth.tfa.AuthenticationResponse;
import com.thofactorauth.tfa.TwoFactorAuthenticationService;
import com.thofactorauth.tfa.VerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/twofa")
@RequiredArgsConstructor
public class UserDataController {

    private final User_DataService userDataService;
    private final TwoFactorAuthenticationService tfaService;

    @GetMapping("/{cip}")
    public ResponseEntity<?> findByCip(@PathVariable("cip") String cip) throws Exception {
        User_Data obj = userDataService.findById(cip);

        if (obj == null) {
            Map<String, String> resp = new HashMap<>();
            resp.put("menssage", "no se encuentra registrado");
            resp.put("status", "0");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        if (obj.getStatus() == 0) {
            Map<String, String> resp = new HashMap<>();
            resp.put("menssage", "no se encuentra registrado");
            resp.put("status", "0");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        Map<String, String> resp = new HashMap<>();
        resp.put("menssage", "ok");
        resp.put("status", "1");
        return new ResponseEntity<>(resp, HttpStatus.OK);
        /*
        else {

            Map<String, String> resp = new HashMap<>();
            resp.put("menssage", "ok");
            resp.put("status", "1");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }*/
    }

    @PostMapping("/save")
    public ResponseEntity<?> create(@RequestBody User_Data obj) {

        Map<String, Object> response = new HashMap<>();

        try {
            String newSecret = tfaService.generateNewSecret();
            obj.setStatus(0);
            obj.setSecret(newSecret);

            try {
                userDataService.save(obj);

            } catch (Exception e) {
                response.put("menssage", "error en la inserción");
            }
            return ResponseEntity.ok().body(AuthenticationResponse.builder()
                    .status(obj.getStatus())
                    .cip(obj.getIdCip())
                    .secretImageUri(tfaService.generateQrCodeImageUri(newSecret, obj.getUsername()))
                    .build());

        } catch (DataAccessException e) {
            response.put("msj", "Error al realizar el insert en la BD!");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            response.put("ok", false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest) {

        Map<String, Object> response = new HashMap<>();

        User_Data obj = userDataService.findById(verificationRequest.getUsername());

        if (obj == null) {
            Map<String, String> resp = new HashMap<>();
            resp.put("menssage", "Usuario no se encuentra egistrado");
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);

        }

        if (tfaService.isOtpNotValid(obj.getSecret(), verificationRequest.getCode())) {
            response.put("menssage", "código incorrecto");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } else {

            if (obj.getStatus() != 1) {
                obj.setStatus(1);
                userDataService.update(obj);
                response.put("message", "validado correctamente");
                response.put("status", true);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {

                response.put("message", "validado correctamente");
                response.put("status", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }
    }
}
