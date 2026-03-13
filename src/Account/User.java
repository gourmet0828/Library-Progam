package Account;

public class User extends Account {

    private String phoneNum;

    public User(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public User getUser() {
        return this;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}
