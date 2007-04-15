/*
 * Created on 17.03.2007
 */
package ru.amse.soultakov.ereditor.model;

public enum SimpleAttributeType implements IAttributeType {
    INTEGER("Integer") {
        @Override
        public boolean isCorrectString(String value) {
            if (value == null) {
                return false;
            }
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    },
    CHAR("Char") {
        @Override
        public boolean isCorrectString(String value) {
            return value == null ? false : value.length() == 1;
        }
    },
    DOUBLE("Double") {
        @Override
        public boolean isCorrectString(String value) {
            if (value == null) {
                return false;
            }
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
    };

    private String name;

    private SimpleAttributeType(String name) {
        this.name = name;
    }
    
    public IAttributeType copy() {
        return this;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isCorrectString(String value);

    public String makeCorrectLiteral(String value) {
        throw new UnsupportedOperationException("Not supported yet");
    }

}
