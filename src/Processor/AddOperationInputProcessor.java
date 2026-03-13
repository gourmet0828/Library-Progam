package Processor;

import Constant.Constants;

public class AddOperationInputProcessor extends InputProcessor{
    public static int processInput(String str){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL|| result == Constants.CLOSE){
            return Constants.FAIL;
        }

        if(str.equalsIgnoreCase("new"))
            return Constants.SUCCESS;
        else
            //
            return Constants.FAIL;
    }
}
