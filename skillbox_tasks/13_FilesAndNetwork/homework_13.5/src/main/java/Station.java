public class Station {
    private Line line;
    private String name;

    public Station(String name, Line line) {
        this.name = name;
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " " + name;
    }
}
