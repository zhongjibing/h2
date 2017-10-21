package com.icezhg.h2.enums;

/**
 * TestEnum:
 *
 * @author zhongjibing 2017-10-20
 * @version 1.0
 */
public enum TestEnum {
    A(1),
    B(1),
    C(2);

    private int value;

    TestEnum(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
       String[] books = new String[2];

       books[0] = "1";
       books[1] = "2";

        for (String book : books) {
            System.out.println(book);
        }
     }
}
