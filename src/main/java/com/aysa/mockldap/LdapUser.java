package com.aysa.mockldap;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LdapUser {
    private long id;
    private String username;
    private List<LdapAction> actions;
}
