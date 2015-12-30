package ukazkaMoznosti.syntaxHighLighting;

// A program illustrating the use of the SyntaxHighlighter class.
// Public domain, no restrictions, Ian Holyer, University of Bristol.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class HighlightEdit extends JFrame
{
    String filename;
    SyntaxHighlighter text;

    public static void main(String[] args)
    {
       if (args.length != 1)
        {
            System.err.println("Usage: java HighlightEdit filename");
            System.exit(1);
        }
        Runner runner = new Runner(args[0]);
        SwingUtilities.invokeLater(runner);
    }

    static class Runner implements Runnable
    {
        String filename;
        Runner(String f)
        {
            filename = f;
        }
        public void run()
        {
            HighlightEdit program = new HighlightEdit();
            program.display(filename);
        }
    }

    void display(String s)
    {
        filename = s;
        
        setTitle("HighlightEdit " + filename);
        addWindowListener(new Closer());
        Scanner scanner = new JavaScanner();
        text = new SyntaxHighlighter(24, 80, scanner);
        JScrollPane scroller = new JScrollPane(text);
        scroller.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Container pane = getContentPane();
        pane.add(scroller);
        pack();
        setVisible(true);

        try
        {
            text.read(new FileReader(filename), null);
        }
        catch (IOException err)
        {
            System.err.println(err.getMessage());
            //System.exit(1);
            this.setVisible(false);
        }
        // Workaround for bug 4782232 in Java 1.4
        text.setCaretPosition(1);
        text.setCaretPosition(0);
    }

    class Closer extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
          e.getWindow().setVisible(false);
        }
    }
}
