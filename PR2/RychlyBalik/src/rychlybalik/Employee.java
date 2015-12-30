/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik;

/**
 *
 * @author michal
 */
public class Employee {
	protected String name, surname;
	protected DrivingSkill skill;
	protected JobPosition position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JobPosition getPosition() {
		return position;
	}

	public void setPosition(JobPosition position) {
		this.position = position;
	}

	public DrivingSkill getSkill() {
		return skill;
	}

	public void setSkill(DrivingSkill skill) {
		this.skill = skill;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return name + " " + surname;
	}



	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Employee other = (Employee) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if ((this.surname == null) ? (other.surname != null) : !this.surname.equals(other.surname)) {
			return false;
		}
		if (this.skill != other.skill) {
			return false;
		}
		if (this.position != other.position) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 97 * hash + (this.surname != null ? this.surname.hashCode() : 0);
		hash = 97 * hash + (this.skill != null ? this.skill.hashCode() : 0);
		hash = 97 * hash + (this.position != null ? this.position.hashCode() : 0);
		return hash;
	}
}
