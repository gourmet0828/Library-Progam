package Processor;

import Constant.Constants;
import ItemList.Item;

import java.util.List;

public class ISBNInputProcessor {
    public static int processInput(String str, List<Item> items){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL || result == Constants.CLOSE){
            return result;
        }
        return checkISBNFormat(str,items);

    }
    private static int checkISBNFormat(String str, List<Item> items){
        if(str.length() != 4 || !str.matches("[0-9]+"))
        {
            System.out.println("*Invalid Input. Please re-enter*");
            return Constants.FAIL;
        }

        for (Item item : items) {
            if (item.getIsbnId().equals(str)) return Constants.SUCCESS;
        }
        System.out.println("*Please enter the ISBN of a book in list*\n");
        return Constants.FAIL;
    }
}
