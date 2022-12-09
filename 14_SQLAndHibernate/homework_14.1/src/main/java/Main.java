import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String password = Password.getPassword();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select course_name as name,count(course_name)*30/" +
                    " (select datediff(max(subscription_date), min(subscription_date))" +
                    " from purchaselist)" +
                    " as average_quantity from purchaselist" +
                    " group by course_name;");
            while (resultSet.next()) {
                String courseName = resultSet.getString("name");
                float averageSubscriptionQuantity = resultSet.getFloat("average_quantity");
                System.out.print(courseName);
                System.out.println(" " + averageSubscriptionQuantity);
            }
            statement.close();
            resultSet.close();
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
