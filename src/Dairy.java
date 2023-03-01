import java.io.*;
import java.sql.Time;
import java.util.Date;

public class Dairy implements Serializable{
    /**
     * 日记类
     *      日期时间
     *      内容
     */
    private String dateTime;

    private String content;

    public Dairy(String date, String content) {
        this.dateTime = date;
        this.content = content;
    }

    public Dairy() {
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String date) {
        this.dateTime = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Dairy{" +
                "date='" + dateTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
    public boolean save() {
        String path = this.dateTime + ".txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path));){
            bw.write(this.dateTime);
            bw.write(this.content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
