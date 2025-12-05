package homework;

import java.util.Scanner;

public class runnian {
    public static void main(String[] args){
        System.out.print("请输入年份：");
        int year=new Scanner(System.in).nextInt();

        if(year%4==0 && year%100!=0){
            System.out.println(year+"是闰年");
        }
        else if(year%400==0){
            System.out.println(year+"是闰年");
        }
        else{
            System.out.println(year+"不是闰年");
        }

    }
}
