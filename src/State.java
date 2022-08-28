import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class State implements Comparable<State>{
    private String stateShortcut;
    private String stateFullName;
    private float fullVat;
    private float discountedVat;
    private boolean specialVat;

    static DecimalFormat myDF = new DecimalFormat("0.#");

    public State(String stateShortcut, String stateFullName, float fullVat, float discountedVat, boolean specialVat) {
        this.stateShortcut = stateShortcut;
        this.stateFullName = stateFullName;
        this.fullVat = round(fullVat, 2);
        this.discountedVat = round(discountedVat, 2);
        this.specialVat = specialVat;
    }

    public String getDescritionWithBasicVat() {
        return this.getStateFullName() + " (" + this.getStateShortcut() + "): " + this.getFullVat() + " %";
    }

    @Override
    public int compareTo(State s) {
        return Float.compare(s.getFullVat(), this.getFullVat());
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.floatValue();
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

    public float getFullVat() {
        return fullVat;
    }

    public String getFullVatStr() {
        return myDF.format(this.fullVat);
    }

    public String getDiscountedVatStr() {
        return myDF.format(this.discountedVat);
    }

    public void setFullVat(float fullVat) {
        this.fullVat = fullVat;
    }

    public float getDiscountedVat() {
        return discountedVat;
    }

    public void setDiscountedVat(float discountedVat) {
        this.discountedVat = discountedVat;
    }

    public boolean isSpecialVat() {
        return specialVat;
    }

    public void setSpecialVat(boolean specialVat) {
        this.specialVat = specialVat;
    }
}
