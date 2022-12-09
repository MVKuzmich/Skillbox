public enum RandomAccessMemory {

    TYPE, MEMORY_VOLUME, WEIGHT;

    private int weight;

    RandomAccessMemory(int weight) {
        this.weight = weight;
    }

    RandomAccessMemory() {

    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
