import javax.swing.*;
import java.io.*;

public class MarkupTagEraserV2 {

    public static void main(String[] args) {

        // Frame
        JFrame frame = new JFrame("Markup Tag Eraser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(370, 160);

        // Visual Components
//        TODO: Menu Bar + Drag & Drop Area

        JPanel panelPath = new JPanel();
        JLabel labelFilePath = new JLabel("File Path");
        JTextField textFieldFilePath = new JTextField(30);
        panelPath.add(labelFilePath);
        panelPath.add(textFieldFilePath);

        JPanel panelTagName = new JPanel();
        JLabel labelTagName = new JLabel("Tag Name");
        JTextField textFieldTagName = new JTextField(29);
        textFieldTagName.setText("FeaturePot");
        JButton buttonRemoveTags = new JButton("Remove Tags");
        panelTagName.add(labelTagName);
        panelTagName.add(textFieldTagName);

        JRadioButton radioButtonSelfClosing = new JRadioButton("Self Closing Tag", true);
        JRadioButton radioButtonOpen = new JRadioButton("Open Tag");
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(radioButtonSelfClosing);
        radioButtonGroup.add(radioButtonOpen);
        JPanel panelButtons = new JPanel();
        panelButtons.add(radioButtonSelfClosing);
        panelButtons.add(radioButtonOpen);
        panelButtons.add(buttonRemoveTags);

        // Layout
        JPanel panelLayout = new JPanel();
        panelLayout.add(panelPath);
        panelLayout.add(panelTagName);
        panelLayout.add(panelButtons);

        frame.add(panelLayout);
        frame.setVisible(true);



    }

    public static void eraseSelfClosingTags(String filePath, String tagName){
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

    public static void eraseOpenTags(String filePath, String tagName){
        String[] pathParts = filePath.split("\\.");
        String pathToResult = pathParts[0] + "-without-" + tagName + "." + pathParts[1];
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathToResult))) {
            String line;
            while((line = br.readLine()) != null){
                if(line.contains("<" + tagName)){
                    eraseNestedTags(line, tagName, br);
                }
                else{
                    bw.write(line);
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void eraseNestedTags(String line, String tagName, BufferedReader br) throws IOException {
        while(!line.contains("</" + tagName + ">")){
            line = br.readLine();
            if(line.contains("<" + tagName)){
                eraseNestedTags(line, tagName, br);
            }
        }
    }
}
