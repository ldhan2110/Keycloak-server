package com.kc.storage.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.kc.storage.model.UserInfo;

public interface UserInfoMapper {
	UserInfo findUserById(String usrId);

	UserInfo findUserByUsername(String username);

	List<UserInfo> searchForUser(@Param("search") String search, @Param("firstResult") Integer firstResult,
			@Param("maxResults") Integer maxResults);
}
