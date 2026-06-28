package tgb.cryptoexchange.supportusers.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @see tgb.cryptoexchange.supportusers.entity.RefreshToken
 */
@Data
@Builder
public class UserRefreshTokenDTO {

    private final String token;

    private final Long userId;

    private final Instant expiresAt;

}
