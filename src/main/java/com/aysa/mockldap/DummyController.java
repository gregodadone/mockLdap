package com.aysa.mockldap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@RestController
public class DummyController {

    @PostMapping("/api/v1/ldapLogin")
    public ResponseEntity<LdapUser> dummyEndpoint(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestHeader("application-id") String applicationId
    ) {
        String[] values;
        if (authorization != null && authorization.toLowerCase().startsWith("basic") && applicationId != null) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            values = credentials.split(":", 2);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (values[1] == "wrong") {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        LdapSucursal sucursal = LdapSucursal.builder()
                .id(1L)
                .name("Sucursal")
                .build();

        LdapAction action = LdapAction.builder()
                .id(1L)
                .habilitado(true)
                .name("accion")
                .sucursales(Collections.singletonList(sucursal))
                .build();

        LdapUser user = LdapUser.builder()
                .id(1L)
                .username(values[0])
                .actions(Collections.singletonList(action))
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
