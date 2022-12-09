public enum Screen {

    DIAGONAL, IPS, TN, VA, WEIGHT;

    private int weight;

    Screen(int weight) {
        this.weight = weight;
    }

    Screen() {

    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
