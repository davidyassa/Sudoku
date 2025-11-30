/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.List;
// to store errors
public class ValidationResult {

    private List<String> rowErrors = new ArrayList<>();
    private List<String> colErrors = new ArrayList<>();
    private List<String> boxErrors = new ArrayList<>();

   
    public void addRowError(String error) {
        rowErrors.add(error);
    }

    public void addColError(String error) {
        colErrors.add(error);
    }

    public void addBoxError(String error) {
        boxErrors.add(error);
    }

    // Merge  result in the other threads
    // add all this add all errors in  other rows to rowerror arratlist
    public void merge(ValidationResult other) {
        this.rowErrors.addAll(other.rowErrors);
        this.colErrors.addAll(other.colErrors);
        this.boxErrors.addAll(other.boxErrors);
    }

    
    public boolean isValid() {
        return rowErrors.isEmpty() && colErrors.isEmpty() && boxErrors.isEmpty();
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
}
