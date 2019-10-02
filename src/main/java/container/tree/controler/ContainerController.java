package container.tree.controler;

import container.tree.dto.ContainerDtoRequest;
import container.tree.dto.ContainerDtoResponse;
import container.tree.exc.EntityNotFoundException;
import container.tree.model.Container;
import container.tree.service.ContainerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for containers.
 *
 * @author yaroslav.shymkiv
 */
@RestController
@RequestMapping("/api/containers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContainerController {

    private ContainerService containerService;

    /**
     * Endpoint to save new {@link Container} tree.
     *
     * @param urlDto data with short and real URL
     * @return empty response
     */
    @PostMapping
    public ResponseEntity createNewContainerTree(@RequestBody ContainerDtoRequest urlDto) {
        containerService.createNewContainerTree(urlDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Endpoint to get all {@link Container} instances.
     *
     * @return the list of {@link ContainerDtoResponse} items
     */
    @GetMapping("/all")
    public ResponseEntity<List<ContainerDtoResponse>> findAll() {
        return new ResponseEntity<> (containerService.findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to get specified {@link Container} item.
     *
     * @param id container identifier
     * @return the {@link ContainerDtoResponse} instance
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerDtoResponse> getContainer(@PathVariable int id)
            throws EntityNotFoundException {
        return new ResponseEntity<>(containerService.getContainer(id), HttpStatus.OK);
    }

    /**
     * Endpoint to remove child of the {@link Container} item.
     *
     * @param id identifier of the main container
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity removeMainContainer(@PathVariable int id)
            throws EntityNotFoundException {
        containerService.removeMainContainer(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // **************** Endpoints for sub-containers configuration ***************************

    /**
     * Endpoint to get all sub-containers of the specified  {@link Container} item.
     *
     * @param id container identifier
     * @return the list of {@link ContainerDtoResponse} items
     */
    @GetMapping("/{id}/subContainers")
    public ResponseEntity<List<ContainerDtoResponse>> getAllSubContainers(@PathVariable int id) throws EntityNotFoundException {
        return new ResponseEntity<> (containerService.getAllSubContainers(id), HttpStatus.OK);
    }

    /**
     * Endpoint to update {@link Container} item.
     *
     * @param urlDto updated data
     * @return empty response
     */
    @PutMapping("/{id}/subContainers")
    public ResponseEntity updateContainer(@PathVariable int parentId, @RequestBody ContainerDtoRequest urlDto)
            throws EntityNotFoundException {
        containerService.update(parentId, urlDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint to create new child of the {@link Container} item.
     *
     * @param containerDto data with container value
     * @return empty response
     */
    @PostMapping("/{id}/subContainers")
    public ResponseEntity createSubContainer(@PathVariable int id, @RequestBody ContainerDtoRequest containerDto)
            throws EntityNotFoundException {
        containerService.createSubContainer(id, containerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Endpoint to remove child of the {@link Container} item.
     *
     * @param childId identifier of the sub-container
     * @return empty response
     */
    @DeleteMapping("{id}/subContainers/{childId}")
    public ResponseEntity removeSubContainer(@PathVariable int id, @PathVariable int childId)
            throws EntityNotFoundException {
        containerService.removeSubContainer(id, childId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
