public enum Keyboard {

    TYPE, ILLUMINATION_YES, ILLUMINATION_NO,

    WEIGHT;

    private int weight;

    Keyboard(int weight) {
        this.weight = weight;
    }

    Keyboard() {

    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

