import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Operate {
    static private ArrayList<Dairy> lists = new ArrayList<>();
    /**
     * 创建日记
     * @return
     */
    static public boolean createDairy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的日记内容:");
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
        if (save()) {
            System.out.println("日记保存成功！");
            return true;
        } else {
            System.out.println("日记保存失败！");
            return false;
        }
    }
    /**
     * 查看日记
     * @return
     */
    static public void checkDairy() {
        for (int i = 0; i < lists.size(); i++) {
            System.out.println("================Dairy " + i + "==================");
            System.out.println(lists.get(i).getDate());
            System.out.println(lists.get(i).getContent());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入Enter键返回上一页");
        String a = scanner.nextLine();
    }
    /**
     * 将新日志保存到lists中
     * @return
     */
    static public boolean save() {
        boolean res = false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lists.txt"))) {
            oos.writeObject(lists);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 初始化日志管理器
     */
    static public void load() {
        File file = new File("lists.txt");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lists.txt"))) {
                Object o = ois.readObject();
                lists = (ArrayList<Dairy>) o;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 管理日记
     */
    static public void manageDairy() {
        System.out.println("==========" + "日志列表" + "==========");
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(i + ":" + lists.get(i).getDate());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你想要删除的日志序号:");
        int choice = scanner.nextInt();

        while (choice < 0 && choice > lists.size()) {
            System.out.println("您输入的序号有误，请重新输入");
            choice = scanner.nextInt();
        }

        lists.remove(choice);
        System.out.println("删除成功！");
        save();
    }

}
