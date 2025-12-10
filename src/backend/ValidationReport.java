package backend;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import java.util.ArrayList;

public class ValidationReport {

    private final ConcurrentLinkedQueue<String> errors = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<String> nulls = new ConcurrentLinkedQueue<>();

    private final AtomicBoolean valid = new AtomicBoolean(true);
    private final AtomicBoolean invalid = new AtomicBoolean(false);
    private final AtomicBoolean incomplete = new AtomicBoolean(false);

    public void addError(String error) {
        valid.set(false);
        invalid.set(true);
        errors.add(error);
    }

    public void addNull(String error) {
        valid.set(false);
        incomplete.set(true);
        nulls.add(error);
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public List<String> getNulls() {
        return new ArrayList<>(nulls);
    }

}
