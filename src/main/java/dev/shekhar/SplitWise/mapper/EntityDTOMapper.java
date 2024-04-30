package dev.shekhar.SplitWise.mapper;

import dev.shekhar.SplitWise.dto.GroupResponseDTO;
import dev.shekhar.SplitWise.dto.UserFriendResponseDTO;
import dev.shekhar.SplitWise.dto.UserLoginResponseDTO;
import dev.shekhar.SplitWise.entity.Group;
import dev.shekhar.SplitWise.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EntityDTOMapper {
    public static UserLoginResponseDTO toUserLoginResponseDTO(User user) {
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setId(user.getId());
        userLoginResponseDTO.setName(user.getName());
        userLoginResponseDTO.setEmail(user.getEmail());
        if(user.getFriends() != null) {
            List<UserFriendResponseDTO> userFriendResponseDTOList = new ArrayList<>();
            for(User friend : user.getFriends()) {
                userFriendResponseDTOList.add(toUserFriendResponseDTO(friend));
            }
            userLoginResponseDTO.setFriendList(userFriendResponseDTOList);
        }

        if(user.getGroups() != null) {
            List<GroupResponseDTO> groupResponseDTOList = new ArrayList<>();
            for(Group group : user.getGroups()) {
                groupResponseDTOList.add(toGroupResponseDTO(group));
            }
            userLoginResponseDTO.setGroupList(groupResponseDTOList);
        }
        return userLoginResponseDTO;
    }

    public static UserFriendResponseDTO toUserFriendResponseDTO(User user) {
        UserFriendResponseDTO userFriendResponseDTO = new UserFriendResponseDTO();
        userFriendResponseDTO.setId(user.getId());
        userFriendResponseDTO.setName(user.getName());
        return userFriendResponseDTO;
    }

    public static GroupResponseDTO toGroupResponseDTO(Group group) {
        return null;
    }
}
