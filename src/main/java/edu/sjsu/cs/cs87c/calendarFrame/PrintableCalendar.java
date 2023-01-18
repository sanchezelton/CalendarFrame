package edu.sjsu.cs.cs87c.calendarFrame;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class PrintableCalendar extends GregorianCalendar {

    public static final List<Integer> MONTHS_WITH_31_DAYS = Arrays.asList(
        Calendar.JANUARY,
        Calendar.MARCH,
        Calendar.MAY,
        Calendar.JULY,
        Calendar.AUGUST,
        Calendar.OCTOBER,
        Calendar.DECEMBER
    );
    public static final int FIELD_WIDTH = 6;
    static List<String> DAYS_OF_WEEK = null;
    public PrintableCalendar() {
        super();
        Collection<String> daysOfWeek = Arrays.stream(DayOfWeek.values()).map(DayOfWeek::toString).collect(Collectors.toList());
        PrintableCalendar.DAYS_OF_WEEK = daysOfWeek.stream().toList();
    }

    /**
     * For the current month, returns an Iterator that retrieves a string of characters for each call of
     * its Iterator.next() method, such that each result is a formatting string of the dates for a week
     * beginning on Sunday
     *
     * @return
     */
    public Iterator<String> getWeekIterator() {
        ArrayList<String> result = new ArrayList<>();

        // add first week
        int firstDayOfMonthDayOfWeek;
        int dayOfWeekSkipCount;
        if (this.get(DATE) == 1) {
            firstDayOfMonthDayOfWeek = this.get(DAY_OF_WEEK);
        } else {
            int oldDate = this.get(DATE);
            this.set(DATE, 1);
            firstDayOfMonthDayOfWeek = this.get(DAY_OF_WEEK);
            this.set(DATE, oldDate);
        }

        dayOfWeekSkipCount = firstDayOfMonthDayOfWeek - 1;
        result.add(this.getFirstWeekInMonthStr(dayOfWeekSkipCount, PrintableCalendar.FIELD_WIDTH));

        // print rest of weeks

        int currentMonth = this.get(MONTH);
        int daysInMonth = 30;
        if (ArrayUtils.contains(PrintableCalendar.MONTHS_WITH_31_DAYS.toArray(), currentMonth)) {
            daysInMonth++;
        } else if (currentMonth == Calendar.FEBRUARY) {
            daysInMonth = 28;
            if (this.isLeapYear(this.get(Calendar.YEAR))) {
                daysInMonth++;
            }
        }

        // start adding in rest of days
        int originalDate = this.get(Calendar.DATE);
        this.set(Calendar.DATE, DAYS_OF_WEEK.size() - dayOfWeekSkipCount);
        do {
            // each buffer instances is a week
            StringBuilder weekBuffer = new StringBuilder();
            int currentDate = this.get(Calendar.DATE);
            int dayOfWeek = this.get(Calendar.DAY_OF_WEEK);

            do {
                weekBuffer.append(" ");
                if (currentDate < 10) {
                    weekBuffer.append(" ");
                }
                weekBuffer.append(currentDate);

                this.set(Calendar.DATE, currentDate + 1);
                currentDate = this.get(Calendar.DATE);
                dayOfWeek = this.get(Calendar.DAY_OF_WEEK);
            } while (dayOfWeek != Calendar.SUNDAY || currentDate <= daysInMonth);

            result.add(weekBuffer.toString());
            if (currentDate + 1 <= daysInMonth) {
                this.set(Calendar.DATE, currentDate + 1);
            } else {
                break;
            }
        } while (this.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY);
        this.set(Calendar.DATE, originalDate);

        return result.iterator();
    }

    private String getFirstWeekInMonthStr(int dayOfWeekSkipCount, int fieldWidth) {
        StringBuilder buffer = new StringBuilder();
        int spacesToFill = PrintableCalendar.DAYS_OF_WEEK.size() - dayOfWeekSkipCount;

        buffer.append(" ".repeat(Math.max(0, dayOfWeekSkipCount)));

        // complete rest of columns
        int j; // counter for counting spaces between columns
        for (int i = 0; i < spacesToFill; i++) {
            buffer.append("  ");

            String dateStr = Integer.toString(i + 1);
            buffer.append(dateStr);
            if (i + 1 < spacesToFill) {
                for (j = 1; j <= (fieldWidth / 2); j++) {
                    buffer.append(" ");
                }
            }
        }

        return buffer.toString();
    }

    /**
     * Returns a string with the four digits of the calendar's current year.
     * @return
     */
    public String getYearStr() {
        return new SimpleDateFormat("yyyy").format(this.getTime());
    }

    /**
     * Returns the current calendar's formatted full name of the month
     * @return
     */
    public String getMonthName() {
        return new SimpleDateFormat("MMMM").format(this.getTime());
    }
    public ArrayList<String> toPrintForm() {
        // formatting and containers
        ArrayList<String> result = new ArrayList<>();

        // special chars
        final String WS = " ";
        final String COLUMN_SEPARATOR = StringUtils.repeat(" ", 3);
        final String newLine = "¥n";

        // get month and year for first line
        StringBuilder yearAndMonthBuffer = new StringBuilder();
        yearAndMonthBuffer
                .append(this.getMonthName())
                .append(WS)
                .append(this.getYearStr());
        result.add(yearAndMonthBuffer.toString());

        // add CR and LF
        result.add(newLine);

        // add header for days of week
        result.add(StringUtils.join(PrintableCalendar.DAYS_OF_WEEK, COLUMN_SEPARATOR));

        Iterator<String> weekIterator = this.getWeekIterator();
        while (weekIterator.hasNext()) {
            result.add(weekIterator.next());
        }
        return result;
    }
}
