package Checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {

    public static boolean isThisStringDateFormat(String data){
        Pattern pattern = Pattern.compile("(19|20)[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])");
        Matcher matcher = pattern.matcher(data);
        if(matcher.find( )){
            return true;
        } else  {
            return false;
        }
    }


    public static boolean isThisStringInteger(String string){
        try {
            Integer.valueOf(string);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
