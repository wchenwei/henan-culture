package com.henan.culture;

import cn.hutool.json.JSONUtil;
import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.service.IRankService;
import com.henan.culture.service.impl.AccountService;
import com.henan.culture.enums.RankType;
import com.henan.culture.utils.gson.GSONUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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
