package com.niit.restcontroller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.DAO.ProfileUpdateDAO;
import com.niit.Model.ProfilePicture;
import com.niit.Model.User;

@RestController
public class ProfileController {
	
	@Autowired
    ProfileUpdateDAO profileUpdateDAO;
	
    
    @RequestMapping(value="/doUpload",method=RequestMethod.POST)
    public ResponseEntity<?> uploadPicture(@RequestParam(value="file1") CommonsMultipartFile fileupload,HttpSession session,User user)
    {
    	 System.out.println("m in upload function");
      String user1=(String) session.getAttribute("username");
    	 
        
        if(user==null) 
        {
        	 System.out.println("Null User Name");
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        else
        {
        
            ProfilePicture profilePicture=new ProfilePicture();
            System.out.println("Profile Picture Found");
            profilePicture.setLoginName(user1);

            profilePicture.setImage(fileupload.getBytes());
            System.out.println("Profile Image Set");
            profileUpdateDAO.save(profilePicture);
            return new ResponseEntity<Void>(HttpStatus.OK);
       }
    }
    
    @RequestMapping(value="/getImage/{loginName}")
    public @ResponseBody byte[] getProfilePic(@PathVariable("loginName") String loginName)
    {
    	System.out.println( "I AM IN GETIMAGE FUN ");
        ProfilePicture profilePicture=profileUpdateDAO.getProfilePicture(loginName);
        
        
        if(profilePicture==null)
        {
        	System.out.println( "I AM IN GETIMAGE FUN ");
            return null;
        }
        else
        {
            return profilePicture.getImage();
        }
    }
	

}
