import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> registeredUsers;

    private final static String KEY = "REGISTERED_USERS";

    private double getTs() {
        return new Date().getTime() / 1000;
    }


    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        registeredUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    public void registerUser(int user_id) {
        //ZADD ONLINE_USERS
        registeredUsers.add(getTs(), String.valueOf(user_id));
    }

    public RScoredSortedSet<String> getAllUsers() {
        return registeredUsers;
    }

    public String logRandomUser() {
        List<String> usersList = registeredUsers.stream().collect(Collectors.toList());
        Collections.shuffle(usersList);
        return usersList.get(0);
    }

    void deleteUser(String user) {

        registeredUsers.remove(user);
    }

    public int calculateUsersNumber() {
        //ZCOUNT ONLINE_USERS
        return registeredUsers.count(Double.NEGATIVE_INFINITY, true, Double.POSITIVE_INFINITY, true);
    }

    // Пример вывода всех ключей
    public void listKeys() {
        Iterable<String> keys = rKeys.getKeys();
        for (String key : keys) {
            out.println("KEY: " + key + ", type:" + rKeys.getType(key));
        }
    }

    public void shutdown() {
        redisson.shutdown();
    }
}


