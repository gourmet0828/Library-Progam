package LoanList;

public interface LoanDurationDao {
    public void setLoanDuration(int days);
    public int getLoanDuration();
    public void verifyFileIntegrity(int n);
}
