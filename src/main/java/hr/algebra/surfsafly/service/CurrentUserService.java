package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;

public interface CurrentUserService {
    User getCurrentUser() throws UserNotFoundException;
}
