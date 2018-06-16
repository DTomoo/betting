package com.dt.betting.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.UserDataRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Override
	public User getLoggedUser(HttpServletRequest request) throws UserDoesNotExistsException {
		String authenticatedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user;
		try {
			user = userDataRepository.getByName(authenticatedUserName);

		} catch (DataNotExistsInRepositoryException ex) {
			user = createNewUser(authenticatedUserName);
		}
		return user;
	}

	private User createNewUser(String authenticatedUserName) {
		try {
			User user = new User();
			user.setName(authenticatedUserName);
			user.setAdmin(isAdmin());
			user = userDataRepository.addData(user);
			return user;

		} catch (DataAlreadyExistsException e) {
			return null;
		}
	}

	@Override
	public boolean isAdmin() {
		boolean isAdmin = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().contains("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}
}
