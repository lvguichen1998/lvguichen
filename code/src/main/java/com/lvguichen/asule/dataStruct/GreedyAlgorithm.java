package com.lvguichen.asule.dataStruct;

import java.util.*;

/**
 * @author xiaohu_zong
 * @version 1.0
 * @description: 贪心算法
 * @date 2022/10/11 9:12
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台，放到hashmap,hashset是广播覆盖地区不重复
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        //将各个电台放到broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");
        //加入到map
        broadcasts.put("K1", hashSet1);
        broadcasts.put("K2", hashSet2);
        broadcasts.put("K3", hashSet3);
        broadcasts.put("K4", hashSet4);
        broadcasts.put("K5", hashSet5);
        //存放所有地区
        HashSet<String> hashSetAll = new HashSet<>();
        for (Map.Entry<String, HashSet<String>> entry : broadcasts.entrySet()) {
            hashSetAll.addAll(entry.getValue());
        }
        //System.out.println(hashSetAll.toString());
        //创建ArraysList存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();
        //定义一个临时集合，存放选择的电台和总电台的交集
        HashSet<String> tempSet = new HashSet<>();
        //定义maxKey,保存在一次遍历过程中，能够覆盖最大未覆盖的地区对应的电台的key
        //maxKey不为空，则会加入到selects
        String maxKey = null;
        while (hashSetAll != null) {//如果hashSetAll不为空，则表示还没有覆盖到所有的地区
            //每次进行while时要把maxKey置空
            maxKey =null;
            for (String key : broadcasts.keySet()) {
                //每进行一次for，清空tempSet
                tempSet.clear();
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //求交集,取出tempSet和hashSetAll相同的元素，结果再赋给tempSet
                tempSet.retainAll(hashSetAll);
                if (tempSet.size()>0&&(maxKey ==null)||tempSet.size()>broadcasts.get(key).size()){
                    maxKey = key;
                }
            }
            //maxKey!=null,就应该吧maxKey加入到selects
            if (maxKey!=null){
                selects.add(maxKey);
                //清除掉已选择的电台
                hashSetAll.removeAll(broadcasts.get(maxKey));
            }
        }
        System.out.println("得到的选择结果"+selects);
    }

}
