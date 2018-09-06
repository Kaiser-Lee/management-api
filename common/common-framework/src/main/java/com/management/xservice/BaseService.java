package com.management.xservice;

public interface BaseService<T> {

    //	@RedisEvict(type = Object.class)
    int deleteByPrimaryKey(Object id);

    //	@RedisEvict(type = Object.class)
    int insert(T po);

    //	@RedisEvict(type = Object.class)
    int insertSelective(T po);

    //    @RedisCache(type = Object.class, result = RESULT_TYPE_SINGLE)
    T selectByPrimaryKey(Object id);

    //    @RedisEvict(type = Object.class)
    int updateByPrimaryKeySelective(T po);

    //    @RedisEvict(type = Object.class)
    int updateByPrimaryKey(T po);

    /* *//** 全量查询 *//*
    List<T> selectByCondition();
    *//** 条件查询 *//*
    List<T> selectByCondition(T condition);

    int updateByIdsSelective(T po, List ids);*/

}
