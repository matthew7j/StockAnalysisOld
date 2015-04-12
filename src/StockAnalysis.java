import javax.swing.JFileChooser;
import java.io.FileNotFoundException;

public class StockAnalysis
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        String selection = "";
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose a data location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selection = chooser.getSelectedFile().getPath();
            AnalysisEngine engine = new AnalysisEngine(selection);
        } else {
            System.out.println("No Selection ");
        }
    }
}

