package container.tree.dao;

import container.tree.model.Container;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Container} instance.
 *
 * @author yaroslav.shymkiv
 */
@Repository
public interface ContainerRepository extends CrudRepository<Container, Integer> {

    List<Container> findAll();

    Optional<Container> findAllById(int id);

}
