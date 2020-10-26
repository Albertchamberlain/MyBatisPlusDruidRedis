DROP TABLE IF EXISTS USER;

CREATE TABLE USER
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    NAME VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

INSERT INTO user (id,name,age,email) VALUES
(1,'onestar',18,'onestar@qq.com'),
(2,'twostar',18,'twostar@qq.com'),
(3,'threestar',18,'threestar@qq.com'),
(4,'fourstar',18,'fourstar@qq.com'),
(5,'fivestar',18,'fivestar@qq.com');