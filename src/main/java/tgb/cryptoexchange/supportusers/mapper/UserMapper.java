package tgb.cryptoexchange.supportusers.mapper;

import org.springframework.stereotype.Component;
import tgb.cryptoexchange.supportusers.dto.UserDTO;
import tgb.cryptoexchange.supportusers.entity.SupportUser;

@Component
public class UserMapper {

    public UserDTO fromEntity(final SupportUser user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

}
