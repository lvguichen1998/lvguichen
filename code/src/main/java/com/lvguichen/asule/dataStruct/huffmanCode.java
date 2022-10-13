package com.lvguichen.asule.dataStruct;

import java.io.*;
import java.util.*;

/**
 * @author xiaohu_zong
 * @version 1.0
 * @description: 赫夫曼编码:数据压缩和解压
 * @date 2022/10/8 13:44
 */
public class huffmanCode {
    public static void main(String[] args) {
        //给定一串字符“i like like like java do you like a java”进行压缩编码
        //首先根据每个字符出现的频率定义权重，然后用权重创建赫夫曼编码
        /**
         * 思路
         * 1.Node{ data(存放数据)，weight(权重),left和right}
         * 2.得到字符串对应的byte[]数组
         * 3.编写一个方法，将准备构建赫夫曼树的Node节点放到List
         * 4.通过List创建赫夫曼树
         */

        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println(contentBytes.length);//长度是40
        List<Node> nodes = getNodes(contentBytes);
        System.out.println("node = " + nodes);
        //输出创建的赫夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        System.out.println("输出赫夫曼树~");
        preOrder(huffmanTreeRoot);
        //输出赫夫曼编码
        getHuffmanCode(huffmanTreeRoot, "", stringBuilder);
        for (Map.Entry<Byte, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
        //赫夫曼编码压缩结果
        byte[] zip = zip(contentBytes, huffmanCode);
        System.out.println(Arrays.toString(zip));
        //解压缩为原来的字符串
        byte[] decode = decode(huffmanCode, zip);
        System.out.println("原来的字符串" + new String(decode));

    }

    /**
     * 接受字节数组，返回node集合
     *
     * @param bytes
     * @return
     */
    private static List<Node> getNodes(byte[] bytes) {
        //创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();
        //存储每个字符出现的次数
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            if (counts.get(b) == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, counts.get(b) + 1);
            }
        }
        //把每个键值对转成一个node对象，并加入到node集合中
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    /**
     * 根据Node集合创建赫夫曼树
     *
     * @param nodes
     * @return 返回赫夫曼树的根节点
     */
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            //排序，从小到大
            Collections.sort(nodes);
            //按照顺序取出最小的node和次小的node
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //两个叶子节点生成一个二叉树，它的根节点没有data只有权重值,权重是两个小的权重和
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.lift = leftNode;
            parent.right = rightNode;
            //将已经处理的两个小的叶子节点移除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新生成的节点加入进去
            nodes.add(parent);
        }
        //最后只会有一个节点存在就是赫夫曼树的根节点
        return nodes.get(0);
    }

    /**
     * 前序遍历的方法
     *
     * @param root 赫夫曼树的根节点
     */
    private static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        }
    }

    /**
     * 生成赫夫曼树对应的赫夫曼编码表,赫夫曼树的规则向左的枝干为0，向右的枝干为1
     * 将赫夫曼编码表放在Map<Byte,String>
     * 在生成编码表的过程中，需要去拼接路径（0或1），需要定义一个StringBuilder,存储某个叶子节点的路径
     */
    static Map<Byte, String> huffmanCode = new HashMap<>();
    static StringBuilder stringBuilder = new StringBuilder();

    /**
     * 将传入的node的叶子节点的赫夫曼编码得到，并放入到huffmanCode集合中
     *
     * @param root          传入节点
     * @param code          路径（0或1）左子节点拼接0，右子节点拼接1
     * @param stringBuilder 拼接路径
     */
    private static void getHuffmanCode(Node root, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code加入到stringBuilder2
        stringBuilder2.append(code);
        if (root != null) {
            //判断当前节点是否是叶子节点
            if (root.data == null) {
                //如果不是叶子节点
                //向左递归
                getHuffmanCode(root.lift, "0", stringBuilder2);
                //向右递归
                getHuffmanCode(root.right, "1", stringBuilder2);
            } else {
                //表示找到了叶子节点
                huffmanCode.put(root.data, stringBuilder2.toString());
            }
        }
    }

    /**
     * 将字符串对应的byte【】数组，通过生成的赫夫曼编码表，返回一个通过赫夫曼编码压缩后的byte[]
     *
     * @param bytes
     * @param huffmanCode
     * @return
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCode) {
        //利用huffmanCode将byte转化为赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            //拼接字符对应的赫夫曼编码
            stringBuilder.append(huffmanCode.get(b));
        }
        //将拼接后的赫夫曼编码每八个一组封装成byte,
        int length;//生成后的byte数组的长度
        if (stringBuilder.length() % 8 == 0) {
            length = stringBuilder.length() / 8;
        } else {
            length = stringBuilder.length() / 8 + 1;
        }
        //创建储存压缩后的byte数组
        byte[] bytes1 = new byte[length];
        int index = 0;//新byte数组的索引
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            //定义截取的字符串
            String str;
            if (i + 8 < stringBuilder.length()) {
                str = stringBuilder.substring(i, i + 8);
            } else {
                str = stringBuilder.substring(i);
            }
            //将截取的字符串转化为byte
            bytes1[index] = (byte) Integer.parseInt(str, 2);
            index++;

        }
        return bytes1;
    }

    //压缩后的解压方法，重新解压为String content = "i like like like java do you like a java";

    /**
     * 将一个byte转化为一个二进制的字符串
     *
     * @param b    传入的一个byte
     * @param flag 标识是否需要补高位，true表示需要补高位，FALSE表示不需要
     * @return 是b对应的二进制字符串（是按补码返回的）
     */
    private static String byteToBitString(boolean flag, byte b) {
        int temp = b;//将b转成了int
        //如果是正数，还需要补高位
        if (flag) {
            temp |= 256;//按位或 256  1 0000 0000 | 0000 0001 =>1 0000 0001,在相同位置有一个为1就为1,
        }
        String s = Integer.toBinaryString(temp);//返回的是temp对应的二进制补码
        if (flag) {
            return s.substring(s.length() - 8);
        } else {
            return s;
        }

    }

    /**
     * @param huffmanCode  哈弗曼编码
     * @param huffmanBytes 处理过后的数组
     * @return
     */
    private static byte[] decode(Map<Byte, String> huffmanCode, byte[] huffmanBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            //判断是不是最后一个字节
            byte huffmanByte = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, huffmanByte));
        }
        //得到的二进制字符串根据赫夫曼编码表进行解码
        //把赫夫曼编码表的key和value进行调换
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCode.entrySet()) {
            //进行调换
            map.put(entry.getValue(), entry.getKey());
        }
        //创建一个集合存放byte
        List<Byte> list = new ArrayList<>();
        //扫描二进制字符串
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while (flag) {
                //i不动，count一直向后移，因为是前缀编码没有重复，直至找到一个和赫夫曼编码表匹配的字符串
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    //说明没有匹配到，count后移
                    count++;
                } else {
                    //匹配到了,退出循环
                    flag = false;
                }
            }
            //把匹配得到的字符添加到集合中，并把i移到之前找到的位置
            list.add(b);
            i += count;
        }
        //当for循环结束，list集合中就存放了所有的字符
        //把list中的元素，放到byte数组里返回
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    /**
     * 使用一个方法封装赫夫曼编码压缩的过程、
     *
     * @param bytes 原始的字符串对应的字节数组
     * @return 经过赫夫曼编码处理后的数组
     */
    private static byte[] huffManZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        Node huffmanTree = createHuffmanTree(nodes);
        getHuffmanCode(huffmanTree, "", stringBuilder);
        byte[] zip = zip(bytes, huffmanCode);
        return zip;
    }

    /**
     * 编写一个方法压缩一个文件
     *
     * @param srcFile 压缩目标路径
     * @param detFile 压缩到路径
     */
    private static void zipFile(String srcFile, String detFile) {
        FileInputStream in = null;
        FileOutputStream out = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            //创建文件输入流
            in = new FileInputStream(srcFile);
            //创建和源文件大小一样的byte数组
            byte[] bytes = new byte[in.available()];
            //读取文件
            int read = in.read(bytes);
            //获取压缩文件
            byte[] huffManZip = huffManZip(bytes);
            //创建文件输出流
            out = new FileOutputStream(detFile);
            //创建和文件输出流相关的ObjectOutputSteam:对象输出流
            objectOutputStream = new ObjectOutputStream(out);
            //把赫夫曼编码后的字节数组写入压缩文件
            objectOutputStream.writeObject(huffManZip);
            //以对象流的方式写入赫夫曼编码，是为了以后恢复文件时使用,一定要把赫夫曼编码写入压缩文件
            objectOutputStream.writeObject(huffmanCode);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压文件的方法
     *
     * @param zipFile 准备需要解压的文件的路径
     * @param dstFile 将文件解压到那个路径
     */
    private static void unZipFile(String zipFile, String dstFile) {
        //定义输入流
        InputStream in = null;
        //定义对象输入流
        ObjectInputStream ois = null;
        //定义文件输出流
        OutputStream out = null;
        try {
            in = new FileInputStream(zipFile);
            ois = new ObjectInputStream(in);
            //读取byte数组,压缩时候写入的，这里要读取
            byte[] bytes = (byte[]) ois.readObject();
            //读取赫夫曼编码表
            Map<Byte, String> huffmanCode = (Map<Byte, String>) ois.readObject();
            //解码
            byte[] decode = decode(huffmanCode, bytes);
            //将文件写入输出流
            out = new FileOutputStream(dstFile);
            out.write(decode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //关闭流顺序要和打开流相反
            try {
                out.close();
                ois.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class Node implements Comparable<Node> {
    Byte data;//存放数据本身，如果是‘a’对应的是97，‘ ’对应的是32
    int weight;//权重，就是data出现的次数
    Node lift;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        //定义规则，从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder() {
        //先输出当前节点
        System.out.println(this);
        //输出左节点
        if (this.lift != null) {
            this.lift.preOrder();
        }
        //输出右节点
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}

