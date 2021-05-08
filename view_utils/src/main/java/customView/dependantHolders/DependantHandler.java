package customView.dependantHolders;

import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import interfaces.VuValidatable;
import models.Validations;
import utils.VuValidationMethods;

public class DependantHandler {

    private ArrayList<VuValidatable> validatables;
    private VuValidationMethods vuValidationMethods;

    public DependantHandler() {
        validatables = new ArrayList<>();
    }

    public DependantHandler setValidatables(VuValidatable... validatables) {
        this.validatables = new ArrayList<>(Arrays.asList(validatables));

        return this;
    }

    public DependantHandler setCustomValidatableMethods(VuValidationMethods customValidatableMethods) {
        this.vuValidationMethods = customValidatableMethods;
        return this;
    }

    public void removeAllValidatables() {
        validatables = new ArrayList<>();
    }

    public Boolean validateValidatables() throws InvocationTargetException, IllegalAccessException {

        for (VuValidatable vuValidatable: validatables) {

            Validations.Validation validationError = validate(vuValidatable, vuValidationMethods);
            if (validationError != null) {
                vuValidatable.handleValidationError(validationError);
                return false;
            }
        }
        return true;
    }

    private Validations.Validation validate(VuValidatable vuValidatable, VuValidationMethods vuValidationMethods) throws InvocationTargetException, IllegalAccessException {
        if (vuValidationMethods == null) {
            vuValidationMethods = new VuValidationMethods();
        }
        vuValidationMethods.setVuValidatable(vuValidatable);

        for (Validations.Validation validation : vuValidatable.validations().getValidations()) {

            try {
                Method  validationMethod   = vuValidationMethods.getClass().getMethod(validation.getValidation(), Validations.Validation.class);
                Boolean isValidationPasses = (Boolean) validationMethod.invoke(vuValidationMethods, validation);

                if (!isValidationPasses) {
                    if (TextUtils.isEmpty(validation.getMessage())) {
                        validation.setMessage("Invalid");
                    }
                    return validation;
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Method '" + validation.getValidation() + "' not found in class '" + vuValidationMethods.getClass().toString() + "'. If you used your custom class then please pass it while setDependent.");
            }
        }
        return null;
    }
}
