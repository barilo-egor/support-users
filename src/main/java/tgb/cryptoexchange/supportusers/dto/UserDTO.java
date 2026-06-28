package tgb.cryptoexchange.supportusers.dto;

import lombok.*;
import tgb.cryptoexchange.supportusers.enums.UserRole;

/**
 * @see tgb.cryptoexchange.supportusers.entity.SupportUser
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private UserRole role;

    @ToString.Exclude
    private String password;

}
