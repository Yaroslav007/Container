package container.tree.dto;

import container.tree.model.Container;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * DTO request representation for the {@link Container} class.
 *
 * @author yaroslav.shymkiv
 */
@Getter
@Setter
@AllArgsConstructor
public class ContainerDtoRequest {

    @NotBlank
    private String value;

}
