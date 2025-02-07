package com.server.gymServerApplication.modelView.map;

import com.server.gymServerApplication.entity.mysql.User;
import com.server.gymServerApplication.modelView.repon.LoginRepose;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ObjMap {

    ObjMap INSTANCE = Mappers.getMapper(ObjMap.class);

    LoginRepose loginRepose(User user);

}
