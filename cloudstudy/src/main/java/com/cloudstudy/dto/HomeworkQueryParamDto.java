package com.cloudstudy.dto;

import java.util.HashSet;

import com.cloudstudy.constant.SearchType;

@SuppressWarnings("unused")
public class HomeworkQueryParamDto {

	private HashSet<SearchType> searchTypeSet = new HashSet<SearchType>() {
		private static final long serialVersionUID = 1L;
		{
			searchTypeSet.add(SearchType.all);
			searchTypeSet.add(SearchType.adminName);
			searchTypeSet.add(SearchType.teacherName);
			searchTypeSet.add(SearchType.studentName);
			searchTypeSet.add(SearchType.courseName);
			searchTypeSet.add(SearchType.homeworkName);
			searchTypeSet.add(SearchType.no);
			searchTypeSet.add(SearchType.account);
			searchTypeSet.add(SearchType.phone);
			searchTypeSet.add(SearchType.email);
			searchTypeSet.add(SearchType.fileName);

		}
	};
	private Integer searchType;

	/**
	 * 时间开始
	 */
	private String fromTime;

	/**
	 * 时间结束
	 */
	private String toTime;

	/**
	 * 关键字
	 */
	private String keyword;

	private PageDto pageDto;
}
