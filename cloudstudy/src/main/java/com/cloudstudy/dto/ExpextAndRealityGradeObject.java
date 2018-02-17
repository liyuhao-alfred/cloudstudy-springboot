package com.cloudstudy.dto;

import java.util.ArrayList;

public class ExpextAndRealityGradeObject {
	private ArrayList<Integer> value = new ArrayList<Integer>();
	private String name;

	public ExpextAndRealityGradeObject(ArrayList<Integer> value, String name) {
		this.value = value;
		this.name = name;
	}

	public ArrayList<Integer> getValue() {
		return value;
	}

	public void setValue(ArrayList<Integer> value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}