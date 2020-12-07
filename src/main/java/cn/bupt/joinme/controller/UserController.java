package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.dao.UserTestDao;
import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.model.UserTest;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@BaseResponse
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserTestDao userTestDao;
    @Autowired
    private UserDao userDao;

    // Test only
//    @GetMapping("hello")
//    @ResponseBody
//    public String Hello()
//    {
//        return "hello world!";
//    }

    // Test only
//    @GetMapping("userTest")
//    @ResponseBody
//    public UserTest getUser(UserTest user)
//    {
//        userTestDao.saveUserTest(user);
//        return user;
//    }

    @GetMapping("/create")
    @ResponseBody
    public ResponseResult tryCreateUser() {
        User res = userDao.getUser();
        if (res != null)
            throw new BaseException(ResponseType.COMMON_FAIL);
        else
            return new ResponseResult(ResponseType.SUCCESS);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseResult createUser(@RequestBody User user) {
        User res = userDao.getUser();
        if (res != null)
            throw new BaseException(ResponseType.COMMON_FAIL);
        if (userDao.createUser(user))
            return new ResponseResult(ResponseType.SUCCESS);
        else
            throw new BaseException(ResponseType.USER_ALREADY_EXIST);
    }

    @GetMapping("/")
    @ResponseBody
    public User getUser() {
        User res = userDao.getUser();
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseResult updateUser(@RequestBody User user) {
        if (userDao.updateUser(user))
            return new ResponseResult(ResponseType.SUCCESS);
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserBasedOnUserId(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            User target = userDao.getUserBasedOnUserId(res, id);
            if (target != null)
                return target;
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }
}