package Processor;

import Constant.Constants;

public class InputProcessor {
    public static int processInput(String str){
        if(checkInputLength(str) ==Constants.FAIL)
            return Constants.FAIL;
        //길이 검사
        if(isBlank(str) ==Constants.FAIL)
            return Constants.FAIL;

        int quit = handleQuit(str);
        if(quit == Constants.CLOSE)
            return quit;
        //종료 검사

        if(checkAvailableLetter(str) == Constants.FAIL)
            return Constants.FAIL;
        //쓸 수 있는 문자인지 검사

        return Constants.SUCCESS;
    }
    public static int processInput_withoutBlankCondition(String str){
        if(checkInputLength(str) ==Constants.FAIL)
            return Constants.FAIL;
        //길이 검사

        int quit = handleQuit(str);
        if(quit == Constants.CLOSE)
            return quit;
        //종료 검사

        if(checkAvailableLetter(str) == Constants.FAIL)
            return Constants.FAIL;
        //쓸 수 있는 문자인지 검사

        return Constants.SUCCESS;
    }

    private static int checkAvailableLetter(String str) {
        if(!str.matches("[A-Za-z0-9 !@#$%]*") )
        {
            System.out.println("\n" +
                    "It appears that invalid characters were entered.");
            return Constants.FAIL;
        }
        else
            return Constants.SUCCESS;
    }
    private static int isBlank(String str) {
        if(str.isBlank())
        {
            System.out.println("\n" +
                    "It appears that only whitespace were entered.");
            return Constants.FAIL;
        }
        else
            return Constants.SUCCESS;
    }

    private static int handleQuit(String str) {
        if(str.trim().equals("!q"))
            return Constants.CLOSE;
        else
            return Constants.SUCCESS;
    }

    private static int checkInputLength(String str) {
        if(str.length() < 20)
            return Constants.SUCCESS;
        else{
            System.out.println("Allow input of less than 20 characters. Please re-enter.");
            return Constants.FAIL;
    }}

}
