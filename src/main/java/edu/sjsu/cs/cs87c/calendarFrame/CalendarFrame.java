package edu.sjsu.cs.cs87c.calendarFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (C) 2001, 2023 Elton R Sanchez, All Rights Reserved
 *
 * ORIGINAL START DATE: 10/25/2001
 * ORIGINAL END DATE: 10/29/2001
 * REVISION 1.1 DATE: 1/17/2023
 */
public class CalendarFrame extends JFrame {
    PrintableCalendar calInstance;

    JComboBox monthField;
    JTextField yearField;

    CalendarPanel calPanel;
    JPanel controls;

    public static int INIT_YEAR = 2001;
    public static int INIT_MONTH = 0;

    /**
     * Creates a frame containing a panel for two GUI components at the top for setting the month
     * and date and a panel for painting the corresponding calendar.
     */
    public CalendarFrame() {
        final String TITLE = "CalendarFrame";
        final int WIDTH = 640;
        final int HEIGHT = 480;
        final int START_X = 100;
        final int START_Y = 100;

        this.setSize(WIDTH, HEIGHT);
        this.setLocation(START_X, START_Y);
        this.setTitle(TITLE);

        // set calendar to default date
        this.calInstance = new PrintableCalendar();
        this.calInstance.set(INIT_YEAR, INIT_MONTH, 1);

        // setup panel for controls on the top part of the screen
        this.controls = new JPanel();
        this.controls.setBackground(Color.WHITE);

        // SETUP GUI COMPONENTS

        this.monthField = new JComboBox();
        Collection<String> months = Arrays.stream(Month.values()).map(Month::toString).collect(Collectors.toList());
        for (String s : months) {
            this.monthField.addItem(s);
        }
        this.monthField.setEditable(true);

        // REGISTER LISTENERS FOR COMPONENTS

        this.yearField.addActionListener(e -> {
            int monthNum = calInstance.get(Calendar.MONTH);
            int yearNum = Integer.parseInt(yearField.getText().trim());
            calInstance.set(yearNum, monthNum, 1);
            calPanel.repaint();
        });
        this.monthField.addActionListener(e -> {
            int yearNum = calInstance.get(Calendar.YEAR);

            String selectedMonthStr = (String) monthField.getSelectedItem();

            int selectedMonth = -1;
            for (Month month : Month.values()) {
                if (month.toString().equals(selectedMonthStr)) {
                    selectedMonth = month.getValue();
                }
            }

            if (selectedMonth < 0) {
                String err = "Bad month redefinition; no changes made";
                System.out.println("CalenderFrame.monthField.actionListener: " + err);
            } else {
                calInstance.set(yearNum, selectedMonth, 1);
            }
        });

        // Add UI components to panel CONTROLS

        this.controls.add(monthField);
        this.controls.add(yearField);

        // Get the calendar and panel CALENDAR PANEL

        Container contentPane = this.getContentPane();
        this.calPanel = new CalendarPanel(calInstance);

        // add panels to the content pane
        contentPane.add(controls, BorderLayout.NORTH);
        contentPane.add(calPanel, BorderLayout.CENTER);
    }
}
