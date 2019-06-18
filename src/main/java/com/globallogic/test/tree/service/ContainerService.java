package com.globallogic.test.tree.service;

import com.globallogic.test.tree.dao.ContainerRepository;
import com.globallogic.test.tree.dto.ContainerDto;
import com.globallogic.test.tree.exc.EntityNotFoundException;
import com.globallogic.test.tree.model.Container;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for
 *
 * @author yaroslav.shymkiv
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContainerService {

    private ContainerRepository containerRepository;

    /**
     * Saves new {@link Container} com.globallogic.test.tree item into DB.
     *
     * @param urlDto data with container value
     */
    public void createNewContainerTree(ContainerDto urlDto) {
        containerRepository.save(new Container(urlDto.getValue()));
    }

    /**
     * Saves {@link Container} into DB as a child of the specified container.
     *
     * @param parentId identifier of the parent container
     * @param containerDto data with container value
     */
    public void createSubContainer(int parentId, ContainerDto containerDto) throws EntityNotFoundException {
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
    public void update(int id, ContainerDto containerDto) throws EntityNotFoundException {
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
    public List<ContainerDto> getAllSubContainers(int id) throws EntityNotFoundException {
        return getContainerItem(id).getSubContainers().stream().map(this::toContainerDto).collect(Collectors.toList());
    }

    /**
     * Returns {@link Container} item.
     *
     * @param id container identifier
     * @return the {@link ContainerDto} instance
     * @throws EntityNotFoundException will be thrown if {@link Container} was not found
     */
    public ContainerDto getContainer(int id) throws EntityNotFoundException {
        return toContainerDto(getContainerItem(id));
    }

    private Container getContainerItem(int id) throws EntityNotFoundException {
        Optional<Container> optionalShortUrl = containerRepository.findById(id);
        if (!optionalShortUrl.isPresent()) {
            throw new EntityNotFoundException(id);
        }
        return optionalShortUrl.get();
    }

    private ContainerDto toContainerDto(Container container) {
        return new ContainerDto(container.getId(), container.getValue());
    }

    public List<ContainerDto> findAll() {
        return containerRepository.findAll().stream().map(this::toContainerDto).collect(Collectors.toList());
    }
}
