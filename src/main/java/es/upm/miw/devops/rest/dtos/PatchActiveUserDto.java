package es.upm.miw.devops.rest.dtos;

import java.io.Serializable;

/**
 * DTO for {@link es.upm.miw.devops.code.User}
 */
public record PatchActiveUserDto(String id, boolean active) implements Serializable {
}