package Processor;

import Constant.Constants;

import java.util.List;

public class AuthorCodeInputProcessor extends InputProcessor{
    public static int processInput(String str, List<Integer> authorCodeList){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL|| result == Constants.CLOSE)
            return result;
        else if(result == Constants.SUCCESS )
            return isNumericAndWithinIntRange(str, authorCodeList);
        else
            return Constants.FAIL;
    }


    private static int isNumericAndWithinIntRange(String input, List<Integer> authorCodeList) {


        if (!input.matches("-?\\d+") && !input.equalsIgnoreCase("new")) {
            //System.out.println("*잘못된 입력*\n");
            return Constants.FAIL;
        }


        try {
            int inputToInt = Integer.parseInt(input);
            for (Integer code : authorCodeList){
                if(inputToInt==code){
                    //System.out.println("*성공적으로 추가*\n");
                    return Constants.SUCCESS;
                }
            }
            //System.out.println("* 리스트에 없는 코드 *\n");
            return Constants.FAIL;

        } catch (NumberFormatException e) {
            //System.out.println("*정수 범위 안*\n");
            return Constants.FAIL;
        }
    }
}