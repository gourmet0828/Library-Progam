package Processor;

import Constant.Constants;

// 추가할 책 권 수 입력 검사
public class ItemNumInputProcessor extends  InputProcessor{
    public static int processInput(String str){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL||result ==Constants.CLOSE)
            return result;
        else if(result == Constants.SUCCESS)
            return checkItemNumFormat(str);
        else
            return Constants.FAIL;
    }


    private static int checkItemNumFormat(String str) {

        if(str.matches("\\d+")&& !str.equals("0")){
            String intRegex = "^[-+]?([1-9]\\d{0,8}|0|214748364[0-7]|-214748364[0-8])$";
            if(!str.matches(intRegex)) {
                System.out.println("The number of books can't exceed 2,147,483,647");
                return Constants.FAIL;
            }
            else {
                return Constants.SUCCESS;
            }
        }

        else {
            System.out.println("*Only integers other than 0 can be entered.*");
            return Constants.FAIL;
        }
    }
}
