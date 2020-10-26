# MyBatisPlusDruid


采用SpringBoot快速构建集成MyBatisPlus和Druid监控，后续整合Redis

创建数据库user，数据库编码为UTF-8

执行user.sql文件，初始化数据

访问localhost：8080查看监控页面



待完成添加功能
- 分页查询
- 自动填充
- mybatisplus实现乐观锁
- 逻辑删除
- 性能分析
- 复杂条件查询





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


Mybatisplus实现乐观锁

- 悲观锁，顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。悲观锁适用于并发竞争很厉害，写比较多的操作。

- 乐观锁，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在提交更新的时候会判断一下在此期间别人有没有去更新这个数据。乐观锁适用于读多写少的应用场景，这样可以提高吞吐量。

这里只讲乐观锁，为了解决写数据时丢失更新问题而出现的一种解决方法。当多个人同时修改同一条记录，最后提交的把之前的提交数据覆盖，这就是丢失更新，为了防止出现这种情况，就可以采用乐观锁，在提交更新的时候会判断一下在此期间别人有没有去更新这个数据，如12306抢票

MybatisPlus实现原理：通过添加version（版本号）字段来判断是否对数据进行了修改，修改将version加一，比较新的version和原有的version是不是一样，一样的version才进行更新操作，更新完成后，version就会+1，这时候另外一个拿到数据想要更新的人，在比较version那里就会不同从而更新失败。


