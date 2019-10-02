package container.tree.service;

import container.tree.dao.ContainerRepository;
import container.tree.dto.ContainerDtoRequest;
import container.tree.dto.ContainerDtoResponse;
import container.tree.exc.EntityNotFoundException;
import container.tree.model.Container;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service logic for {@link Container} class.
 *
 * @author yaroslav.shymkiv
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContainerService {

    private ContainerRepository containerRepository;

    /**
     * Saves new root {@link Container} instance into DB.
     *
     * @param urlDto data with container value
     */
    public void createNewContainerTree(ContainerDtoRequest urlDto) {
        containerRepository.save(new Container(urlDto.getValue()));
    }

    /**
     * Saves {@link Container} into DB as a child of the specified container.
     *
     * @param parentId identifier of the parent container
     * @param containerDto data with container value
     */
    public void createSubContainer(int parentId, ContainerDtoRequest containerDto) throws EntityNotFoundException {
        Optional<Container> optionalContainer = containerRepository.findAllById(parentId);
        if (!optionalContainer.isPresent()){
            throw new EntityNotFoundException(parentId);
        }
        Container container = optionalContainer.get();
        Container subContainer = new Container(containerDto.getValue());
        containerRepository.save(subContainer);
        container.addSubContainer(subContainer);
        containerRepository.save(container);
    }

    /**
     * Method removes {@link Container} item to DB.
     *
     * @param id identifier of the parent container
     * @param childId identifier of the child container
     */
    public void removeSubContainer(int id, int childId) throws EntityNotFoundException {
        Optional<Container> optionalContainer = containerRepository.findAllById(id);
        if (!optionalContainer.isPresent()){
            throw new EntityNotFoundException(id);
        }
        Container container = optionalContainer.get();
        Container subContainer = getContainerItem(childId);
        container.removeSubContainer(subContainer);
        containerRepository.save(container);
        containerRepository.delete(subContainer);
    }

    /**
     * Updates {@link Container} item.
     *
     * @param id container identifier
     * @param containerDto new container data
     */
    public void update(int id, ContainerDtoRequest containerDto) throws EntityNotFoundException {
        Container container = getContainerItem(id);
        container.setValue(containerDto.getValue());
        containerRepository.save(container);
    }

    /**
     * Returns all sub-containers of the specified  {@link Container} item.
     *
     * @param id container identifier
     * @return list of sub-containers
     * @throws EntityNotFoundException will be thrown if {@link Container} was not found
     */
    public List<ContainerDtoResponse> getAllSubContainers(int id) throws EntityNotFoundException {
        return getContainerItem(id).getSubContainers().stream().map(this::toContainerDto).collect(Collectors.toList());
    }

    /**
     * Returns {@link Container} item.
     *
     * @param id container identifier
     * @return the {@link ContainerDtoResponse} instance
     * @throws EntityNotFoundException will be thrown if {@link Container} was not found
     */
    public ContainerDtoResponse getContainer(int id) throws EntityNotFoundException {
        return toContainerDto(getContainerItem(id));
    }

    /**
     * Method to find all containers.
     */
    public List<ContainerDtoResponse> findAll() {
        return containerRepository.findAll().stream().map(this::toContainerDto).collect(Collectors.toList());
    }

    /**
     * Removes the main container instance.
     *
     * @param id the container identifier
     * @throws EntityNotFoundException will be thrown if {@link Container} was not found
     */
    public void removeMainContainer(int id) throws EntityNotFoundException {
        containerRepository.delete(getContainerItem(id));
    }


    private Container getContainerItem(int id) throws EntityNotFoundException {
        Optional<Container> optionalShortUrl = containerRepository.findById(id);
        if (!optionalShortUrl.isPresent()) {
            throw new EntityNotFoundException(id);
        }
        return optionalShortUrl.get();
    }

    private ContainerDtoResponse toContainerDto(Container container) {
        return new ContainerDtoResponse (container.getValue(), container.getId());
    }
}
