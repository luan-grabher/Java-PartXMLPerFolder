package partxmlperfolder;

import fileManager.FileManager;
import fileManager.Selector;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;
import partxmlperfolder.Model.Config;
import partxmlperfolder.Model.PartXML;
import static partxmlperfolder.Model.PartXML.filters;
import static partxmlperfolder.Model.PartXML.xmlFiles;

public class PartXMLPerFolder {

    public static void main(String[] args) {
        try {
            int option = JOptionPane.showOptionDialog(null, "Arquivo CSV de configuração necessário!\nVocê deseja obter o CSV de configuração de exemplo ou deseja escolher o arquivo pronto para uso?", "Obter csv ou escolher csv?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Obter CSV Exemplo", "Escolher CSV"}, "Obter CSV Exemplo");
            if (option == 0) {
                Config.getFileExemple();
                JOptionPane.showMessageDialog(null, "Arquivo de exemplo salvo na área de trabalho");
            } else {
                File file = Selector.selectFile(null, "CSV", ".csv");
                if (file != null && file.exists()) {
                    //Pega mada de filtros
                    filters = Config.getConfigFilters(file);

                    //Verifica se tem filtros
                    if (filters.size() > 0) {
                        //Escolhe pasta com XML
                        JOptionPane.showMessageDialog(null, "Escolha a seguir a pasta com os XMLs para separar:");

                        File xmlFolder = Selector.selectFolder(null);
                        if (xmlFolder != null && xmlFolder.exists()) {
                            xmlFiles = xmlFolder.listFiles();
                            
                            
                            PartXML.partXMLs();
                            JOptionPane.showMessageDialog(null, "XMLs salvos! Verifique as pastas");
                        } else {
                            throw new Error("Pasta inválida!");
                        }
                    } else {
                        throw new Error("Arquivo de configuração vazio, nada irá acontecer...");
                    }
                } else {
                    throw new Error("Arquivo inválido!");
                }
            }
        } catch (Exception e) {
            showException(e);
        } catch (Error e) {
            showException(new Exception(e));
        }
    }

    private static void showException(Exception e) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        FileManager.save(new File(System.getProperty("user.home")) + "\\Desktop\\JavaError.txt", getStackTrace(e));
        JOptionPane.showMessageDialog(null, "Arquivo de erro java salvo na área de trabalho 'JavaError.txt'. Use se precisar contatar o programador.", "Erro!", JOptionPane.ERROR_MESSAGE);
    }

    private static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}
