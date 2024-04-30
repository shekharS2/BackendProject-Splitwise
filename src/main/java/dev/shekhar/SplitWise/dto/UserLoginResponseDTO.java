package dev.shekhar.SplitWise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserLoginResponseDTO {
    private int id;
    private String name;
    private String email;
    List<UserFriendResponseDTO> friendList;
    List<GroupResponseDTO> groupList;
}
