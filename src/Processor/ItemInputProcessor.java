package Processor;

import Constant.Constants;

public class ItemInputProcessor extends InputProcessor{


    public static int processInput(String str){
        int result = InputProcessor.processInput(str);
        if(result == Constants.SUCCESS){
            return checkItemFormat(str);
        }else if(result == Constants.CLOSE || result == Constants.FAIL){
            return result;
        }
        else
            return Constants.FAIL;
    }

    private static int checkItemFormat(String str){
        if(!str.matches("[A-Za-z0-9 !@#$%]*") || str.isBlank() || str.length() >= 20)
        {
            System.out.println("*Invalid Input. Entering only spaces is not allowed to be entered. Please re-enter it*\n");
            return Constants.FAIL;
        }
        else
            return Constants.SUCCESS;

    }

//    //test용
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("테스트용");
//        while(true) {
//            System.out.println(processInput(scanner.nextLine()));
//        }
//    }
}
