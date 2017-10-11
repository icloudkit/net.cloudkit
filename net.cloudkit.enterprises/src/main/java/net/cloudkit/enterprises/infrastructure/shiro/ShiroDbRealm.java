/*
 * Copyright (C) 2015 The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cloudkit.enterprises.infrastructure.shiro;

import com.google.common.base.Objects;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ShiroDbRealm.java
 * 身份（Principals）是Subject的“身份属性”，可以是任何与Subject相关的标识，比如名称（给定名称）、名字（姓或者昵称）、用户名、安全号码等等，当然像昵称这样的内容不能很好的对Subject进行独特标识，所以最好的身份信息（Principals）是使用在程序中唯一的标识--典型的使用用户名或邮件地址。
 * 最主要的身份
 * 虽然shiro可以使用任何数量的身份，Shiro还是希望一个程序精确地使用一个主要的身份--一个仅有的唯一标识Subject值。在多数程序中经常会是一个用户名、邮件地址或者全局唯一的用户ID。
 * 证明（Credentials）通常是只有Subject知道的机密内容，用来证明他们真正拥有所需的身份，一些简单的证书例子如密码、指纹、眼底扫描和X.509证书等。
 * 最常见的身份/证明是用户名和密码，用户名是所需的身份说明，密码是证明身份的证据。如果一个提交的密码和系统要求的一致，程序就认为该用户身份正确，因为其他人不应该知道同样的密码。
 * Authenticating对象
 * Subject验证的过程可以有效地划分分以下三个步骤：
 * 1.收集Subject提交的身份和证明；
 * 2.向Authenticating提交身份和证明；
 * 3.如果提交的内容正确，允许访问，否则重新尝试验证或阻止访问。
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:46:30
 */
public class ShiroDbRealm extends AuthorizingRealm {

    //    private static final String ALGORITHM = "MD5";

    private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;

//    @Resource
//    private AuthenticationService authenticationService;

    /**
     * 当用户进行访问链接时的授权方法
     * 获取认证信息(认证回调函数, 登录时调用).
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        //UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //logger.info("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        //
        //// 通过表单接收用户名
        //String username = token.getUsername();
        //if (username != null && !"".equals(username)) {
        //    // AuthenticationService authenticationService = ApplicationServiceFactroy.authenticationService();
        //    // 获取登录的用户认证信息
        //    // AuthUserDTO authUser = memberService.getAuthUser(username);
        //    if (authUser != null) {
        //
        //        if (authUser.isDisabled()) {
        //            throw new DisabledAccountException();
        //        }
        //        // 加盐值(SALT)
        //        byte[] salt = EncodeHelper.decodeHex(authUser.getSalt());
        //        // 设置认证信息进行验证
        //        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        //        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(new ShiroUser(authUser.getLoginName(), null), authUser.getPassword(), ByteSource.Util.bytes(salt), getName());
        //
        //        // this.setSession("user", user);
        //        return authcInfo;
        //    } else {
        //        // NoFoundAccountException
        //        throw new UnknownAccountException();
        //    }
        //}
        return null;
    }

    /**
     * 用户登录的认证方法
     * 获取授权信息(授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用).
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName());
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

        // AuthenticationService authenticationService = ApplicationServiceFactroy.authenticationService();
        // 获取登录的用户认证信息
        // AuthUserDTO authUser = authenticationService.getAuthUser(shiroUser.loginName);
        // User user = authenticationService.findUserByLoginName(shiroUser.loginName);

        List<String> roleList = new ArrayList<String>();
        List<String> permissionList = new ArrayList<String>();

        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

        // TODO 获取角色和权限信息
        /*
        for (Role role : user.getRoles()) {
            // 基于Role的权限信息
            // info.addRole(role.getName());
            roleList.add(role.getCode());
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                // 基于Permission的权限信息
                // simpleAuthorInfo.addStringPermission(permission.getCode());
                permissionList.add(permission.getCode());
            }
        }
        */

        // XXX 测试用 添加一个角色,不是配置意义上的添加,而是证明该用户拥有user角色
        simpleAuthorInfo.addRole("user");
        // 添加权限
        simpleAuthorInfo.addStringPermission("user:manage");

        simpleAuthorInfo.addRoles(roleList);
        simpleAuthorInfo.addStringPermissions(permissionList);

        return simpleAuthorInfo;

        // 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next();
        // String currentUsername = (String)super.getAvailablePrincipal(principals);

        // String username = (String) principals.fromRealm(getName()).iterator().next();
        // if (username != null) {
        //     // 查询用户授权信息
        //     Collection<String> pers = businessManager.queryPermissions(username);
        //     if (pers != null && !pers.isEmpty()) {
        //         SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //         for (String each : pers)
        //             simpleAuthorInfo.addStringPermissions(each);
        //
        //         return simpleAuthorInfo;
        //     }
        // }
        // return null;

        // ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
        // User user = UserService.findUserByLoginName(shiroUser.getLoginName());
        // // 基于Permission的权限信息
        // if (user != null) {
        //     SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //     for (Role role : user.getRoles()) {
        //         for (Permission permission : role.getPermisssions()) {
        //             info.addStringPermission(permission.getValue());
        //         }
        //     }
        //     return simpleAuthorInfo;
        // } else {
        //     return null;
        // }
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {

        // // MD5加密
        // HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
        // setCredentialsMatcher(matcher);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    // 密码是否一致则在HashedCredentialsMatcher的方法doCredentialsMatch中进行
    // TODO 可以重写assertCredentialsMatch进行不传密码认证
    
    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    @SuppressWarnings("unused")
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    /**
     * 自定义Authentication对象,使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {

        private static final long serialVersionUID = 423613100213753942L;

        /** 登录名称 */
        public String loginName;

        public String name;

        public Map<String, Object> otherDataMap;

        public ShiroUser(String loginName, String name) {
            this.loginName = loginName;
            this.name = name;
        }

        public ShiroUser(String loginName, String name, Map<String, Object> otherDataMap) {
            this.loginName = loginName;
            this.name = name;
            this.otherDataMap = otherDataMap;
        }

        public String getName() {
            return name;
        }

        public String getLoginName() {
            return loginName;
        }

        public Map<String, Object> getOtherDataMap() {
            return otherDataMap;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal />输出.
         */
        @Override
        public String toString() {
            return loginName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null) {
                if (other.loginName != null) return false;
            } else if (!loginName.equals(other.loginName)) return false;
            return true;
        }
    }
}
