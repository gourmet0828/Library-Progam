package Date;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(String dateStr) {
        // instantiate with user input(str);

        this.year = Integer.parseInt(dateStr.substring(0, 2));
        this.month = Integer.parseInt(dateStr.substring(2, 4));
        this.day = Integer.parseInt(dateStr.substring(4, 6));
    }

    public Date addDays(int add) {
        Date result = new Date(this.toString());
        if(result.year == 99&& result.month == 12 && result.day>=24){
            return new Date("991231");
        } else {
        for(int i = 0; i < add; i++) {
            result.addOneDay();
        }
        return result;
    }
    }

    private void addOneDay() {
        day++;
        if (day > daysInMonth(month, year)) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }
    private int daysInMonth(int month, int year) {
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return (month == 2 && isLeapYear(year)) ? 29 : daysInMonth[month];
    }
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int compareTo(Date other) {
        long thisDate = this.toAbsoluteDays();
        long otherDate = other.toAbsoluteDays();
        return (int) (otherDate - thisDate);
    }

    private long toAbsoluteDays() {
        int y = year;
        int m = month;
        if (m < 3) {
            y--;
            m += 12;
        }
        return 365L * y + y / 4 - y / 100 + y / 400 + (153 * m - 457) / 5 + day - 306;
    }

    @Override
    public String toString() {
        String dateStr = String.format("%2d%2d%2d", year, month, day).replace(" ", "0");
        return dateStr;
    }

    public String toStringPrint() {
        String dateStr = String.format("20%2d.%2d.%2d", year, month, day).replace(" ", "0");
        return dateStr;
    }
}
