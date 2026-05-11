package com.ljc.textgame.ui;

import java.util.Scanner;

public class InputHelper {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("输入范围错误，请输入 " + min + " 到 " + max + " 之间的数字。");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入整数。");
            }
        }
    }
}