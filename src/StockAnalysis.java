import javax.swing.JFileChooser;
import java.io.File;
import java.util.ArrayList;

public class StockAnalysis
{
    protected static ArrayList<AnalysisEngine> engines = new ArrayList<>();
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selection;
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose a data location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selection = chooser.getSelectedFile();
            find_files(selection);
        } else {
            System.out.println("No Selection ");
        }
    }

    private static void find_files(File root) {
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
                if (extension.equals("txt")){
                    AnalysisEngine e = new AnalysisEngine(file.getPath().substring(0, file.getPath().lastIndexOf('\\')));
                    engines.add(e);
                    break;
                }
            } else if (file.isDirectory()) {
                find_files(file);
            }
        }
    }
}

