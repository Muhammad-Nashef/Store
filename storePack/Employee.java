package storePack;

import java.time.LocalDate;

public class Employee extends Person {

	private double Salary;
	private Date joinDate;
	private int vacationDays;

	public Employee(String id, String name, String email, double salary, Date joinDate, int vacationDays,Date birthdayDate) {
		super(id, name, email,birthdayDate);
		this.Salary = salary;
		this.joinDate = joinDate;
		this.vacationDays = vacationDays;
	}

	public double getSalary() {
		return Salary;
	}

	public void setSalary(double salary) throws IllegalArgumentException {
		if (salary > 0)
			this.Salary = salary;
		else
			throw new IllegalArgumentException("Salary must be 0 or more");
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public int getVacationDays() {
		return vacationDays;
	}

	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays;
	}

	public String checkForVacation(Date vacDateStart, Date vacDateEnd) throws IllegalArgumentException {
		Date todayDate = new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(),
				LocalDate.now().getYear());
		boolean VacStartDateVsTodayDate = vacDateStart.compareTo(todayDate);
		boolean VacEndDateVsTodayDate = todayDate.compareTo(vacDateEnd);
		boolean VacStartDateVsVacEndDate = vacDateStart.compareTo(vacDateEnd);
		if (!VacStartDateVsTodayDate && VacEndDateVsTodayDate && VacStartDateVsVacEndDate) {
			if (vacationDays > 0) {
				if (vacDateStart.daysTo(vacDateEnd) < vacationDays) {
					return this.getName() + " eligible to start vacation from " + vacDateStart.toString() + " "
							+ vacDateStart.getDayOfWeek() + " to: " + vacDateEnd.toString() + " "
							+ vacDateEnd.getDayOfWeek(); // add day name and date
				} else {
					return this.getName() + " not eligible to start vacation ,vacation days not enough";
				}
			} else
				return this.getName() + " vacation has been denied ,vacation days not enough";
		} else {
			if (VacStartDateVsTodayDate) {

				throw new IllegalArgumentException("Vacation start date must start at least from today");
			} else if (!VacStartDateVsVacEndDate) {
				throw new IllegalArgumentException("Vacation start date must be before end date");
			} else {
				throw new IllegalArgumentException("Vacation end date must start at least two days from today");
			}
		}
	}

	public String toString() {
		return super.toString() + "\n Salary: " + this.getSalary() + "\n Join date:" + this.getJoinDate()
				+ "\n Vacation days:" + this.getVacationDays();
	}
}
