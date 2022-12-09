import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static TodoList todoList = new TodoList();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String todo = scanner.nextLine();
            if (todo.equals("0")) {
                break;
            }
            // Вывод в консоль списка дел LIST
            if (todo.equals("LIST")) {
                int length = todoList.getTodos().size();
                for (int i = 0; i < length; i++) {
                    System.out.println(i + " - " + todoList.getTodos().get(i));
                }
            } else {
                // для ADD EDIT DELETE
                String[] words = todo.split("\\s", 3);

                // название команды
                String operationName = words[0];

                // название дела
                String todoName = getTodoName(todo);

                // Индекс
                int index = -1;
                if (words[1].matches("\\d+")) {
                    index = Integer.parseInt(words[1]);
                }
                // Добавление дела в Список
                if (operationName.equals("ADD")) {
                    if (index >= 0 && index < todoList.getTodos().size()) {
                        todoList.add(index, todoName);
                    } else {
                        todoList.add(todoName);
                    }
                    System.out.println("Добавлено дело " + "\"" + todoName + "\"");
                }

                // Замена дела с указанным номером
                if (operationName.equals("EDIT")) {
                    if (index < todoList.getTodos().size()) {
                        String previousTodoName = todoList.getTodos().get(index);
                        todoList.edit(todoName, index);
                        System.out.println("Дело " + "\"" + previousTodoName + "\"" + " заменено на " + "\"" + todoName + "\"");
                    } else {
                        System.out.println("Дело с таким номером не существует");
                    }
                }
                // Удалить дело
                if (operationName.equals("DELETE")) {
                    if (index < todoList.getTodos().size()) {
                        String previousTodoName = todoList.getTodos().get(index);
                        todoList.delete(index);
                        System.out.println("Дело " + "\"" + previousTodoName + "\"" + " удалено");
                    } else {
                        System.out.println("Дело с таким номером не существует");
                    }
                }
            }
        }
    }

    // Вычленяем название дела
    public static String getTodoName(String todo) {
        String todoName = "";
        String regex = "[^A-Z0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(todo);
        while (matcher.find()) {
            todoName = todo.substring(matcher.start(), matcher.end()).trim();
        }
        return todoName;
    }

}


// написать консольное приложение для работы со списком дел todoList




