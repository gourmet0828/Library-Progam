package Processor;
import Constant.Constants;

public class MenuInputProcessor extends InputProcessor {
    private static String menuNum;


    public static int processInput(String response, int who) {

        int result1 = InputProcessor.processInput(response);
        if(result1 == Constants.FAIL){
            return Constants.FAIL;
        }

        menuNum = response.trim();

        int result = InputProcessor.processInput(menuNum);

        if (result == Constants.CLOSE || result == Constants.FAIL) {
            return Constants.FAIL;
        }
        else if (result == Constants.SUCCESS) {
            if (who == Constants.USER)
                return checkUserMenuFormat(menuNum);
            else
                return checkAdminMenuFormat (menuNum);
        }

        return Constants.FAIL;
    }

    private static int checkUserMenuFormat(String menuNum) {
        if (menuNum.matches("\\s*([1-5])\\s*")) {
            return Constants.SUCCESS;
        } else {
            System.out.println("");
            return Constants.FAIL;
        }
    }
    private static int checkAdminMenuFormat(String menuNum) {
        if (menuNum.matches("\\s*([1-6])\\s*")) {
            return Constants.SUCCESS;
        } else {
            System.out.println("");
            return Constants.FAIL;
        }
    }
}

