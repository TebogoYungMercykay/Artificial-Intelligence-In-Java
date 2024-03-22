public class Main {
    public static void main(String[] args) {
        IteratedLocalSearch tempVariable = new IteratedLocalSearch(1);
        
        for(int i = tempVariable.getCount(); i < 10; i++) {
            tempVariable.run(i);
        }
    }
}