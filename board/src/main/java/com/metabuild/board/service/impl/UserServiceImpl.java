package com.metabuild.board.service.impl;

import com.metabuild.board.dto.UserDTO;
import com.metabuild.board.exception.DuplicateIdException;
import com.metabuild.board.mapper.UserProfileMapper;
import com.metabuild.board.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.metabuild.board.utils.SHA256Util.encryptSHA256;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private UserProfileMapper userProfileMapper;

    @Override
    public void registerUser(UserDTO user) {
        // ID가 중복되었는지 확인
        boolean dupleIdResult = isDuplicatedId(user.getUserId());
        if(dupleIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        user.setCreateTime(new Date());
//      패스워드 암호화 작업 진행
        user.setPassword(encryptSHA256(user.getPassword()));
        int insertCount = userProfileMapper.register(user);

        if(insertCount != 1) {
            log.error("insertMember ERROR~ {}", user);
            throw new RuntimeException(
                    "insertUser ERROR~ 회원가입 메서드를 확인하여 주세요\n" + "Params : " +user);
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        return userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);
    }

    // 중복 ID인지 확인
    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String id) {
        return null;
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {

        String cryptoPassword = encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR~ {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            int deleteCount = userProfileMapper.deleteUserProfile(id);
        } else {
            log.error("deleteId ERROR~ {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
