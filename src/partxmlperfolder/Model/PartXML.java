package partxmlperfolder.Model;

import fileManager.FileManager;
import fileManager.StringFilter;
import fileManager.XML;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NodeList;

public class PartXML {

    public static Map<String, String> filters = null;
    public static File[] xmlFiles = null;

    /**
     * -- Se tiver 1 filtro encontrado
     * --- Cria pasta com o numero do produto(valor do mapa) se nao existir
     * --- Salva arquivo
     * -- Se tiver mais de 1 filtro
     * --- Cria pasta se não existir "Mais de 1 produto"
     * --- Salva arquivo
     * -- Se não
     * --- Cria pasta se não existir "Nenhum produto cadastrado"
     * --- Salva arquivo
     */
    public static void partXMLs() {
        for (File xmlFile : xmlFiles) {
            try {
                if (xmlFile.getName().endsWith(".xml")) {

                    XML xml = new XML(xmlFile);
                    NodeList products = xml.doc.getElementsByTagName("Discriminacao");

                    //Somente se tiver a discriminação
                    if (products.item(0) != null) {
                        String product = products.item(0).getTextContent();

                        List<String> foundFilters = new ArrayList<>();

                        filters.forEach((filter, name) -> {
                            StringFilter sf = new StringFilter(filter);
                            //Se tiver o filtro na Discriminacao
                            if (sf.filterOfString(product)) {
                                foundFilters.add(name);
                            }
                        });

                        if (foundFilters.size() == 1) {
                            saveFileOnFolder(xmlFile, foundFilters.get(0));
                        } else if (foundFilters.size() > 1) {
                            saveFileOnFolder(xmlFile, "Mais de 1 produto");
                        } else {
                            saveFileOnFolder(xmlFile, "Nenhum produto encontrado");
                        }
                    }
                }
            } catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }

    private static void saveFileOnFolder(File xmlFile, String newFolder) {
        // Cria pasta se não existir "Mais de 1 produto"
        File folder = new File(xmlFile.getParent() + "\\" + newFolder);
        folder.mkdirs();

        // Salva arquivo
        FileManager.save(folder, xmlFile.getName(), FileManager.getText(xmlFile));
    }
}
