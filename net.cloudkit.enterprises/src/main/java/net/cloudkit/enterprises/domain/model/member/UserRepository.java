/*
 * Copyright (C) 2015 The Workbench Open Source Project
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
package net.cloudkit.enterprises.domain.model.member;

import org.springframework.data.jpa.repository.*;

import javax.persistence.LockModeType;
import java.util.List;
//import org.springframework.data.repository.RepositoryDefinition;

/**
 * UserRepository.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:54:23
 */
// @RepositoryDefinition(domainClass = User.class, idClass = Long.class)
// public interface UserRepository extends Repository<User, Long> {
// extends JpaRepository<User, Long> {
// extends CrudRepository<User, Long>
// CGLIB代理不允许 @Transactional
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    // CrudRepository<User, Long>,
    // PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
    // PagingAndSortingRepository<User, Long>

    // @Query("select u from User u where u.firstname like %?1")
    // in (?1)
    // Long[] ids
    // List<Long> ids
    // 本地SQL语句
    // @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?0", nativeQuery = true)
    // @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
    // @Query("select u from #{#entityName} u where u.lastname = ?1")
    // @Modifying @Query("update User u set u.firstname = ?1 where u.lastname = ?2")
    // @Transactional(timeout = 10)

    /**
     * 根据用户名查询
     *
     * @param username
     * @param email
     * @return
     */
    @Query("select u from User u where u.username = ?1 or u.email = ?2")
    public User findByUsernameOrEmail(String username, String email);

    @Query("select u from User u where u.email = ?1")
    public User findByEmail(String email);

    @Query("select u from User u where u.username = ?1")
    public User findByUsername(String username);

    @Query("select u from User u where u.mobile = ?1")
    public User findByMobile(String mobile);

    @Query("select u from User u where u.id = ?1")
    public User findById(Long id);

    @Modifying
    @Query("update User u set u.password = ?1 where u.username = ?2")
    public int setPasswordByUsername(String username);

    // @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    // @QueryHints(value = { @QueryHint(name = "name", value = "value")}, forCounting = false)
    // Page<User> findByLastname(String lastname, Pageable pageable);

    // @Modifying
    // @Query("delete from User u where u.active = false")
    // void deleteInactiveUsers();

    // Plain query method
    @Lock(LockModeType.READ)
    public List<User> findByNickname(String nickname);

    /*
     *//**
     * Saves given user.
     *
     * @param user to save
     * @return
     *//*
    User save(User user);

    *//**
     * Finds a user using given id.
     *
     * @param id Id
     * @return user if found, else {@code null}
     *//*
    @Query("select a from User a where a.id = ?1")
    User find(long id);

    *//**
     * Finds all user.
     *
     * @return All user.
     *//*
    @Query("select a from User a")
    List<User> findAll();

    /// 使用 @Modifying 将查询标识为修改查询

    *//**
     * Saves given user.
     *
     * @param user to save
     *//*
    void store(User user);*/

}
