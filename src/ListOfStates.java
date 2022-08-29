import javax.naming.LimitExceededException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class ListOfStates {
    private ArrayList<State> listOfStates = new ArrayList<>();

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
            Collections.sort(this.getListOfStates());
        } catch (FileNotFoundException e) {
            throw new StateException("Soubor " + filename + " nebyl nalezen:" + e.getLocalizedMessage());
        } catch (ParseException e) {
            throw new StateException("Špatný formát DPH " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("true or false is allowed");
        }
    }


    public void seeList() {
        for (State state: this.listOfStates) {
            System.out.println(getInfo(state));
        }
    }

    public void seeListOver(float over) {
        for (State state: this.listOfStates) {
            if (over >= state.getFullVat()) break;
            System.out.println(getInfo(state));
        }
    }

    public void seeListOver(float over, boolean status) {
        for (State state: this.listOfStates) {
            if (over >= state.getFullVat()) break;
            if (state.isSpecialVat() == status) {System.out.println(getInfo(state));}
        }
    }

    public void toFile(float limit, boolean status) throws StateException {
        ArrayList<ArrayList<State>> sortedStates;
        sortedStates = sorting(limit, status);
        try(FileWriter writer = new FileWriter("vat-over-" + limit + ".txt")) {
            for (State state: sortedStates.get(0)) {
                System.out.println(getInfo(state));
                writer.write(getInfo(state) + System.lineSeparator());
            }
            String text = "Sazba VAT 20 % nebo nižší nebo " + specialVatToString(status) + " speciální sazbu: ";
            System.out.print(text);
            writer.write(text);
            for (State state: sortedStates.get(1)) {
                text = state.getStateShortcut() + ", ";
                System.out.print(text);
                writer.write(text);
            }
        } catch (IOException e) {
            throw new StateException("Soubor nemohl být vytvořen" + e.getLocalizedMessage());
        }
    }

    private String specialVatToString(boolean status) {
        return status? "používají" : "nepouživají";
    }

    private ArrayList<ArrayList<State>> sorting(float limit, boolean status) {
        ArrayList<State> listOfStatesGood = new ArrayList<>();
        ArrayList<State> listOfStatesOther= new ArrayList<>();
        for (State state: this.listOfStates) {
            if (state.isSpecialVat() == status && limit < state.getFullVat()) {
                listOfStatesGood.add(state);
            } else {
                listOfStatesOther.add(state);
            }
        }
        ArrayList<ArrayList<State>> result = new ArrayList<ArrayList<State>>();
        result.add(listOfStatesGood);
        result.add(listOfStatesOther);
        return result;
    }

    private String getInfo(State state) {
        return state.getStateFullName() + " (" + state.getStateShortcut() + "): " + state.getFullVatStr() + " %";
    }

    private void forPoint5(float limit, boolean status) {
        ArrayList<State> StatesHigherThan = new ArrayList<>();
        ArrayList<State> StatesOthers = new ArrayList<>();

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

    public ArrayList<State> getListOfStates() {
        return listOfStates;
    }

    public void setListOfStates(ArrayList<State> listOfStates) {
        this.listOfStates = listOfStates;
    }
}
