package Strategy;


import AuthorList.AuthorDataDao;
import AuthorList.AuthorDataDaoImpl;
import Constant.Constants;
import Date.LocalDate;
import ItemList.Item;
import ItemList.ItemListDao;
import ItemList.ItemListDaoImpl;
import LoanList.LoanDurationDao;
import LoanList.LoanDurationDaoImpl;
import LoanList.LoanListDao;
import LoanList.LoanListDaoImpl;
import Log.*;
import Processor.BookIdInputProcessor;
import Processor.ItemInputProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Strategy {
    LocalDate localDate = LocalDate.getInstance();
    LoanListDao lld = LoanListDaoImpl.getInstance();
    LoanDurationDao ldd = LoanDurationDaoImpl.getInstance();
    ItemListDao ild = new ItemListDaoImpl();
    AuthorDataDao add = new AuthorDataDaoImpl();
    LogDao ld = new LogDaoImpl();
    Scanner sc = new Scanner(System.in);
    public void terminate(){
        System.out.println("********************************************************************");
        System.out.println("[ The program is terminated ]");
        System.out.println("********************************************************************");
        System.exit(0);
    }

    public void checkBookHistory(){
        String bookSearchInput;
        int itemResult;
        List<Item> books;
        Item book = null;

        ItemInputProcessor iip = new ItemInputProcessor();
        BookIdInputProcessor biip = new BookIdInputProcessor();

        boolean isAllLoaned = true;

        while(true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[Book History] Current date:"+localDate.getDate().toStringPrint()+"\n" +
                    "\n" +
                    "Please enter the title of a book or the author of the book you want to check the history\n" +
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
                    books = ild.getItemByTitleOrAuthor(bookSearchInput,tmp,0);
                } else {
                    books = ild.getItemByTitleOrAuthor(bookSearchInput,tmp,0);
                }
                //books = ild.getItemByTitleOrAuthor(bookSearchInput);
                if(books.size()==0){
                    System.out.println("* The book You entered does not exist in this library.*");
                    continue;
                }
                while(true) {
                    System.out.println("******************************************************************************************\n");
                    System.out.println("[Book History] Current date:" + localDate.getDate().toStringPrint() + "\n" +
                            "\n" +
                            "Please enter the Physical ID of a book\n");
                    System.out.println("   Pysical ID    |    ISBN   |   title    |  author    ");
                    for(Item item : books){
                        List<String> authorCodeList = item.getAuthor();
                        List<String> authorNameList = new ArrayList<>();
                        for(String author : authorCodeList){
                            if(author.equals("0"))
                                break;
                            authorNameList.add(add.search_author(Integer.parseInt(author)));}
                        System.out.println(item.getPhysicalId()+"    |    "+item.getIsbnId()+" |  "  + item.getTitle()+" |  "  + authorNameList +" |  " +"\n");
                    }
                    System.out.println("(Please enter !q if you want to log out)\n" +
                            "\n");
                    System.out.print("Enter:");
                    String bookId = sc.nextLine();
                    int bookIdResult = biip.processInput(bookId,books);
                    if(bookIdResult==Constants.FAIL){
                        continue;
                    } else if (bookIdResult == Constants.SUCCESS){
                        for(Item item : books){
                            if(item.getPhysicalId().equals(bookId))
                                book = item;
                        }
                        if(book ==null){
                            //에러메시지 출력
                            System.out.println("*Please enter the PhysicalId of a book in list*\n");
                            continue;
                        }
                        else {
                            lld.verifyFileIntegrity(1);
                            ld.verifyFileIntegrity(1);
                            List<Log> logList = ld.getLogs(book.getPhysicalId());

                            for(Log log : logList){
                                if(log.getAction()=="Loan")
                                    System.out.println(log.getAction() + "   |" + log.getDate()+"  | "+ log.getReturnDate() + "|" + log.getBorrowerId()  + "\n");
                                else
                                    System.out.println(log.getAction() + "   |" + log.getDate() +"\n");
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
}
