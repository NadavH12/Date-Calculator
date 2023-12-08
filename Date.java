public class Date {
    //Class constant String array that holds the names of the months of the year
    public static final String[] NAMES = {"N/A","January","February","March","April",
                                          "May","June","July","August","September","October","December"};
                                          
    //Class constant int array that holds the days in each month for the months of the year
    public static final int[] DAYS_IN_MONTHS = {0,31,28,31,30,31,30,31,31,30,31,30,31};

    //Private Fields of Date Objects
    private int currentYear;
    private int currentMonth;
    private int currentDay;

    //Constructors    
    //Date Object Custom Constructor
    //Fields set to inputted values. Checks if input is valid before setting fields, throws IllegalArgumentException for invalid input
    public Date(int year, int month, int day) {
        boolean isValid = isValidDate(year, month, day);
        if (!isValid) {
            throw new IllegalArgumentException();
        }
        currentYear = year;
        currentMonth = month;
        currentDay = day;
    }
    
    //Date Object Default Constructor
    //Sets fields to the date January, 1, 1970
    public Date() {
        this(1970, 1, 1);
    }


    //SetDate Mutator   
    //Sets fields to inputted values. Checks if input is valid before setting fields, throws IllegalArgumentException for invalid input
    public void setDate(int year, int month, int day) {
        if (!isValidDate(year, month, day)) {
            throw new IllegalArgumentException();
        }
        currentYear = year;
        currentMonth = month;
        currentDay = day;
    }


    //addDays Method
    //Takes a number of days as input and adjusts the Date object by the inputted number of days
    //method can take positive and negative values and adjust Date object forwards or backwards
    public void addDays(int days) {
        if (days > 0) {
            for (int i = 1; i <= days; i++) {
                nextDay();
            }
        }
        if (days < 0) {
            for (int i = 0; i > days; i--) {
                prevDay();
            }
        }
    }


    //addWeeks Method
    //Takes a number of weeks as input and adjusts the Date object by the inputted number of weeks
    //method can take positive and negative values and adjust Date object forwards or backwards
    //functions by calling addDays 7 * the inputted value  
    public void addWeeks(int weeks) {
        addDays(7 * weeks);
    }


    //daysTo Methods
    //daysTo Static Method
    //takes two Date objects as parameters, calls helper methods to calculate the number
    //of days between the two Date objects and returns the calculated value
    public static int daysTo(Date other, Date thisDate) {
        int days = daysYearTo(other, thisDate);
        days += daysMonthTo(other, thisDate, days);
        days += daysDayTo(other, thisDate);
        return (days);
    }

    //daysTo non-Static Method
    //takes Date object other as parameter and returns the number of days between inputted Date object
    //and the current Date object. Functions by calling Static daysTo method passing the current
    //and other Date objects
    public int daysTo(Date other) {
        return (daysTo(this, other));
    }


    //daysTo Helper methods
    //nextDay Method
    //increments the current Date object forwards by 1 day
    //method takes into account month and year boundries
    private void nextDay() {
        int daysInMonth = getDaysInMonth(currentMonth, currentYear);
        if (currentDay == 31 && currentMonth == 12) {
            currentYear++;
            currentMonth = 1;
            currentDay = 1;
        } else if (currentDay == daysInMonth) {
            currentDay = 1;
            currentMonth++;
        } else {
            currentDay++;
        }
    }
    
    //prevDay Method
    //decrements the current Date object backwards by 1 day
    //method takes into account month and year boundries
    private void prevDay() {
        if (currentDay == 1 && currentMonth == 1) {
            currentYear--;
            currentDay = 31;
            currentMonth = 12;
        } else if (currentDay == 1) {
            currentMonth--;
            currentDay = getDaysInMonth(currentMonth, currentYear);
        } else {
            currentDay--;
        }
    }

    //daysYearTo Method
    //Method takes 2 Date objects as parameters and calculates the number of days
    //needed to adjust the year field of Date object thisDate to match Date object other
    //method can calculate a positive or negative difference
    //method accounts for leap years
    private static int daysYearTo(Date other, Date thisDate) {
        int days = 0;
        if (thisDate.currentYear > other.currentYear) {
            for (int year = other.currentYear; year < thisDate.currentYear; year++) {
                days += daysInYear(year);
            }
        } else {
            for (int year = other.currentYear; year > thisDate.currentYear; year--) {
                days -= daysInYear(year);
            }
        }
        return days;
    }
    
    //daysMonthTo Method
    //Method takes 2 Date objects and an int of the number of years between them as parameters
    //Calculates the number of days needed to adjust the month field of Date object thisDate
    //to match Date object other
    //method can calculate a positive or negative difference, method uses parameter yearDiff
    //to figure out whether to to go backwards or forwards
    //method accounts for leap years   
    private static int daysMonthTo(Date other, Date thisDate, int yearDiff) {
        int days = 0;
        int year;
        if (yearDiff > 0) {
            year = thisDate.currentYear;
        } else {
            year = other.currentYear;
        }
        if (thisDate.currentMonth > other.currentMonth) {
            for (int month = other.currentMonth; month < thisDate.currentMonth; month++)
                days += daysInMonth(month, year);
        } else {
            for (int month = other.currentMonth; month > thisDate.currentMonth; month--)
                days -= daysInMonth(month, year);
        }
        return days;
    }
       
    //daysDayTo Method
    //Method takes 2 Date objects as parameters, and calculates the number of days needed
    //to adjust the day field of Date object thisDate to match Date object other
    private static int daysDayTo(Date other, Date thisDate) {
        return (thisDate.currentDay - other.currentDay);
    }

    //daysInYear Method
    //Static helper method that returns the number of days in a given year
    //determines if it's a leap year by calling is isLeapYearHelper
    private static int daysInYear(int year) {
        if (isLeapYearHelper(year)) {
            return 366;
        } else {
            return 365;
        }
    }
   
    //daysInMonth Method
    //Static helper method that returns the number of days in a given month in a given year
    //accounts for leap years by calling isLeapYearHelper
    private static int daysInMonth(int month, int year) {
        if (isLeapYearHelper(year) && month == 2) {
            return 29;
        } else {
            return DAYS_IN_MONTHS[month];
        }
    }


    //General Helper Methods
    //isValidDate Method
    //Helper method that ensures the validity of the fields of a date object
    //Ensures that no values before 1582/10/15, or after year 2999 can be set
    //Also ensures that only valid days can be set, ie only positive values and no larger number
    //of days than exist in a given month
    private boolean isValidDate(int year, int month, int day) {
        if (year > 2999) {
            return false;
        }
        if (year < 1582 || month <= 0 || day <= 0) {
            return false;
        }
        if (year == 1582 && ((month < 10) || ((month == 10) && day < 15))) {
            return false;
        }
        if ((month == 2) && (day > 28) && (!this.isLeapYear())) {
            return false;
        }
        if ((month == 2) && (day > 29) && (this.isLeapYear())) {
            return false;
        }
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day > 31)) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && (day > 30)) {
            return false;
        }
        return true;
    }

    //getDaysInMonth Method
    //helper method that returns the number of days in a given month in a given year
    //functions by calling the Static method daysInMonth
    private int getDaysInMonth(int month, int year) {
        return daysInMonth(month, year);
    }

    //isLeapYearHelper Method
    //Static helper method that returns whether a given year is a leap year
    private static boolean isLeapYearHelper(int year) {
        if (year % 4 == 0) {
            return (year % 100 != 0) || (year % 400 == 0);
        }
        return false;
    }
   
    //isLeapYear
    //helper method that returns whether a given year is a leap year
    //functions by calling the Static method isLeapYearHelper
    public boolean isLeapYear() {
        return isLeapYearHelper(currentYear);
    }


    //Accessors
    //getDay
    //returns the currentDay field of the current Date object
    public int getDay() {
        return currentDay;
    }

    //getMonth
    //returns the currentMonth field of the current Date object
    public int getMonth() {
        return currentMonth;
    }
    
    //getYear
    //returns the currentYear field of the current Date object
    public int getYear() {
        return currentYear;
    }

    //toString
    //returns the fields of the current Date object as a String in a formatted fashion 
    public String toString() {
        String month = "" + currentMonth;
        month = twoDigitConverter(month);
        String day = "" + currentDay;
        day = twoDigitConverter(day);
        return currentYear + "/" + month + "/" + day;
    }
    
    //longDate
    //returns the fields of the current Date object as a String in a formatted fashion
    //converts from currentMonth numerical field to name of month by using NAMES class constant String array
    public String longDate() {
        String day = "" + currentDay;
        day = twoDigitConverter(day);
        String month = NAMES[currentMonth];
        String year = "" + currentYear;
        return month + " " + day + ", " + year;
    }
    
    //twoDigitConverter Method
    //helps formatting single digit numbers when calling toString and longDate methods
    private String twoDigitConverter(String s) {
        if (s.length() <= 1) {
            s = "0" + s;
        }
        return s;
    }

}