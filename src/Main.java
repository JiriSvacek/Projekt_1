public class Main {
    public static void main(String[] args) {
        ListOfStates seznam = new ListOfStates();
        try {
            seznam.addFromFile("src/vat-eu.csv");
            seznam.toFile(false);
        } catch (StateException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}