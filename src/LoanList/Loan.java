package LoanList;

import Account.*;
import ItemList.*;
import Date.*;

public class Loan {
    public User user;
    public Item item;
    public Date loanDate;
    public Date returnDate;

    public Loan(User user, Item item, Date loanDate, Date returnDate) {
        this.user = user;
        this.item = item;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Item getItem() {
    	return item;
    }

    public String getLoanDate() {
        return loanDate.toString();
    }

    @Override
    public String toString() {
        return item.getPhysicalId();
    }

}
