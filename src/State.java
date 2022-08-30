import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class State implements Comparable<State>{
    private String stateShortcut;
    private String stateFullName;
    private double fullVat;
    private double discountedVat;
    private boolean specialVat;

    static DecimalFormat myDF = new DecimalFormat("0.#");

    public State(String stateShortcut, String stateFullName, double fullVat, double discountedVat, boolean specialVat) {
        this.stateShortcut = stateShortcut;
        this.stateFullName = stateFullName;
        this.fullVat = round(fullVat, 2);
        this.discountedVat = round(discountedVat, 2);
        this.specialVat = specialVat;
    }

    public String getDescritionWithBasicVat() {
        return this.getStateFullName() + " (" + this.getStateShortcut() + "): " + this.getFullVatStr() + " %";
    }

    @Override
    public int compareTo(State s) {
        return Double.compare(s.getFullVat(), this.getFullVat());
    }

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getStateShortcut() {
        return stateShortcut;
    }

    public void setStateShortcut(String stateShortcut) {
        this.stateShortcut = stateShortcut;
    }

    public String getStateFullName() {
        return stateFullName;
    }

    public void setStateFullName(String stateFullName) {
        this.stateFullName = stateFullName;
    }

    public double getFullVat() {
        return fullVat;
    }

    public String getFullVatStr() {
        return myDF.format(this.fullVat);
    }

    public String getDiscountedVatStr() {
        return myDF.format(this.discountedVat);
    }

    public void setFullVat(double fullVat) {
        this.fullVat = fullVat;
    }

    public double getDiscountedVat() {
        return discountedVat;
    }

    public void setDiscountedVat(double discountedVat) {
        this.discountedVat = discountedVat;
    }

    public boolean isSpecialVat() {
        return specialVat;
    }

    public void setSpecialVat(boolean specialVat) {
        this.specialVat = specialVat;
    }
}
