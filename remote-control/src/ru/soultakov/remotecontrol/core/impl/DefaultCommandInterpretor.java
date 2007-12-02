package ru.soultakov.remotecontrol.core.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.soultakov.remotecontrol.core.ICommandInterpreter;
import ru.soultakov.remotecontrol.core.exceptions.IllegalJobException;

public class DefaultCommandInterpretor implements ICommandInterpreter {

    private String commandPrefix;
    private String delimeter;

    @Override
    public String getName(String command) throws IllegalJobException {
        checkCommand(command);
        final String[] strings = command.split(delimeter);
        if (strings.length == 0) {
            throw new IllegalJobException("Command name must be specified");
        }
        return strings[0];
    }

    private static void checkCommand(String command) {
        if (command == null) {
            throw new IllegalArgumentException("Command can't be null");
        }
    }

    @Override
    public Map<String, List<String>> getParameters(String command) throws IllegalJobException {
        checkCommand(command);
        final String[] strings = command.split(delimeter);
        final Map<String, List<String>> parameters;
        if (strings.length > 1) {
            parameters = parseParameters(Arrays.copyOfRange(strings, 1, strings.length));
        } else {
            parameters = Collections.emptyMap();
        }
        return parameters;
    }

    private Map<String, List<String>> parseParameters(String[] strings)
            throws IllegalJobException {
        assert strings.length > 0;
        final Map<String, List<String>> params = new HashMap<String, List<String>>();
        ArrayList<String> currentList = null;
        for (final String string : strings) {
            if (string.startsWith(commandPrefix)) {
                final ArrayList<String> list = new ArrayList<String>();
                params.put(string.substring(1), list);
                currentList = list;
            } else {
                if (currentList == null) {
                    throw new IllegalJobException("Wrong parameters : " + strings);
                }
                currentList.add(string);
            }
        }
        return params;
    }

    public String getDelimeter() {
        return this.delimeter;
    }

    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }
}
