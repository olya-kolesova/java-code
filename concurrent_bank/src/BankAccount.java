import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private long balance;
    private final Lock lock;

    public BankAccount() {
        this.balance = 0;
        this.lock = new ReentrantLock();

    }

    public void deposit(long amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Balance after deposit: " + balance);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(long amount) {
        lock.lock();
        try {
            if (amount <= balance) {
                balance -= amount;
                System.out.println("Balance after withdrawal: " + balance);
            } else {
                System.out.println("Insufficient balance");
            }
        } finally {
            lock.unlock();
        }
    }

    Lock getLock() {
        return lock;
    }

    long getBalance() {
        return balance;
    }

    void setBalance(long balance) {
        this.balance = balance;
    }

}
