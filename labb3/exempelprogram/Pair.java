public class Pair {

    private int first, second, capacity, flow, residualCapacity;
    
    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public Pair(int first, int second, int capacity) {
        this.first = first;
        this.second = second;
        this.capacity = capacity;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int newCapacity) {
        capacity = newCapacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int newFlow) {
        flow = newFlow;
    }

    public int getResidualCapacity() {
        return residualCapacity;
    }

    public void setResidualCapacity(int newResidualCapacity) {
        residualCapacity = newResidualCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair p = (Pair) o;
            return p.first == first && p.second == second;
        }
        return false;
    }
}
