package models;

import java.util.ArrayList;

public class Validations implements BaseModel {

    ArrayList<Validation> validations;

    public Validations() {
        validations = new ArrayList<>();
    }

    public Validations add(String validation) {
        validations.add(new Validation(validation, null, null));
        return this;
    }

    public Validations add(String validation, Object extra) {
        validations.add(new Validation(validation, extra, null));
        return this;
    }

    public Validations add(String validation, Object extra, String message) {
        validations.add(new Validation(validation, extra, message));
        return this;
    }

    public Boolean remove(String validationString) {
        for (Validation validation: validations) {
            if (validation.validation.equalsIgnoreCase(validationString)) {
                return validations.remove(validation);
            }
        }
        return false;
    }

    public ArrayList<Validation> getValidations() {
        return validations;
    }

    public class Validation implements BaseModel {
        String validation, message;
        Object extra;

        Validation(String validation, Object extra, String message) {
            this.validation = validation;
            this.extra      = extra;
            this.message    = message;
        }

        public String getValidation() {
            return validation;
        }

        public String getMessage() {
            return message;
        }

        public Object getExtra() {
            return extra;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
