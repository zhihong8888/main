package seedu.address.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Versioned Model List keeps track of a list of models committed defined in ModelTypes.java.
 */

public class VersionedModelList {
    private int currentStatePointer;
    private List<Set<ModelTypes>> myCommitModelTypes;

    public VersionedModelList() {
        currentStatePointer = 0;
        myCommitModelTypes = new ArrayList<>();
    }

    //-----------------------------------------------------------------------------

    /**
     *  Undo Versioned Model List
     */
    public void undo() {
        if (!canUndoStorage()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
    }

    /**
     *  Redo Versioned Model List
     */
    public void redo() {
        if (!canRedoStorage()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
    }

    /**
     *  Add to the list to keep track of which model is committed.
     */
    public void add (ModelTypes type) {
        Set<ModelTypes> set = new HashSet<>();
        set.add(type);
        myCommitModelTypes.add(set);
        currentStatePointer = myCommitModelTypes.size();
    }

    /**
     *  Adds to the list to keep track of which models are committed.
     */
    public void addMultiple (Set<ModelTypes> set) {
        myCommitModelTypes.add(set);
        currentStatePointer = myCommitModelTypes.size();
    }

    /**
     *  Used to check which model was last committed with a command.
     *  Important for undo and redo class.
     */
    public Set<ModelTypes> getLastCommitType () {
        if (!canUndoStorage()) {
            throw new NoRedoableStateException();
        }
        return myCommitModelTypes.get(currentStatePointer - 1);
    }

    public Set<ModelTypes> getNextCommitType () {
        if (!canRedoStorage()) {
            throw new NoRedoableStateException();
        }
        return myCommitModelTypes.get(currentStatePointer);
    }

    /**
     * Returns true if {@code redo()} has states to redo in any of the model.
     */
    public boolean canRedoStorage() {
        return currentStatePointer < myCommitModelTypes.size();
    }

    /**
     * Returns true if {@code undo()} has states to undo in any of the model.
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
            /*
            super("Current state pointer at start of storage list in all Storages, unable to undo."
                + " [pointer:" + versionedModelList.currentStatePointer + "] "
                + " [list size:" + versionedModelList.myCommitModelTypes.size() + "]");
            */
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of storage list in all storages, unable to redo.");
            /*
            super("Current state pointer at end of storage list in all storages, unable to redo."
                + " [pointer:" + versionedModelList.currentStatePointer + "] "
                + " [list size:" + versionedModelList.myCommitModelTypes.size() + "]");
            */
        }
    }
}
