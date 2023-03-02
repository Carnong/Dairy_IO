import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Manager implements Serializable {
    /**
     *  日志系统管理员
     *  用户-密码 映射表
     *
     */
    public static Map<String,String> usertable = new HashMap<>();
    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_map.txt"))) {
            oos.writeObject(usertable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void load() {
        File file = new File("user_map.txt");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_map.txt"))) {
                usertable = (Map<String, String>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
