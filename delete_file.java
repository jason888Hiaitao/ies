import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class delete_file {
    public static void main(String[] args) {
        try {
            String filePath = "d:/WorkSpace/JavaProject/wms-ies-core/src/main/java/com/example/wmsiescore/dao/EtUserExamHistoryDao.java";
            Path path = Paths.get(filePath);
            
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("文件删除成功: " + filePath);
            } else {
                System.out.println("文件不存在: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("删除文件时出错: " + e.getMessage());
        }
    }
}