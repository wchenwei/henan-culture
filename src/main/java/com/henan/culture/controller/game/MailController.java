package com.henan.culture.controller.game;

import com.henan.culture.domain.dto.PlayerDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 13:42
 **/
@RequestMapping("/mail")
@RestController
public class MailController {

    @RequestMapping("/list")
    public PlayerDTO list(){
        return null;
    }

}
