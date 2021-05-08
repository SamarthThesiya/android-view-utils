package exceptions;

import androidx.annotation.Nullable;

public class InvalidArgumentException extends RuntimeException {

    private String message;
    public InvalidArgumentException(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
