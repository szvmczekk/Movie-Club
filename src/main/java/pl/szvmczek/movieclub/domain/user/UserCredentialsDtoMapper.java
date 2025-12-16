package pl.szvmczek.movieclub.domain.user;

import pl.szvmczek.movieclub.domain.user.dto.UserCredentialsDto;

import java.util.stream.Collectors;

public class UserCredentialsDtoMapper {
    static UserCredentialsDto map(User user){
        return new UserCredentialsDto(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet())
        );
    }
}
