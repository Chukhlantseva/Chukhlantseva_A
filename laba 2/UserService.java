import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    public static void printUsersSortedByLastName(List<User> users) {
        users.stream()
                .sorted(Comparator.comparing(User::getLastName))
                .forEach(System.out::println);
    }

    public static void printUsersSortedByAge(List<User> users) {
        users.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .forEach(System.out::println);
    }

    public static boolean areAllUsersAboveAge(List<User> users, int age) {
        return users.stream()
                .allMatch(user -> user.getAge() > age);
    }

    public static double calculateAverageAge(List<User> users) {
        return users.stream()
                .mapToInt(User::getAge)
                .average()
                .orElse(0.0);
    }

    public static long countUniqueCountries(List<User> users) {
        return users.stream()
                .map(User::getCountry)
                .distinct()
                .count();
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User(1, "Александра", "Чухланцева", 28, "Россия"),
                new User(2, "Наталья", "Дрофа", 35, "Россия"),
                new User(3, "Элина", "Гарибуллина", 18, "Казахстан"),
                new User(4, "Элина", "Набиулина", 22, "Латвия"),
                new User(5, "Анна", "Ефремова", 19, "Беларусь")
        );

        System.out.println("Sorted by last name:");
        printUsersSortedByLastName(users);

        System.out.println("\nSorted by age:");
        printUsersSortedByAge(users);

        System.out.println("\nAre all users older than 7? " + areAllUsersAboveAge(users, 7));

        System.out.println("\nAverage age: " + calculateAverageAge(users));

        System.out.println("\nNumber of unique countries: " + countUniqueCountries(users));
    }
}