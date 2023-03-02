import org.junit.Test;

import java.net.URLEncoder;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean res = true;
        while (true) {
            // 初始化管理静态类
            Manager.load();
            // 登录&注册界面
            User user = new User();
            user = loginAndRegister();
            // 进入用户界面
            res = userWelcome(user);
            Manager.save();
            if (res == false) {
                System.out.println("感谢您的使用，再见！");
                break;
            }
        }
    }

    private static boolean userWelcome(User user) {
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
            // 初始化日志管理对象
            while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
                System.out.println("输入有误！请重新输入（1-4）:");
                choice = scanner.nextInt();
            }
            if (choice == 1) {
                user.createDairy();
            } else if (choice == 2) {
                user.showDairy();
            } else if (choice == 3){
                user.manageDairy();
            }else if (choice == 4) {
                break;
            }
        }
        return false;
    }

    /**
     *  登录账户
     */
    private static User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****************用户登录****************");
        System.out.println("请输入您的名称:");
        String name = scanner.nextLine();
        while (!Manager.usertable.containsKey(name)) {
            System.out.println("用户不存在，请重新输入名称:");
            name = scanner.nextLine();
        }
        System.out.println("请输入您的密码:");
        String password = scanner.nextLine();
        while (!password.equals(Manager.usertable.get(name))) {
            System.out.println("密码错误，请重新输入:");
            password = scanner.nextLine();
        }
        System.out.println(name + "，欢迎您！");
        User user = new User();
        user.load(name);
        return user;
    }
    /**
     * 注册账户
     */
    private static User register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****************用户注册****************");
        System.out.println("请输入您的名称:");
        String name = scanner.nextLine();
        while (Manager.usertable.containsKey(name)) {
            System.out.println("用户已存在，请重新输入名称:");
            name = scanner.nextLine();
        }
        System.out.println("恭喜您，用户名可用！");
        System.out.println("请输入您的密码:");
        String password = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Manager.usertable.put(name, password);

        if (user.save()) {
            System.out.println(name + "，您已经注册成功，欢迎您的使用!");
            return user;
        } else {
            System.out.println("注册失败!请重新注册");
            return null;
        }
    }

    /**
     * 登录 & 注册 界面
     */
    private static User loginAndRegister() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****************日记管理系统**************");
        System.out.println("您好，欢迎来到日记管理系统!");
        System.out.println("请输入您的选项：");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("输入有误，请重新输入（1 或 2）:");
            choice = scanner.nextInt();
        }
        if (choice == 1) {
            return login();
        } else if (choice == 2) {
            return register();
        } else return null;
    }
}