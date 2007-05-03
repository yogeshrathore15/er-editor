package ru.amse.soultakov.ereditor.controller.undo;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newArrayList;

import java.util.List;

import ru.amse.soultakov.ereditor.util.BoundedStack;

public class CommandManager {

    private final BoundedStack<ICommand> undoList = new BoundedStack<ICommand>();

    private final BoundedStack<ICommand> redoList = new BoundedStack<ICommand>();

    private final List<CommandManagerListener> listeners = newArrayList();

    public CommandManager() {
    }

    public void executeCommand(ICommand command) {
        command.doIt();
        undoList.push(command);
        redoList.clear();
        notifyInvoked();
    }

    public boolean canUndoCommand() {
        return !undoList.isEmpty();
    }

    public void undoCommand() {
        if (!canUndoCommand()) {
            throw new IllegalStateException("Undo list is empty!");
        }
        ICommand command = undoList.pop();
        redoList.push(command);
        command.undoIt();
        notifyUndone();
    }

    public boolean canRedoCommand() {
        return !redoList.isEmpty();
    }

    public void redoCommand() {
        if (!canRedoCommand()) {
            throw new IllegalStateException("Redo list is empty!");
        }
        ICommand command = redoList.pop();
        undoList.push(command);
        command.doIt();
        notifyRedone();
    }

    public boolean addListener(CommandManagerListener cml) {
        return listeners.add(cml);
    }

    public boolean removeListener(CommandManagerListener cml) {
        return listeners.remove(cml);
    }

    private void notifyUndone() {
        for (CommandManagerListener cml : listeners) {
            cml.commandUndone();
        }
    }

    private void notifyRedone() {
        for (CommandManagerListener cml : listeners) {
            cml.commandRedone();
        }
    }

    private void notifyInvoked() {
        for (CommandManagerListener cml : listeners) {
            cml.commandInvoked();
        }
    }

    // @Override
    // public String toString() {
    // for(ICommand command : undoList) {
    // System.out.println(command);
    // }
    // for(ICommand command : redoList) {
    // System.out.println(command);
    // }
    // return "";
    // }

}
