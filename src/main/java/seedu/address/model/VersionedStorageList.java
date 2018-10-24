package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Versioned Storage List keeps track of a list of storage committed defined in StorageTypes.java.
 */

public class VersionedStorageList {
    private static VersionedStorageList storage;
    private static int currentStatePointer;
    private static List<StorageTypes> myCommitStorageTypes;
    private boolean deletedPerson;

    public VersionedStorageList() {
        currentStatePointer = 0;
        myCommitStorageTypes = new ArrayList<>();
        deletedPerson = false;
    }

    /**
     * Enforces only a single instance of the class is allowed.
     */
    public static VersionedStorageList getInstance() {
        if (storage == null) {
            storage = new VersionedStorageList();
        }
        return storage;
    }

    //-----------------------------------------------------------------------------

    /**
     *  Check if command can be undo in storage list
     */
    public void checkCanUndoStorage () {
        if (!canUndoStorage()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
    }

    /**
     *  Check if command can be redo in storage list
     */
    public void checkCanRedoStorage () {
        if (!canRedoStorage()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
    }

    /**
     *  Adds to the list to keep track of which storage is committed.
     */
    public void add (StorageTypes type) {
        myCommitStorageTypes.add(type);
        currentStatePointer++;
        deletedPerson = false;
    }

    /**
     *  Used to check which storage was last committed with a command.
     *  Important for undo and redo class.
     */
    public StorageTypes getLastCommitType () {
        if (!canUndoStorage()) {
            throw new NoRedoableStateException();
        }
        return myCommitStorageTypes.get(currentStatePointer - 1);
    }

    public StorageTypes getNextCommitType () {
        if (!canRedoStorage()) {
            throw new NoRedoableStateException();
        }
        return myCommitStorageTypes.get(currentStatePointer);
    }

    /**
     *  Checks if person is deleted, then more than 1 storage will be affected i.e addressbook, schedulelist..
     *  Getters and setters for deletedPerson
     *  Important for undo and redo class.
     */
    public boolean getDeletedPersonUndoRedoLoop () {
        return deletedPerson;
    }

    public void setDeletedPersonUndoRedoLoop (boolean logic) {
        deletedPerson = logic;
    }

    /**
     * Returns true if {@code redo()} has states to redo in any of the storage.
     */
    public boolean canRedoStorage() {
        return currentStatePointer < myCommitStorageTypes.size();
    }

    /**
     * Returns true if {@code undo()} has states to undo in any of the storage.
     */
    public boolean canUndoStorage() {
        return currentStatePointer > 0;
    }


    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of storage list in all Storages, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of storage list in all storages, unable to redo.");
        }
    }
}
