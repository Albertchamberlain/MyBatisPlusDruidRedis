# MyBatisPlus,Druid,Redis


采用SpringBoot快速构建集成MyBatisPlus和Druid监控，整合Redis

创建数据库user，数据库编码为UTF-8

执行user.sql文件，初始化数据

访问localhost：8080查看监控页面

# MyBatis-Plus特性

- 无侵入：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- 损耗小：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- 强大的 CRUD 操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- 支持 Lambda 形式调用：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- 支持主键自动生成：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- 支持 ActiveRecord 模式：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- 支持自定义全局通用操作：支持全局通用方法注入（ Write once, use anywhere ）
- 内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- 内置分页插件：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- 分页插件支持多种数据库：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- 内置性能分析插件：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- 内置全局拦截插件：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

已经添加功能
- 分页查询
- 逻辑删除(增加字段)





-
分页过程分析：
> - 创建page对象：new Page<>(1,3); 参数表示当前页和每页显示记录数
> - 调用分页查询方法，将分页所有数据封装到page对象里面：selectPage(page,null);
> - 通过调用Mybatisplus插件提供的方法实现分页功能
>   - page.getCurrent()：当前页
>   - page.getRecords()：每页数据list集合
>   - page.getPages()：总页数
>   - page.getSize()：每页显示记录数
>   - page.getTotal()：总记录数
>   - page.hasNext()：是否有下一页
>   - page.hasPrevious()：是否有上一页


Mybatisplus实现乐观锁(添加字段)

- 悲观锁，顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。悲观锁适用于并发竞争很厉害，写比较多的操作。

- 乐观锁，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在提交更新的时候会判断一下在此期间别人有没有去更新这个数据。乐观锁适用于读多写少的应用场景，这样可以提高吞吐量。

这里只讲乐观锁，为了解决写数据时丢失更新问题而出现的一种解决方法。当多个人同时修改同一条记录，最后提交的把之前的提交数据覆盖，这就是丢失更新，为了防止出现这种情况，就可以采用乐观锁，在提交更新的时候会判断一下在此期间别人有没有去更新这个数据，如12306抢票

MybatisPlus实现原理：通过添加version（版本号）字段来判断是否对数据进行了修改，修改将version加一，比较新的version和原有的version是不是一样，一样的version才进行更新操作，更新完成后，version就会+1，这时候另外一个拿到数据想要更新的人，在比较version那里就会不同从而更新失败。

# Redis 部分
关于spring-redis
spring-data-redis针对jedis提供了如下功能：

1. 连接池自动管理，提供了一个高度封装的“RedisTemplate”类

2. 针对jedis客户端中大量api进行了归类封装,将同一类型操作封装为operation接口

ValueOperations：简单K-V操作
SetOperations：set类型数据操作
ZSetOperations：zset类型数据操作
HashOperations：针对map类型的数据操作
ListOperations：针对list类型的数据操作

3. 提供了对key的“bound”(绑定)便捷化操作API，可以通过bound封装指定的key，然后进行一系列的操作而无须“显式”的再次指定Key，即BoundKeyOperations：

BoundValueOperations
BoundSetOperations
BoundListOperations
BoundSetOperations
BoundHashOperations

4. 将事务操作封装，有容器控制。

5. 针对数据的“序列化/反序列化”，提供了多种可选择策略(RedisSerializer)

JdkSerializationRedisSerializer：POJO对象的存取场景，使用JDK本身序列化机制，将pojo类通过ObjectInputStream/ObjectOutputStream进行序列化操作，最终redis-server中将存储字节序列。是目前最常用的序列化策略。

StringRedisSerializer：Key或者value为字符串的场景，根据指定的charset对数据的字节序列编码成string，是“new String(bytes, charset)”和“string.getBytes(charset)”的直接封装。是最轻量级和高效的策略。

JacksonJsonRedisSerializer：jackson-json工具提供了javabean与json之间的转换能力，可以将pojo实例序列化成json格式存储在redis中，也可以将json格式的数据转换成pojo实例。因为jackson工具在序列化和反序列化时，需要明确指定Class类型，因此此策略封装起来稍微复杂。【需要jackson-mapper-asl工具支持】

OxmSerializer：提供了将javabean与xml之间的转换能力，目前可用的三方支持包括jaxb，apache-xmlbeans；redis存储的数据将是xml工具。不过使用此策略，编程将会有些难度，而且效率最低；不建议使用。【需要spring-oxm模块的支持】
如果你的数据需要被第三方工具解析，那么数据应该使用StringRedisSerializer而不是JdkSerializationRedisSerializer。

关于key的设计
key的存活时间：
无论什么时候，只要有可能就利用key超时的优势。一个很好的例子就是储存一些诸如临时认证key之类的东西。当你去查找一个授权key时——以OAUTH为例——通常会得到一个超时时间。 这样在设置key的时候，设成同样的超时时间，Redis就会自动为你清除。

关系型数据库的redis
1: 把表名转换为key前缀 如, tag: 2: 第2段放置用于区分区key的字段--对应mysql中的主键的列名,如userid 3: 第3段放置主键值,如2,3,4...., a , b ,c 4: 第4段,写要存储的列名 例：user:userid:9:username

Redis的数据类型
String字符串
string是redis最基本的类型，一个key对应一个value。
string类型是二进制安全的。意思是redis的string可以包含任何数据。比如jpg图片或者序列化的对象 。
string类型是Redis最基本的数据类型，一个键最大能存储512MB。

链表
redis列表是简单的字符串列表，排序为插入的顺序。列表的最大长度为2^32-1。

redis的列表是使用链表实现的，这意味着，即使列表中有上百万个元素，增加一个元素到列表的头部或尾部的操作都是在常量的时间完成。

可以用列表获取最新的内容（像帖子，微博等），用ltrim很容易就会获取最新的内容，并移除旧的内容。

用列表可以实现生产者消费者模式，生产者调用lpush添加项到列表中，消费者调用rpop从列表中提取，如果没有元素，则轮询去获取，或者使用brpop等待生产者添加项到列表中。



集合
redis集合是无序的字符串集合，集合中的值是唯一的，无序的。可以对集合执行很多操作，例如，测试元素是否存在，对多个集合执行交集、并集和差集等等。
我们通常可以用集合存储一些无关顺序的，表达对象间关系的数据，例如用户的角色，可以用sismember很容易就判断用户是否拥有某个角色。
在一些用到随机值的场合是非常适合的，可以用 srandmember/spop 获取/弹出一个随机元素。 同时，使用@EnableCaching开启声明式缓存支持，这样就可以使用基于注解的缓存技术。注解缓存是一个对缓存使用的抽象，通过在代码中添加下面的一些注解，达到缓存的效果。

ZSet 有序集合
有序集合由唯一的，不重复的字符串元素组成。有序集合中的每个元素都关联了一个浮点值，称为分数。可以把有序看成hash和集合的混合体，分数即为hash的key。

有序集合中的元素是按序存储的，不是请求时才排序的。



Hash-哈希
redis的哈希值是字符串字段和字符串之间的映射，是表示对象的完美数据类型。

哈希中的字段数量没有限制，所以可以在你的应用程序以不同的方式来使用哈希。


