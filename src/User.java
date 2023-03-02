import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Serializable {
    /**
     *  用户类
     *  String name
     *  String password
     *  ArrayList<Dairy> lists;
     */
    private String name = "";
    private String password = "";
    private ArrayList<Dairy> lists = new ArrayList<>();

    public User() {
    }

    public User(String name, String password, ArrayList<Dairy> lists) {
        this.name = name;
        this.password = password;
        this.lists = lists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Dairy> getLists() {
        return lists;
    }

    public void setLists(ArrayList<Dairy> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", lists=" + lists +
                '}';
    }

    /**
     *  将自身写入本地文件
     */
    public boolean save() {
        boolean res = false;
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name + ".txt"))) {
            oos.writeObject(this);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从本地文件导入
     * @param name
     * @return
     */
    public boolean load(String name) {
        boolean res = false;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name + ".txt"))) {
            User user = (User) ois.readObject();
            this.setName(user.getName());
            this.setPassword(user.getPassword());
            this.setLists(user.getLists());
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 创建日志
     * @return
     */
    public boolean createDairy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的日记内容(输入 -1 结束):");
        String content = new String();
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.equals("-1")) {
                break;
            }
            content += line;
            content += "\n";
        }

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String dataTime = dateTime.format(formatter);
        Dairy dairy = new Dairy(dataTime, content);
        lists.add(dairy);
        // 对日记进行保存
        if (this.save()) {
            System.out.println("日记保存成功！");
            System.out.println("请输入 Enter 返回上一级");
            scanner.nextLine();
            return true;
        } else {
            System.out.println("日记保存失败！");
            System.out.println("请输入 Enter 返回上一级");
            scanner.nextLine();
            return false;
        }
    }

    /**
     * 查看日志
     */
    public void showDairy() {
        if (lists.isEmpty()) {
            System.out.println("当前日志目录为空！请返回上一级创建日志吧~");
        } else {
            for (int i = 0; i < lists.size(); i++) {
                System.out.println("================Dairy " + i + "==================");
                System.out.println(lists.get(i).getDate());
                System.out.println(lists.get(i).getContent());
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入 Enter 键返回上一页");
        String a = scanner.nextLine();
    }

    /**
     * 管理日志
     */
    public void manageDairy() {
        if (lists.isEmpty()) {
            System.out.println("当前日志目录为空！请返回上一级创建日志吧~");
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入 Enter 返回上一页");
            scanner.nextLine();
        } else {
            System.out.println("==========" + "日志列表" + "==========");
            for (int i = 0; i < lists.size(); i++) {
                System.out.println(i + ":" + lists.get(i).getDate());
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入你想要删除的日志序号(输入 -1 返回):");
            int choice = scanner.nextInt();
            while (choice != -1 && !(choice >= 0 && choice < lists.size())) {
                System.out.println("您输入的序号有误，请重新输入");
                choice = scanner.nextInt();
            }
            if (choice != -1) {
                lists.remove(choice);
                System.out.println("删除成功！");
                save();
            }
        }

    }
}
