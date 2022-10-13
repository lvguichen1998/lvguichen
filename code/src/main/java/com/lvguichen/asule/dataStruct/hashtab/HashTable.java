package com.lvguichen.asule.dataStruct.hashtab;

import java.util.Scanner;

/**
 * @author xiaohu_zong
 * @version 1.0
 * @description: hashTable相当于手写的一个小缓存区
 * @date 2022/10/7 9:32
 */
public class HashTable {
    public static void main(String[] args) {
        //创建一个hashTab
        HashTab hashTab = new HashTab(7);
        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("find:查找雇员");
            System.out.println("exit:退出");
            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("输入要查找的id");
                    int i = scanner.nextInt();
                    hashTab.findEmpById(i);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}

//表示一个雇员
class Emp {
    public int id;
    public String name;
    public Emp next;//默认为空

    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}

//创建EmpLinkedList,表示链表
class EmpLinkedList {
    //头指针直接指向第一个雇员
    private Emp head;


    /**
     * 添加雇员到链表,假定添加雇员时id是自增的，总是从小到大
     *
     * @param emp
     */
    public void add(Emp emp) {
        //如果是添加第一个雇员
        if (head == null) {
            head = emp;
            return;
        }
        //如果不是第一个雇员，则使用辅助指针帮助定位到最后
        Emp curEmp = head;
        while (true) {
            if (curEmp.next == null) {
                //说明到最后了
                break;
            }
            //后移直至最后
            curEmp = curEmp.next;
        }
        //添加雇员到最后
        curEmp.next = emp;
    }

    /**
     * 遍历链表
     */
    public void list(int no) {
        if (head == null) {
            System.out.println("第" + (no + 1) + "链表为空~");
            return;
        }
        System.out.print("第" + (no + 1) + "链表信息为~");
        Emp curEmp = head;
        while (true) {
            System.out.printf("=> id=%d name=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
        System.out.println();
    }

    /**
     * 根据id查找员工
     *
     * @param id
     * @return
     */
    public Emp findEmpById(int id) {
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                break;
            }
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
        return curEmp;
    }
}

//创建 hashTab管理多条链表
class HashTab {
    private EmpLinkedList[] empLinkedListArray;
    private int size;//表示有多少条链表

    //初始化里有多少条链表
    public HashTab(int size) {
        this.size = size;
        empLinkedListArray = new EmpLinkedList[size];
        //初始化hashTab的每条链表，不然会报空指针
        for (int i = 0; i < empLinkedListArray.length; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    //添加雇员
    public void add(Emp emp) {
        //根据员工的id来判断要加入到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        //添加到对应的链表
        empLinkedListArray[empLinkedListNo].add(emp);

    }

    /**
     * 根据id查找员工
     *
     * @param id
     * @return
     */
    public Emp findEmpById(int id) {
        //根据散列函数到那条链表去查找
        int i = hashFun(id);
        Emp empById = empLinkedListArray[i].findEmpById(id);
        if (empById != null) {
            //找到
            System.out.printf("在第%d条链表找到雇员id =%d\n", (i + 1), id);
        } else {
            System.out.println("在哈希表中没有找到该雇员");
        }

        return empById;
    }

    //遍历所有的链表
    public void list() {
        for (int i = 0; i < empLinkedListArray.length; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    //编写散列函数
    public int hashFun(int id) {
        return id % size;
    }
}
