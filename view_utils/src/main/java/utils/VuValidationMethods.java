package utils;

import android.text.TextUtils;

import exceptions.InvalidArgumentException;
import interfaces.VuValidatable;
import models.Validations;

public class VuValidationMethods {

    VuValidatable vuValidatable;

    public static final String REQUIRED   = "required";
    public static final String MIN_LENGTH = "minLength";

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
}
