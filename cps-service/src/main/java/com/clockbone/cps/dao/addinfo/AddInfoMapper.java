package com.clockbone.cps.dao.addinfo;

import com.clockbone.cps.pojo.AddInfo;

public interface AddInfoMapper {

    AddInfo selectByAID(String aid);

}