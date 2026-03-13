package Processor;

import Constant.Constants;

public class DateInputProcessor extends InputProcessor{
    public static int processInput(String str){
        int result = InputProcessor.processInput(str);
        if(result == Constants.CLOSE || result == Constants.FAIL)
            return Constants.FAIL;
        else if(result == Constants.SUCCESS)
            return checkDateFormat(str);
        else
            return Constants.FAIL;
    }

    private static int checkDateFormat(String str) {
        if(!str.matches("[0-9]+") || str.length() != 6)
            //숫자로만 구성되어있는지, 길이가 6인지
            return Constants.FAIL;

        int tmp = Integer.parseInt(str);
        int year = tmp / 10000 + 2000;
        int month = tmp % 10000 / 100;
        int date = tmp % 100;
        int leapYear = 0;// 윤년 여부(윤년이면 1, 아니면 0)

        if(month > 12 || month == 0)
            //월이 범위 내에 있는지
            return Constants.FAIL;

        if(date == 0)
            return Constants.FAIL;
        if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            leapYear = 1;
        //윤년인지 확인
        if(leapYear == 1 && month == 2 && date > 29)
            return Constants.FAIL;
        if(leapYear == 0 && month == 2 && date > 28)
            return Constants.FAIL;
        //2월 먼저 확인

        if(month % 2 == 1 && month <= 7 && date > 31)
            return Constants.FAIL;
        if(month % 2 == 0 && month > 7 && date > 31)
            return Constants.FAIL;
        //31일까지 있는 달 처리

        if(month % 2 == 0 && month <= 7 && date > 30)
            return Constants.FAIL;
        if(month % 2 == 1 && month > 7 && date > 30)
            return Constants.FAIL;
        //30일까지 있는 달 처리

        return Constants.SUCCESS;
    }

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        while(true){
//            System.out.println(processInput(scanner.nextLine()));
//        }
//    }
}
