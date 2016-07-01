JPA加载，更新，删除

JUnit中：

private static EntityManager em=null;

@BeforeClass
public static void setUpBeforeClass() throws Exception {
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("jpaPU");
    em=emf.createEntityManager();
}

1.两种方法：

Person person = em.find(Person.class, 1);
已经经过泛型处理。find方法相当于Hibernate里面的get方法。如果没有找到相应数据，就返回null.

Person person = em.getReference(Person.class,2);
如果这条记录不存在，单单这句话执行不会出错（返回代理）。但当使用person.getName()时会报实体不存在的异常。
相当于Hibernate里面的load方法。而且这种方法是延迟加载的。只有当实际访问实体属性时才到数据库中去执行查询。

Person person = em.getReference(Person.class,1);
em.close();
System.out.println(person.getName());

此句话会报错，因为person的属性其实没有加载出来。

Person person=em.find(Person.class, 1);
em.close();
System.out.println(person.getName());

而这段可以正确执行，因为person在find的时候就已经获取了相应的属性。

2.更新：

先看JPA里面的实体的状态：

新建、托管、游离、删除

处于托管状态下的实体发生的任何改变当实体管理器关闭的时候都会自动更新到数据库。

注意要更新必须要开启事务

em.getTransaction().begin();
Person person = em.find(Person.class, 1);
em.close();
person.setName("Coffee");

以上是不能成功更新的。以下可以修改。

em.getTransaction().begin();
Person person = em.find(Person.class, 1);
person.setName("Coffee");
em.getTransaction().commit();
em.close();

em.getTransaction().begin();
Person person = em.find(Person.class, 1);
em.clear(); // 把实体管理器中的所有的实体都变成游离状态
person.setName("Coffee");
em.merge(person);
em.getTransaction().commit();
em.close();

em.getTransaction().begin();
Person person = em.find(Person.class, 1);
em.remove(person); // 操作的也必须是持久化状态，或者托管状态的对象
em.getTransaction().commit();
em.close();

以下代码会报错
em.getTransaction().begin();
Person person = new Person();
person.setId(1);
em.remove(person);
em.getTransaction().commit();
em.close();

java.lang.IllegalArgumentException: Removing a detached instance com.tazi.domin.Person#1


merge()也有persist()的作用
persist会把传进去的实体放到持久化上下文中，此时如果持久化上下文中有了这个实体，就会抛出javax.persistence.EntityExistsException，没有的话事务提交的时候把那个对象加进数据库中，如果数据库中已经存在了那个对象（那一行），就会抛出com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException；
而merge会在持久化上下文中生成传进去的实体的受管版本，如果已经有了受管版本，那也不会抛出异常，然后把那个受管的实体返回出来，事务提交的时候如果数据库中不存在那个对象（那一行），就把把那个受管的加进去，存在的话就替换掉原来的数据。merge是如果持久化上下文中有了受管版本，那就更新，没有就复制一份，返回受管的。

再次总结persist（1，2-3，4-5）：
（这里说的抛出的异常都是指对象（或者数据库中的行）重复的异常）
1 如果persist的是一个受管实体（即已经在上下文中），就不会抛出异常。
2 如果persist的是一个游离实体（即上下文中没有它），而上下文中又没有它的受管版本，数据库中也没有，也不会抛出异常，而会把这个实体写进数据库中。
3 如果persist的是一个游离实体（即上下文中没有），而上下文中又没有它的受管版本，数据库却有这个实体，那么EntityManager在persist它的时候不会抛出异常，但是事务提交的时候就会抛出异常：Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '7' for key 1；
4 如果persist的是一个游离实体（即上下文中没有），而上下文中却有它的受管版本，数据库中又没有这个实体，那么还是不会抛出异常，而是把它的受管版本加进去（不是那个游离的，是那个受管的！）（即，这种情况persist和没persist是一样的！）。
5 如果persist的是一个游离实体（即上下文中没有），而上下文中却有它的受管版本，数据库中也有了这个实体，那么EntityManager在persist它的时候就会抛出异常：javax.persistence.EntityExistsException 而merge就不会抛出什么对象重复的异常的了。。


@Transactional
public void delete(T entity) {
    Assert.notNull(entity, "The entity must not be null!");
    this.em.remove(this.em.contains(entity)?entity:this.em.merge(entity));
}
