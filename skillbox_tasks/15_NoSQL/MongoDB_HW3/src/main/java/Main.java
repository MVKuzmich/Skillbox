import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.BsonDocument;
import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("students");
        MongoCollection<Document> studentsCollection = database.getCollection("studentsCollection");
        studentsCollection.drop();
        try {
            List<String> studentsList = Files.readAllLines(Paths.get("src/main/resources/mongo.csv"));
            for (String studentInfo : studentsList) {
                String[] personalDataAndCourses = studentInfo.split("\"");
                String[] personalNameAndAge = personalDataAndCourses[0].split(",");
                Document document = new Document()
                        .append("FullName", personalNameAndAge[0])
                        .append("Age", personalNameAndAge[1])
                        .append("Courses", personalDataAndCourses[1]);

                studentsCollection.insertOne(document);

            }
            //Количество студентов в базе
            System.out.println("Количество студентов в базе "
                    + studentsCollection.countDocuments());

            //Количество студентов старше 40 лет
            System.out.println("Количество студентов старше 40 лет "
                    + studentsCollection.countDocuments(BsonDocument.parse("{Age: {$gt: '40'}}")));

            //Самый молодой студент
            MongoCursor<Document> cursor1 = studentsCollection.find()
                    .sort(BsonDocument.parse("{Age: 1}"))
                    .limit(1)
                    .iterator();
            try {
                while (cursor1.hasNext()) {
                    System.out.println("Самый молодой студент: " + cursor1.next());
                }
            } finally {
                cursor1.close();
            }
            //Самый старый студент
            MongoCursor<Document> cursor = studentsCollection.find()
                    .sort(BsonDocument.parse("{Age: -1}"))
                    .limit(1)
                    .iterator();

            try {
                while (cursor.hasNext()) {
                    System.out.println("Самый старый студент: " + cursor.next());
                }
            } finally {
                cursor.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
