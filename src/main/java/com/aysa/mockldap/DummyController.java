package com.aysa.mockldap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

        if ("wrong".equals(values[1])) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        LdapSucursal sucursalAvellaneda = LdapSucursal.builder()
                .id(7L)
                .name("AVELLANEDA")
                .build();

        LdapSucursal sucursalBelgrano = LdapSucursal.builder()
                .id(30L)
                .name("BELGRANO")
                .build();

        LdapAction actionDeshabiltarCAU = LdapAction.builder()
                //.id(1L)
                //.habilitado(true)
                .name("deshabiltarCAU")
                .sucursales(Collections.singletonList(sucursalAvellaneda))
                .build();

        LdapAction actionCancelarTurno = LdapAction.builder()
                //.id(2L)
                //.habilitado(true)
                .name("cancelarTurno")
                .sucursales(Collections.singletonList(sucursalAvellaneda))
                .build();


        LdapAction actionActualizarPuestos = LdapAction.builder()
                //.id(3L)
                //.habilitado(true)
                .name("actualizarPuestos")
                .sucursales(Collections.singletonList(sucursalBelgrano))
                .build();

        LdapUser user = LdapUser.builder()
                .id(1L)
                .username(values[0])
                .actions(Arrays.asList(actionDeshabiltarCAU, actionCancelarTurno, actionActualizarPuestos))
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
