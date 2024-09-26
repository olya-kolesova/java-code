import java.util.concurrent.TimeUnit;

public class ConcurrentBank {
    public static void main(String[] args) {

        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();


        Thread thread1 = new Thread(() -> bankAccount1.deposit(1000));
        Thread thread2 = new Thread(() -> bankAccount2.deposit(500));
        Thread thread3 = new Thread(() -> transfer(bankAccount1, bankAccount2, 400));
        thread1.start();
        thread2.start();
        thread3.start();

    }

    public BankAccount createAccount() {
        return new BankAccount();
    }


    public static void transfer(BankAccount accountFrom, BankAccount accountTo, long amount) {
        try {
            if (accountFrom.getLock().tryLock(3, TimeUnit.SECONDS) && accountTo.getLock().tryLock(3, TimeUnit.SECONDS)) {
                long balanceFrom = accountFrom.getBalance();
                long balanceTo = accountTo.getBalance();
                try {
                    if (amount <= accountFrom.getBalance()) {
                        accountFrom.withdraw(amount);
                        accountTo.deposit(amount);
                    } else {
                        System.out.println("Not enough balance");
                    }
                } catch (Exception e) {
                    accountFrom.setBalance(balanceFrom);
                    accountTo.setBalance(balanceTo);
                } finally {
                    accountFrom.getLock().unlock();
                    accountTo.getLock().unlock();
                }
            } else {
                System.out.println("Couldn't access accounts");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}