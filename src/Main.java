public class Main {
    public static void main(String[] args) {
        ListOfStates seznam = new ListOfStates();
        try {
            seznam.addFromFile("src/vat-eu.csv");
            seznam.seeListOver();
            seznam.seeListOver(20);
        } catch (StateException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}