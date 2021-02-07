package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        int count=0;
        count=accountMapper.judge_exist11(user.getEmail());
        System.out.println(count);
        if(count==0) {
            try {
                accountMapper.createNewAccount(user);
                // System.out.println(userVO.getUserType());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure("注册失败，密码不能超过11位");
            }
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess("注册成功");
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id, password, username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO minusCredit(int id, double credit) throws ServiceException {
        accountMapper.minusCredit(id,credit);
        return ResponseVO.buildSuccess(true);
    }
}
