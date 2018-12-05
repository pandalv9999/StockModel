package view;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represent the gui of a file chooser.
 */
public class FileChooser {

  /**
   * This method will initialize a file chooser and return the selected file's path.
   *
   * @return the selected file's path
   */
  public static String fileChooser() {
    final JFileChooser fChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "csv files", "csv");
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(new JLabel());
    File f;
    String res = "";
    if (retValue == JFileChooser.APPROVE_OPTION) {
      f = fChooser.getSelectedFile();
      res = f.getAbsolutePath();
    }
    return res;
  }
}
