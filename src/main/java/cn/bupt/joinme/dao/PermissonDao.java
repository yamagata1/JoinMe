package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class PermissonDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<String> getUserPermisson(String username) {
        if (username.equals("admin"))
            return new ArrayList<>(Arrays.asList("admin","user"));
        else
            return new ArrayList<>(Collections.singletonList("user"));
    }

    public List<String> getUrlPermisson(String url) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (url.equals("/user/create"))
            return null;
        if (pathMatcher.match("/admin/**", url))
            return new ArrayList<>(Arrays.asList("admin"));
        else if (pathMatcher.match("/user/**", url) ||
                pathMatcher.match("/orderrequest/**", url) ||
                pathMatcher.match("/order/**", url))
            return new ArrayList<>(Collections.singletonList("user"));
        else
            return null;
    }
}
