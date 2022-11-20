package storePack;

import java.util.Calendar;

public class Date {

	private static final int JANUARY = 1;
	private static final int FEBRUARY = 2;
	private static final int DECEMBER = 12;
	private static final int DAYS_PER_WEEK = 7;
	public static final String AM = "Good morning";
	public static final String PM = "Good afternoon";
	public static final String EVE = "Good evening";

	private static final String[] DAY_NAMES = {
			// 0, 1, 2, 3,
			"Sunday", "Monday", "Tuesday", "Wednesday",
			// 4, 5, 6
			"Thursday", "Friday", "Saturday" };

	private static final int[] DAYS_PER_MONTH = { -1,
			// 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
			31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, };

	private int day;
	private int month;
	private int year;

	public Date(int day, int month, int year) throws IllegalArgumentException {
		if (!isValid(day, month, year))
			throw new IllegalArgumentException("Invalid date,check the values of day,month,year");
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public Date() {
		this(1, 1, 1900);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// true if this earlier than other
	public boolean compareTo(Date other) {
		if (other.year > this.year)
			return true;
		else if (other.year == this.year) {
			if (other.month > this.month)
				return true;
			else if (other.month == this.month) {
				if (other.day > this.day)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;
	}

	public boolean isValid(int day, int month, int year) {
		if (year < 0)
			return false;
		if (month < 1 || month > 12)
			return false;
		if (day < 1 || day > 31)
			return false;
		switch (month) {
		case 1:
			return true;
		case 2:
			return isLeap() ? day <= 29 : day <= 28;
		case 3:
			return true;
		case 4:
			return day < 31;
		case 5:
			return true;
		case 6:
			return day < 31;
		case 7:
			return true;
		case 8:
			return true;
		case 9:
			return day < 31;
		case 10:
			return true;
		case 11:
			return day < 31;
		case 12:
			return true;
		default:
			return true;
		}
	}

	public boolean isLeap() {
		return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
	}

	public int daysTo(Date other) {
		if (other.year < year || (other.year == year && other.month < month)
				|| (other.year == year && other.month == month && other.day < day)) {
			// other is before this date; reverse the call
			return -other.daysTo(this);
		} else {
			// other is after this Date; count days between
			Date copy = new Date(day, month, year);
			int count = 0;
			while (!copy.equals(other)) {
				copy.nextDay();
				count++;
			}
			return count;
		}
	}

	public boolean equals(Object o) {
		if (o instanceof Date) {
			Date other = (Date) o;
			return day == other.day && month == other.month && year == other.year;
		} else {
			return false;
		}
	}

	public void addDays(int days) {
		for (int i = 0; i < days; i++) {
			nextDay();
		}
	}

	public void nextDay() {
		day++;
		if (day > getDaysInMonth()) {
			// wrap to next month
			month++;
			day = 1;
			if (month > DECEMBER) {
				// wrap to next year
				year++;
				month = JANUARY;
			}
		}
	}

	private int getDaysInMonth() {
		int result = DAYS_PER_MONTH[month];
		if (month == FEBRUARY && isLeap()) {
			result++;
		}
		return result;
	}

	public String getDayOfWeek() {
		int dayIndex = 1;
		Date temp = new Date(1, JANUARY, 1753);
		while (!temp.equals(this)) {
			temp.nextDay();
			dayIndex = (dayIndex + 1) % DAYS_PER_WEEK;
		}

		return DAY_NAMES[dayIndex];
	}

	public static boolean checkDate(String date) {
		if (date == null)
			return false;
		else {
			int count = 0;
			for (int i = 0; i < date.length(); i++) {
				if (date.charAt(i) == '/')
					count++;
			}
			if (count == 2)
				return true;
			else
				return false;
		}
	}

	public static Date splitDate(String date) throws IllegalArgumentException {
		if (checkDate(date)) {
			String[] splitDate = date.split("/");
			if (splitDate.length == 3) {
				Date newpDate = new Date(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]),
						Integer.parseInt(splitDate[2]));
				return newpDate;
			} else {
				throw new IllegalArgumentException("Invalid date");
			}
		} else {
			throw new IllegalArgumentException("Invalid date");
		}
	}

	public static String decodePeriod(Calendar time) {
		String decodedPeriod = null;
		int hour = time.get(Calendar.HOUR_OF_DAY);
		if (hour >= 18) {
			decodedPeriod = EVE;
		} else if (hour >= 12) {
			decodedPeriod = PM;
		} else {
			decodedPeriod = AM;
		}
		return decodedPeriod;
	}
}
