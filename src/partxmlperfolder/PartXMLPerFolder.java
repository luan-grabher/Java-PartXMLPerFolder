package partxmlperfolder;

import fileManager.FileManager;
import fileManager.Selector;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.swing.JOptionPane;
import partxmlperfolder.Model.Config;

public class PartXMLPerFolder {

    /**
     * -Pegar pasta com XMLs
     * -Percorrer arquivos XMLs da pasta
     * - Para cada arquivo
     * -- Pega texto do campo 'Discriminacao'
     * --
     * -- Percorre mapa de filtros
     * --- Se
     */
    public static void main(String[] args) {
        try {
            int option = JOptionPane.showOptionDialog(null, "Arquivo CSV de configuração necessário!\nVocê deseja obter o CSV de configuração de exemplo ou deseja escolher o arquivo pronto para uso?", "Obter csv ou escolher csv?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Obter CSV Exemplo","Escolher CSV"}, "Obter CSV Exemplo");
            if(option == 0){
                Config.getFileExemple();
                JOptionPane.showMessageDialog(null, "Arquivo de exemplo salvo na área de trabalho");
            }else{
                File file =  Selector.selectFile(null, "CSV", ".csv");
                if(file != null && file.exists()){
                    Map<String, String>  filters = Config.getConfigFilters(file);
                    System.out.println("");
                }else{
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
