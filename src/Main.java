public class Main {
    public static void main(String[] args) {
        ListOfStates seznam = new ListOfStates();
        try {
            seznam.addFromFile("src/vat-eu.csv");
            //seznam.seeList();
            //seznam.seeListOver(20, false);
            seznam.toFile(20, false);
        } catch (StateException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}