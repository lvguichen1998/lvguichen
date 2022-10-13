package com.lvguichen.asule.dataStruct.recursion;

public class demo {
    public static void main(String[] args) {
        test(8);
        System.out.println(test(8));
    }

    public static int test(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return test(n - 1) + test(n - 2);
    }
}
