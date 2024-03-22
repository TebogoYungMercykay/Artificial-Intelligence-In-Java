public class IteratedLocalSearch {
    private Integer count;

    public IteratedLocalSearch(Integer count) {
        this.count = count;
    }

    public void run(Integer count) {
        this.count += count;
        System.out.println("Running Iterated Local Search with Count = " + count);
    }

    public Integer getCount() {
        return count++;
    }
}
