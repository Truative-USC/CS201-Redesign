package objects;

import java.util.ArrayList;
import java.util.List;

public class StaffMember {
	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<TimePeriod> getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(List<TimePeriod> officeHours) {
		this.officeHours = officeHours;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	private Integer id;
	private Name name;
	private String email;
	private String image;
	private String phone;
	private String office;
	private List<TimePeriod> officeHours;

	public StaffMember() {
		officeHours = new ArrayList<>();
	}

	public String getJobType() {
		return type;
	}

	public Integer getID() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getImage() {
		return image;
	}

	public String getOffice() {
		return office;
	}

	public List<TimePeriod> getOH() {
		return officeHours;
	}
}
