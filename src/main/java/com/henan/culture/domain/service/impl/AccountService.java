package com.henan.culture.domain.service.impl;

import com.henan.culture.cache.AccountCacheManager;
import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.dto.PlayerDTO;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.repository.WxAccountRepository;
import com.henan.culture.domain.service.IAccountService;
import com.henan.culture.domain.service.IPlayerService;
import com.henan.culture.domain.service.WxAppletService;
import com.henan.culture.domain.vo.Code2SessionResponse;
import com.henan.culture.infrastructure.config.jwt.JwtConfig;
import com.henan.culture.infrastructure.util.HttpUtil;
import com.henan.culture.infrastructure.util.JSONUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:01
 **/
@Service
public class AccountService implements IAccountService {

    @Resource
    private RestTemplate restTemplate;

    @Value("${wx.applet.appid}")
    private String appid;

    @Value("${wx.applet.appsecret}")
    private String appSecret;

    @Resource
    private WxAccountRepository wxAccountRepository;

    @Resource
    private IPlayerService playerService;

    @Override
    public PlayerDTO userLogin(CodeDTO dto) {
        //1 . code2session返回JSON数据
        String resultJson = code2Session(dto.getCode());
        //2 . 解析数据
        Code2SessionResponse response = JSONUtil.toJavaObject(resultJson, Code2SessionResponse.class);
        if (!response.getErrcode().equals("0"))
            throw new AuthenticationException("code2session失败 : " + response.getErrmsg());
        else {
            //3 . 先从本地数据库中查找用户是否存在
            WxAccount wxAccount = AccountCacheManager.getInstance().getAccount(response.getOpenid());
            if (wxAccount == null) {
                wxAccount = new WxAccount();
                wxAccount.setWxOpenid(response.getOpenid());    //不存在就新建用户
            }
            //4 . 更新sessionKey和 登陆时间
            wxAccount.setSessionKey(response.getSession_key());
            wxAccount.setName(dto.getName());
            wxAccountRepository.save(wxAccount);
            // 检查玩家
            Player player = playerService.checkAccountPlayer(wxAccount);
            PlayerDTO playerDTO = player.buildDTO();
            return playerDTO;
        }
    }


    private String code2Session(String jsCode) {
        String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("secret", appSecret);
        params.add("js_code", jsCode);
        params.add("grant_type", "authorization_code");
        URI code2Session = HttpUtil.getURIwithParams(code2SessionUrl, params);
        return restTemplate.exchange(code2Session, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();
    }
}
