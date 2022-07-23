public enum HardDisk {
    HDD, SDD, MEMORY_VOLUME, WEIGHT;

    private int weight;

    HardDisk(int weight) {
        this.weight = weight;
    }

    HardDisk() {

    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
