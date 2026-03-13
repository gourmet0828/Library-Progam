package Strategy;

import Account.LocalAccount;
import Account.User;
import Constant.Constants;
import Date.*;
import ItemList.Item;
import ItemList.ItemListDao;
import ItemList.ItemListDaoImpl;
import LoanList.*;
import Processor.*;


import java.util.ArrayList;
import java.util.List;

public class UserStrategy extends Strategy{

    public void getUserInput(){
        String menuNumber ="";
        int menuResult = 0;
        Boolean userState = false;
        MenuInputProcessor mip = new MenuInputProcessor();

        while(true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[ Main Menu ] Current date: "+localDate.getDate().toStringPrint()+ "\n" +
                    "\n" +
                    "Please enter the menu number\n" +
                    "\n" +
                    "1. Browse a book\n" +
                    "2. Check loan history\n" +
                    "3. Check book history\n" +
                    "4. Log Out\n" +
                    "5. Terminate\n" +
                    "\n");
            System.out.print("Enter:");
            menuNumber = sc.nextLine();
            menuResult = mip.processInput(menuNumber, Constants.USER);
            if (menuResult == Constants.FAIL) {
                System.out.println("*It's not the correct format of menu selection. Only 1~5 number can be entered. Please check it again.*");
                continue;
            } else if (menuResult == Constants.SUCCESS) {
                switch (menuNumber.trim()) {
                    case "1":
                        userState = lld.userLoanValid();
                        if (userState) {
                            browse();
                            return;
                        } else {
                            return;
                        }
                    case "2":
                        checkLoanHistory();
                        return;
                    case "3":
                        checkBookHistory();
                        return;
                    case "4":
                        return;
                    case "5":
                        terminate();
                }
            } else {
                return;
            }
        }
    }
    private void browse(){
        User user = (User) LocalAccount.getInstance().getAccount();

        String bookSearchInput;
        int itemResult;
        List<Item> books;
        List<Item> target_books = new ArrayList<>();

        ItemInputProcessor iip = new ItemInputProcessor();
        ISBNInputProcessor isbnip = new ISBNInputProcessor();

        boolean isAllLoaned = true;

        while(true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[Browser] Current date:"+localDate.getDate().toStringPrint()+"\n" +
                    "\n" +
                    "Please enter the title of a book or the author of the book you want to borrow\n" +
                    "\n" +
                    "(Please enter !q if you want to log out)\n" +
                    "\n" );
            System.out.print("Enter:");
            bookSearchInput = sc.nextLine();

            itemResult = iip.processInput(bookSearchInput);
            if (itemResult == Constants.FAIL) {

                continue;
            } else if (itemResult == Constants.SUCCESS) {
                ild.verifyFileIntegrity(1);
                ArrayList<Integer> tmp = add.search_author(bookSearchInput);
                if(tmp == null) {
                    tmp.add(-1);
                    books = ild.getItemByTitleOrAuthor(bookSearchInput,tmp,1);
                } else {
                    books = ild.getItemByTitleOrAuthor(bookSearchInput,tmp,1);
                }
                if(books.size()==0){
                    System.out.println("*You cannot borrow this book as a book that does not exist in this library.*");
                    continue;
                }
                while(true) {
                    System.out.println("******************************************************************************************\n");
                    System.out.println("[Browser] Current date:" + localDate.getDate().toStringPrint() + "\n" +
                            "\n" +
                            "Please enter the ISBN of a book\n");
                    System.out.println("ISBN   |   title    |  author    ");
                    add.verifyFileIntegrity(1);
                    for(Item item : books){
                        List<String> authorCodeList = item.getAuthor();
                        List<String> authorNameList = new ArrayList<>();
                        for(String author : authorCodeList){
                            if(author.equals("0"))
                                break;
                            authorNameList.add(add.search_author(Integer.parseInt(author)));}
                        System.out.println(item.getIsbnId()+" |  "  + item.getTitle()+" |  "  + authorNameList +" |  " +"\n");
                    }
                    System.out.println("(Please enter !q if you want to log out)\n" +
                            "\n");
                    System.out.print("Enter:");
                    String ISBN = sc.nextLine();
                    int ISBNResult = isbnip.processInput(ISBN,books);
                    if(ISBNResult==Constants.FAIL){
                    } else if (ISBNResult == Constants.SUCCESS){
                        for(Item item : books){
                            if(item.getIsbnId().equals(ISBN)){
                                target_books.add(item);
                                break;
                            }
                        }
                        if(target_books.size()==0){
                            //에러메시지 출력
                            System.out.println("*Please enter the Id of a book in list*\n");
                            continue;
                        }
                        else {
                            lld.verifyFileIntegrity(1);

                            for(Item item: target_books)
                                if(!lld.itemIsLoan(item)){
                                    String returnDate = lld.add(item);
                                    isAllLoaned = false;
                                    System.out.println("*The book has been successfully checked out.*\n");

                                    ld.verifyFileIntegrity(1);
                                    ld.loanLog(item.getPhysicalId(),user.getPhoneNum(),returnDate);
                                    break;
                                }
                            if (isAllLoaned) {
                                System.err.println("Error: All items are already loaned out.");
                                continue;
                            }
                            return;
                        }
                    } else {
                        return;
                    }
                }
            } else if(itemResult==Constants.CLOSE){
                System.out.println("*Since you entered !q, you will be logged out.*");
                return;
            } else {
                System.out.println("SYSTEM ERROR"); //나타날 수 없는 오류(안전상으로 넣어둠)
                //에러메시지
                return;
            }
        }

    }
    private void checkLoanHistory(){
        List<Loan> loanList;
        String bookNumber;
        int menuResult;
        ReturnMenuInputProcessor rmip = new ReturnMenuInputProcessor();
        Loan loan;
        lld.verifyFileIntegrity(1);
        Date date = new Date(localDate.getDate().toString());
        loanList = lld.find();
        while(true){
            System.out.println("******************************************************************************************\n");
            System.out.println("[Loan History] Current date:" + localDate.getDate().toStringPrint() +
                "\n" +
                "Enter the number of book you want to return\n" +
                "\n");
            System.out.println("Number. Physical ID    |   ISBN    |   Title     |   Author     | LoanDate     | RemainDays To Return");
            add.verifyFileIntegrity(1);
            for(int i =0; i<loanList.size();i++){
            loan = loanList.get(i);

            List<String> authorCodeList = loan.getItem().getAuthor();
            List<String> authorNameList = new ArrayList<>();

            for(String author : authorCodeList){
                if(author.equals("0"))
                    break;
                authorNameList.add(add.search_author(Integer.parseInt(author)));}
            if(date.compareTo(loan.returnDate)>0){
                System.out.println(i+1+".    "+ loan.getItem().getPhysicalId()+"|      "+loan.getItem().getIsbnId()+"|      " + loan.getItem().getTitle() + "|    "+authorNameList+"|    " +loan.loanDate+"|     D-"+ date.compareTo(loan.returnDate)+"\n");
            } else if(date.compareTo(loan.returnDate)==0){
                System.out.println(i+1+".    " + loan.getItem().getPhysicalId()+"|      "+loan.getItem().getIsbnId()+"|      " + loan.getItem().getTitle() + "|    " +authorNameList+"|    " +loan.loanDate+"|     D-day Please return this book! \n");
            } else {
                System.out.println(i+1+".    " + loan.getItem().getPhysicalId()+"|      "+loan.getItem().getIsbnId()+"|      " + loan.getItem().getTitle() + "|    " +authorNameList+"|    " +loan.loanDate+"|     D+"+ Math.abs(date.compareTo(loan.returnDate))+" <overdue>  \n");
            }
        }
        System.out.println("\n" +
                "(Please enter !q if you want to log out)\n");
            System.out.print("Enter:");
        bookNumber = sc.nextLine();
        List<Loan> loans = lld.find();
        menuResult = rmip.processInput(bookNumber,loans.size());
        if(menuResult==Constants.CLOSE){
            System.out.println("*Since you entered !q, you will be logged out.*\n");
            return;
        }else if(menuResult==Constants.FAIL){
            //에러메시지
            continue;
        } else{
            loan = loanList.get(Integer.parseInt(bookNumber.trim())-1);
            lld.verifyFileIntegrity(1);
            lld.delete(loan);
            System.out.println("*Your book has been returned successfully.*\n");

            ld.verifyFileIntegrity(1);
            ld.returnLog(loan.getItem().getPhysicalId());
            if(date.compareTo(loan.returnDate)<0)
                ld.lateLog(loan.getItem().getPhysicalId(),loan.user.getPhoneNum(),loan.returnDate.toString());
            return;
        }
        }


    }

}
