package com.globallogic.test.tree.exc;

/**
 * Exceptions which will be thrown when entity was not found.
 *
 * @author yaroslav.shymkiv
 */
public class EntityNotFoundException extends Exception {

    private int entityId;

    public EntityNotFoundException(int id) {
        this(id, String.format("Entity with id %s was not found.", id));
    }

    public EntityNotFoundException(int entityId, String message) {
        super(message);
        this.entityId = entityId;
    }
}
