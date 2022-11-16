import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MarkupTagEraser {

    public static void main(String[] args) {

        // Layout
        JFrame frame = new JFrame("Markup Tag Eraser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(430,100);

        JPanel pathPanel = new JPanel();
        JLabel filePathLabel = new JLabel("File Path");
        JTextField filePathTextField = new JTextField(31);
        pathPanel.add(filePathLabel);
        pathPanel.add(filePathTextField);

        JPanel tagPanel = new JPanel();
        JLabel tagNameLabel = new JLabel("Tag Name");
        JTextField tagNameTextField = new JTextField(20);
        tagNameTextField.setText("FeaturePot");
        JButton removeTagButton = new JButton("Remove Tags");
        tagPanel.add(tagNameLabel);
        tagPanel.add(tagNameTextField);
        tagPanel.add(removeTagButton);

        frame.getContentPane().add(BorderLayout.CENTER, pathPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, tagPanel);
        frame.setVisible(true);

        // Functions
        removeTagButton.addActionListener(e -> eraseTags(filePathTextField.getText(), tagNameTextField.getText()));

    }

    public static void eraseTags(String filePath, String tagName){
        String[] pathParts = filePath.split("\\.");
        String pathToResult = pathParts[0] + "-without-" + tagName + "." + pathParts[1];
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathToResult))) {
            String line;
            while((line = br.readLine()) != null){
                if(!line.contains("<" + tagName)){
                    bw.write(line);
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
