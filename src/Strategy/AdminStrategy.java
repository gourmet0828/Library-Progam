package Strategy;

import ItemList.Book;
import ItemList.Item;

import Processor.*;
import Constant.Constants;


import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.System.out;

import java.util.HashSet;
import java.util.Set;

public class AdminStrategy extends Strategy {
    public void setLoanDuration() {
        String inputedLoanDuration;
        int deadlineResult;
        LoanDeadlineInputProcessor ldip = new LoanDeadlineInputProcessor();
        int currentLoanDuration = ldd.getLoanDuration();

        while (true) {
            System.out.println("************************************************************************");
            System.out.println("[ Set LoanDuration ] Current date: " + localDate.getDate().toStringPrint() +
                    "\nPlease enter the loan duration \n" +
                    "(If you want to log out, enter q!)");
            System.out.print("Enter: ");
            inputedLoanDuration = sc.nextLine();

            deadlineResult = ldip.processInput(inputedLoanDuration, currentLoanDuration);
            if (deadlineResult == Constants.CLOSE) {
                return;
            } else if (deadlineResult == Constants.SUCCESS) {
                lld.verifyFileIntegrity(1);
                ldd.verifyFileIntegrity(1);
                ldd.setLoanDuration(Integer.parseInt(inputedLoanDuration));
                return;
            }
        }
    }

    public void getAdminInput() {
        String menuNumber;
        int menuResult;
        MenuInputProcessor mip = new MenuInputProcessor();

        while (true) {
            System.out.println("************************************************************************");
            System.out.println("[ Main Menu ] Current date: " + localDate.getDate().toStringPrint() +
                    "\nPlease enter the menu number \n" +
                    "    1. Add a book\n" +
                    "    2. Delete / Discover a book\n" +
                    "    3. Check book history\n" +
                    "    4. Set Loan Duration\n" +
                    "    5. Log Out\n" +
                    "    6. Terminate\n");
            System.out.print("Enter: ");
            menuNumber = sc.nextLine();
            menuResult = mip.processInput(menuNumber, Constants.ADMIN);

            if (menuResult == Constants.FAIL) {
                System.out.println("*It's not the correct format of menu selection. " +
                        "Only 1~6 number can be entered. Please check it again.*");
            } else if (menuResult == Constants.SUCCESS) {
                if (menuNumber.trim().equals("1")) {
                    add();
                    return;
                } else if (menuNumber.trim().equals("2")) {
                    deleteOrDiscover();
                    return;
                } else if (menuNumber.trim().equals("3")) {
                    checkBookHistory();
                    return;
                } else if (menuNumber.trim().equals("4")) {
                    setLoanDuration();
                    return;
                } else if (menuNumber.trim().equals("5")) {
                    return;
                } else {
                    terminate();
                }
            }

        }
    }


    private void add() {
        String bookTitle;
        String bookAuthor;
        String bookNum;
        String bookAuthorCode;

        int titleResult;
        int nameResult;
        int numResult;
        int authorCodeResult_1;
        int authorCodeResult_2;

        int registeredBookNum;
        int numAuthor = 1;
        boolean exitAllLoops = false;
        List<Integer> registeredAuthorCode = new ArrayList<>();
        List<String> registeredAuthorName = new ArrayList<>();

        ItemInputProcessor iip = new ItemInputProcessor();
        ItemNumInputProcessor inip = new ItemNumInputProcessor();
        AddOperationInputProcessor aoip = new AddOperationInputProcessor();
        BookIdInputProcessor biip = new BookIdInputProcessor();
        AuthorCodeInputProcessor acip = new AuthorCodeInputProcessor();
        AuthorNameInputProcessor anip = new AuthorNameInputProcessor();
        ISBNInputProcessor isbnip = new ISBNInputProcessor();

        List<Item> srcBookList;
        String isItem;

        while (true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[ Add a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
            System.out.println("Please enter [the title] of the book you want to add\n\n");
            System.out.println("(Please enter !q if you want to log out)\n");
            System.out.print("Enter: ");
            bookTitle = sc.nextLine();
            titleResult = iip.processInput(bookTitle);

            if (titleResult == Constants.FAIL) {
                System.out.println("*Invalid Input. Entering only spaces is not allowed to be entered. Please re-enter it*");
            } else if (titleResult == Constants.CLOSE) {
                return;
            } else break;
        }
        while (true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[ Add a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
            System.out.println("Please enter [the author name] of book you want to add" + "(" + numAuthor + "/5)\n\n");
            System.out.println("(Please enter !q if you want to log out)\n");
            System.out.print("Enter: ");
            bookAuthor = sc.nextLine();
            nameResult = anip.processInput(bookAuthor);

            if (nameResult == Constants.FAIL) {
                System.out.println("*Invalid Input. Entering only spaces is not allowed to be entered. Please re-enter it*");

            } else if (nameResult == Constants.CLOSE) {
                System.out.println("*Since you entered !q, you will be logged out.*\n");
                return;
            } else if (nameResult == Constants.NEXT) {
                if (numAuthor == 1) {
                    registeredAuthorCode.add(0);
                }

                break;
            } else {
                ArrayList<Integer> existedAuthorCode;
                add.verifyFileIntegrity(1);
                existedAuthorCode = add.search_author(bookAuthor);
                if (existedAuthorCode.size() != 0) {
                    while (true) {
                        System.out.println("******************************************************************************************\n");
                        System.out.println("[ Add a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
                        System.out.println("Please enter [the author Code] of book you want to add\n But if the author code is not in list, and you want to add it as a new author, please enter \"new\" \n");
                        for (Integer code : existedAuthorCode) {
                            System.out.println(code);
                        }
                        System.out.println("(Please enter !q if you want to log out)\n");
                        System.out.print("Enter: ");
                        bookAuthorCode = sc.nextLine();
                        authorCodeResult_1 = acip.processInput(bookAuthorCode, existedAuthorCode);
                        authorCodeResult_2 = aoip.processInput(bookAuthorCode);
                        if (authorCodeResult_1 == Constants.CLOSE) {
                            System.out.println("*Since you entered !q, you will be logged out.*\n");
                            return;
                        } else if (authorCodeResult_1 == Constants.FAIL && authorCodeResult_2 == Constants.FAIL) {
                            System.out.println("*Invalid Input. Please re-enter it*\n");
                            //continue;
                        } else if (authorCodeResult_1 == Constants.SUCCESS || authorCodeResult_2 == Constants.SUCCESS) {
                            if (authorCodeResult_2 == Constants.SUCCESS) {
                                add.verifyFileIntegrity(1);
                                int addResult = add.addAuthor(bookAuthor);
                                if (addResult != Constants.FAIL) {
                                    registeredAuthorCode.add(addResult);
                                    registeredAuthorName.add(bookAuthor);
                                } else {
                                    exit(0);
                                }
                            } else {
                                registeredAuthorName.add(bookAuthor);
                                registeredAuthorCode.add(Integer.parseInt(bookAuthorCode));
                            }
                            System.out.println("* New Author is registered successfully *\n");
                            if (numAuthor <= 4) {
                                numAuthor++;
                                break;
                            } else {
                                exitAllLoops = true;
                                break;
                            }
                        }

                    }
                } else {
                    add.verifyFileIntegrity(1);
                    int addResult = add.addAuthor(bookAuthor);
                    if (addResult != Constants.FAIL) {
                        registeredAuthorCode.add(addResult);
                        registeredAuthorName.add(bookAuthor);
                    } else {
                        exit(0);
                    }
                    System.out.println("* Author added successfully *\n");
                    if (numAuthor <= 4) {
                        numAuthor++;
                        continue;
                    } else {
                        System.out.println("* Max 5 authors can be registered in a book. *\n");
                        break;
                    }
                }
                if (exitAllLoops) {
                    System.out.println("* Max 5 authors can be registered in a book *\n");
                    break;
                }
            }
        }
        System.out.println("이름" + registeredAuthorName);
        while (true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[ Add a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
            System.out.println("Please enter [the numbers] of book you want to add\n\n");
            System.out.println("(Please enter !q if you want to log out)\n");
            System.out.print("Enter: ");
            bookNum = sc.nextLine();
            registeredBookNum = ild.getResigteredBookNum();
            if (registeredBookNum >= 10000) {
                System.out.println("*There are 10,000 books, so You can’t add anymore books. Please enter q! and log out.*\n");
                continue;
            }
            if (bookNum.isBlank() && bookNum.isEmpty()) {
                bookNum = "1";
                numResult = Constants.SUCCESS;
            } else {
                numResult = inip.processInput(bookNum);
            }

            if (numResult == Constants.FAIL) {

            } else if (numResult == Constants.CLOSE) {
                System.out.println("*Since you entered !q, you will be logged out.*\n");
                return;
            } else {
                if (Integer.parseInt(bookNum) + registeredBookNum > 10000 ) {
                    if( Integer.parseInt(bookNum) <= 10000){
                        System.out.println("* It's over 10000. You can add less than " + (10001 - registeredBookNum) + " books *\n");

                    } else {
                        System.out.println("* It's over 10000. You can enter less than 10001 *\n");

                    }
                    //continue;
                }  else break;
            }
        }

        ild.verifyFileIntegrity(1);
        List<String> strAuthorCode = new ArrayList<>();

        // Integer 값을 String으로 변환하여 추가
        for (Integer num : registeredAuthorCode) {
            strAuthorCode.add(String.valueOf(num));
        }
        srcBookList = ild.getItemByTitleAndAuthor(bookTitle, strAuthorCode);
        Set<String> printedIsbnIds = new HashSet<>();
        if (!srcBookList.isEmpty()) {


            while (true) {
                System.out.println("******************************************************************************************\n");
                System.out.println("[ Add a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
                System.out.println("ISBN  |   title    |  author ");
                for (Item item : srcBookList) {

                    if (!printedIsbnIds.contains(item.getIsbnId())) {
                        printedIsbnIds.add(item.getIsbnId());
                        if (registeredAuthorName.size() != 0) {
                            System.out.println(item.getIsbnId() + " |  " + item.getTitle() + " |  " + registeredAuthorName + "\n");
                        } else {
                            System.out.println(item.getIsbnId() + " |  " + item.getTitle() + " |      \n");
                        }
                    }
                }
                System.out.println("\nIs it an existing or new book?\n" +
                        "If it is an existing book, please enter the unique number of the book\n" +
                        "If it's a new book, type new\n\n");
                System.out.println("(Please enter !q if you want to log out)\n");
                System.out.print("Enter: ");
                isItem = sc.nextLine();
                if (aoip.processInput(isItem) == Constants.FAIL && isbnip.processInput(isItem, srcBookList) == Constants.FAIL) {
                    System.out.println("*Invalid Input. Please re-enter it*\n");
                    //continue;
                } else if (aoip.processInput(isItem) == Constants.FAIL && isbnip.processInput(isItem, srcBookList) == Constants.CLOSE) {
                    System.out.println("*Since you entered !q, you will be logged out.*\n");
                    return;
                } else if (aoip.processInput(isItem) == Constants.FAIL && isbnip.processInput(isItem, srcBookList) == Constants.SUCCESS) {
                    Item existedBook = new Book(isItem, bookTitle, strAuthorCode);
                    List<String> newPhysicalIDs = ild.addBook(existedBook, Integer.parseInt(bookNum), 0);

                    System.out.println("The book was added successfully*\n");

                    ld.verifyFileIntegrity(1);
                    for (String id : newPhysicalIDs) {
                        ld.addLog(id);
                    }
                    return;
                } else if (aoip.processInput(isItem) == Constants.SUCCESS) {
                    Item newBook = new Book(bookTitle, strAuthorCode);
                    List<String> newPhysicalIDs = ild.addBook(newBook, Integer.parseInt(bookNum), 1);

                    System.out.println("The book was added successfully*\n");

                    ld.verifyFileIntegrity(1);
                    for (String id : newPhysicalIDs) {
                        ld.addLog(id);
                    }
                    return;
                }
            }


        } else {
            Item newBook = new Book(bookTitle, strAuthorCode);
            ild.verifyFileIntegrity(1);
            List<String> newPhysicalIDs = ild.addBook(newBook, Integer.parseInt(bookNum), 1);

            System.out.println("The book was added successfully*\n");

            ld.verifyFileIntegrity(1);
            for (String id : newPhysicalIDs) {
                ld.addLog(id);
            }

        }
    }

    private void deleteOrDiscover() {
        String bookTitle;
        String uniqueNum;
        int titleResult;

        Item book;
        ItemInputProcessor iip = new ItemInputProcessor();
        BookIdInputProcessor biip = new BookIdInputProcessor();
        List<Item> srcBookList;

        while (true) {
            System.out.println("******************************************************************************************\n");
            System.out.println("[ Delete or Discover a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
            System.out.println("Please enter the title of the book or the author of the book you want to delete or discover\n\n");
            System.out.println("(Please enter !q if you want to log out)\n");
            System.out.print("Enter: ");
            bookTitle = sc.nextLine();
            titleResult = iip.processInput(bookTitle);

            if (titleResult == Constants.FAIL) {
                System.out.println("*This is not a rule-based input. Please check it again*");
                //continue;
            } else if (titleResult == Constants.CLOSE) {
                System.out.println("*Since you entered !q, you will be logged out.*");
                return;
            } else {
                ild.verifyFileIntegrity(1);
                ArrayList<Integer> tmp = add.search_author(bookTitle);
                if (tmp == null) {
                    tmp.add(-1);
                    srcBookList = ild.getItemByTitleOrAuthor(bookTitle, tmp, 0);
                } else {
                    srcBookList = ild.getItemByTitleOrAuthor(bookTitle, tmp, 0);
                }
                //srcBookList = ild.getItemByTitleOrAuthor(bookTitle);
                if (!srcBookList.isEmpty()) {
                    System.out.println("******************************************************************************************\n");
                    System.out.println("[ Delete or Discover a Book ] Current date:" + localDate.getDate().toStringPrint() + "\n");
                    System.out.println("Physical ID | ISBN  |   title    |  author    |   numbers | isRegistered");
                    for (Item item : srcBookList) {
                        List<String> authorCodeList = item.getAuthor();
                        List<String> authorNameList = new ArrayList<>();
                        for(String author : authorCodeList){
                            if(author.equals("0"))
                                break;
                            authorNameList.add(add.search_author(Integer.parseInt(author)));}
                        System.out.println(item.getPhysicalId() + "    |    " + item.getIsbnId() + "   |   " + item.getTitle() + " |  " + authorNameList + " |  " + item.getIsRegistered() + "\n");
                    }
                    while (true) {
                        System.out.println("Please enter the physicL ID of the book you want to delete or discover from the list\n(Please enter !q if you want to log out)\n ");
                        System.out.print("Enter: ");
                        uniqueNum = sc.nextLine();


                        if (biip.processInput(uniqueNum, srcBookList) == Constants.FAIL) {
                            //System.out.println("*This is not a rule-based input. Please check it again*");

                        } else if (biip.processInput(uniqueNum, srcBookList) == Constants.CLOSE) {
                            System.out.println("*Since you entered !q, you will be logged out.*");
                            return;
                        } else {
                            ild.verifyFileIntegrity(1);
                            lld.verifyFileIntegrity(1);
                            System.out.println(uniqueNum);
                            book = ild.getItemByNumber(uniqueNum);

                            if (book.getIsRegistered().equals("O")) {

                                if (!lld.itemIsLoan(book)) {
                                    ild.setBook(book, "X");
                                    ld.verifyFileIntegrity(1);
                                    ld.deleteLog(uniqueNum);
                                    System.out.println("*The book has successfully deleted*");
                                    return;
                                } else {
                                    System.out.println("*The book has been borrowed. You can’t delete*");
                                    //continue;
                                }
                            } else {
                                ild.setBook(book, "O");
                                ld.verifyFileIntegrity(1);
                                ld.findLog(uniqueNum);
                                System.out.println("* The book has been successfully re-registered *");
                                return;
                            }
                        }
                    }

                } else {
                    System.out.println("*The book doesn’t exist. Please check it again*");
                    //continue;
                }

            }
        }

    }
}
