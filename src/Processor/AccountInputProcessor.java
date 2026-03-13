package Processor;

import Constant.Constants;

public class AccountInputProcessor extends InputProcessor{
    public static int processInput(String str){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL)
            return result;
        else if(result == Constants.SUCCESS || result == Constants.CLOSE)
            return checkAccountFormat(str);
        else
            return Constants.FAIL;
    }


    private static int checkAccountFormat(String str) {
        if(str.equalsIgnoreCase("admin"))
            //admin인지 체크
            return Constants.ADMIN;
        else if(str.matches("[0-9]+") && str.charAt(0) == '0' && str.charAt(1) == '1' && str.charAt(2) == '0' && str.length() == 11)
            //전화번호인지 체크
            return Constants.USER;
        else
            //아니면 -1 반환
        {System.out.println("*It’s not the correct format of login. Please check it again.*");return Constants.FAIL;}
    }

    //test용
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        while(true){
//            System.out.println(AccountInputProcessor.processInput(scanner.nextLine()));
//        }
//    }
}
