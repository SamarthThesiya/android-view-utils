package customView.validatable;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import interfaces.VuValidatable;
import models.Validations;

public class VuEditText extends AppCompatEditText implements VuValidatable {

    private Validations validations;
    public VuEditText(@NonNull Context context) {
        super(context);
        init();
    }

    public VuEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VuEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        validations = new Validations();
    }


    @Override
    public Validations validations() {
        return validations;
    }

    @Override
    public void handleValidationError(Validations.Validation validationError) {
        setError(validationError.getMessage());
    }
}
