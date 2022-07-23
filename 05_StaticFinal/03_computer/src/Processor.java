public enum Processor {
    FREQUENCY, CORE_AMOUNT, PRODUCER, WEIGHT;

    private int weight;

    Processor(int weight) {
        this.weight = weight;
    }

    Processor() {

    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
