package com.cloudstudy.dto;

import java.util.ArrayList;

public class CalResultDto {

	private ArrayList<String> studentNameArray = new ArrayList<String>();
	private ArrayList<Double> gradeArray = new ArrayList<Double>();
	private ArrayList<Double> averageArray = new ArrayList<Double>();

	private ArrayList<EveryCourseGradeObject> everyCourseGradeArray = new ArrayList<EveryCourseGradeObject>();
	private ArrayList<ExpextAndRealityGradeObject> expextAndRealityGradeArray = new ArrayList<ExpextAndRealityGradeObject>();

	public ArrayList<String> getStudentNameArray() {
		return studentNameArray;
	}

	public void setStudentNameArray(ArrayList<String> studentNameArray) {
		this.studentNameArray = studentNameArray;
	}

	public ArrayList<Double> getGradeArray() {
		return gradeArray;
	}

	public void setGradeArray(ArrayList<Double> gradeArray) {
		this.gradeArray = gradeArray;
	}

	public ArrayList<Double> getAverageArray() {
		return averageArray;
	}

	public void setAverageArray(ArrayList<Double> averageArray) {
		this.averageArray = averageArray;
	}

	public ArrayList<EveryCourseGradeObject> getEveryCourseGradeArray() {
		return everyCourseGradeArray;
	}

	public void setEveryCourseGradeArray(ArrayList<EveryCourseGradeObject> everyCourseGradeArray) {
		this.everyCourseGradeArray = everyCourseGradeArray;
	}

	public ArrayList<ExpextAndRealityGradeObject> getExpextAndRealityGradeArray() {
		return expextAndRealityGradeArray;
	}

	public void setExpextAndRealityGradeArray(ArrayList<ExpextAndRealityGradeObject> expextAndRealityGradeArray) {
		this.expextAndRealityGradeArray = expextAndRealityGradeArray;
	}

}