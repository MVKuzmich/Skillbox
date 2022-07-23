import java.sql.*;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "jdbc:mysql://localhost:3306/learn?serverTimezone=Europe/Moscow&useSSL=false";
    private static String dbUser = "root";
    private static String dbPass = "Olga-Shorokh92";

    private static StringBuilder insertQuery = new StringBuilder();

    public static final int MAX_COUNT = 1_000;

    private static int voterCount = 0;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dbName, dbUser, dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_birthDate(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void countVoter(String name, String birthDay) throws SQLException {

        birthDay = birthDay.replace('.', '-');

        insertQuery.append((insertQuery.length() == 0 ? "" : ", ") + "('" + name + "', '" + birthDay + "', 1)");
        ++voterCount;

        if (voterCount > MAX_COUNT) {
            executeMultiInsert();

            insertQuery = new StringBuilder();

            voterCount = 0;
        }
    }

    public static void executeMultiInsert() throws SQLException {

        String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
                "VALUES" + insertQuery.toString() +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";

        DBConnection.getConnection().createStatement().execute(sql);
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
