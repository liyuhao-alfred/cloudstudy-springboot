package com.cloudstudy.mapper;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstudy.bo.Admin;
import com.cloudstudy.bo.example.AdminExample;

@CacheConfig(cacheNames = "admins")//配置了该数据访问对象中返回的内容将存储于名为users的缓存对象中，我们也可以不使用该注解，直接通过@Cacheable自己配置缓存集的名字来定义
public interface AdminMapper extends BaseMapper<Admin, AdminExample> {

	// 如果指定为 true，则方法调用后将立即清空所有缓存
	@CacheEvict(key = "#p0", allEntries = true)
	int deleteByPrimaryKey(String primarykey);

	@Cacheable(key = "#p0")
	Admin select(String primarykey);

	@Cacheable(key = "#p0")
	Admin selectByPrimaryKey(String primarykey);

	@CachePut(key = "#p0")
	int update(Admin record);

}