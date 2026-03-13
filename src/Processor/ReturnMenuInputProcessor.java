package Processor;
import Constant.Constants;


public class ReturnMenuInputProcessor extends InputProcessor {
    private String returnedBook;
    private int borrowedCount;

    public static int processInput(String returnedBook, int borrowedCount) {

        ReturnMenuInputProcessor processor = new ReturnMenuInputProcessor();
        processor.returnedBook = returnedBook;
        processor.borrowedCount = borrowedCount;

        int result = InputProcessor.processInput(returnedBook);

        if (result == Constants.FAIL || result == Constants.CLOSE) {
            return result; //
        }

        else if (result == Constants.SUCCESS) {
            return processor.checkMenuFormat(processor.returnedBook, processor.borrowedCount);
        }

        return Constants.FAIL;
    }

    private int checkMenuFormat(String returnedBook, int borrowedCount) {

        if (returnedBook.trim().length() > 0 && returnedBook.trim().matches("[0-9]+")) {
            String intRegex = "^[-+]?([1-9]\\d{0,8}|0|214748364[0-7]|-214748364[0-8])$";
            if(!returnedBook.matches(intRegex)) {
                System.out.println("The number of books can't exceed 2,147,483,647");
                return Constants.FAIL;
            }

            int returnedCount = Integer.parseInt(returnedBook.trim());

            if (returnedCount >= 1 && returnedCount <= 3) {
                if (borrowedCount >= returnedCount) {
                    return Constants.SUCCESS;
                } else {
                    System.out.println("**A number does not exist or is not in a valid input format. Please re-enter.*");
                    return Constants.FAIL;
                }
            }
            else  {
                System.out.println("**You must return between 1 and 3 books. Please re-enter.*");
                return Constants.FAIL;
            }
        }
        else  {
            System.out.println("**You must return between 1 and 3 books. Please re-enter.*");
            return Constants.FAIL;
        }

    }


}



