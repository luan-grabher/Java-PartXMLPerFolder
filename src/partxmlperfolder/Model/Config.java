package partxmlperfolder.Model;

import fileManager.FileManager;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public static void getFileExemple() {
        String textExemple = FileManager.getText(".\\exemplo-PartXMLPerFolder.csv");
        FileManager.save(new File(System.getProperty("user.home")) + "\\Desktop\\Exemplo Separar XML por pasta(Abra como texto).csv", textExemple);
    }

    public static Map<String, String> getConfigFilters(File file) {
        Map<String, String> filters = new HashMap<>();

        String[] lines = FileManager.getText(file).split("\r\n");

        for (String line : lines) {
            //Se não começar com # (comentários)
            if (!line.startsWith("#")) {
                //maximo 2 colunas
                String[] cols =  line.split(";", 2);
                
                //Se tiver duas colunas
                if(cols.length == 2){
                    filters.put(cols[1], cols[0]);
                }
            }
        }

        return filters;
    }
}
