package Processor;

import Constant.Constants;

public class AuthorNameInputProcessor {
    public static int processInput(String str){
        int result = InputProcessor.processInput_withoutBlankCondition(str);
        if(result == Constants.SUCCESS){
            return checkAuthorNameFormat(str);
        }else if(result == Constants.CLOSE || result == Constants.FAIL){
            return result;
        }
        else
            return Constants.FAIL;
    }

    private static int checkAuthorNameFormat(String str){
        if(!str.matches("[A-Za-z0-9 !@#$%]*") )
        {
            System.out.println("*Invalid Input. Entering only spaces is not allowed to be entered. Please re-enter it*\n");
            return Constants.FAIL;
        }else if (
                str.isBlank()
        ){
            return Constants.NEXT;
        }
        else
            return Constants.SUCCESS;

    }


}


