package com.henan.culture;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IAccountService;
import com.henan.culture.domain.service.IRankService;
import com.henan.culture.domain.service.impl.AccountService;
import com.henan.culture.domain.service.impl.RankService;
import com.henan.culture.enums.RankType;
import com.henan.culture.infrastructure.gson.GSONUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroTest {

    @Resource
    private IRankService rankService;
    @Resource
    private AccountService accountService;

    /**
     * 加密测试
     */
    @Test
    public void encryption() {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
        System.out.println(obj);
    }


    @Test
    public void rankTest(){
        Player player = new Player();
        player.setId(1000000);
        player.setName("hao");
        rankService.updatePlayerRankForAdd(player, RankType.Score, 3);
        List<LeaderboardInfo> groupRanks = rankService.getGroupRanks(RankType.Score, 1);
        System.out.println(GSONUtils.ToJSONString(groupRanks));
    }

    @Test
    public void add(){
        WxAccount wxAccount = accountService.getWxAccount("chenw", "chenw");
        System.out.println("account:"+JSONUtil.toJsonStr(wxAccount));
        Player player = accountService.accountLogin(wxAccount);
        System.out.println(player.buildDTO());
    }


}
