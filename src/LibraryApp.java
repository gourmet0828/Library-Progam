import Account.LocalAccount;
import AuthorList.AuthorDataDao;
import AuthorList.AuthorDataDaoImpl;
import Constant.Constants;
import Date.*;
import ItemList.ItemListDaoImpl;
import LoanList.LoanDurationDao;
import LoanList.LoanDurationDaoImpl;
import LoanList.LoanListDao;
import LoanList.LoanListDaoImpl;
import Log.LogDao;
import Log.LogDaoImpl;
import Processor.AccountInputProcessor;
import Processor.DateInputProcessor;
import Strategy.AdminStrategy;
import Strategy.UserStrategy;
import ItemList.ItemListDao;

import java.util.Scanner;

public abstract class LibraryApp {

    public static void start () {
        Scanner scanner = new Scanner(System.in);

        //파일 유효성 검사 - 날짜 파일
        DateDao dd = DateDaoImpl.getInstance();
        dd.verifyFileIntegrity(0);

        //파일 유효성 검사 - 도서 목록 파일
        ItemListDao  ild = new ItemListDaoImpl();
        ild.verifyFileIntegrity(0);

        //파일 유효성 검사 - 대출 내역 파일
        LoanListDao lld = LoanListDaoImpl.getInstance();
        lld.verifyFileIntegrity(0);

        //파일 유효성 검사 - 반납기한 파일
        LoanDurationDao ldd = LoanDurationDaoImpl.getInstance();
        ldd.verifyFileIntegrity(0);

        LogDao ld = new LogDaoImpl();
        ld.verifyFileIntegrity(0);

        AuthorDataDao add = new AuthorDataDaoImpl();
        add.verifyFileIntegrity(0);

        LocalAccount account;
        UserStrategy userMode = null;
        AdminStrategy adminMode = null;

        // 로그인 프롬프트
        while(true) {
            System.out.println("***************************************************************************");
            System.out.println("[Login] Please enter the phone number");
            System.out.print("Enter: ");
            String accountInput = scanner.nextLine();

            AccountInputProcessor accountProc = new AccountInputProcessor();
            int checkAccount = accountProc.processInput(accountInput);

            if (checkAccount == Constants.FAIL) {

                continue;
            }
            else {
                account = LocalAccount.getInstance();
                account.setAccount(checkAccount, accountInput); //account 인스턴스 생성

                switch (checkAccount) { //user admin 나눠서 메뉴 인스턴스 생성 -> 실행은 아래에서
                    case Constants.USER:
                        userMode = new UserStrategy();
                    case Constants.ADMIN:
                        adminMode = new AdminStrategy();
                }
                break;
            }



        }

        //날짜 입력 프롬프트
        LocalDate localdate = LocalDate.getInstance();
        while(true) {
            System.out.println("***************************************************************************");
            System.out.println("[Date: " + localdate.getDate().toStringPrint() +" ] Please enter the current date");
            System.out.print("Enter: ");
            String dateInput = scanner.nextLine();

            DateInputProcessor dateProc = new DateInputProcessor();
            int checkDate = dateProc.processInput(dateInput);

            if (checkDate == Constants.FAIL ) {
                System.out.println("*It's not the correct format of date. Please check it again.*");
                continue;
            }
            else {
                DateDaoImpl ddl = (DateDaoImpl) dd;
                Date date = new Date(dateInput);
                boolean checkFuture = ddl.verifyDate(date);

                if (!checkFuture) {
                    System.out.println("*It's not the correct format of date. Please check it again.*");
                    continue;
                }
                else {
                    localdate.setDate(date);
                    ddl.verifyFileIntegrity(1);
                    ddl.update();
                    break;
                }
            }

        }

        //user or admin strategy 메뉴
        while(true) {
            if(userMode != null) {
                userMode.getUserInput();
            }
            else {
                adminMode.getAdminInput();
            }
            account.Reset(); // 각 strategy 에서 return 되면 로그아웃
            break;
        }
        start();

    }
}
