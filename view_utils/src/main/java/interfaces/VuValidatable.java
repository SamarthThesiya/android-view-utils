package interfaces;

import android.text.Editable;

import models.Validations;

public interface VuValidatable {

    Validations validations();
    void handleValidationError(Validations.Validation validationError);
    Editable getText();
}
