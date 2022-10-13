package com.lvguichen.asule.dataStruct.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xiaohu_zong
 * @version 1.0
 * @description:基数排序也叫桶子排序
 * @date 2022/10/6 13:36
 */
public class RadixSort {
    public static void main(String[] args) {
//        int[] arr = {53, 3, 542, 748, 14, 214};
//        radixSort(arr);
        //测试基数排序的速度
        int[] arr=new int[800000];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=(int)(Math.random()*800000);//生成0到800000的随机数
        }

        Date date =new Date();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sm.format(date);
        System.out.println("排序前时间"+format);
        radixSort(arr);
        Date date1 =new Date();
        String format1 = sm.format(date1);
        System.out.println("排序后时间"+format1);

    }


    //基数排序方法
    public static void radixSort(int[] arr) {
        //定义一个二维数组表示十个桶，每个桶都是一个一维数组
        //基数排序是用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];
        //为了记录每个桶的数据个数，我们定义了一个一维数组来记录各个桶每次放入数据的个数
        int[] bucketElementCounts = new int[10];
        //通过三次排序得到最终的结果，我们可以推理得到最终的代码
        //1.得到数组中最大值得位数
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[0]) {
                max = arr[i];
            }
        }
        //最大位数决定了需要几轮排序
        int maxLength = (max + "").length();
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            for (int j = 0; j < arr.length; j++) {
                //取模，第一次个位，第二次十位，第三次百位。。。。
                int digitOfElement = arr[j] / n % 10;//取模
                //放到对应的位置,
                //bucketElementCounts[digitOfElement]表示放到这个桶的第几个元素
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            //按照这个顺序，将排序后的数组取出放入到原数组
            int index = 0;
            for (int k = 0; k < bucketElementCounts.length; k++) {
                if (bucketElementCounts[k] != 0) {
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index] = bucket[k][l];
                        index++;
                    }
                }
                //每一轮需要重新计数
                bucketElementCounts[k] = 0;
            }
            //System.out.println("第"+(i+1)+"次排序后的数组arr="+Arrays.toString(arr));
        }
    }

    public static void radixSortTest(int[] arr) {
        /**
         * 第一轮根据每个元素的个位数进行排序处理
         * 第二轮是根据每个元素的十位数
         * 第三轮是百位数。。。依次类推，以元素的最大位数为排序次数
         */
        //定义一个二维数组表示十个桶，每个桶都是一个一维数组
        //基数排序是用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];
        //为了记录每个桶的数据个数，我们定义了一个一维数组来记录各个桶每次放入数据的个数
        int[] bucketElementCounts = new int[10];

        for (int i = 0; i < arr.length; i++) {
            //取出每个元素的个位数
            int digitOfElement = arr[i] % 10;//取模得到个位数的值
            //放到对应的位置,
            //bucketElementCounts[digitOfElement]表示放到这个桶的第几个元素
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[i];
            bucketElementCounts[digitOfElement]++;

        }
        //按照这个顺序，将排序后的数组取出放入到原数组
        int index = 0;
        for (int i = 0; i < bucketElementCounts.length; i++) {
            if (bucketElementCounts[i] != 0) {
                for (int j = 0; j < bucketElementCounts[i]; j++) {
                    arr[index] = bucket[i][j];
                    index++;
                }
            }
            //每一轮需要重新计数
            bucketElementCounts[i] = 0;
        }
        System.out.println("输出第一轮排序后的数组arr=" + Arrays.toString(arr));

        //第二轮
        for (int i = 0; i < arr.length; i++) {
            //取出每个元素的个位数
            int digitOfElement = arr[i] / 100 % 10;//取模得到十位数的值
            //放到对应的位置
            //bucketElementCounts[digitOfElement]表示放到这个桶的第几个元素
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[i];
            bucketElementCounts[digitOfElement]++;

        }
        //按照这个顺序，将排序后的数组取出放入到原数组
        index = 0;
        for (int i = 0; i < bucketElementCounts.length; i++) {
            if (bucketElementCounts[i] != 0) {
                for (int j = 0; j < bucketElementCounts[i]; j++) {
                    arr[index] = bucket[i][j];
                    index++;
                }
            }
            //每一轮需要重新计数
            bucketElementCounts[i] = 0;
        }
        System.out.println("输出第2轮排序后的数组arr=" + Arrays.toString(arr));

        //第三轮
        for (int i = 0; i < arr.length; i++) {
            //取出每个元素的个位数
            int digitOfElement = arr[i] / 10 / 10 % 10;//取模得到百位数的值
            //放到对应的位置
            //bucketElementCounts[digitOfElement]表示放到这个桶的第几个元素
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[i];
            bucketElementCounts[digitOfElement]++;

        }
        //按照这个顺序，将排序后的数组取出放入到原数组
        index = 0;
        for (int i = 0; i < bucketElementCounts.length; i++) {
            if (bucketElementCounts[i] != 0) {
                for (int j = 0; j < bucketElementCounts[i]; j++) {
                    arr[index] = bucket[i][j];
                    index++;
                }
            }
            //每一轮需要重新计数
            bucketElementCounts[i] = 0;
        }
        System.out.println("输出第3轮排序后的数组arr=" + Arrays.toString(arr));
    }
}
