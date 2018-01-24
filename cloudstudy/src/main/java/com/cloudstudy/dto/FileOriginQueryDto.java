package com.cloudstudy.dto;

import java.util.HashSet;

import com.cloudstudy.constant.SearchType;

@SuppressWarnings("unused")
public class FileOriginQueryDto {
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
	 * 文件大小
	 */
	private Integer fromFileSize;
	/**
	 * 文件大小
	 */
	private Integer toFileSize;

	/**
	 * 文件格式类型
	 */
	private String fileType;

	/**
	 * 关键字
	 */
	private String keyword;

	private PageDto pageDto;

	public HashSet<SearchType> getSearchTypeSet() {
		return searchTypeSet;
	}

	public void setSearchTypeSet(HashSet<SearchType> searchTypeSet) {
		this.searchTypeSet = searchTypeSet;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Integer getFromFileSize() {
		return fromFileSize;
	}

	public void setFromFileSize(Integer fromFileSize) {
		this.fromFileSize = fromFileSize;
	}

	public Integer getToFileSize() {
		return toFileSize;
	}

	public void setToFileSize(Integer toFileSize) {
		this.toFileSize = toFileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

}
