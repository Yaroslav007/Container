package com.globallogic.test.tree.controler;

import com.globallogic.test.tree.dto.ContainerDto;
import com.globallogic.test.tree.exc.EntityNotFoundException;
import com.globallogic.test.tree.model.Container;
import com.globallogic.test.tree.service.ContainerService;
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
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContainerController {

    private ContainerService containerService;

    /**
     * Endpoint to save new {@link Container} com.globallogic.test.tree.
     *
     * @param urlDto data with short and real URL
     * @return empty response
     */
    @PostMapping("/createNewContainerTree")
    public ResponseEntity createNewContainerTree(@RequestBody ContainerDto urlDto) {
        containerService.createNewContainerTree(urlDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Endpoint to create new child of the {@link Container} item.
     *
     * @param containerDto data with container value
     * @return empty response
     */
    @PostMapping("{id}/createSubContainer")
    public ResponseEntity createSubContainer(@PathVariable int id, @RequestBody ContainerDto containerDto)
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
    @DeleteMapping("{id}/removeSubContainer")
    public ResponseEntity removeSubContainer(@PathVariable int id, @RequestParam int childId)
            throws EntityNotFoundException {
        containerService.removeSubContainer(id, childId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Endpoint to update {@link Container} item.
     *
     * @param urlDto updated data
     * @return empty response
     */
    @PutMapping("/{id}/updateContainer")
    public ResponseEntity updateContainer(@PathVariable int id, @RequestBody ContainerDto urlDto)
            throws EntityNotFoundException {
        containerService.update(id, urlDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint to get specified {@link Container} item.
     *
     * @param id container identifier
     * @return the {@link ContainerDto} instance
     */
    @GetMapping("/{id}")
    public ResponseEntity getContainer(@PathVariable int id)
            throws EntityNotFoundException {
        return new ResponseEntity<>(containerService.getContainer(id), HttpStatus.OK);
    }

    /**
     * Endpoint to get all sub-containers of the specified  {@link Container} item.
     *
     * @param id container identifier
     * @return the list of {@link ContainerDto} items
     */
    @GetMapping("/{id}/getAllSubContainers")
    public ResponseEntity<List<ContainerDto>> getAllSubContainers(@PathVariable int id) throws EntityNotFoundException {
        return new ResponseEntity(containerService.getAllSubContainers(id), HttpStatus.OK);
    }

     /**
     * Endpoint to get all {@link Container} items.
     *
     * @return the list of {@link ContainerDto} items
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<ContainerDto>> findAll() {
        return new ResponseEntity(containerService.findAll(), HttpStatus.OK);
    }
}
