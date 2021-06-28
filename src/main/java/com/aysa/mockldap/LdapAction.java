package com.aysa.mockldap;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LdapAction {
    private long id;
    private String name;
    private boolean habilitado;
    private List<LdapSucursal> sucursales;
}
