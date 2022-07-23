public class Main {

    public static void main(String[] args) {

        String text = "Вася заработал 2001 рублей, Петя - 2022 рубля, а Маша - 2023 рублей";
        int totalSalary = 0;
        for(int i = 0; i < text.length(); i++) {
            if(Character.isDigit(text.charAt(i))) {
                int number = Integer.parseInt(text.substring(i, text.indexOf(" ", i)));
                totalSalary += number;
            }
        }
        System.out.println(totalSalary);
    }
}