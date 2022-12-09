public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        /*
         ниже напишите код для выполнения задания:
          С помощью цикла и преобразования чисел в символы найдите все коды
          букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

         */
        for (char letter = 'А'; letter <= 'я'; letter++) {
            int i = letter;
            System.out.println(letter + " - " + i);
        }

            char letter = 'Ё';
            int i = letter;
            System.out.println(letter + " - " + i);

            char letter1 = 'ё';
            int i1 = letter1;
        System.out.println(letter1 + " - " + i1);

        }
    }

