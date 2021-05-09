package utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

import exceptions.InvalidArgumentException;
import interfaces.VuValidatable;
import models.Validations;

public class VuValidationMethods {

    protected VuValidatable vuValidatable;

    public static final String REQUIRED   = "required";
    public static final String MIN_LENGTH = "minLength";
    public static final String REGEX      = "regex";

    public void setVuValidatable(VuValidatable vuValidatable) {
        this.vuValidatable = vuValidatable;
    }

    public Boolean required(Validations.Validation validation) {
        if (TextUtils.isEmpty(vuValidatable.getText())) {
            if (TextUtils.isEmpty(validation.getMessage())) {

                // If message is not set while adding validation then it will set message
                // This is optional
                validation.setMessage("This field is required");
            }
            return false;
        }

        return true;
    }

    public Boolean minLength(Validations.Validation validation) {
        if (validation.getExtra() == null) {
            throw new InvalidArgumentException("Need to pass minimum length of string while adding the validation");
        } else if (!(validation.getExtra() instanceof Integer)) {
            throw new InvalidArgumentException("For minLength, extra should be positive number only");
        }

        int minLength = (int) validation.getExtra();

        if (vuValidatable.getText().length() < minLength) {
            if (TextUtils.isEmpty(validation.getMessage())) {
                validation.setMessage("Too few characters");
            }
            return false;
        }
        return true;
    }

    public Boolean regex(Validations.Validation validation) {

        if (validation.getExtra() == null) {
            throw new InvalidArgumentException("Regex needs to pass in 2nd argument of models.Validations.add()");
        } else if (!(validation.getExtra() instanceof String)) {
            throw new InvalidArgumentException("Regex should be string");
        }

        String regex = (String) validation.getExtra();

        if (!Pattern.matches(regex, vuValidatable.getText())) {
            if (TextUtils.isEmpty(validation.getMessage())) {
                validation.setMessage("Invalid input");
            }
            return false;
        }
        return true;
    }
}
