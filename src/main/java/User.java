import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private Long id;
    private AtomicInteger balance;

    public User(Long id, Integer balance) {
        this.id = id;
        this.balance = new AtomicInteger(balance);
    }

    public Long getId() {
        return this.id;
    }
    public Integer getBalance() {
        return this.balance.get();
    }

    public void onBalance(Integer balance) {
        Integer now = this.balance.get();
        this.balance.compareAndSet(now, now + balance);
    }
    public void offBalance(Integer balance) {
        Integer now = this.balance.get();
        this.balance.compareAndSet(now, now - balance);
    }
}
