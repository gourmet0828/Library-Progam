package Account;

public class LocalAccount implements handleAccount{

    private Account account;
    private static LocalAccount instance;

    private LocalAccount() {

    }

    public static LocalAccount getInstance() {
        if (instance == null) {
            instance = new LocalAccount();
        }
        return instance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(int checkAccount, String input) {
        if (checkAccount == 1) {
            account = new User(input);
        }
        else if (checkAccount == 0){
            account = new Admin(input);
        }
    }

    @Override
    public void Reset() {
        this.account = null;
        instance = null;
        System.out.println("***************************************************");
        System.out.println("Log Out");

    }
}
