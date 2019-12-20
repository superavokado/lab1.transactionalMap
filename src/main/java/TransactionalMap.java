import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionalMap {
    private HashMap<Long, User> Users = new HashMap();
    private static ReentrantLock locker = new ReentrantLock();

    public void addUser(User user) {
        Users.put(user.getId(), user);
    }

    public User GetUser(Long id) {
        return Users.get(id);
    }

    //ВАЖНО!!! Функция транзакции
    public void transaction(Long id1, Long id2, Integer money) {
        locker.lock();
        try {
        checkBalance(id1, id2);

        System.out.println("$" + money + " transaction from " + id1 + " to " + id2);

        User user1 = GetUser(id1);
        User user2 = GetUser(id2);

        if (user1.getBalance() < money) {
            System.out.println(id1 + "user has only " + user1.getBalance() + "! Error!");
            return;
        }

        user1.offBalance(money);
        user2.onBalance(money);

        checkBalance(id1, id2);
        System.out.println("Transaction complete!");
        } catch (Exception e) {

        } finally {
            locker.unlock();
        }
    }

    public void checkBalance (Long id1, Long id2) {
        System.out.println(id1 + " user has balance " + GetUser(id1).getBalance() + " and " + id2 + " user has balance " + GetUser(id2).getBalance());
    }
}