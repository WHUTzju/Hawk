package cn.easy.util;

import java.util.Random;

/**
 * Created by superlee on 2017/12/7.
 * 随机码生成类
 */
public final class RandomCodeUtil {

    /**
     * 生成数字随机码
     *
     * @param number 指定长度
     * @return 随机码
     */
    public static String getRandomNumCode(int number) {
        StringBuilder codeNum = new StringBuilder();
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
            codeNum.append(numbers[next % 10]);
        }
        return codeNum.toString();
    }

    public static int getSingleNumCodeNotEqual(int dstCode) {
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        int next = random.nextInt(10000);
        int code = numbers[next % 10];
        while (code == dstCode) {
            next = random.nextInt(10000);
            code = numbers[next % 10];
        }
        return code;
    }

    /**
     * 生成随机码，包含数字、大小写字母
     *
     * @param number 指定长度
     * @return 随机码
     */
    public static String getRandomCode(int number) {
        StringBuilder codeNum = new StringBuilder();
        int[] code = new int[3];
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum.append((char) code[random.nextInt(3)]);
        }

        return codeNum.toString();
    }

    public static void main(String[] args) {
        for (int i=0;i<50;i++){
            System.out.println(getSingleNumCodeNotEqual(0));
        }

    }

}
