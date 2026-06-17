package controller;

import java.util.ArrayList;

public class myUtils {
    public static boolean isNumeric(String str) {
        if (str == null) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static ArrayList<String> capitalizeStrings(ArrayList<String> inputStrings){
        ArrayList<String> outputStrings = new ArrayList<>();
        for(String string : inputStrings){
            if(string != null && !string.isBlank()){
                outputStrings.add(string.substring(0, 1).toUpperCase() + string.substring(1));
            }
        }
        return outputStrings;
    }

    public static String capitalize(String string){
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("bababa");
        a.add("lelele");
        System.out.println(capitalizeStrings(a));
    }
}
