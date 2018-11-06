package com.management.xservice.ximpl;

import com.management.xservice.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl<T> implements BaseService<T> {
    @Override
    public int deleteByPrimaryKey(Object id) {
        return 0;
    }

    @Override
    public int insert(T po) {
        return 0;
    }

    @Override
    public int insertSelective(T po) {
        return 0;
    }

    @Override
    public T selectByPrimaryKey(Object id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(T po) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(T po) {
        return 0;
    }
}
