import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("****************日记管理系统**************");
            System.out.println("您好，欢迎来到日记管理系统!");
            System.out.println("请输入您的选项：");
            System.out.println("1. 创建日记");
            System.out.println("2. 查看日记");
            System.out.println("3. 管理日记");
            System.out.println("4. 退出系统");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            Operate.load();
            if (choice == 1) {
                Operate.createDairy();
            } else if (choice == 2) {
                Operate.checkDairy();
            } else if (choice == 3){
                Operate.manageDairy();
            }else if (choice == 4) {
                System.out.println("感谢您的使用，再见！");
                break;
            } else {
                System.out.println("输入有误,请重新输入:");
                continue;
            }
        }
    }
}