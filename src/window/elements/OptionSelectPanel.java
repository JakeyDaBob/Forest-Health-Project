package window.elements;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import java.awt.*;
import java.awt.event.*;
import window.WindowUtil;
import java.util.Arrays;

public class OptionSelectPanel extends JPanel
{
    final Color colorDeselected = new Color(0,0,0,64);
    final Color colorSelected = new Color(66, 173, 38, 128);

    JButtonWithData[] buttons;
    int idSelected = -1;

    public int getId()
    {
        return idSelected;
    }

    EventListenerList eventListenerList;

    public OptionSelectPanel(Object[] optionObjects, Rectangle rect, int buttonWidth, int buttonHeight, int fontSize)
    {
        super(null);

        setBounds(rect);
        String[] optionStrings = Arrays.stream(optionObjects).map(Object::toString).toArray(String[]::new);

        setOpaque(false);
        setBackground(new Color(0,0,0,0));

        eventListenerList = new EventListenerList();

        buttons = new JButtonWithData[optionStrings.length];

        for (int i = 0; i < optionStrings.length; i++)
        {
            String text = WindowUtil.FormatCodeString(optionStrings[i]);

            var button = WindowUtil.CreateButton(text, getWidth()/2, 50+((buttonHeight+20)*i), buttonWidth, buttonHeight, Color.black, Color.white);
            button.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, fontSize));
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    OnButtonPress(button);   
                }
            });

            button.setData(i);

            buttons[i] = button;
            add(button);
        }

        idSelected = -1;
        SetSelectedId(idSelected);
    }

    public void addActionListener(ActionListener l)
    {
        listenerList.add(ActionListener.class, l);
    }

    void OnButtonPress(JButtonWithData button)
    {
        int id = button.getData();
        if (idSelected == id)
        {
            idSelected = -1;
        }
        else
        {
            idSelected = id;
        }

        SetSelectedId(idSelected);
    }

    public void SetSelectedId(int id)
    {
        for (int i = 0; i < buttons.length; i++)
        {
            boolean selected = i == id;
            Color color = selected ? colorSelected : colorDeselected;
            buttons[i].setBackground(color);
        }

        ActionListener[] listeners = listenerList.getListeners(ActionListener.class);
        ActionEvent ae = new ActionEvent(this, 0, "optionSelected");
        for (ActionListener listener : listeners)
        {
            listener.actionPerformed(ae);
        }
    }
    
}
