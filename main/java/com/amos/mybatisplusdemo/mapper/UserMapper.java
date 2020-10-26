package com.amos.mybatisplusdemo.mapper;

import com.amos.mybatisplusdemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Amos
 * @date 2020-10-26 20:17
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
