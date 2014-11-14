import acm.program.*;
import acm.util.RandomGenerator;
import acm.graphics.*;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.media.*; 
import java.net.*;
import java.io.*; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

class Snakes extends GraphicsProgram
{
 
 public Snakes(String user_name)
 {
	 nickname= user_name;
 }
 
	public Snakes() {
	// TODO Auto-generated constructor stub
}

	public static void main(String args[])
	{
		obj=new Snakes();
		obj.start(args);
	}
	
	public void run() 
	{
		
		addKeyListeners();
		instructions();
		setup_Nibble();
        get_Highscore();

		
		
		while(dead==false)
		{
	
			if(getWidth()>900&& tester==0)
 	       {
 	    	 //flag=true;
 	    	 tester=1;
 	    	 setup_Nibble();
 	       }
 	  
 	       if(getWidth()<900&& tester==1)
 	       {
 	    	   //flag=false;
 	    	   tester=0;
 	    	   setup_Nibble();
 	       }
			generateFood();
	
		      if(pressed==false)
		    {
			   nokeypressed();
		    }
		    
		      //Power Available for Short time
		      
		      if(power) 
		      {
		    	  timer_Pow++;
		    	     
		    	    if(timer_Pow>300) //time of 300 loops
		    	     {
		    	    	 power=false;
		    	    	 remove(splPow);
		    	    	 timer_Pow=0;
		    	     }
		      }
		
		
		     
		    	     if(boolPower_delay)
		    	  {
		    		  power_delay++;
		    		  slow=new GLabel("SLOW-MO ACTIVATED" );
				      slow.setFont("Sans Sarif-13"); 
			          slow.setColor(Color.BLUE);
			          add(slow,0,15);
			    
		    		  
		    		  if(power_delay>200)
		    		  {
		    			  power_delay=0;
		    			  boolPower_delay=false;
		    		  }
		    			  
		    	  }
		      
		    splPower();
		    
		    if(!boolPower_delay)
		    speed();
		    
		     pressed=false;
		     pause(delay);
		     
		     
		}
		
		 String path="C:\\Users\\Federer\\Desktop\\sound\\gameover.mp3"; //Path of songs
 		try{
 		       File f=new File(path); 
 		// Create a Player object that realizes the audio 
 		
 	       	final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
 		// Start the music
 		     p.start();
 		     
 		}
 		
 		 catch(Exception x)
 		 {
 			 System.out.println(x.getMessage());
 		 }
 		 
 		 addRecord();
 		 retrieveRecord();
	    // this.exit();    	
	     display_Leaderboard();
         
		
	}
	
	
void instructions()
{
   GLabel obj[];
   obj= new GLabel[5];
   
   obj[0]=new GLabel("UP--W",300,200);
   obj[1]=new GLabel("DOWN--S",300,250);
   obj[2]=new GLabel("LEFT--A",300,300);
   obj[3]=new GLabel("RIGHT--D",300,350);
   
  obj[4]= new GLabel("CLICK ON SCREEN TO ENABLE CONTROLS",150,100);
   for(int i=0;i<5;i++)
   {
	   obj[i].setFont("COURIER-24");
	   obj[i].setColor(Color.BLUE);
	   add(obj[i]);
   }
   
   pause(3000);
   removeAll();
   
   
   

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
	   ResultSet rs= stmt.executeQuery(" select * from snake order by high desc ");
	   
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
		 println("asdda");
		 println(e.getMessage());
	  }
	  
	  
	  try {
		record.con.close();
	     } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
}

void setup_Nibble()
{
 removeAll();	
  Max_Height=getHeight();
  Max_Length=getWidth();
  locX=new int[40];
  locY=new int[40];
  rgen=new RandomGenerator();
  timer_Pow=0;
  up_margin=20;
  
	    	
 // frontpage obj=new frontpage();
  
	     delay=40;
	     bool_power=false;
	     pow_No=0;
	     boolPower_delay=false;
	     power_delay=0;
	     justAte_food=false;
	     gulp_counter=0;
	
  setBackground(Color.BLACK);
  //Defining Outer Wall Of Grid	
  
    up= new GRect(5,up_margin,Max_Length-5,10);
  	left=new GRect(0,up_margin,5,Max_Height-5);
  	down= new GRect(0,Max_Height-5,Max_Length-5,5);
  	right= new GRect(Max_Length-5,up_margin,5,Max_Height);
  	
  	mid_up=new GRect(Max_Length/2-Max_Length/4,Max_Height/2-50,Max_Length/2,10);
  	mid_down=new GRect(Max_Length/2-Max_Length/4,Max_Height/2+50,Max_Length/2,10);
  	
  	add(up);
  	add(down);
  	add(left);
  	add(right);	
  	
  	up.setFilled(true);
  	down.setFilled(true);
  	left.setFilled(true);
  	right.setFilled(true);
  	
 //Set Color Of Wall and Adding to Canvas
  	up.setFillColor(Color.RED);
  	down.setFillColor(Color.RED);
  	left.setFillColor(Color.RED);
  	right.setFillColor(Color.RED);
  	
  
// Setting up Snake
  	 snake_length=30;
  	 count=0;
  	 pressed=false;
  	 Head_X=(getWidth()-snake_length)/2;
  	 Head_Y=(getHeight()-5)/2;
    
  	 
  	 dir='a'; //Setting Initial Direction
     for(int i =Head_X;i<Head_X+snake_length;i+=5)
     {
   	 locX[count]=i;
   	 locY[count]=Head_Y;
   	 count++;
     }
    
     
     for(int i=0;i<count;i++)
      {
    	
     recObj[i]=new GRect(locX[i],locY[i],5,5); 
    
     recObj[i].setFilled(true);
     recObj[i].setFillColor(Color.GREEN);
     add(recObj[i]);
      }
  
     // Generate Food
      prey=new GRect(30,30,5,5);
      prey.setFilled(true);
      prey.setFillColor(Color.YELLOW);
      add(prey);
      food_X=food_Y=20;
      
      food=true;
      dead=false;
      
  
}



   public void keyTyped(KeyEvent e)
   {
	   correct_invalid=dir; // stores previous dir if cheat activated
	  
	   dir=e.getKeyChar();
	   
  pressed=true;
	      switch(dir)
	      {
	      case('w'):
	         {
	    	   Head_Y-=5;
	    	   break;
	         }
	      case('s'):
	         {
	    	   Head_Y+=5;
	    	   break;
	         }
	      case('a'):
	         {
	    	   Head_X-=5;
	    	   break;
	         }
	      
	      case('d'):
	        {
	    	   Head_X+=5;
	    	   break;
	        }
	      
	      case('m'):
	      {
	    	  dir=correct_invalid;
	    	  score+=10;
	    	  pressed=false;// becoz not a valid button
	    	  return; //dont want to update now
	      }
	      
	         default:
	         {
	        	 dir=correct_invalid;
	        	 return;
	         }
	      
	       
	      
	      }
	       
	      update();
	}
   
   
   private void nokeypressed()
   {
	   // Using Last pressed key for directions
	   switch(dir)
	      {
	      case('w'):
	         {
	    	   Head_Y-=5;
	    	   break;
	         }
	      case('s'):
	         {
	    	   Head_Y+=5;
	    	   break;
	         }
	      case('a'):
	         {
		       Head_X-=5;
	    	   break;
	         }
	      
	      case('d'):
	        {
	    	   Head_X+=5; 
	    	   break;
	        }
	      
	      }
	   
	   update();
   }
   
 private void update()
 {
	//increase head decrease tail
	 // count == snake length -1
	 
   	for(int i=count-1;i>0;i--)
	 
		{
		  locX[i]=locX[i-1];
		  locY[i]=locY[i-1];
		  
		}
	  
		locX[0]=Head_X;
		locY[0]=Head_Y;
		 
	
 checkCollision();
 if(!dead)
 {
  
	 removeAll();
	
	 
	 
	 //ScoreCard
	 
	 scorecard=new GLabel("SCORE "+score);
	 scorecard.setFont("Times New Roman-13"); 
     scorecard.setColor(Color.YELLOW);
     add(scorecard,Max_Length-70,15);
	 
	   //Displaying HighScore
     if(score_high[0]>score)  
       {
    	 highscore=new GLabel("TO BEAT :  "+score_name[0]+"  "+score_high[0]);
    	 highscore.setFont("Times New Roman-13");
    	 highscore.setColor(Color.MAGENTA);
    	 add(highscore,180,15);
       }
       
     //In game New High score is achieved
     
        else
       {
        	highscore=new GLabel("NEW RECORD  "+nickname+" "+score);
       	    highscore.setFont("Times New Roman-13");
       	    highscore.setColor(Color.MAGENTA);
       	    add(highscore,180,15);
        	
       }
     
       
     
//Redrawing the Boundaries	 
	    up= new GRect(5,up_margin,Max_Length-5,5);
	  	left=new GRect(0,up_margin,5,Max_Height-5);
	  	down= new GRect(0,Max_Height-5,Max_Length-5,5);
	  	right= new GRect(Max_Length-5,up_margin,5,Max_Height);
	  	
	  	add(up);
	  	add(down);
	  	add(left);
	  	add(right);	
	  	
	  	up.setFilled(true);
	  	down.setFilled(true);
	  	left.setFilled(true);
	  	right.setFilled(true);
	  	
	 //Set Color Of Wall and Adding to Canvas
	  	up.setFillColor(Color.WHITE);
	  	down.setFillColor(Color.WHITE);
	  	left.setFillColor(Color.WHITE);
	  	right.setFillColor(Color.WHITE);

	  	if(score>40)
	  	{
	  		add(mid_up);
	  		add(mid_down);
	  		mid_up.setFilled(true);
		  	mid_down.setFilled(true);
		  		mid_up.setFillColor(Color.WHITE);
		  		mid_down.setFillColor(Color.WHITE);
	  	}
	  	
if(display_time>0&&display_time<=70)
{
display_time--;

	if(display_time>60)
	{	

		try{
		
		 String path="C:\\Users\\Federer\\Desktop\\sound\\power.mp3";
			File f=new File(path); 
			// Create a Player object that realizes the audio 
			final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
			// Start the music
			p.start();
			
		     }
		
		 catch(Exception e)
		 {
			 println("File PATH not found");
		 }
		 
    }		 
	switch(pow_No)
	    {
  		case 1:
  	{
  		//pause(20);
	        boost=new GLabel("BOOSTER ACTIVATED" );
		    boost.setFont("Sans Sarif-13"); 
	        boost.setColor(Color.BLUE);
	        add(boost,0,15);
	        
	        up.setFillColor(Color.RED);
		  	down.setFillColor(Color.RED);
		  	left.setFillColor(Color.RED);
		  	right.setFillColor(Color.RED);

	        
	     break;
  	}
  
  		case 2:
  	{
  		//pause(20);
  		    zip=new GLabel("ZIPPER ACTIVATED" );
		    zip.setFont("Sans Sarif-13"); 
	        zip.setColor(Color.BLUE);
	        add(zip,0,15);
	            
	        up.setFillColor(Color.RED);
		  	down.setFillColor(Color.RED);
		  	left.setFillColor(Color.RED);
		  	right.setFillColor(Color.RED);


	      break;
  	
  	}
  	
  	        default:
  		        break;
  	
   } 

}

	 if(power)
 {
	 add(splPow);
	 
 }
 
  if(food)
  {
	  add(prey);
  }
	
          for(int i=0;i<count;i++)
	{
        	 
		recObj[i]=new GRect(locX[i],locY[i],5,5);
		add(recObj[i]);	
		recObj[i].setFilled(true);
		recObj[i].setFillColor(Color.GREEN);
		   
		
	 }
          
          if(gulp_counter<count&&justAte_food)	
  		{
  			recObj[gulp_counter].setFillColor(Color.YELLOW);
  			pause(10);
  			gulp_counter++;
  		}
  		
  		
  		if(gulp_counter>=count)
  		 {
  		   	gulp_counter=0;
  			justAte_food=false;
  		 }
	
 }
 
 }
 
 private void generateFood() 
 {
    boolean done=false;
   
    food_X= rgen.nextInt(10,Max_Length-15);
    food_Y=rgen.nextInt(up_margin+15,Max_Height-15);
  
  if(food==false)
  {  
	 
	  while(done==false)
	 
	     {
	  
		        for(int i=0;i<count;i++) 
		 {      //Check if food Produced on Snake-Body
		        if( recObj[i]==getElementAt(food_X,food_Y)||(score>40 && (mid_up==getElementAt(food_X,food_Y)|| mid_down==getElementAt(food_X,food_Y)))) 
		       {
		    	   done=false;
		    	   break;
		       }
		         
		        done=true;
		 }     
		        
		      
		        	if(done==true);
		        	 {
		             food=true;
		        	 prey=new GRect(food_X,food_Y,5,5);
		             prey.setFilled(true);
		             prey.setFillColor(Color.YELLOW);
		             add(prey);
		        	 }
		       } 	 
		       
	      } 
     } 
 
 
 void speed()
 {
	 if(score>30)
	 {
		 delay=30;
	 }
	 
	 else if(score>20)
	 {
		 delay=20;
		 
	 }
	 
	 else if(score>10)
	 {
		 delay=30;
	 }
	 
 }
 
     void splPower()
  {
    	 
    	 //Same Mechanism as Generate Food
    	 boolean done=false;
    	 
	    if(score>15 && rgen.nextInt(0,500)%500==1&&!power)
	 {
	    	power_X= rgen.nextInt(10,Max_Length-15);
	         power_Y=rgen.nextInt(up_margin+10,Max_Height-15);
	      
	     
	    	 
	     	  while(done==false)
	    	 
	    	     {
	    	  
	     		     done=true;
	    		        for(int i=0;i<count;i++) 
	    		 {      //Check if food Produced on Snake-Body
	    		        if( recObj[i]==getElementAt(power_X,power_Y)||(score>40 &&( mid_up==getElementAt(power_X,power_Y)|| mid_down==getElementAt(power_X,power_Y)))) 
	    		       {
	    		    	   done=false;
	    		    	   break;
	    		       }
	    		         
	    		       
	    		 }     
	    		        
	    		   
	    		        
	    		        	if(done==true);
	    		        	 {
	    		             power=true;
	    		        	 splPow=new GRect(power_X,power_Y,5,5);
	    		             splPow.setFilled(true);
	    		             splPow.setFillColor(Color.RED);
	    		             add(splPow);
	    		        	 }
	    		       } 	 
	    		       
	    	      } 

	 
	 
	 
  }
 
 void checkCollision()
 {
	// Checks if snake Collides with itself
	 for(int i=0;i<count;i++)
	   {
		 if(dir=='w'||dir=='a')
		   {
			 if(recObj[i]==getElementAt(Head_X,Head_Y))
		   
		   {
		
				 
               removeAll();
               
              
               
               
               GLabel label= new GLabel("GAME ENDED--- SCORE "+score );
               label.setFont("Times New Roman-24"); 
               label.setColor(Color.RED);
               setBackground(Color.WHITE);
               add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
			   dead=true;
			   pause(900);
			   return;
		     }
		   }
		 
		 
		 
		   if(dir=='s')
		   {
			   if(recObj[i]==getElementAt(Head_X,Head_Y+5))  
               {
				   
				   removeAll();
               
               GLabel label= new GLabel("GAME ENDED--- SCORE "+score );
               label.setFont("Times New Roman-24"); 
               label.setColor(Color.RED);
               setBackground(Color.WHITE);
               add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
			   dead=true;
			   pause(900);
			   return;
			    
               }
		   }
		   
		   if(dir=='d')
		   { 
			   if(recObj[i]==getElementAt(Head_X+5,Head_Y))
               {
				   
				   removeAll();
               
               GLabel label= new GLabel("GAME ENDED--- SCORE "+score);
               label.setFont("Times New Roman-24"); 
               label.setColor(Color.RED);
               setBackground(Color.WHITE);
               add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
			   dead=true;
			   pause(2000);
			   return;
               }
		   }
	   }   

	 //Checks if Snake Collides with Wall		   
	if(Head_X<5||Head_X>=getWidth()-7||Head_Y<=up_margin+5||Head_Y>getHeight()-5)
    {
		removeAll();
		GLabel label= new GLabel("GAME ENDED--- SCORE "+score);
		label.setFont("Times New Roman-24"); 
        label.setColor(Color.RED);
        setBackground(Color.WHITE);
        add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
		dead=true;
		pause(2000);
		return;
	 }
	
	 
	   // Check If Snake Eats the Prey
       //+5,-5 to accommodate breadth of snake(pixels);
	if(dir=='w')
	  {
		if(prey==getElementAt(Head_X,Head_Y)||prey==getElementAt(Head_X+5,Head_Y)) 
	  
	    	     {
	    	    	remove(prey);
	    	    	food=false;
	    	        count++;
	    	        ++score;
	    	        justAte_food=true;
	    	         return;    
	    	        
	             }
		
		if((score>40 && (mid_up==getElementAt(Head_X,Head_Y)||mid_up==getElementAt(Head_X+5,Head_Y)|| mid_down==getElementAt(Head_X,Head_Y)||mid_down==getElementAt(Head_X+5,Head_Y))))
		{
			removeAll();
			GLabel label= new GLabel("GAME ENDED--- SCORE "+score);
			label.setFont("Times New Roman-24"); 
	        label.setColor(Color.RED);
	        setBackground(Color.WHITE);
	        add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
			dead=true;
			pause(2000);
			return;
			
		}
	  }
	
	if(dir=='a')
	  {
		if(prey==getElementAt(Head_X,Head_Y)||prey==getElementAt(Head_X,Head_Y+5))
	  
	    	     {
	    	    	remove(prey);
	    	    	food=false;
	    	        count++;
	    	        ++score;
	    	        justAte_food=true;

	    	         return;    
	             }
		
		if((score>40 && (mid_up==getElementAt(Head_X,Head_Y)||mid_up==getElementAt(Head_X,Head_Y+5)|| mid_down==getElementAt(Head_X,Head_Y)||mid_down==getElementAt(Head_X,Head_Y+5))))
		{
			removeAll();
			GLabel label= new GLabel("GAME ENDED--- SCORE "+score);
			label.setFont("Times New Roman-24"); 
	        label.setColor(Color.RED);
	        setBackground(Color.WHITE);
	        add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
			dead=true;
			pause(2000);
			return;
			
		}
	  }
	
	   if(dir=='s')
	   {
		
		   if(prey==getElementAt(Head_X,Head_Y+5)||prey==getElementAt(Head_X+5,Head_Y+5))
				  
  	      {
  	    	remove(prey);
  	    	food=false;
  	        count++;
  	        ++score;
	        justAte_food=true;

  	         return;    
           }
		   
		   
			if((score>40 && (mid_up==getElementAt(Head_X,Head_Y+5)||mid_up==getElementAt(Head_X+5,Head_Y+5)|| mid_down==getElementAt(Head_X,Head_Y+5)||mid_down==getElementAt(Head_X+5,Head_Y+5))))
			{
				removeAll();
				GLabel label= new GLabel("GAME ENDED--- SCORE "+score);
				label.setFont("Times New Roman-24"); 
		        label.setColor(Color.RED);
		        setBackground(Color.WHITE);
		        add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
				dead=true;
				pause(2000);
				return;
				
			}
		
	   }
	   
	   if(dir=='d')
	   {
		   if(prey==getElementAt(Head_X+5,Head_Y)||prey==getElementAt(Head_X+5,Head_Y+5))
				  
	  	      {
			   
	  	    	remove(prey);
	  	    	food=false;
	  	        count++;
	  	        ++score;
    	        justAte_food=true;

	  	         return;    
	           }
		   
			if((score>40 && (mid_up==getElementAt(Head_X+5,Head_Y)||mid_up==getElementAt(Head_X+5,Head_Y+5)|| mid_down==getElementAt(Head_X+5,Head_Y)||mid_down==getElementAt(Head_X+5,Head_Y+5))))
			{
				removeAll();
				GLabel label= new GLabel("GAME ENDED--- SCORE "+score);
				label.setFont("Times New Roman-24"); 
		        label.setColor(Color.RED);
		        setBackground(Color.WHITE);
		        add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
				dead=true;
				pause(2000);
				return;
				
			}
		
		   
	   }
	
	       if(power)   
	   {
		  eat_Pow();
       }
	
 }
 
 
 
 
 
 void eat_Pow()
 {
	 if(dir=='w')
	  {
		if(splPow==getElementAt(Head_X,Head_Y)||splPow==getElementAt(Head_X+5,Head_Y)) 
	  
	    	     {
	    	    	remove(splPow);
	    	    	power=false;
	    	    	bool_power=true;
	    	        // return;    
	             }
	  }
	
	if(dir=='a')
	  {
		if(splPow==getElementAt(Head_X,Head_Y)||splPow==getElementAt(Head_X,Head_Y+5))
	  
	    	     {
	    	    	remove(splPow);
	    	    	bool_power=true;
	    	    	power=false;
	    	         //return;    
	             }
	  }
	
	   if(dir=='s')
	   {
		
		   if(splPow==getElementAt(Head_X,Head_Y+5)||splPow==getElementAt(Head_X+5,Head_Y+5))
				  
 	      {
 	    	remove(splPow);
 	    	power=false;
 	        bool_power=true;
 	         //return;    
          }
	   }
	   
	   if(dir=='d')
	   {
		   if(splPow==getElementAt(Head_X+5,Head_Y)||splPow==getElementAt(Head_X+5,Head_Y+5))
				  
	  	      {
	  	    	remove(splPow);
	  	    	bool_power=true;
	  	    	power=false;
	  	        // return;    
	           }
		   
	   }
	   
	   //bool_power means eaten splPow
	   
	   if(bool_power)
	   {
		   //println("hello");
		    pow_No=rgen.nextInt(1,3);
		    
		    switch(pow_No)
		    {
		      		case 1:
		      	{
		    	   score+=5;                //increase score
		    	   bool_power=false;
		    	   println("score");
		    	   
		    	        boost=new GLabel("BOOSTER ACTIVATED" );
		    		    boost.setFont("Sans Sarif-13"); 
		    	        boost.setColor(Color.BLUE);
		    	        
		    	        add(boost,70,10);
		    	        display_time=70;
		    	        pause(100);
		    	     break;
		      	}
		      
		      		case 2:
		      	{
		      		if(count>5)	
		    	   {
		      		  count/=2;             //decrease snake length
		    	      bool_power=false;
		    	     
		    	        zip=new GLabel("ZIPPER ACTIVATED" );
		    		    zip.setFont("Sans Sarif-13"); 
		    	        zip.setColor(Color.BLUE);
		    	        
		    	        add(zip,70,10);
		    	        pause(100);
		    	        display_time=70;
		    	      break;
		    	   }
		      	
		      	}
		      	
		      		case 3:
		      	{
		      	    delay=(float)2* delay; //increase time
		      	    boolPower_delay=true;
		    	    bool_power=false;

		    	      slow=new GLabel("SLOW-MO ACTIVATED" );
	    		      slow.setFont("Sans Sarif-13"); 
	    	          slow.setColor(Color.BLUE);
	    	          add(slow,70,10);
		      	    break;
		      	}
		      	
		      	
		    }
		    
	   }

 }

 private void addRecord()
 {
   
	 record.estab_connect();
	  
	    	
	    	try {
	    		
				   PreparedStatement pst= record.con.prepareStatement("insert into snake values(?,?)");
			
				   pst.setString(1,nickname);
			       pst.setInt(2,score);
			       
			       int status= pst.executeUpdate();

			   	if(status>0)
			    	{
			        		System.out.println("Record Updated");
			    	}


			   	else
			    	{
			            	System.out.println("Record Not Updated");
			    	}
			   	
				  
			        
	    	    } 
	    	catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	
	}
 
 
 private void retrieveRecord() 
	{
		try
		{
			
	    Statement stmt= (Statement) record.con.createStatement();
		ResultSet rs= stmt.executeQuery(" select * from snake order by high desc ");
		   
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
		removeAll(); 
		setBackground(Color.GRAY);

		GLabel hof= new GLabel("HALL OF FAME");
		hof.setFont("Sans Sarif-30");
		hof.setColor(Color.BLUE);
		add(hof,(getWidth()-hof.getWidth())/2,getHeight()/2-50);
		
		 try
		 {

		//Displaying TOP SCORE
			 
			GLabel first=new GLabel("1. "+score_name[0]+"   "+score_high[0]);
	        first.setFont("Sans Sarif-15");
			first.setColor(Color.RED);
			add(first,0,getHeight()/2);
			  int temp=0;
			  
			    while(temp<(getWidth()-first.getWidth())/2)
			    {
			    	temp+=10;
			    	first.move(10,0);
			    	pause(10);
			    }
			    
			    
		        GLabel second=new GLabel("2. "+score_name[1]+"   "+score_high[1]);
		        second.setFont("Sans Sarif-15");
				second.setColor(Color.yellow);
				add(second,0,getHeight()/2+30);
				 
				  temp=0;
				  
				    while(temp<(getWidth()-second.getWidth())/2)
				    {
				    	temp+=10;
				    	second.move(10,0);
				    	pause(10);
				    }

				    
				    GLabel third=new GLabel("3. "+score_name[2]+"   "+score_high[2]);
			        third.setFont("Sans Sarif-15");
					third.setColor(Color.BLACK);
					add(third,0,getHeight()/2+60);
					   temp=0;
					  
					    while(temp<(getWidth()-third.getWidth())/2)
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
 
 

 static Snakes obj;
 GObject chk;
 GRect recObj[]=new GRect[40];
 private boolean food,pressed,dead,power,bool_power,boolPower_delay,justAte_food; //power-splPower generated bool_power-splPower eaten
 char dir,correct_invalid;
 private RandomGenerator rgen ;
 private int Max_Height,Max_Length,snake_length,Head_X,Head_Y,food_X,food_Y,power_X,power_Y,tester=0;
 private int locX[],locY[],count,score=0,timer_Pow,pow_No,power_delay,gulp_counter,up_margin,display_time=0,db_count=0;
 private float delay;
 private GRect up,down,left,right,mid_up,mid_down; 
 private GRect prey,splPow;
 private GLabel scorecard,boost,zip,slow,highscore;
 private String nickname;
 private String score_name[];
 private int score_high[];
 private Database_connect record;
}

