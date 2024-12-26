package Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFormatChecker {

    public static boolean isValidEmail(String email){
        //I've changed this regex from the one in the course,
        // so it requires a dot after the @.
        String emailRegex = "^(.+)@(.+)\\.(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
