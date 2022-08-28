import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ListOfStates {
    private ArrayList<State> listOfStates = new ArrayList<>();

    public void addPlant(State state) {
        listOfStates.add(state);
    }

    public void addFromFile(String filename) throws StateException {
        String line;
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {

                line = scanner.nextLine();
                String[] items = line.split("\\t");

                String stateShortcut = items[0];
                String stateFullName = items[1];
                String fullVatAsText = items[2];
                String discountedVatAsText = items[3];
                String specialVatAsText = items[4];

                float fullVat = toLong(fullVatAsText);
                float discountedVat = toLong(discountedVatAsText);
                boolean specialVat = toBoolean(specialVatAsText);

                State state = new State(stateShortcut, stateFullName, fullVat, discountedVat, specialVat);

                listOfStates.add(state);

            }
        } catch (FileNotFoundException e) {
            throw new StateException("Soubor " + filename + " nebyl nalezen:" + e.getLocalizedMessage());
        } catch (ParseException e) {
            throw new StateException("Špatný formát DPH " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new StateException("Špatný formát dnů " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("true or false is allowed");
        }
    }

    private float toLong(String number) throws ParseException {
        if ((number.matches("\\d+")) | (number.contains("."))){
            return Float.parseFloat(number);
        } else {
            return fromStringToFloatWithComma(number);
        }
    }

    private static float fromStringToFloatWithComma(String digits) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
        Number n = format.parse(digits);
        return n.floatValue();
    }

    private boolean toBoolean(String s) {
        if ("true".equals(s) || "false".equals(s)) {
            return Boolean.parseBoolean(s);
        }
        else  {
            throw new IllegalArgumentException("true or false is allowed");
        }
    }
}
