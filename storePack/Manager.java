package storePack;

public class Manager extends Employee {
	private int teamSize;
	private double bonus;
	private static final double BonusPercentage = 0.2;
	private static boolean busy;
	private static final String password="123";
	
	public Manager(String id, String name, String email, double salary, Date joinDate, int vacationDays,int teamSize,double bonus , Date birthdayDate) {
		super(id, name, email, salary, joinDate, vacationDays,birthdayDate);
		this.teamSize=teamSize;
		this.bonus=bonus;
	}
	public int getTeamSize() {
		return this.teamSize;
	}
	public double getbonus() {
		return this.bonus;
	}
	public void setTeamSize(int teamSize) {
		this.teamSize=teamSize;
	}
	public void setBonus() {
		this.bonus = this.getSalary() * Manager.BonusPercentage;
	}
	public void setSalary(double salary) {
		this.setSalary(salary+this.bonus);
	}
	public void isBusy(boolean flag) {
		busy = flag;
	}
	public String inviteToHearing(Employee employee, Date hearingDate) {
		return "Dear " + employee.getName() + " ,You have a hearing on " + hearingDate + " with " + this.getName() ;
	}
	public static String getPassword() {
		return Manager.password;
	}
	public String toString() {
		return super.toString() + "\nTeam size:" + this.getTeamSize() + "\nBonus:" + this.getbonus(); 
	}
}
