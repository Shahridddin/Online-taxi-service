package uz.pdp.online_taxi_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online_taxi_service.dto.AuthUserCreateDto;
import uz.pdp.online_taxi_service.entity.AuthUser;
import uz.pdp.online_taxi_service.repository.AuthUserRepository;

import java.util.UUID;

@Service
public class AuthUserService implements AuthUserInterface {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(AuthUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(AuthUserCreateDto dto) {
        AuthUser authUser = new AuthUser();
        authUser.setEmail(dto.email());
        authUser.setUsername(dto.username());
        authUser.setPassword(passwordEncoder.encode(dto.password()));
        authUser.setFirstName(dto.firstName());
        authUser.setLastName(dto.lastName());
        authUser.setPhoneNumber(dto.phoneNumber());
        userRepository.save(authUser);
    }
}
