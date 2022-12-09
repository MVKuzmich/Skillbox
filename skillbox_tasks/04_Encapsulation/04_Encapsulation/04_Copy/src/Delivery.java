public class Delivery {
    private final Dimensions deliveryDimensions;
    private final double deliveryWeight;
    private final String deliveryAddress;
    private final boolean deliveryCanTurned;
    private final String  deliveryRegistrNumber;
    private final boolean deliveryFragility;

    public Delivery(Dimensions deliveryDimensions, double deliveryWeight, String deliveryAddress,
                    boolean deliveryCanTurned, String deliveryRegistrNumber, boolean deliveryFragility) {
        this.deliveryDimensions = deliveryDimensions;
        this.deliveryWeight = deliveryWeight;
        this.deliveryAddress = deliveryAddress;
        this.deliveryCanTurned = deliveryCanTurned;
        this.deliveryRegistrNumber = deliveryRegistrNumber;
        this.deliveryFragility = deliveryFragility;
    }

    public Dimensions getDeliveryDimensions() {
        return deliveryDimensions;
    }

    public Delivery setDeliveryDimensions(Dimensions deliveryDimensions) {
        return new Delivery(deliveryDimensions, deliveryWeight, deliveryAddress, deliveryCanTurned, deliveryRegistrNumber,
                deliveryFragility);
    }

    public double getDeliveryWeight() {
        return deliveryWeight;
    }

    public Delivery setDeliveryWeight(double deliveryWeight) {
        return new Delivery(deliveryDimensions, deliveryWeight, deliveryAddress, deliveryCanTurned, deliveryRegistrNumber,
                deliveryFragility);
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public Delivery setDeliveryAddress(String deliveryAddress) {
        return new Delivery(deliveryDimensions, deliveryWeight, deliveryAddress, deliveryCanTurned, deliveryRegistrNumber,
                deliveryFragility);
    }

    public boolean isDeliveryCanTurned() {
        return deliveryCanTurned;
    }

    public String getDeliveryRegistrNumber() {
        return deliveryRegistrNumber;
    }

    public boolean isDeliveryFragility() {
        return deliveryFragility;
    }
}


