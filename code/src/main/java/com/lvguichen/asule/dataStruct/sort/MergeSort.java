package com.lvguichen.asule.dataStruct.sort;

import java.util.Arrays;

/**
 * @author xiaohu_zong
 * @version 1.0
 * @description: 归并排序
 * @date 2022/10/6 10:08
 */
public class MergeSort {
    public static void main(String[] args) {
        int arr[] = {8, 4, 6, 3, 2, 7, 1, 5};
        int[]temp = new int[arr.length];//归并排序需要一个额外的空间开销
        mergeSort(arr,0, arr.length-1,temp);//这一步完成数组没有发生变化，只是分裂不排序
        //归并排序后的数组
        System.out.println("归并排序后的数组"+ Arrays.toString(arr));
    }

    /**
     * 递归拆分数组
     *
     * @param arr   初始数组
     * @param left  左下标
     * @param right 右下标
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        //递归思想，每一次分裂的中间索引就是分裂后左边数组的最后索引，右边数组的最初索引的前一个索引。
        if (left<right) {
            int mid = (right+left)/2;
            //先向左递归分裂数组
            mergeSort(arr, left, mid, temp);
            //向右递归分裂数组
            mergeSort(arr, mid+1, right, temp);
            //每分裂一次就合并一次
            sort(arr,left,mid,right,temp);

        }
    }

    /**
     * 数组合并排序方法
     * @param arr   无序数组
     * @param left  左边数组的初始下标
     * @param mid   两个数组中间的下标
     * @param right 右边数组的最右边下标
     * @param temp  辅助数组
     */
    public static void sort(int[] arr, int left, int mid, int right, int[] temp) {
        System.out.println("xxxxx");
        int i = left;
        int j = mid + 1;
        int t = 0;//辅助数组的第一个元素的下标
        //表示数组还没有排序完成
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                i += 1;
                t += 1;
            } else {
                temp[t] = arr[j];
                j += 1;
                t += 1;
            }
        }
        //左右数组有一个排序完成，剩下的数组直接依次拷贝到temp
        while (i<=mid){
            temp[t] = arr[i];
            i += 1;
            t += 1;
        }
        while (j<=right){
            temp[t] = arr[j];
            j += 1;
            t += 1;
        }
        //合并数组,8个元素需要合并7次,n-1次,时间复杂度是线性的比较小
        int tempLeft = left;
        t = 0;
        while (tempLeft <= right) {
            arr[tempLeft]= temp[t];
            tempLeft += 1;
            t += 1;
        }
    }
}
