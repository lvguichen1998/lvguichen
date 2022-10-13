package com.lvguichen.asule.dataStruct.recursion;

/**
 * @author xiaohu_zong
 * @version 1.0
 * @description: 八皇后问题
 * @date 2022/10/6 11:51
 */
public class EightQueens {
    int max = 8;
    //创建8*8的棋盘，这里使用一维数组，下标代表第几行，下标对应的元素代表第几列
    int[]arr=new int[max];
    public static void main(String[] args) {


    }
    //打印皇后位置
    private void print(){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    //判断皇后位置是否冲突,第N个皇后
    private boolean judge(int n){
        for (int i = 0; i < n; i++) {
            if (arr[i]==arr[n]||Math.abs(arr[i]-arr[n])==Math.abs(i-n)){
                return false;
            }
        }
        return true;
    }
    //放置皇后
    private void check(int n){
        if (n==max){
            //当n==8时就是在放第九个皇后
            print();
            return;
        }
        //依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            arr[n]=i;
            //判断不冲突
            if (judge(n)){
                check(n+1);
            }
            //冲突后执行for循环，会吧皇后放在i+1的位置。。。。。
        }
    }

}
