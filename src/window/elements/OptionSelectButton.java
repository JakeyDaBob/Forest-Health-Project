package window.elements;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import generic.UtilMethods;

import java.awt.*;
import java.awt.event.*;
import window.WindowUtil;
import java.util.Arrays;

public class OptionSelectButton extends JLayeredPane
{
    String[] optionStrings;

    JButton button;
    int id;
    public int getId()
    {
        return id;
    }

    public OptionSelectButton(Object[] optionObjects, Rectangle rect)
    {
        setBounds(rect);

        optionStrings = Arrays.stream(optionObjects).map(Object::toString).toArray(String[]::new);

        button = WindowUtil.CreateButton("Placeholder", getWidth()/2, getHeight()/2, getWidth(), getHeight(), Color.black, Color.white);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                OnButtonPress();
            }
        });

        add(button, JLayeredPane.DEFAULT_LAYER);

        id = 0;
        setId(id);
    }

    void OnButtonPress()
    {
        id++;
        if (id >= optionStrings.length)
        {
            id = 0;
        }

        setId(id);
    }

    public void setId(int id)
    {
        this.id = id;
        button.setText(optionStrings[id]);
    }
}
