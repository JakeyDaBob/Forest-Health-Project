package window.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

import application.AppInfo;
import application.FileSystem;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

public class InfoPanel extends JLayeredPane
{
    public InfoPanel(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        Random random = new Random();
        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory("photos");
        String imageFileName = imagePaths[random.nextInt(imagePaths.length)];
        String imagePath = "photos/"+imageFileName;

        DrawImage image = new DrawImage(FileSystem.Resources.GetImage(imagePath));
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,200));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("Info", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        String info = "<html>";
        info += "<br>Welcome to the Forest Health Project!";
        
        info += "<br><br>The Forest Health Project is dedicated to enhancing the resilience and sustainability of Australia's vital forest ecosystems. By leveraging<br> cutting-edge technology, our platform empowers users to actively<br>participate in the monitoring of forest health, specifically focusing on burn activity and fire impact. Through real-time photo submissions and detailed burn reports, users contribute crucial data that helps to map, analyze, and respond to fire events across Australian forests.";
        
        info += "<br><br>This project aims to improve the early detection and ongoing assessment<br>of burns, providing key insights that will aid in preventing widespread damage and supporting recovery efforts. Our mission is to foster a collaborative network of environmental stewards, researchers, and conservationists, all working towards a common goal: protecting Australia’s forests from the ever-growing threat of wildfires.";
        
        info += "<br><br>With a focus on innovation, community engagement, and environmental preservation, the Forest Health Project aspires to become a cornerstone in national efforts to safeguard one of the world’s most unique and irreplaceable natural habitats. Together, we can help ensure the long-term health and sustainability of our forests for future generations.";

        info += "</html>";
        JLabel labelInfo = WindowUtil.CreateLabel(info, 20, 150, getWidth()-40, 500, Color.white);
        labelInfo.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 16));
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        labelInfo.setVerticalAlignment(SwingConstants.NORTH);
        add(labelInfo, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 100;
        JButton buttonInfo = WindowUtil.CreateButton("Back", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3.5*1.5))), (int)(getWidth()*0.6f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonInfo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.Menu);
            }
        });
        add(buttonInfo, JLayeredPane.MODAL_LAYER);
    }
}
