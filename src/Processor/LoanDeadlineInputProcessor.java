package Processor;

import Constant.Constants;

public class LoanDeadlineInputProcessor {
    public static int processInput(String str, int currentDeadline){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL||result ==Constants.CLOSE)
            return result;
        else if(result == Constants.SUCCESS)
            return checkLoanDeadlineFormat(str, currentDeadline);
        else
            return Constants.FAIL;
    }


    private static int checkLoanDeadlineFormat(String str, int currentDeadline) {

        if(str.matches("\\d+")&& !str.equals("0")){
            String intRegex = "^[-+]?([1-9]\\d{0,8}|0|214748364[0-7]|-214748364[0-8])$";
            if(!str.matches(intRegex)) {
                System.out.println("The loan deadline can't exceed 2,147,483,647");
                return Constants.FAIL;
            }
            else if (Integer.parseInt(str)==currentDeadline){
                System.out.println("The loan deadline that inputed is same with current loan deadline");
                return Constants.FAIL;
            }
            else {
                return Constants.SUCCESS;
            }
        }


        else {
            System.out.println("*Only integers more than 0 can be entered.*");
            return Constants.FAIL;
        }
    }
}
