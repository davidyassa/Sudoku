package backend;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import java.util.ArrayList;

public class ValidationReport {

    private final ConcurrentLinkedQueue<String> errors = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean valid = new AtomicBoolean(true);

    public void addError(String error) {
        valid.set(false);
        errors.add(error);
    }

    public boolean isValid() {
        return valid.get();
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
}
