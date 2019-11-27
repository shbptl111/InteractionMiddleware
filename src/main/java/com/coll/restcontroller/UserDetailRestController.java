package com.coll.restcontroller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coll.dao.UserDetailDAO;
import com.coll.model.UserDetail;

@RestController
public class UserDetailRestController {

	@Autowired
	UserDetailDAO userDetailDAO;

	@PostMapping("registerUser")
	@ResponseStatus(value = HttpStatus.OK)
	public void registerUser(@RequestBody UserDetail user) {
		userDetailDAO.registerUser(user);
	}

	@PostMapping("updateProfile")
	public ResponseEntity<String> updateProfile(@RequestBody UserDetail user) {
		if (userDetailDAO.updateProfile(user)) {
			return new ResponseEntity<String>("User updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed to update user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getUser/{loginname}")
	public ResponseEntity<UserDetail> getUser(@PathVariable("loginname") String loginName) {
		UserDetail user = userDetailDAO.getUser(loginName);

		if (user != null) {
			return new ResponseEntity<UserDetail>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDetail>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("checkUser")
	public ResponseEntity<UserDetail> checkLogin(@RequestBody UserDetail userDetail, HttpSession session) {
		UserDetail userDetail1 = userDetailDAO.checkUserValidation(userDetail.getLoginName(), userDetail.getPassword());
		if (userDetail1 != null) {
			session.setAttribute("userDetail", userDetail1);
			return new ResponseEntity<UserDetail>(userDetail1, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDetail>(userDetail1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
