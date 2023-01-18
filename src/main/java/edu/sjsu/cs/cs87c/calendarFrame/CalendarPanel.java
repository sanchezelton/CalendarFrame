package edu.sjsu.cs.cs87c.calendarFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * (C) 2001, 2023 Elton R Sanchez, All Rights Reserved
 *
 * ORIGINAL START DATE: 10/25/2001
 * ORIGINAL END DATE: 10/29/2001
 * REVISION 1.1 DATE: 1/17/2023
 */
public class CalendarPanel extends JPanel {
    private PrintableCalendar calInstance;

    public static final int INIT_MONTH = new GregorianCalendar().get(Calendar.MONTH);
    public static final int INIT_YEAR = new GregorianCalendar().get(Calendar.YEAR);
    public static final int LINESPACE_SIZE = 10;
    public static final int FONT_SIZE = 14;

    public CalendarPanel() {
        this.calInstance = new PrintableCalendar();
        this.calInstance.set(INIT_YEAR, INIT_MONTH, 1);
    }

    public CalendarPanel(PrintableCalendar original) {
        this.calInstance = original;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.WHITE);

        final int CALENDAR_X = 100;
        final int CALENDAR_Y = 100;

        // GET CALENDAR AND PRINT IT

        ArrayList<String> out = this.calInstance.toPrintForm();
        if (out == null) {
            System.out.println("No content in PrintableCalendar.toPrintForm result. Abnormal program termination.");
            System.exit(1);
        }

        // Prepare font for monospace type
        Font monospaced = new Font("Monospaced", Font.PLAIN, CalendarPanel.FONT_SIZE);
        g2.setFont(monospaced);
        int currentY = CALENDAR_Y;

        // Prepare iteration and printing of lines
        Iterator<String> it = out.iterator();
        String s;

        while (it.hasNext()) {
            s = it.next();
            g2.drawString(s, CALENDAR_X, currentY);
            currentY += CalendarPanel.LINESPACE_SIZE;
        }
    }
}
