package hezix.org.shaudifydemo1.entity.user.dto;

public record CreateUserDTO(String username,
                            String password,
                            String passwordConfirmation,
                            String desctiprion,
                            String email)
{}
