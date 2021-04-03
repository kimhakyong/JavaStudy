package modern.java.in.action.chapter8;

import modern.java.in.action.sample.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Map.entry;
import static modern.java.in.action.sample.Transaction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Chapter8 {
    public static void main(String[] args) {
        // of()
        useCollectionFactoryMethod();

        // removeIf()
        useCollectionRemoveIf();

        // replaceAll()
        useCollectionReplaceAll();

        // Map collection new method
        // forEach()
        useMapForEachMethod();

        // sorted()
        useMapSortedMethod();
        
        // getOrDefault(),
        // computeIfAbsent(), computeIfPresent(), compute()
        // remove()
        // replaceAll()
        useMapEtcMethod();

        // merge()
        useMapMergeMethod();
    }

    private static void useMapMergeMethod() {
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond")
        );
        Map<String, String> friends = Map.ofEntries(
                entry("Teo", "Star Wars2"),
                entry("Raphael", "Echo Movie")
        );

        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends);
        System.out.println(everyone);

        Map<String, String> everyone2 = new HashMap<>(family);
        friends.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println(everyone2);
    }

    private static void useMapEtcMethod() {
//        Map<String, String> favouriteMovies = Map.ofEntries(
//                entry("Raphael", "Star Wars"),
//                entry("Olivia", "Matrix"),
//                entry("Thibaut", "James Bond")
//        );

        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("Raphael", "Star Wars");
        favouriteMovies.put("Olivia", "Matrix");
        favouriteMovies.put("Thibaut", "James Bond");

        System.out.println(favouriteMovies.getOrDefault("Olivia", "Default"));
        System.out.println(favouriteMovies.getOrDefault("Kim", "Default"));

        // computeIfAbsent()
        favouriteMovies.computeIfAbsent("Kim", key -> key + " movies");
        System.out.println(favouriteMovies);

        // computeIfPresent()
        favouriteMovies.computeIfPresent("Olivia", (key, value) -> key + " movies " + value);
        System.out.println(favouriteMovies);

        // compute()
        favouriteMovies.compute("Echo", (key, value) -> key + " Echo " + value);
        System.out.println(favouriteMovies);

        // remove()
        favouriteMovies.remove("Kim", "Kim movies");
        System.out.println(favouriteMovies);

        // replaceAll()
        favouriteMovies.replaceAll((key, value) -> value.toUpperCase());
        System.out.println(favouriteMovies);
    }

    private static void useMapSortedMethod() {
        Map<String, String> favouriteMovies = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Olivia", "Matrix"),
                entry("Thibaut", "James Bond")
        );

        // Entry.comparingByValue
        // Entry.comparingByKey
        favouriteMovies.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
                .sorted(reverseOrder(Map.Entry.comparingByKey()))
                .forEachOrdered(System.out::println);
    }

    private static void useMapForEachMethod() {
        Map<String, Integer> ageOfFriends
                = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);

        for (Map.Entry<String, Integer> entry : ageOfFriends.entrySet()) {
            String friend = entry.getKey();
            Integer age = entry.getValue();
            System.out.println(friend + " is " + age);
        }

        // forEach()
        ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age));
    }

    private static void useCollectionReplaceAll() {
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");

        // 신규 List 생성
        referenceCodes.stream()
                .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println(referenceCodes);

        // ListIterator : 요소를 바꾸는 set() 메서드 지원
        for (ListIterator<String> iterator = referenceCodes.listIterator();
             iterator.hasNext(); ) {
            String code = iterator.next();
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }
        System.out.println(referenceCodes);

        // UnaryOperator : 단항 연산자
        // Input / Output Type 동일
        referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
    }

    private static void useCollectionRemoveIf() {
        transactions = new ArrayList<>();

        transactions.add(new Transaction(brian, 2011, 300));
        transactions.add(new Transaction(raoul, 2012, 1000));
        transactions.add(new Transaction(raoul, 2011, 400));
        transactions.add(new Transaction(mario, 2012, 710));
        transactions.add(new Transaction(mario, 2012, 700));
        transactions.add(new Transaction(alan, 2012, 950));

//        transactions.stream()
//                .filter(transaction -> transaction.getYear() == 2011)
//                .forEach(transactions::remove);

        // 최초 하나는 삭제 처리되고, Exception 발생
        assertThrows(ConcurrentModificationException.class, () -> {
            for (Transaction transaction : transactions) {
                if (transaction.getYear() == 2011) {
                    transactions.remove(transaction);
                }
            }
        });

        // 위 구문과 동일 코드
        // Iterator, Collection 두 개의 객체의 동기화 문제로 오류 발생
        assertThrows(ConcurrentModificationException.class, () -> {
            for (Iterator<Transaction> iterator = transactions.iterator();
                 iterator.hasNext(); ) {
                Transaction transaction = iterator.next();
                if (transaction.getYear() == 2011) {
                    transactions.remove(transaction);
                }
            }
        });

        assertEquals(4, transactions.size());
        for (Iterator<Transaction> iterator = transactions.iterator();
             iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            if (transaction.getYear() == 2011) {
                iterator.remove();
            }
        }
        assertEquals(4, transactions.size());

        // removeIf()
        transactions.removeIf(tran -> tran.getValue() == 700);
        assertEquals(3, transactions.size());

        Map<String, Integer> movies = new HashMap<>();
        movies.put("JamesBond", 20);
        movies.put("Matrix", 15);
        movies.put("Harry Potter", 5);

        movies.entrySet().removeIf(entry -> entry.getValue() < 10);
        System.out.println("echo : " + movies);
    }

    private static void useCollectionFactoryMethod() {
        List<String> friends1 = new ArrayList<>();
        friends1.add("Raphael");
        friends1.add("Olivia");
        friends1.add("Thibaut");

        List<String> friends2 = Arrays.asList("Raphael", "Olivia", "thibaut");
        friends2.set(0, "Kim");
        assertThrows(UnsupportedOperationException.class, () -> {
            friends2.add("Echo");
        });

        System.out.println(friends2.getClass() + " : " + friends2);

        Set<String> friends3 = new HashSet<>(Arrays.asList("Raphael", "Olivia", "thibaut"));
        friends3.add("Echo");
        assertEquals(4, friends3.size());

        Set<String> friends4 = Stream.of("Raphael", "Olivia", "thibaut")
                .collect(Collectors.toSet());

        List<String> friends5 = List.of("Raphael", "Olivia", "thibaut");
        System.out.println(friends5);
        assertThrows(UnsupportedOperationException.class, () -> {
            friends5.add("Echo");
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            friends5.set(0, "Kim");
        });
        System.out.println(friends5.getClass() + " : " + friends5);

        Set<String> friends6 = Set.of("Raphael", "Olivia", "thibaut");
        System.out.println(friends6);

        assertThrows(IllegalArgumentException.class, () -> {
            Set<String> friends7 = Set.of("Raphael", "Olivia", "Olivia", "thibaut");
        });

        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
        System.out.println(ageOfFriends);

        Map<String, Integer> ageOfFriends2 = Map.ofEntries(
                entry("Raphael", 30),
                entry("Olivia", 25),
                entry("Thibaut", 26)
        );
        System.out.println(ageOfFriends);
    }
}
