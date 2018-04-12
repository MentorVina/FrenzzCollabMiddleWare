package com.niit.restcontroller;

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

import com.niit.DAO.FriendDAO;
import com.niit.Model.Friend;
import com.niit.Model.User;

@RestController
public class FriendController {
	
@Autowired
FriendDAO friendDAO;
@PostMapping(value="/sendFriendRequest")
public ResponseEntity<String> sendFriendRequest(@RequestBody Friend friend)
{
	
	if(friendDAO.sendFriendRequest(friend))
	{
		return new ResponseEntity<String>("Success",HttpStatus.OK);
		
	}
	else
	{
		return new ResponseEntity<String>("Failure",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


@PostMapping(value="/deleteFriendRequest/{friendId}")
public ResponseEntity<String> deleteFriendRequest(@PathVariable("friendId") int friendId)
{
	
	if(friendDAO.deleteFriendRequest(friendId))
	{
		return new ResponseEntity<String>("Success",HttpStatus.OK);
		
	}
	else
	{
		return new ResponseEntity<String>("Failure",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping(value="/acceptFriendRequest/{friendId}")
public ResponseEntity<String> acceptFriendRequest(@PathVariable("friendId") int friendId)
{
	
	if(friendDAO.acceptFriendRequest(friendId))
	{
		return new ResponseEntity<String>("Success",HttpStatus.OK);
		
	}
	else
	{
		return new ResponseEntity<String>("Failure",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping(value="/showAllFriends")
public ResponseEntity<List<Friend>> showAllFriends(HttpSession session)
{
	
   String loginName=((User)session.getAttribute("user")).getLoginName();
   List<Friend>listAllFriend=friendDAO.showAllFriends(loginName);
	
	if(listAllFriend.size()>0)
	{
		return new ResponseEntity<List<Friend>>(listAllFriend,HttpStatus.OK);
		
	}
	else
	{
		return new ResponseEntity<List<Friend>>(listAllFriend,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping(value="/showPendingRequest")
public ResponseEntity<List<Friend>> showPendingRequest(HttpSession session)
{
	
   String loginName=((User)session.getAttribute("user")).getLoginName();
   List<Friend>listPendingRequestsFriend=friendDAO.showRequestPendingList(loginName);
	
System.out.println(listPendingRequestsFriend.size()+" is pending");
	if(listPendingRequestsFriend.size()>0)
	{
		return new ResponseEntity<List<Friend>>(listPendingRequestsFriend,HttpStatus.OK);
		
	}
	else
	{
		return new ResponseEntity<List<Friend>>(listPendingRequestsFriend,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


}
	

