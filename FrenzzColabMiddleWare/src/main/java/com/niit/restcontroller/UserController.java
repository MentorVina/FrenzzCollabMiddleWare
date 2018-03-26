package com.niit.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.niit.DAO.UserDAO;
import com.niit.Model.Blog;
import com.niit.Model.User;

@RestController
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	
	//--------------------------Get All List-----------------------------------//
	
	
	@GetMapping(value="/listUsers")
	public ResponseEntity<List<User>> getListUsers()
	{
		System.out.println("rest controller in User list");
		List<User> listUsers=userDAO.listUser("aditi@gmail.com");
		return new ResponseEntity<List<User>>(listUsers,HttpStatus.OK);

	}
	
	
	//---------------------------Add Into User---------------------------------//
	
		@PostMapping(value="/addUser"  )
		public ResponseEntity<String>addUser(@RequestBody User user)
		{
			System.out.println("rest controller in adduser");
			
			if(userDAO.addUser(user))
			{
				return new ResponseEntity<String>("Success",HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
			}
			
			
		}

		
		
		//--------------------Get User By Id---------------------------------//
		
		@RequestMapping(value = "/getUserById/{userId}", method = RequestMethod.GET)
	    public ResponseEntity<User> get(@PathVariable("userId") int userId){
	        
	        User user = userDAO.getUser(userId);

	        if (user == null){
	           
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }
		
		
		//--------------------Update User By Id----------------------------//
		
		 @RequestMapping(value = "/UpdateUser/{userId}", method = RequestMethod.PUT)
		    public ResponseEntity<User> update(@PathVariable("userId") int userId){
		       
			   User users = userDAO.getUser(userId);

		        if (users == null){
		            
		            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		        }

		                     users.setContactNo(users.getContactNo()); 
		                    
		                       userDAO.updateUser(users);
		        
		        return new ResponseEntity<User>(users, HttpStatus.OK);
		    }
		
		 
		 
		//------------------------Delete Blog By Id-----------------------//
		 
		 @RequestMapping(value ="/deleteUser/{userId}",method=RequestMethod.DELETE)
		 public ResponseEntity<String> deleteuser(@PathVariable("userId") int userId) 
		 {
		       
			   User users = userDAO.getUser(userId);

		        if (users == null){
		            
		            return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		        }

		                   //  blogs.setBlogContent(blog.getBlogContent()); 
		                    
		                       userDAO.deleteUser(users);
		        
		        return new ResponseEntity<String>("Success", HttpStatus.OK);
		    }
		 
		
}