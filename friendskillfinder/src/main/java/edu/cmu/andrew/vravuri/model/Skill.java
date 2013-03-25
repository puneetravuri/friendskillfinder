package edu.cmu.andrew.vravuri.model;

import java.util.ArrayList;
import java.util.List;

public class Skill {

	private String name;
	private int count;
	private List<String> person_ids;

	public Skill() {
		super();
		person_ids = new ArrayList<String>();
	}

	public Skill(String name, String person_id) {
		this.name = name;
		person_ids = new ArrayList<String>();
		person_ids.add(person_id);
		count = 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public List<String> getPerson_ids() {
		return person_ids;
	}

	public void setPerson_ids(List<String> person_ids) {
		this.person_ids = person_ids;
	}

	public void addPerson(String person_id) {
		person_ids.add(person_id);
		count++;
	}

}
