import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

// Class Containing IteratedLocalSearch Algorithm - Doxygen Comments for Documentation

public class IteratedLocalSearch extends Helper {

    private ArrayList<ArrayList<Integer>> instance;
    private Integer cap;
    private ArrayList<ArrayList<Integer>> bestInstance;
    private Integer binCount = 0;
    private AtomicLong runtime = new AtomicLong(0);
    private Double bestFitness;

    private int startValueCount = 0;

}
