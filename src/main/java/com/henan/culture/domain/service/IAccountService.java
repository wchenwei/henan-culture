package com.henan.culture.domain.service;

import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.dto.PlayerDTO;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:01
 **/
public interface IAccountService {
    PlayerDTO userLogin(CodeDTO dto);
}
