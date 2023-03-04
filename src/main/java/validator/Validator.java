package validator;

import domain.User;
import exceptions.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public void ValidateUser(String username, String email, String password) throws ValidatorException{
        try {
            Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            boolean matchFound = matcher.find();
            if(!matchFound){
                throw new ClassCastException();
            }
            pattern = Pattern.compile("^([A-Z][a-z.\\-]+)+( [A-Z][a-z.\\-]+)*$");
            matcher = pattern.matcher(username);
            matchFound = matcher.find();
            if(!matchFound){
                throw new ClassCastException();
            }
            pattern = Pattern.compile("^[\\w.\\-_]{5,16}$");
            matcher = pattern.matcher(password);
            matchFound = matcher.find();
            if(!matchFound){
                throw new ClassCastException();
            }
        }
        catch (ClassCastException exception){
            throw new ValidatorException("introduce strings for username, email and password or a valid email or username.");
        }
    }
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
