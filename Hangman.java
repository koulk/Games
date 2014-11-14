
import acm.program.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.media.Manager;
import javax.media.Player;

import com.mysql.jdbc.Statement;

import acm.graphics.*;
import acm.util.*;
import acm.io.*;

public class Hangman extends ConsoleProgram
{
	
	public Hangman(String user_name)
	  {
	 	 nickname= user_name;
	  }
	  
	 	public Hangman() {
	 	// TODO Auto-generated constructor stub
	 } 
	public static void main(String args[]) 
	{	
	    obj=new Hangman();
		obj.start(args);
	
	}
	

public void init()
	{
	
		   setLayout(new GridLayout(1,3)); // creating a grid of 1 row 3 columns
		   setup_Screen();
	       get_Highscore();
	       
		   
	}

public void run() 
	{	 				 

	instructions();
	 while(wrong!=7&& num_movie_Displayed!=16)
        {

	    	   
                   	Hangman_setup();
	         //Show Word on Screen
                   	
                   	show_word();
                    show_poster();
    
   
         correct_loc=num_Space; // no of correct location = no of spaces			
   
    
   
        guess=false;
    
		  while(wrong!=7&&!guess)
		{
		    println("\nEnter Your Guess");
		    input();
		    display_Highscore();
		    	
		   
		    		found=false;
		            boolean fnd=false;
		            boolean Exp;
	do
	{
		loop :
	   {		
		  Exp=false;
		  
		  for(int i=0;i<sel_movie.length();i++)
		    {
		     try{                                  // if no character pressed
		    	
		    	 if(input[0]==' ')
		    	 {
		    		    println("No Guess Entered");
		            	println("Enter Your Guess");
		            	Exp=true;
		            	input();
		            	break loop;
		    		 
		    	 }
		    	 
		    	if(input[0]==sel_movie.charAt(i)&& !find_char(i))
		    	  {
		    		found=true;
		    		update_loc[correct_loc++]=i;
		    		fnd=false;
		    	 }
		    	
		        }
		     
		            catch(Exception e)
		            {
		            	println("No Guess Entered");
		            	println("Enter Your Guess");
		            	Exp=true;
		            	input();
		            	break loop;
		            }
		     
		    }
		  
		}
	}
       while(Exp);	
	
for(int i=0;i<sel_movie.length();i++)
{
	if(input[0]==sel_movie.charAt(i))
	   {
		  fnd=true;
		  break;
	   }
}
		    
		    if(!found&&!fnd)
		    {
		    	
		    	   if(wrong>0) //  if Incorrect guess entered again dont increase wrong count then 
		    	   {
		    		   loop:
		    		   {
		    	       for(int i=0;i<wrong;i++)
		    	          {
		    	    	      
		    	    	      if(input[0]==incorrect[i])
		    	    	     {
		    	    		    println("Wrong Guess");
		    	    		    println("This Was Entered Before Also");
		    	    		    break loop;
		    	    	     }
		    	    	   
		    	          }
		    	       
		    	        println("Wrong Guess");
			    		incorrect[wrong]=input[0];
			    		GLabel disp= new GLabel(" "+input[0]+" ",margin+30,30);
			    		right.add(disp);
			    		disp.setFont("COURIER-25");
			    		margin+=30;
			    		++wrong;
			    		
		    		   } //end of loop:
		    	  
		    	   }//end of if 
		    	   
		   
		    	   
		    	   if(wrong==0)
			    	{
			    		println("Wrong Guess");
			    		incorrect[wrong]=input[0];
			    		++wrong;
			    		GLabel disp= new GLabel(" "+input[0]+" ",margin+30,30);
			    		right.add(disp);
			    		disp.setFont("COURIER-25");
			    		margin+=30;
	
			    	} 
		    }
		    		   
	 show_updated();
	 show_man();
	 
	     if(correct_loc==sel_movie.length())
	     {
	 
	    	 println("\n\n\tYOU ROCK!!");
	    	 println("\nNext Challenge");
	    	 guess=true;
	    	 wrong=0;
	    	 pause(100);
             score++;

	     }
		    
		    		
	} //End of Inner While Loop
		  
		  
		  	if(wrong==7)
		  {
			  println("\n\n\tGame Ended");
			  println(sel_movie);
			  
			  try{
	       			
	          		 String path="C:\\Users\\Federer\\Desktop\\sound\\gameover.mp3";
	          			File f=new File(path); 
	          			// Create a Player object that realizes the audio 
	          			final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
	          			// Start the music
	          			p.start();
	          			
	          		     }
	          		 catch(Exception e)
	          		 { 
	          		 }
		  }
		  	
		  	if(num_movie_Displayed==16)
		  	{
		  		//score++;
		  		guess=true;
		  		wrong=0;
		  		println("\n\n\tYou Are A Pro");
		  		println("\n\tGame Completed");
		  		pause(200);
		  	}
	   } //End of Main While Loop		  	
	
		 
		     addRecord();
	    	retrieveRecord();
	       display_Leaderboard();
	    // this.exit();    	
	}
	
	

	void instructions()
	{
	   GLabel obj[];
	   obj= new GLabel[5];
	   
	   obj[0]=new GLabel("GUESS THE NAME OF THE MOVIE",right.getWidth()/2-30,right.getHeight()/2);
	   obj[1]=new GLabel("ENTER ONE CHARACTER AT A TIME",right.getWidth()/2-33,right.getHeight()/2+50);
	   
	   for(int i=0;i<2;i++)
	   {
		   obj[i].setFont("COURIER-15");
		   obj[i].setColor(Color.BLACK);
		   right.add(obj[i]);
	   }
	   
	   pause(5000);
	   right.removeAll();
	   
	   
	   

	}

	
	
void setup_Screen()
{
	left=new GCanvas();
	right=new GCanvas();
	
add(left);
add(right);
right.setBackground(Color.CYAN);
left.setBackground(Color.BLACK);
movie_Displayed=new int[20];
num_movie_Displayed=0;

}
	
	void Hangman_setup()
{
		
		right.removeAll();
		
		left.removeAll();
		String [] movieList={"a beautiful mind","blood diamond","cinderella man","company","fight club","gangaajal","khakee","minority report","omkara","the pianist","prestige","pulp fiction","saaransh","shanghai","taxi driver","titanic"};
		incorrect=new char[7];
		rgen=new RandomGenerator();
		margin=10;
		if(score!=0)
		display_Highscore();
		
  do
  {
 

loop:
   {
 	
	  movie_Repeated=false;
 				option=rgen.nextInt(0,15);

 //CHECK MOVIE SHOULD NOT REPEAT
 
 				if(num_movie_Displayed==0)
 				{
 					movie_Displayed[num_movie_Displayed]=option;
 					num_movie_Displayed++;
 				}
 				
 				else
 				{
 					for(int i=0;i<num_movie_Displayed;i++)
 					{
 						  if(movie_Displayed[i]==option)
 						  {
 							  movie_Repeated=true;
 							  break loop;
 						  }
 					}
 					
 					movie_Displayed[num_movie_Displayed]=option;
 					num_movie_Displayed++;   
 				}
        } 	
 
  }
 	 while(movie_Repeated);
 
    
  num_Space=0;
 
     sel_movie=movieList[option];   // Selected Word
	
	
}
	

	void get_Highscore()
	{

	    //DATABASE CONNECTIVITY

	   record=new Database_connect();
	   record.estab_connect();
	   

		 score_name=new String[3];
		 score_high=new int[3];
	   
	   
		try
		{
			
	       Statement stmt= (Statement) record.con.createStatement();
		   ResultSet rs= stmt.executeQuery(" select * from hangman order by high desc ");
		   
		     db_count=0;
	             while(rs.next()&&db_count<1)
		        	{
	            	 score_name[db_count]=rs.getString(1);
		        	 score_high[db_count]=rs.getInt(2);
		        	 db_count++;
		        	}
		        
	         db_count=0;    
		}      
	       		
		  catch(Exception e)
		  {
			 println(e.getMessage());
		  }
		  
		  
		  try {
			record.con.close();
		     } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}

	
	private void display_Highscore()
	{
		//Displaying HighScore
	     if(score_high[0]>score)  
	       {
	    	 highscore=new GLabel("TO BEAT :  "+score_name[0]+"  "+score_high[0]);
	    	 highscore.setFont("Times New Roman-15");
	    	 highscore.setColor(Color.RED);
	    	 right.add(highscore,right.getWidth()/2,right.getHeight()-30);
	    	 
	       }
	       
	     //In game New High score is achieved
	     
	        else
	       {
	        	highscore=new GLabel("NEW RECORD  "+nickname+" "+score);
	       	    highscore.setFont("Times New Roman-13");
	       	    highscore.setColor(Color.RED);
	       	    right.add(highscore,right.getWidth()/2+30,right.getHeight()-30);
	        	
	       }
	     
	     
	     //Current scorecard
	     
	     scorecard=new GLabel("SCORE "+score+"");
   	     scorecard.setFont("Times New Roman-15");
   	     scorecard.setColor(Color.BLUE);
   	     right.add(scorecard,(right.getWidth()-scorecard.getWidth())/2-80,right.getHeight()-30);
           
	     
	}
	
	private void retrieveRecord() 
		{
			try
			{
				
		    Statement stmt= (Statement) record.con.createStatement();
			ResultSet rs= stmt.executeQuery(" select * from hangman order by high desc ");
			   
			     db_count=0;
			
			        while(rs.next()&&db_count<3)
			        {
			        	score_name[db_count]=rs.getString(1);
			        	score_high[db_count]=rs.getInt(2);
			        	db_count++;
			        	
			        }
			                 
			        
			}      
		       		
			  catch(Exception e)
			  {
				 println("error");
				 println(e.getMessage());
			  }
			  
			  
			  try {
				record.con.close();
			     } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			     
			     
			    
	}	    
	 
	 private void display_Leaderboard()
		{
			right.removeAll(); 
			setBackground(Color.BLACK);

			GLabel hof= new GLabel("HALL OF FAME");
			hof.setFont("Sans Sarif-30");
			hof.setColor(Color.BLUE);
			right.add(hof,(right.getWidth()-hof.getWidth())/2,right.getHeight()/2-50);
			
			 try
			 {

			//Displaying TOP SCORE
				 
				GLabel first=new GLabel("1. "+score_name[0]+"   "+score_high[0]);
		        first.setFont("Sans Sarif-15");
				first.setColor(Color.RED);
				right.add(first,0,right.getHeight()/2);
				  int temp=0;
				  
				    while(temp<(right.getWidth()-first.getWidth())/2)
				    {
				    	temp+=10;
				    	first.move(10,0);
				    	pause(10);
				    }
				    
				    
			        GLabel second=new GLabel("2. "+score_name[1]+"   "+score_high[1]);
			        second.setFont("Sans Sarif-15");
					second.setColor(Color.yellow);
					right.add(second,0,right.getHeight()/2+30);
					 
					  temp=0;
					  
					    while(temp<(right.getWidth()-second.getWidth())/2)
					    {
					    	temp+=10;
					    	second.move(10,0);
					    	pause(10);
					    }

					    
					    GLabel third=new GLabel("3. "+score_name[2]+"   "+score_high[2]);
				        third.setFont("Sans Sarif-15");
						third.setColor(Color.BLACK);
						right.add(third,0,right.getHeight()/2+60);
						   temp=0;
						  
						    while(temp<(right.getWidth()-third.getWidth())/2)
						    {
						    	temp+=10;
						    	third.move(10,0);
						    	pause(10);
						    }
						   
				    pause(100);
				    
			 }
			 
			 catch(Exception e)
			 {
				 println("3 ENTRIES NOT AVAILABLE IN DATABASE");
			 }
			 
		}
	
	
boolean find_char(int loc)    //Finds if character already present
{
	for(int i=0;i<correct_loc;i++)
	{
		if(loc==update_loc[i])
		{
			return(true);
		}
	}
	
	return(false);
}


void input()
{
		
	input=readLine().toLowerCase().toCharArray();
	
}

void show_word()
{
update_loc=new int[sel_movie.length()];

 for(int i=0;i<sel_movie.length();i++)
    {
    	
    	
    		 
    		 if(sel_movie.charAt(i)==' ')
    		 {
    		     update_loc[num_Space++]=i;
    			 print("  ");
    			 //break;
    			 
    		  }
    		 
    		 else
    		 {
    			 print(" "+"_"); // If location not generated
    		 }
    	
    	
    	
    }
}

 void show_updated()
 {
	 for(int i=0;i<sel_movie.length();i++)
	    {
	    	for(int j=0;j<correct_loc;j++)
	    	{
	    		 if(i==update_loc[j]&&sel_movie.charAt(i)!=' ')
	    		 {
	    			 print(" "+sel_movie.charAt(i)); //Print character at generated loc
	    			 break;
	    		 }
	    		 
	    		 if(sel_movie.charAt(i)==' ') // to display space if two words challenge generated
	    		 {
	    			 print("  ");
	    			 break;
	    		 }
	    		 
	    		 if(j==correct_loc-1)
	    		 {
	    			 print(" "+"_");// If location not generated
	    		 }
	    	}
	    	
	    	
	    } 
 }
	
 
 void show_man()
 {
	 switch(wrong)
	 {

		  case 1:
		{
       booth1=new GRect((right.getWidth()/2+40),right.getHeight()/2-200,10,420);
		    booth1.setFilled(true);
       right.add(booth1);
		
       booth2=new GRect((right.getWidth()/2-220),right.getHeight()/2-200,260,10);
       booth2.setFilled(true);
       right.add(booth2);
       
       booth3=new GRect((right.getWidth()/2-110),right.getHeight()/2-200,10,100);
       booth3.setFilled(true);
       right.add(booth3);
		    break;
		}
		
		  case 2:
		  {
			 face= new GOval((right.getWidth()/2-135),right.getHeight()/2-100,50,50);
			 right.add(face);
			 break;
			  
		  }
		  
		  
		  case 3:
		  {
			  body =new GLine((right.getWidth()/2-110),right.getHeight()/2-50,(right.getWidth()/2-110),right.getHeight()/2+150);
			  right.add(body);
			  break;
		  }
		  
		  case 4:
		  {
			  arm1=new GLine((right.getWidth()/2-110),right.getHeight()/2,(right.getWidth()/2-160),right.getHeight()/2+50);
			  right.add(arm1);
			  break;
			  
		  }
		
		  case 5:
		  {	  
			  arm2=new GLine((right.getWidth()/2-110),right.getHeight()/2,(right.getWidth()/2-60),right.getHeight()/2+50);
			  right.add(arm2);
			  break;
		   }
		  
		  case 6:
		  {
			  leg1=new GLine((right.getWidth()/2-110),right.getHeight()/2+150,(right.getWidth()/2-160),right.getHeight()/2+200);
		      right.add(leg1);
		      break;
		  }
		  
		  case 7:
		  {
			leg2=new GLine((right.getWidth()/2-110),right.getHeight()/2+150,(right.getWidth()/2-60),right.getHeight()/2+200);
		    right.add(leg2);
		    break;
		  }
	 }
	 
 }
 
  void show_poster()
  {
	  switch(option)
	  {
	       case 0:
	     {
	    	GImage collage=new GImage("beautifulmind.jpg",80,100);
	 		collage.setSize(300,400);
	 		left.add(collage);
	 		break;
	     }
	  
	       case 1:
	       {
	    	    
   	    	   GImage collage=new GImage("blood.jpg",80,100);
		 		collage.setSize(300,400);
		 		left.add(collage);
		 		break;
	       }
	       
	       case 2:
	       {
	    	   GImage collage=new GImage("cinderella man.jpg",80,100);
		 		collage.setSize(300,400);
		 		left.add(collage);
		 		break;
	       }
	       case 3:
	       {
	    	   GImage collage=new GImage("company.jpg",80,100);
		 		collage.setSize(300,400);
		 		left.add(collage);
		 		break;
	       }
	        case 4:
	       {
	    	 	   GImage collage=new GImage("FightClub.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		    }
	     
	        case 5:
		       {
		    	   GImage collage=new GImage("Gangaajal.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 6:
		       {
		    	 
		    	   GImage collage=new GImage("Khakee.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	           
	        case 7:
		       {
		    	   GImage collage=new GImage("Minority report.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
		       
	        case 8:
		       {
		    	   GImage collage=new GImage("omkara.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 9:
		       {
		    	   GImage collage=new GImage("pianist.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 10:
		       {
		    	   GImage collage=new GImage("Prestige.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 11:
		       {
		    	   GImage collage=new GImage("Pulp.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 12:
		       {
		    	   GImage collage=new GImage("Saaransh.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 13:
		       {
		    	   GImage collage=new GImage("shanghai.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        case 14:
		       {
		    	   GImage collage=new GImage("taxi driver.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
	        
	        case 15:
		       {
		    	   GImage collage=new GImage("titanic.jpg",80,100);
			 		collage.setSize(300,400);
			 		left.add(collage);
			 		break;
		       }
		       
		       default:
		       {
		    	   break;
		       }
	        
	  }
	  
  }
  
  private void addRecord()
  {
	  	//DATABASE CONNECTIVITY

   	record.estab_connect();
 	    	
 	    	try {
 	    		
 				   PreparedStatement pst= record.con.prepareStatement("insert into hangman values(?,?)");
 			
 				   pst.setString(1,nickname);
 			       pst.setInt(2,score);
 			       
 			       int status= pst.executeUpdate();
/*
 			   	if(status>0)
 			    	{
			        		System.out.println("Record Updated");
 			    	}


 			   	else
 			    	{
 			            	System.out.println("Record Not Updated");
 			    	}
 			   	
 */			        
 	    	    } 
 	    	catch (SQLException e) {
 				e.printStackTrace();
 			}
 	    	
 	    	
 	}

 private static Hangman obj; 
 private  boolean guess,pass,found,movie_Repeated;// by default false
 private String sel_movie,score_name[];//selected movie
 //private String wordList[];
 private String nickname;
 private char incorrect[];//to keep track of incorrect option
 private char [] input; 
 private int movie_Displayed[],margin=0,num_movie_Displayed,score=0,score_high[],db_count;
 private GCanvas left,right;
 private GOval face;
 private GLine body,arm1,arm2,leg1,leg2;
 private RandomGenerator rgen;
 private GRect booth1,booth2,booth3;
 private int wrong,option,update_loc[],correct_loc,num_Space;
 private Database_connect record;
 private GLabel scorecard,highscore;
}
