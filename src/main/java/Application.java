import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Application {

    private static List<Long> listOfId= new ArrayList<>();
    private static List<Integer> listOfBalance;
    private static ExecutorService service;
    private static TransactionalMap transactionalMap = new TransactionalMap();

    public static void main(String[] args) {

        Random random = new Random(1);

        listOfBalance = random.ints(10, 10, 100).boxed().collect(Collectors.toList());

        for (int i = 0; i < 10; i++) {
            listOfId.add((long) i);
            User user = new User((long) i, listOfBalance.get(i));
            transactionalMap.addUser(user);
        }

        service = Executors.newCachedThreadPool();

        List<Callable> transactions = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            for (int j = 5; j < 10; j++) {
                int finalI = i;
                int finalJ = j;
                Callable callable = () -> {
                    transactionalMap.transaction(listOfId.get(finalI), listOfId.get(finalJ),  finalI * finalJ);
                    return transactionalMap;
                };
                transactions.add(callable);
            }
        }

        for (Callable transaction : transactions) {
            service.submit(transaction);
        }
    }
}
