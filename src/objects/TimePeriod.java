package objects;

public class TimePeriod {
	private String day;
	public void setDay(String day) {
		this.day = day;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	private Time time;

	public String getDay() {
		return day;
	}

	public Time getTime() {
		return time;
	}
}
