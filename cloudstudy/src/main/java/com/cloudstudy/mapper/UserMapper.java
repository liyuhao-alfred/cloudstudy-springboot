package com.cloudstudy.mapper;

import com.cloudstudy.bo.User;
import com.cloudstudy.bo.example.UserExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "users") // 配置了该数据访问对象中返回的内容将存储于名为users的缓存对象中，我们也可以不使用该注解，直接通过@Cacheable自己配置缓存集的名字来定义
public interface UserMapper extends BaseMapper<User, UserExample> {
	long countByExample(UserExample example);

	// 如果指定为 true，则方法调用后将立即清空所有缓存
	@CacheEvict(key = "#p0", allEntries = true)
	int deleteByExample(UserExample example);

	int deleteByPrimaryKey(String no);

	int insert(User record);

	int insertSelective(User record);

	@Cacheable(key = "#p0")
	List<User> selectByExampleWithBLOBs(UserExample example);

	@Cacheable(key = "#p0")
	List<User> selectByExample(UserExample example);

	@Cacheable(key = "#p0")
	User selectByPrimaryKey(String no);

	@CachePut(key = "#p0")
	int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

	@CachePut(key = "#p0")
	int updateByExampleWithBLOBs(@Param("record") User record, @Param("example") UserExample example);

	@CachePut(key = "#p0")
	int updateByExample(@Param("record") User record, @Param("example") UserExample example);

	@CachePut(key = "#p0")
	int updateByPrimaryKeySelective(User record);

	@CachePut(key = "#p0")
	int updateByPrimaryKeyWithBLOBs(User record);

	@CachePut(key = "#p0")
	int updateByPrimaryKey(User record);
}