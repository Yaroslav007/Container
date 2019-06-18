package com.globallogic.test.tree.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.globallogic.test.tree.model.Container;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * DTO representation of {@link Container} class.
 *
 * @author yaroslav.shymkiv
 */
@Getter
@Setter
@AllArgsConstructor
public class ContainerDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotBlank
    private int id;
    @NotBlank
    private String value;
}
