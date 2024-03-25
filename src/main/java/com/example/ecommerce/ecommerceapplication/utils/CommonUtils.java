package com.example.ecommerce.ecommerceapplication.utils;


public final class CommonUtils {

    private static final String COMMA = ",";

    private CommonUtils(){
        throw new  UnsupportedOperationException("Utility class can not be instantiated");
    }


    public static String arrayToString(String[] arr){
        return String.join(COMMA,arr);
    }

    public static String[] commaSeperatedToArray(String commaSeperated){
        return commaSeperated.split(commaSeperated);
    }
}
