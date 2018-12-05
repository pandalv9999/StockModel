package view;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
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
