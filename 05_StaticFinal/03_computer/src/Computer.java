public class Computer {

    private final Processor processor;
    private final RandomAccessMemory randomAccessMemory;
    private final HardDisk hardDisk;
    private final Screen screen;
    private final Keyboard keyboard;
    private final String vendor;
    private final String name;

    private int computerWeight;

    public Computer (Processor processor, RandomAccessMemory randomAccessMemory, HardDisk hardDisk,
                                Screen screen, Keyboard keyboard, String vendor, String name) {
        this.processor = processor;
        this.randomAccessMemory = randomAccessMemory;
        this.hardDisk = hardDisk;
        this.screen = screen;
        this.keyboard = keyboard;
        this.vendor = vendor;
        this.name = name;

    }

    public Processor getProcessor() {
        return processor;
    }

    public Computer setProcessor(Processor processor) {
        return new Computer(processor, randomAccessMemory, hardDisk,
                screen, keyboard, vendor, name);

    }

    public RandomAccessMemory getRandomAccessMemory() {
        return randomAccessMemory;
    }

    public Computer setRandomAccessMemory(RandomAccessMemory randomAccessMemory) {
        return new Computer(processor, randomAccessMemory, hardDisk,
                screen, keyboard, vendor, name);
    }

    public HardDisk getHardDisk() {
        return hardDisk;
    }

    public Computer setHardDisk(HardDisk hardDisk) {
        return new Computer(processor, randomAccessMemory, hardDisk,
                screen, keyboard, vendor, name);
    }

    public Screen getScreen() {
        return screen;
    }

    public Computer setScreen(Screen screen) {
        return new Computer(processor, randomAccessMemory, hardDisk,
                screen, keyboard, vendor, name);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Computer setKeyboard(Keyboard keyboard) {
        return new Computer(processor, randomAccessMemory, hardDisk,
                screen, keyboard, vendor, name);
    }
    public int calculateComputerWeight () {
        computerWeight = Keyboard.WEIGHT.getWeight() + Screen.WEIGHT.getWeight() + HardDisk.WEIGHT.getWeight() +
        RandomAccessMemory.WEIGHT.getWeight() + Processor.WEIGHT.getWeight();
        return computerWeight;
    }

    public String toString() {
    return "Computer{" +
                "processor=" + processor +
                ", randomAccessMemory=" + randomAccessMemory +
                ", hardDisk=" + hardDisk +
                ", screen=" + screen +
                ", keyboard=" + keyboard +
                ", vendor='" + vendor + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
