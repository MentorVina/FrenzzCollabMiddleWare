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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.DAO.ForumDAO;

import com.niit.Model.Forum;
import com.niit.Model.ForumComment;

@RestController
public class ForumController {
	@Autowired
	ForumDAO forumDAO;
	
	@GetMapping(value="/demoforum")
	public ResponseEntity<String> demoforum()
	{
		System.out.println("rest controller");
		return new ResponseEntity<String>("Demo Data",HttpStatus.OK);
	
	}
	
	//--------------------------Get All List-----------------------------------//
	
	@GetMapping(value="/listForums")
	public ResponseEntity<List<Forum>> getListForums(HttpSession session)
	{
		System.out.println("rest controller in list");
		List<Forum> listForums=forumDAO.listForum((String)session.getAttribute("username"));
		return new ResponseEntity<List<Forum>>(listForums,HttpStatus.OK);
		
	}
	
	//---------------------------Add Into Forum---------------------------------//
	
	
	@PostMapping(value="/addForum"  )
	public ResponseEntity<String>addforum(@RequestBody Forum forum,HttpSession session)
	{
		System.out.println("rest controller in add");
		
		forum.setCreateDate(new java.util.Date());
	
		forum.setStatus("A");
		
		forum.setUsername((String)session.getAttribute("username"));
		if(forumDAO.addForum(forum))
		{
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	

	//--------------------Get Forum By Id---------------------------------//
	
	@RequestMapping(value = "/getForumById/{forumId}", method = RequestMethod.GET)
    public ResponseEntity<Forum> get(@PathVariable("forumId") int forumId){
        
        Forum forum = forumDAO.getForum(forumId);

        if (forum == null){
           
            return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Forum>(forum, HttpStatus.OK);
    }
	
	
	//--------------------Update Forum By Id----------------------------//
	
		 @RequestMapping(value = "/Updateforum/{forumId}", method = RequestMethod.PUT)
		    public ResponseEntity<Forum> update(@PathVariable("forumId") int forumId, @RequestBody Forum forum){
		       
			   Forum forums = forumDAO.getForum(forumId);

		        if (forums == null){
		            
		            return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
		        }

		                     forums.setForumName(forum.getForumName()); 
		                    
		                       forumDAO.updateForum(forums);
		        
		        return new ResponseEntity<Forum>(forums, HttpStatus.OK);
		    }
	
		 
		 
		 
		//------------------------Delete Forum By Id-----------------------//
		 
		 @RequestMapping(value ="/deleteforum/{forumId}",method=RequestMethod.DELETE)
		 public ResponseEntity<String> deleteForum(@PathVariable("forumId") int forumId) 
		 {
		       
			   Forum forum = forumDAO.getForum(forumId);

		        if (forum == null){
		            
		            return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		        }

		                   //  blogs.setBlogContent(blog.getBlogContent()); 
		                    
		                       forumDAO.deleteForum(forum);
		        
		        return new ResponseEntity<String>("Success", HttpStatus.OK);
		    }
		 
		 //--------------------------------Approve Forum-------------------------//
		 
		 @RequestMapping(value = "/approveforum/{forumId}", method = RequestMethod.PUT)
		    public ResponseEntity<Forum> approve(@PathVariable("forumId") int forumId){
		       
			   Forum forums = forumDAO.getForum(forumId);

		        if (forums == null){
		            
		            return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
		        }

		        forums.setStatus("A");
				forumDAO.approveForum(forums);
		        return new ResponseEntity<Forum>(forums, HttpStatus.OK);
		    }
		 
		 //-------------------------------Reject Blog-----------------------------//
		 
		 @RequestMapping(value = "/rejectforum/{forumId}", method = RequestMethod.PUT)
		    public ResponseEntity<Forum> reject(@PathVariable("forumId") int forumId){
		       
			   Forum forums = forumDAO.getForum(forumId);

		        if (forums == null){
		            
		            return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
		        }

		        forums.setStatus("NA");
				forumDAO.rejectForum(forums);
		        return new ResponseEntity<Forum>(forums, HttpStatus.OK);
		    }
		 
		 
		 //-------------------------Add ForumComment--------------------------------//
		 
		    @PostMapping(value="/addForumComment"  )
		  	public ResponseEntity<String>addforumcomment(@RequestBody ForumComment forumComment)
		  	{
		  		System.out.println("rest controller in addForumComment");
		  		
		  		forumComment.setCommentDate(new java.util.Date());       
		  		forumComment.setForumId(24);
		  	
		   		
		  				
		  				
		  		if(forumDAO.addForumComment(forumComment))
		  		{
		  			return new ResponseEntity<String>("Success",HttpStatus.OK);
		  		}
		  		else
		  		{
		  			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		  		}
		  		
		  		
		  	}
	      
		  //------------------------Delete ForumComment By Id-----------------------//
			 
			 @RequestMapping(value ="/deleteForumComment/{commentId}",method=RequestMethod.DELETE)
			 public ResponseEntity<String> deleteForumcomment(@PathVariable("commentId") int commentId) 
			 {
			       
				  ForumComment forumComments = forumDAO.getForumComment(commentId);

			        if (forumComments == null){
			            
			            return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
			        }

			                   //  blogs.setBlogContent(blog.getBlogContent()); 
			                    
			                       forumDAO.deleteForumComment(forumComments);
			        
			        return new ResponseEntity<String>("Success", HttpStatus.OK);
			    }
		 
			 
			   //-------------------------Get ForumComment By CommentId--------------------------------//
		      
		     
		      @RequestMapping(value = "/getForumByCommentId/{commentId}", method = RequestMethod.GET)
		      public ResponseEntity<ForumComment> getForumComment(@PathVariable("commentId") int commentId){
		          
		          ForumComment forumcomments = forumDAO.getForumComment(commentId);

		          if (forumcomments == null){
		             
		              return new ResponseEntity<ForumComment>(HttpStatus.NOT_FOUND);
		          }

		          return new ResponseEntity<ForumComment>(forumcomments, HttpStatus.OK);
		      }
		 
		      
		      
		      //-------------------------List All ForumComment--------------------------------//
		      
		      @GetMapping(value="/listForumComments")
		  	public ResponseEntity<List<ForumComment>> getListForumComment()
		  	{
		  		System.out.println("rest controller in ForumComment list");
		  		List<ForumComment> listForumComments=forumDAO.listForumComment(41);
		  		return new ResponseEntity<List<ForumComment>>(listForumComments,HttpStatus.OK);
		  		
		  	}
}
