package com.clockbone.cps.service;

import com.clockbone.cps.dao.addinfo.AddInfoMapper;
import com.clockbone.cps.pojo.AddInfo;
import com.clockbone.cps.pojo.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddService {

    @Autowired
    private AddInfoMapper addInfoMapper;



    public AddInfo getInfoByAID(String aid){
        return addInfoMapper.selectByAID(aid);
    }

    public int saveLog(String aid,String ip){
        return 1;
    }

    public int cpsRecord(LogRecord loginRecord){

        return 1;


    }

}
