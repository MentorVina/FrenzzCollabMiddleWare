package com.niit.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.DAO.BlogDAO;
import com.niit.Model.Blog;
import com.niit.Model.BlogComment;

@RestController
public class BlogController {
	@Autowired
	BlogDAO blogDAO;
	
	
	@GetMapping(value="/demo")
	public ResponseEntity<String> demoPurpose()
	{
		System.out.println("rest controller");
		return new ResponseEntity<String>("Demo Data",HttpStatus.OK);
	
	}

	//--------------------------Get All List-----------------------------------//
	
	@GetMapping(value="/listBlogs")
	public ResponseEntity<List<Blog>> getListBlogs()
	{
		System.out.println("rest controller in list");
		List<Blog> listBlogs=blogDAO.listBlog("Kiri");
		return new ResponseEntity<List<Blog>>(listBlogs,HttpStatus.OK);

	}
	
	//---------------------------Add Into Blog---------------------------------//
	
	@PostMapping(value="/addBlog"  )
	public ResponseEntity<String>addblog(@RequestBody Blog blog)
	{
		System.out.println("rest controller in add");
		
		blog.setCreateDate(new java.util.Date());
		blog.setLikes(0);
		blog.setUsername("Amol");
		blog.setStatus("A");
		if(blogDAO.addBlog(blog))
		{
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	//--------------------Get Blog By Id---------------------------------//
	
	@RequestMapping(value = "/getById/{blogId}", method = RequestMethod.GET)
    public ResponseEntity<Blog> get(@PathVariable("blogId") int blogId){
        
        Blog blog = blogDAO.getBlog(blogId);

        if (blog == null){
           
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Blog>(blog, HttpStatus.OK);
    }
	
	//--------------------Update Blog By Id----------------------------//
	
	 @RequestMapping(value = "/Update/{blogId}", method = RequestMethod.PUT)
	    public ResponseEntity<Blog> update(@PathVariable("blogId") int blogId, @RequestBody Blog blog){
	       
		   Blog blogs = blogDAO.getBlog(blogId);

	        if (blogs == null){
	            
	            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
	        }

	                     blogs.setBlogContent(blog.getBlogContent()); 
	                    
	                       blogDAO.updateBlog(blogs);
	        
	        return new ResponseEntity<Blog>(blogs, HttpStatus.OK);
	    }
	
		//------------------------Delete Blog By Id-----------------------//
	 
	 @RequestMapping(value ="/delete/{blogId}",method=RequestMethod.DELETE)
	 public ResponseEntity<String> deleteBlog(@PathVariable("blogId") int blogId) 
	 {
	       
		   Blog blogs = blogDAO.getBlog(blogId);

	        if (blogs == null){
	            
	            return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	        }

	                   //  blogs.setBlogContent(blog.getBlogContent()); 
	                    
	                       blogDAO.deleteBlog(blogs);
	        
	        return new ResponseEntity<String>("Success", HttpStatus.OK);
	    }
	 
	 
	 
	 //--------------------------------Approve Blog-------------------------//
	 
	 
	 @RequestMapping(value = "/approve/{blogId}", method = RequestMethod.PUT)
	    public ResponseEntity<Blog> approve(@PathVariable("blogId") int blogId){
	       
		   Blog blogs = blogDAO.getBlog(blogId);

	        if (blogs == null){
	            
	            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
	        }

	        blogs.setStatus("A");
			blogDAO.approveBlog(blogs);
	        return new ResponseEntity<Blog>(blogs, HttpStatus.OK);
	    }
	 
	 //-------------------------------Reject Blog-----------------------------//
	 
	 
	 @RequestMapping(value = "/rejectBlog/{blogId}", method = RequestMethod.PUT)
	    public ResponseEntity<Blog> reject(@PathVariable("blogId") int blogId){
	       
		   Blog blogs = blogDAO.getBlog(blogId);

	        if (blogs == null){
	            
	            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
	        }

	        blogs.setStatus("NA");
			blogDAO.rejectBlog(blogs);
	        return new ResponseEntity<Blog>(blogs, HttpStatus.OK);
	    }
	 
	 
	 
		//-------------------------Increments Likes in blog--------------------//
	 
	 
      @GetMapping(value="/incrementLikes/{blogId}")
     public ResponseEntity<String>IncrementsLikes(@PathVariable("blogId")int blogId)
     {
		 Blog blog=blogDAO.getBlog(blogId);
		 if(blogDAO.incrementLikes(blog))
		 {
			 return new ResponseEntity<String>("Success",HttpStatus.OK);
		 }
		 else
		 {
			 return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		 }
     }
	
      
      
      //-------------------------Add BlogComment--------------------------------//
      
      @PostMapping(value="/addBlogComment"  )
  	public ResponseEntity<String>addblogcomment(@RequestBody BlogComment blogComment)
  	{
  		System.out.println("rest controller in addBlogComment");
  		
  		blogComment.setCommentDate(new java.util.Date());       
  		blogComment.setBlogId(66);
  	
   		
  				
  				
  		if(blogDAO.addBlogComment(blogComment))
  		{
  			return new ResponseEntity<String>("Success",HttpStatus.OK);
  		}
  		else
  		{
  			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
  		}
  		
  		
  	}
      
      //-------------------------Delete BlogComment By Id--------------------------------//
      
      @RequestMapping(value ="/deleteBlogComment/{commentId}",method=RequestMethod.DELETE)
 	 public ResponseEntity<String> deleteBlogcomment(@PathVariable("commentId") int commentId) 
 	 {
 	       
 		   BlogComment comments = blogDAO.getBlogComment(commentId);

 	        if (comments == null){
 	            
 	            return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
 	        }

 	                   //  blogs.setBlogContent(blog.getBlogContent()); 
 	                    
 	                       blogDAO.deleteBlogComment(comments);
 	        
 	        return new ResponseEntity<String>("Success", HttpStatus.OK);
 	    }
      
      
      //-------------------------Get BlogComment By CommentId--------------------------------//
      
     
      @RequestMapping(value = "/getByCommentId/{commentId}", method = RequestMethod.GET)
      public ResponseEntity<BlogComment> getComment(@PathVariable("commentId") int commentId){
          
          BlogComment comments = blogDAO.getBlogComment(commentId);

          if (comments == null){
             
              return new ResponseEntity<BlogComment>(HttpStatus.NOT_FOUND);
          }

          return new ResponseEntity<BlogComment>(comments, HttpStatus.OK);
      }
      
      
      //-------------------------List All BlogComment--------------------------------//
      
      @GetMapping(value="/listBlogComments")
  	public ResponseEntity<List<BlogComment>> getListBlogComment()
  	{
  		System.out.println("rest controller in BlogComment list");
  		List<BlogComment> listBlogComments=blogDAO.listBlogComment(26);
  		return new ResponseEntity<List<BlogComment>>(listBlogComments,HttpStatus.OK);
  		
  	}
      
}
