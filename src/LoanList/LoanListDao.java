package LoanList;

import java.util.List;
import ItemList.*;

public interface LoanListDao {
    String add(Item item);
    void delete(Loan loan);
    List<Loan> find();
    boolean userLoanValid();
    void verifyFileIntegrity(int mode);
    public boolean itemIsLoan(Item item);
}
