package com.cloudstudy.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import com.cloudstudy.bo.Permission;
import com.cloudstudy.bo.Role;
import com.cloudstudy.dto.RoleDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.service.PermissionService;
import com.cloudstudy.service.RoleService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.shiro.token.UserToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 实现Realm,继承AuthorizingRealm，并重写doGetAuthorizationInfo(用于获取认证成功后的角色、权限等信息) 和
 * doGetAuthenticationInfo(验证当前登录的Subject)方法
 * 
 * @ClassName UserRealm
 * @author alfred
 * @Date 2018年1月19日 下午3:47:46
 * @version 1.0.0
 */
public class UserRealm extends AuthorizingRealm {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * Subject.login()执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		System.out.println("###【开始认证[SessionId]】" + SecurityUtils.getSubject().getSession().getId());

		UserToken token = (UserToken) authcToken;
		String loginAccount = token.getUserAccount();
		String loginPassword = token.getUserPassword();

		UserDto loginUserDto = userService.login(loginAccount, loginPassword);
		if (loginUserDto == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginUserDto, // 用户
				loginUserDto.getPassword(), // 账号
				ByteSource.Util.bytes(loginUserDto.getCredentialsSalt()), // salt=account+salt
				getName() // realm name
		);
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserDto loginUserDto = (UserDto) principals.getPrimaryPrincipal();
		if (loginUserDto == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		String loginAccount = loginUserDto.getAccount();
		HashSet<String> userRoleSet = new HashSet<String>();
		HashSet<String> userPermissionSet = new HashSet<String>();
		// 从数据库中获取当前登录用户的详细信息
		loginUserDto = userService.findUserByAccount(loginAccount);
		if (null != loginUserDto) {
			// 获取当前用户下所有ACL权限列表 待续。。。
			// 获取当前用户下拥有的所有角色列表
			List<RoleDto> roles = roleService.findRoleByUserNo(loginUserDto.getNo());
			for (int i = 0; i < roles.size(); i++) {
				String roleCode = roles.get(i).getCode();
				if (!StringUtils.isEmpty(roleCode)) {
					userRoleSet.add(roleCode);
				}

				List<Permission> userTempPermissions = new ArrayList<Permission>();
				userTempPermissions = permissionService.findByRoleId(roles.get(i).getId());
				for (int j = 0; j < userTempPermissions.size(); j++) {
					String permissionCode = userTempPermissions.get(j).getCode();
					if (!StringUtils.isEmpty(permissionCode)) {
						userPermissionSet.add(permissionCode);
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}

		try {
			System.out.println("#######获取角色：" + new ObjectMapper().writeValueAsString(userRoleSet));
			System.out.println("#######获取权限：" + new ObjectMapper().writeValueAsString(userPermissionSet));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRoles(userRoleSet);
		authorizationInfo.addStringPermissions(userPermissionSet);
		return authorizationInfo;
	}

}