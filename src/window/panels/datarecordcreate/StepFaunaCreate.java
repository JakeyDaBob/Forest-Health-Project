package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import window.WindowUtil;
import window.elements.*;

import datarecord.*;

public class StepFaunaCreate extends JLayeredPane
{
    JLabel titleLabel;
    JTextField dataField;
    OptionSelectButton optionRecordType;
    OptionSelectButton optionInteractionType;
    boolean edit;
    JButton deleteButton;

    FaunaRecord faunaRecord;
    public FaunaRecord getFaunaRecord()
    {
        return faunaRecord;
    }

    public Runnable onEnd;

    public StepFaunaCreate(JLayeredPane parent)
    {
        int width = (int)(parent.getWidth()/1.2);
        int height = (int)(parent.getHeight()/2);

        setBounds((parent.getWidth()/2)-(width/2), (parent.getHeight()/2)-(height/2),width, height);

        JPanel bgPanel = new JPanel(null);
        bgPanel.setBounds(0,0,getWidth(),getHeight());
        bgPanel.setBackground(Color.gray);
        add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        titleLabel = WindowUtil.CreateLabel("Title Holder", 0, 0, getWidth(), 50, Color.white);
        titleLabel.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, 26));
        add(titleLabel, JLayeredPane.MODAL_LAYER);

        dataField = WindowUtil.CreateTextField(getWidth()/2, 100, getWidth()/2, 50, Color.black, Color.white);
        add(dataField, JLayeredPane.MODAL_LAYER);

        optionRecordType = new OptionSelectButton(FaunaRecord.Type.values(), new Rectangle(0,150, getWidth(), 50));
        add(optionRecordType, JLayeredPane.MODAL_LAYER);

        optionInteractionType = new OptionSelectButton(FaunaRecord.InteractionType.values(), new Rectangle(0,210, getWidth(), 50));
        add(optionInteractionType, JLayeredPane.MODAL_LAYER);

        JButton buttonConfirm = WindowUtil.CreateButton("Confirm Fauna Record", getWidth()/2, getHeight()-120, getWidth()/2, 50, Color.black, Color.white);
        buttonConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (onEnd != null)
                {
                    faunaRecord = createFaunaRecord();
                    onEnd.run();
                    setVisible(false);
                }
            }
        });
        add(buttonConfirm, JLayeredPane.MODAL_LAYER);

        deleteButton = WindowUtil.CreateButton("DELETE", getWidth()/2, getHeight()-60, getWidth()/2, 50, Color.black, Color.red);
        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (onEnd != null)
                {
                    faunaRecord = null;
                    onEnd.run();
                    setVisible(false);
                }
            }
        });
        deleteButton.setVisible(false);

        add(deleteButton, JLayeredPane.MODAL_LAYER);
    }

    public void begin(boolean edit, FaunaRecord fr)
    {
        String title = (edit ? "EDIT" : "CREATE") + " Fauna Record";
        titleLabel.setText(title);

        dataField.setText("Animal Data Here");
        optionRecordType.setId(0);
        optionInteractionType.setId(0);

        setVisible(true);
        deleteButton.setVisible(edit);

        if (fr != null)
        {
            dataField.setText(fr.data);
            optionRecordType.setId(fr.type.ordinal());
            optionInteractionType.setId(fr.interactionType.ordinal());
        }
    }

    FaunaRecord createFaunaRecord()
    {
        String data = dataField.getText();
        FaunaRecord.Type type = FaunaRecord.Type.values()[optionRecordType.getId()];
        FaunaRecord.InteractionType interactionType = FaunaRecord.InteractionType.values()[optionInteractionType.getId()];

        FaunaRecord fr = new FaunaRecord(data, type, interactionType);
        return fr;
    }
}
