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

    public IteratedLocalSearch(ArrayList<ArrayList<Integer>> instance, Integer cap, Integer iterations) {

        this.instance = instance;
        this.cap = cap;

        startValueCount = SumValues();

        // * Starting The Timer
        long startTime = System.currentTimeMillis();

        // * The Initial Solution
        bestFit();
        this.bestInstance = copyInstance(instance);
        this.bestFitness = Fitness(instance);

        for (int j = 0; j < iterations; j++) {

            // Saving The Current Best Instance
            bestInstance = copyInstance(this.instance);

            // * Pertubaton
            Random random = new Random();
            Integer randomValue = random.nextInt(iterations - j) + j;

            if (randomValue < iterations / 2) {
                reshuffleRandom();
            } else if (randomValue > iterations * 0.75 && j % 4 == 0) {
                reshuffleSmallest();
            }

            // Pertubaton CONT.
            Swap();

            // * LOCAL SEARCH / ACCEPT
            Double newFitness = Fitness(this.instance);
            if (newFitness <= bestFitness) {
                bestFitness = newFitness;
            } else {
                this.instance = bestInstance;
            }
        }

        this.instance = bestInstance;

        runtime = new AtomicLong((System.currentTimeMillis() - startTime));
        binCount = this.instance.size();
    }

}
