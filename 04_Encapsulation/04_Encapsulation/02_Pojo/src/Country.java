public class Country {
    private String countryName;
    private int countryPopulation;
    private double countrySquare;
    private String countryCapitalName;
    private boolean countryAccessSea;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(int countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public double getCountrySquare() {
        return countrySquare;
    }

    public void setCountrySquare(double countrySquare) {
        this.countrySquare = countrySquare;
    }

    public String getCountryCapitalName() {
        return countryCapitalName;
    }

    public void setCountryCapitalName(String countryCapitalName) {
        this.countryCapitalName = countryCapitalName;
    }

    public boolean isCountryAccessSea() {
        return countryAccessSea;
    }

    public void setCountryAccessSea(boolean countryAccessSea) {
        this.countryAccessSea = countryAccessSea;
    }
}
