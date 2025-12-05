package KeyboardEntry;

import java.util.*;
import java.util.Scanner;

public class Input {
    public  Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public Input(Scanner scanner) {
        this.scanner = scanner;
    }


    public int getInt() {
        return getInt("", Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int getInt(String prompt) {
        return getInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int getInt(String prompt, int min, int max) {
        if (!prompt.isEmpty()) {
            System.out.print(prompt);
        }

        String input = scanner.nextLine().trim();
        int inputvalue = Integer.parseInt(input);

        if (inputvalue >= min && inputvalue <= max) {
            return inputvalue;
        } else if (inputvalue < min || inputvalue > max) {
            System.out.printf("输入超出范围 [%d - %d]，请重新输入: ", min, max);
        } else {
            System.out.print("输入无效，请输入整数: ");
        }
       return inputvalue;
    }

    public double getDouble() {
        return getDouble("", -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public double getDouble(String prompt) {
        return getDouble(prompt, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public double getDouble(String prompt, double min, double max) {
        while (true) {
            if (!prompt.isEmpty()) {
                System.out.print(prompt);
            }

                String input = scanner.nextLine().trim();
                double inputvalue = Double.parseDouble(input);

                if (inputvalue >= min && inputvalue <= max) {
                    return inputvalue;
                }
                else if(inputvalue < min || inputvalue > max) {
                    System.out.printf("输入超出范围 [%.2f - %.2f]，请重新输入: ", min, max);
                }
                else {
                System.out.print("输入无效，请输入数字: ");
            }
        }
    }

    public int getTotal() {
        return getTotal("请输入总数: ", 0, Integer.MAX_VALUE);
    }

    public int getTotal(String prompt, int minValue, int maxValue) {
        return getInt(prompt, minValue, maxValue);
    }

    public int getTotal(String prompt, int minValue) {
        return getTotal(prompt, minValue, Integer.MAX_VALUE);

    }

    public int getMenuChoice (String title, List < String > options){
            return getMenuChoice(title, options, "请选择: ");
        }

    public  int getMenuChoice(String title, List<String> options, String prompt) {
        System.out.println("\n" + title);
        System.out.println("=".repeat(40));

        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, options.get(i));
        }

        System.out.println("=".repeat(40));

        return getInt(prompt, 1, options.size());
    }

}