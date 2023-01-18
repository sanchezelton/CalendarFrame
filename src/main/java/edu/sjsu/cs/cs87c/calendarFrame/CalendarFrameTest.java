package edu.sjsu.cs.cs87c.calendarFrame;

import javax.swing.*;

/**
 * (C) 2001, 2023 Elton R Sanchez, All Rights Reserved
 *
 * ORIGINAL START DATE: 10/25/2001
 * ORIGINAL END DATE: 10/29/2001
 * REVISION 1.1 DATE: 1/17/2023
 */
public class CalendarFrameTest {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        CalendarFrame frame = new CalendarFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}