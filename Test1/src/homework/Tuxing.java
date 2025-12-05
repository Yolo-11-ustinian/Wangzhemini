package homework;

import java.util.Scanner;

public class Tuxing {
    private static double PI=3.14;
    private static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args){
        int choice;

        while(true){
            System.out.println("请输入1 2分别计算圆形，矩形的面积");
            choice=scanner.nextInt();

            switch(choice){
                case 1:
                    circle();
                    break;
                case 2:
                    rectangle();
                    break;
            }
            System.out.println();
        }

    }
    static void circle(){
        System.out.printf("请输入圆的半径r:");
        double r,s;
        r=scanner.nextDouble();
        s=PI*r*r;
        System.out.printf("圆的面积=%.2f\n",s);
    }
    static void rectangle(){
        System.out.printf("请输入矩形的长l:，宽d:");
        double d,S;
        double l = scanner.nextDouble();
        d=scanner.nextDouble();
        S=l*d; 
        System.out.printf("矩形的面积=%.2f\n",S);
    }

}
