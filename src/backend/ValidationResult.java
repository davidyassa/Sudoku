/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<String> rowErrors = new ArrayList<>();
    private final List<String> colErrors = new ArrayList<>();
    private final List<String> boxErrors = new ArrayList<>();

    private final List<String> nulls = new ArrayList<>();

    public void addError(String e) {
        if (e.startsWith("ROW")) {
            rowErrors.add(e);
        } else if (e.startsWith("COLUMN")) {
            colErrors.add(e);
        } else {
            boxErrors.add(e);
        }
    }

    public void addNull(String e) {
        nulls.add(e);
    }

    public Validity validate() {
        if (!nulls.isEmpty()) {
            return Validity.INCOMPLETE;
        }
        if (!(rowErrors.isEmpty() && colErrors.isEmpty() && boxErrors.isEmpty())) {
            return Validity.INVALID;
        }
        return Validity.VALID;

    }

    public List<String> getRowErrors() {
        return rowErrors;
    }

    public List<String> getColErrors() {
        return colErrors;
    }

    public List<String> getBoxErrors() {
        return boxErrors;
    }

    public List<String> getNulls() {
        return nulls;
    }
}
