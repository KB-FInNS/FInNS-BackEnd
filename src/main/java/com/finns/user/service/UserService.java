package com.finns.user.service;

import com.finns.Mbti;
import com.finns.amountByCategory.dto.AmountByCategory;
import com.finns.amountByCategory.mapper.AmountByCategoryMapper;
import com.finns.amountByCategory.service.AmountByCategoryService;
import com.finns.follow.service.FollowService;
import com.finns.recentUser.dto.RecentUserResponseDTO;
import com.finns.user.dto.*;
import com.finns.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    private final AmountByCategoryService amountByCategoryService;
    private final FollowService followService;

    public User getUser(Long userNo) {
        return Optional.ofNullable(userMapper.selectOne(userNo))
                .orElseThrow(NoSuchElementException::new);
    }

    public List<SearchUserDTO> getUsers(Long userNo) {
        List<SearchUserDTO> searchedUsers = userMapper.selectAll(userNo);
        for (SearchUserDTO searchedUser : searchedUsers) {
            boolean isFollow = followService.isFollowing(userNo, searchedUser.getUserNo());
            searchedUser.setFollow(isFollow);
        }
        return Optional.of(searchedUsers)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<UserTop3DTO> getTop3ForAmountByDate(YearAndMonthDTO yearAndMonthDTO) {
        return Optional.ofNullable(userMapper.selectTop3ForAmountByDate(yearAndMonthDTO))
                .orElseThrow(NoSuchElementException::new);
    }

    public List<UserRecommendResponseDTO> getRecommend5ByMbti(UserRecommendRequestDTO userRecommendRequestDTO) {
        List<UserRecommendResponseDTO> userRecommendResponseDTOS = userMapper.selectRecommend5ByMbti(userRecommendRequestDTO);
        for (UserRecommendResponseDTO userRecommendResponseDTO : userRecommendResponseDTOS) {
            boolean isFollow = followService.isFollowing(userRecommendRequestDTO.getUserNo(), userRecommendResponseDTO.getUserNo());
            userRecommendResponseDTO.setFollow(isFollow);
        }
        return Optional.of(userRecommendResponseDTOS)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void setMbtiByCategory(Long userNo) {
        String topCategory = amountByCategoryService.calculateTopCategory(userNo);
        String mbtiName = Mbti.getMbtiNameByCategory(topCategory);
        SetMbtiDTO setMbtiDTO = new SetMbtiDTO(userNo, mbtiName);
        userMapper.updateMbti(setMbtiDTO);
    }

}
