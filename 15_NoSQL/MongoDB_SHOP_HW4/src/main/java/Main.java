import com.mongodb.client.*;

import com.mongodb.client.model.*;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.function.Consumer;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.sortByCount;
import static com.mongodb.client.model.Filters.gt;

public class Main {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("trade");

        MongoCollection<Document> productCollection = database.getCollection("products");
        MongoCollection<Document> shopCollection = database.getCollection("shops");

        while (true) {
            System.out.println("Введите команду");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            String[] commandElements = input.split(" ");

            switch (commandElements[0]) {
                case "ADD_SHOP":
                    shopCollection.insertOne(new Document().append("shopName", commandElements[1])
                            .append("products", Arrays.asList()));
                    break;
                case "ADD_PRODUCT":
                    productCollection.insertOne(new Document().append("productName", commandElements[1])
                            .append("price", Integer.parseInt(commandElements[2])));
                    break;
                case "PUT_PRODUCT":
                    shopCollection.findOneAndUpdate(Filters.eq("shopName", commandElements[2]),
                            Updates.addToSet("products", commandElements[1]))
                    break;

                case "PRODUCT_STATS":
                    shopCollection.aggregate((Arrays.asList(
                                    Aggregates.lookup("products", "products", "productName", "product_list"),
                                    Aggregates.unwind("$product_list"),
                                    Aggregates.sort(Sorts.descending("product_list.price")),
                                    Aggregates.group("$shopName",
                                            sum("productCount", 1),
                                            sum("productCountPriceLt100", BsonDocument.parse(
                                                    "{$cond: [ {$lt: [\"$product_list.price\", 100 ] }, 1, 0]}")),
                                            avg("avgPrice", "$product_list.price"),
                                            min("minPrice", "$product_list.price"),
                                            max("maxPrice", "$product_list.price")

                                    ))))
                            .forEach((Consumer<Document>) d -> {

                                System.out.printf("Магазин: %s%n", d.getString("_id"));
                                System.out.printf("Количество товаров в магазине: %s%n", d.get("productCount"));
                                System.out.printf("Количество товаров в магазине стоимостью ниже 100: %s%n", d.get("productCountPriceLt100"));
                                System.out.printf("Средняя цена товаров в магазине: %s%n", d.get("avgPrice"));
                                System.out.printf("Минимальная цена товара в магазине: %s%n", d.get("minPrice"));
                                System.out.printf("Максимальная цена товара в магазине: %s%n", d.get("maxPrice"));
                            });
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }
}


