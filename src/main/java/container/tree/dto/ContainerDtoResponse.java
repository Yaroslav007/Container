package container.tree.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import container.tree.model.Container;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * DTO response representation for the {@link Container} class.
 *
 * @author yaroslav.shymkiv
 */
@Getter
@Setter
public class ContainerDtoResponse extends ContainerDtoRequest{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotBlank
    private int id;

    public ContainerDtoResponse (String value, int id) {
        super (value);
        this.id = id;
    }
}
