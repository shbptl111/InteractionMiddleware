package com.coll.restcontroller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coll.dao.FriendDAO;
import com.coll.model.Friend;
import com.coll.model.UserDetail;

@RestController
public class FriendRestController {

	@Autowired
	FriendDAO friendDAO;
	
	@PostMapping("sendFriendRequest")
	public ResponseEntity<List<Friend>> sendFriendRequest(@RequestBody Friend friend1, HttpSession session){
		//friend.setStatus("NA");
		System.out.println("Inside the FriendRestController's send request method");
		System.out.println(friend1.getFriendLoginName());
		System.out.println(friend1.getLoginName());
		UserDetail userDetail = (UserDetail)session.getAttribute("userDetail");
		List<Friend> listFriends = friendDAO.showFriendList(userDetail.getLoginName());
		if(friendDAO.sendFriendRequest(friend1)) {
			return new ResponseEntity<List<Friend>>(listFriends, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Friend>>(listFriends, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*@GetMapping("getFriend/{friendId}")
	public ResponseEntity<Friend> getFriend(@PathVariable("friendId") int friendId){
		Friend friend = friendDAO.getFriend(friendId);
		if(friend != null) {
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@PostMapping("deleteFriendRequest/{friendID}")
	public ResponseEntity<String> deleteFriendRequest(@PathVariable("friendID") int friendID){
		if(friendDAO.deleteFriendRequest(friendID)) {
			return new ResponseEntity<String>("Friend deleted successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not delete friend", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("showFriendList/{loginname}")
	public ResponseEntity<List<Friend>> showFriendList(@PathVariable("loginname") String loginname){
		List<Friend> listFriends = friendDAO.showFriendList(loginname);
		
		if(listFriends != null) {
			return new ResponseEntity<List<Friend>>(listFriends, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Friend>>(listFriends, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="showPendingFriendRequest/{loginname}")
	public ResponseEntity<List<Friend>> showPendingFriendRequestList(@PathVariable("loginname") String loginname){
		List<Friend> listPendingFriendRequest = friendDAO.showPendingFriendRequest(loginname);
		
		if(listPendingFriendRequest != null) {
			return new ResponseEntity<List<Friend>>(listPendingFriendRequest, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Friend>>(listPendingFriendRequest, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="showSuggestedFriendList/{loginname}")
	public ResponseEntity<List<UserDetail>> showSuggestedFriendList(@PathVariable("loginname") String loginname){
		List<UserDetail> listSuggestedFriendList = friendDAO.showSuggestFriend(loginname);
		
		if(listSuggestedFriendList != null) {
			return new ResponseEntity<List<UserDetail>>(listSuggestedFriendList, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<UserDetail>>(listSuggestedFriendList, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="acceptFriendRequest/{friendID}")
	public ResponseEntity<String> acceptFriendRequest(@PathVariable("friendID") int friendID){
		if(friendDAO.acceptFriendRequest(friendID)) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed", HttpStatus.NOT_FOUND);
		}
	}
}
