package com.globaroman.auxilium.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class ViewAUX implements TextConstants {
    //static Locale locale = new Locale("ru");
    //static Locale locale = new Locale("ua");
    static Locale locale = new Locale("");


    static String MESSAGE_BUNDLE_NAME = "message";
    static String MESSAGE_BUNDLE_REGEX = "regex";

    public static final ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_BUNDLE_NAME, locale);
    public static final ResourceBundle bundleRegEX = ResourceBundle.getBundle(MESSAGE_BUNDLE_REGEX, locale);

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printStringBundle(String message) {
        printMessage(bundle.getString(message));
    }

    public static String concatenationString(String... message) {
        StringBuilder concatString = new StringBuilder();
        for (String v : message) {
            concatString.append(v);
        }
        return new String(concatString);
    }

    public static void printWrongStringInput(String message) {
        printMessage(concatenationString(
                bundle.getString(MESSAGE_WRONG_INPUT_DATA),
                bundle.getString(MESSAGE_INPUT_STRING_DATA),
                bundle.getString(message)));
    }
}
