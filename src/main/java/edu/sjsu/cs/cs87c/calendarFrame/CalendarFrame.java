package edu.sjsu.cs.cs87c;

import javax.swing.*;
import java.awt.*;

public class CalendarFrame extends JFrame {
    CalendarPrinter printer;

    JComboBox monthField;
    JTextField yearField;

    CalendarPanel calPanel;
    JPanel controls;

    public static int INIT_YEAR = 2001;
    public static int INIT_MONTH = 0;
    public static int TEXT_FIELD_SIZE = 20;

    /**
     * Creates a frame containing a panel for two GUI components at the top for setting the month
     * and date and a panel for painting the corresponding calendar.
     */
    public CalendarFrame() {
        final String TITLE = "CalendarFrame";
        final int LENGTH = 640;
        final int WIDTH = 480;
        final int START_X = 100;
        final int START_Y = 100;

        this.setSize(LENGTH, WIDTH);
        this.setLocation(START_X, START_Y);
        this.setTitle(TITLE);

        // set calendar to default date
        this.printer = new CalendarPrinter();
        this.printer.set(INIT_YEAR, INIT_MONTH, 1);

        // setup panel for controls on the top part of the screen
        this.controls = new JPanel();
        this.controls.setBackground(Color.WHITE);

        // SETUP GUI COMPONENTS

        this.monthField = new JComboBox();
        this.monthField.addItem("January");
        this.monthField.addItem("February");
        this.monthField.addItem("March");
        this.monthField.addItem("April");
        this.monthField.addItem("May");
        this.monthField.addItem("June");
        this.monthField.addItem("July");
        this.monthField.addItem("August");
        this.monthField.addItem("September");
        this.monthField.addItem("October");
        this.monthField.addItem("November");
        this.monthField.addItem("December");

        // REGISTER LISTENERS FOR COMPONENTS

        this.yearField.addActionListener(new YearFieldListener());
        this.monthField.addActionListener(new MonthFieldListener());

        // Add UI components to panel CONTROLS

        this.controls.add(monthField);
        this.controls.add(yearField);

        // Get the calendar and panel CALENDARPANEL

        Container contentPane = this.getContentPane();
        this.calPanel = new CalendarPanel(printer);

        // add panels to the content pane
        contentPane.add(controls, BorderLayout.NORTH);
        contentPane.add(calPanel, BorderLayout.CENTER);
    }
}
