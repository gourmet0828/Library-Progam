package Processor;

import Constant.Constants;
import ItemList.Item;

import java.util.List;

public class BookIdInputProcessor extends InputProcessor{
    public static int processInput(String str, List<Item> items){
        int result = InputProcessor.processInput(str);
        if(result == Constants.FAIL || result == Constants.CLOSE){
            return result;
        }
        return checkBookIdFormat(str,items);

    }
    private static int checkBookIdFormat(String str, List<Item> items){
        if(!str.matches("\\d{7}"))
        {
            System.out.println("*Invalid Input. Please re-enter*");
            return Constants.FAIL;
        }

        for (Item item : items) {
            if (item.getPhysicalId().equals(str)) return Constants.SUCCESS;
        }
        System.out.println("*Please enter the Physical Id of a book in list*\n");
        return Constants.FAIL;
    }

}
