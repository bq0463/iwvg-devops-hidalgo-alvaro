package es.upm.miw.devops.rest.dtos;

import java.io.Serializable;

/**
 * DTO for {@link es.upm.miw.devops.code.User}
 */
public record UpdateUserDto(String name, String familyName, String email, String identity, String address, String city,
                            String province, String postalCode, boolean active) implements Serializable {
}