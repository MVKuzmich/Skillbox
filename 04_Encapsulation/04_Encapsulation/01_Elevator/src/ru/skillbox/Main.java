package ru.skillbox;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ru.skillbox.Elevator elevator = new ru.skillbox.Elevator(-3, 26);

        while (true) {
            System.out.print("Введите номер этажа: ");

            int floor = new Scanner(System.in).nextInt();

            elevator.move(floor);
        }
    }
}
