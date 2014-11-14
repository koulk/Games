import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.media.*; 

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 


public class UFO extends GraphicsProgram 
{  
	final private int DELAY=20;

  public UFO(String user_name)
  {
 	 nickname= user_name;
 	
  }
  
 	public UFO() {
 	// TODO Auto-generated constructor stub
 } 
  
	public static void  main(String args[])
	    {
	    	obj=new UFO( );
	    	obj.start(args);
	    }
	    
    
	public void run()
	    {
		
		    instructions();
	    	setup_UFO();
	    	modeChoose();
	    	get_Highscore();
	    	
	    	while(smooth)
	    	{	
	    	
	    	   display_Highscore();
	    	  
	    	  if(getWidth()>900&& tester==0)
	    	       {
	    	    	 //flag=true;
	    	    	 tester=1;
	    	    	 setup_UFO();
	    	       }
	    	  
	    	       if(getWidth()<900&& tester==1)
	    	       {
	    	    	   //flag=false;
	    	    	   tester=0;
	    	    	   setup_UFO();
	    	       }
	    	       
	    	          
	    	          
	    	  firemove();
	    	  move_target();//written second so that is add on top canvas stack frame 
	    	  checkCollision();
	    	  pause(DELAY);
	    	  remove(scorecard);
	    	}
	    	
	    	addRecord();
	    	retrieveRecord();
	       display_Leaderboard();
	   //     this.exit();    	
	    
	    }
	    
	
	
	void instructions()
	{
	   GLabel obj[];
	   obj= new GLabel[4];
	   
	   
	   obj[0]=new GLabel("CLICK TO SHOOT",300,250);
	   obj[1]=new GLabel("LEFT--A",300,300);
	   obj[2]=new GLabel("RIGHT--D",300,350);
	   
	  obj[3]= new GLabel("CLICK ON SCREEN TO ENABLE CONTROLS",150,100);
	   for(int i=0;i<4;i++)
	   {
		   obj[i].setFont("COURIER-24");
		   obj[i].setColor(Color.BLUE);
		   add(obj[i]);
	   }
	   
	   pause(3000);
	   removeAll();
	   
	   
	   

	}
	private void get_Highscore()
	{
		//DATABASE CONNECTIVITY

		   record=new Database_connect();
		   record.estab_connect();
		   
		   score_name=new String[3];
		   score_high=new int[3];
		   
			try
			{
				
		       Statement stmt= (Statement) record.con.createStatement();
			   ResultSet rs= stmt.executeQuery(" select * from ufo order by high desc ");
			   
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
			       } 
			  
			      catch (SQLException e)
			    {
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
	    	 highscore.setColor(Color.MAGENTA);
	    	 add(highscore,10,getHeight());
	       }
	       
	     //In game New High score is achieved
	     
	        else
	       {
	        	highscore=new GLabel("NEW RECORD  "+nickname+" "+score);
	       	    highscore.setFont("Times New Roman-13");
	       	    highscore.setColor(Color.MAGENTA);
	       	    add(highscore,10,getHeight());
	        	
	       }
	     
	     
	     //Current scorecard
	     
	     scorecard=new GLabel("SCORE "+score+"");
   	     scorecard.setFont("Times New Roman-15");
   	     add(scorecard,(getWidth()-scorecard.getWidth()),getHeight());
           
	     
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
	 
	 
	    private void addRecord()
   {
	    	
	    	 
	    	try {
	    		   record.estab_connect();
				   PreparedStatement pst= record.con.prepareStatement("insert into ufo values(?,?)");
			
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
		ResultSet rs= stmt.executeQuery(" select * from ufo order by high desc ");
		
		        while(rs.next()&&db_count<3)
		        {
		        	score_name[db_count]=rs.getString(1);
		        	score_high[db_count]=rs.getInt(2);
		        	db_count++;
		        	
		        }
		        
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
		public void setup_UFO()
	    {   
	    	removeAll();
	    	test=getWidth()/200;
	    	xOffset=0;
	    	SIZE=getWidth()/30;
	 
	    	
	    	getWidth();
	    	getHeight();
	    	
	    	     fx=getWidth()/2-(SIZE/2);
	    	     fy=getHeight()-SIZE;//to accommodate ship/me
	    	
	    	me= new GRect((getWidth()/2)-2*SIZE,getHeight()-SIZE,4*SIZE,SIZE/2);
	    	me.setFilled(true);
	    	me.setFillColor(Color.BLUE);
	    	add(me);
	    	pause(DELAY);
	    	smooth=true;
	    	 
	    	   
	    	
	    	fire=false;
	    	addKeyListeners();
	    	addMouseListeners();
	    	setBackground(Color.LIGHT_GRAY);
	    	rgen=new RandomGenerator();
	    	
	    	           //DATABASE CONNECTIVITY

	                      record=new Database_connect();
	    	              record.estab_connect();
	   }
	    
	    void modeChoose()
	    {
	    	option=rgen.nextInt(1,3);
	    	switch(option)
	    	{
	        	case 1:
	        	       {
	        	    	   setup_target1();
	        		        break;
	        	       }
	        	       
	        	case 2:
	             	{
	             	    setup_target2();
	             	    break;
	             	}
	             	
	        	case 3:
	        	  {
	        		setup_target3();
	        		break;
	        	  }
	    	
	    	}
	    	
	    }
	    
	    
	  private void setup_target1()
	    {
	    	xcor=getWidth()-2*SIZE;
	    	ycor=0;
	    	rec=new GRect(xcor,ycor,SIZE,SIZE);
	    	rec.setFilled(true);
	    	add(rec);
	    	left=true;
	    	
	    }
	    
	 private void setup_target2() //vertical
	    {
	    	xcor=rgen.nextInt(10,700);
	    	//xcor=10;
		    ycor=0;
	    	rec=new GRect(xcor,ycor,SIZE,SIZE);
	    	rec.setFilled(true);
	    	add(rec);

	    	
	    }
	 
	 private void setup_target3() //Horizontal
	    {
	    	xcor=0;
	    	ycor=rgen.nextInt(20,380); //SIZE of me 2*SIZE keep space
	    	//ycor=10;
	    	rec=new GRect(xcor,ycor,SIZE,SIZE);
	    	rec.setFilled(true);
	    	add(rec);

	    	
	    }
	 
	 
	 
	 private void move_target()
	 {
		 
		 switch(option)
		 {
		 
		        case 1:
	        {
	    	    move_target1();
		        break;
	        }
	       
         	case 2:
   	      {
   	    	  move_target2();
   	    	  break;
   	      }
   	
         	case 3:
          {
        	  move_target3();
        	  break;
          }
	 
		 
	    }
		 
	 }
	   
	    private void move_target1()
	    {
	    	temp=xcor;
	    	
	    	if(left&&(temp-SIZE)>0)
	    	{
	    		xcor-=SIZE;
	    		remove(rec);
	    		rec.move(-SIZE,0);
	    		add(rec);
	    		
	    	}
	    	
	    	if(left&&(temp-SIZE)<0)
	    	{
	    		ycor+=SIZE;
	    		xcor=0;
	    		left=false;
	    		remove(rec);
	    		rec.setLocation(0,ycor);
	    		add(rec);
	    		
	    	}
	    	
	    	if(!left&&(temp+SIZE)<getWidth())
	    	{
	    		xcor+=SIZE;
	    		remove(rec);
	    		rec.move(SIZE,0);
	    		add(rec);
	    		
	    	}
	    	
	    	if(!left&&(temp+SIZE)>getWidth())
	    	{
	    	
	    		left=true;
	    		ycor+=SIZE;
	    		xcor=getWidth()-SIZE;
	    		remove(rec);
	    		rec.setLocation(xcor,ycor);
	    		add(rec);
	    		//pause(DELAY+20000);
	    	}
	    
	    }
	  
	  private void move_target2()
	  {
		  ycor+=SIZE/4;
  		  remove(rec);
  		  rec.move(0,SIZE/4);
  		  add(rec);
  		  pause(50);
  		
	  }
	  
	  private void move_target3()
	  {
		  xcor+=SIZE/4;
		  remove(rec);
		  rec.move(SIZE/4, 0);
		  add(rec);
		  pause(50);
	  }
	  
	    public void mouseClicked(MouseEvent e)
	    {
	    	if(!fire)
	    	{
	    		
	    		String path="C:\\Users\\Federer\\Desktop\\sound\\Gun.mp3";
	    		try{
	    		       File f=new File(path); 
	    		// Create a Player object that realizes the audio 
	    		
	    	       	final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
	    		// Start the music
	    		     p.start();
	    		     
	    		}
	    		
	    	
	    		  catch(Exception x)
	    		  {
	    		  }
	    		
	    		gun=new GRect(fx+xOffset,fy,SIZE,SIZE/10); //adjusting initial position
	    		fire=true;
	    		fx=fx+xOffset;
	    		
	    		
	    		
	    	
	    			
	    	 }
	    	
	    	
	    }
	    
	    public void keyTyped(KeyEvent e)
	    {
	    	int var=e.getKeyChar();;
	    	//println("hello");
	    	switch(var)
	    	{
	    	  case('a'):
	    	    {
	    		  
	    		  if(xOffset>(-getWidth()/2))
	    		  {
	    			  remove(me);
	    			  me.move(-test,0);
	    			  xOffset+=(-test);
	    			  add(me);
	    			  break;
	    			  
	    		  }		  
	    	    }
	    	
	    	  
	    	  case('d'):
	    	  { 
	    		     if(xOffset<(getWidth()/2))
	    		    {
	    			  remove(me);
	    			  me.move(test,0);
	    			  xOffset+=(test);
	    			  add(me);
	    			  break;
	    		    }
	    	    
	    	    }
	    	
	    	}
	    }
	    
	    
	    private void firemove()
	    {
	    	if(fire)
	    	{
	    		remove(gun);
	    		gun=new GRect(fx,fy,SIZE,SIZE/10);
	    		add(gun);
	    		gun.setColor(Color.MAGENTA);
	    		gun.setFilled(true);
	    		gun.setFillColor(Color.RED);
	    		fy-=20;
	        }
	    	
	    }
	    
	    private void checkCollision()
	    {
	    	
	    	for(int i=1;i<SIZE/10;i++)
	    	{
	    		if(rec==getElementAt(fx,fy+i)||rec==getElementAt(fx+SIZE/2,fy+i)||rec==getElementAt(fx+SIZE,fy+i))
	    		{
	    		 
	    		++score;
	    		rec.setColor(Color.YELLOW);
	    		
	    		String path="C:\\Users\\Federer\\Desktop\\sound\\Shotgun.mp3";
	    		try{
	    		       File f=new File(path); 
	    		// Create a Player object that realizes the audio 
	    		
	    	       	final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
	    		// Start the music
	    		     p.start();
	    		     
	    		}
	    		
	    	
	    		  catch(Exception x)
	    		  {
	    		  }
	    		pause(500);
	    		removeAll();
	    		setup_UFO();
	    		modeChoose();
	    		
	    		
	    		break;
		 		
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	if(fy<=0)
	    	{
	  
	    		fx=getWidth()/2;
		    	fy=getHeight();
		    	remove(gun);
	    		fire=false;   		
	    	}
	    	
	    	if(xcor>getWidth()||ycor>(getHeight()-2*SIZE))
	    	{
	    		smooth=false;
	    		removeAll();
	    		
	    		String path="C:\\Users\\Federer\\Desktop\\sound\\gameover.mp3";
	    		try{
	    		       File f=new File(path); 
	    		// Create a Player object that realizes the audio 
	    		
	    	       	final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
	    		// Start the music
	    		     p.start();
	    		     
	    		}
	    		
	    		 catch(Exception x)
	    		 {
	    			 println("Music File Missing");
	    		 }
	    		 
	    		
	    		GLabel label=new GLabel("UFO DESTROYED -"+score+"");    		
	    		label.setFont("Times New Roman-30");
	    		add(label,(getWidth()-label.getWidth())/2,getHeight()/2);
	    		
	    			
	    		
	    	}
	    }
	    

	
	private static UFO obj;
	private String score_name[];
	private int score_high[];
	private int score=0,xOffset,test;//offset used when ship moves left or right     
	private GRect gun; //width of gun should be SIZE for best performance current SIZE/2   
	private int ycor,xcor,temp,fx,fy,SIZE,option,tester=0,db_count=0;
	private GRect rec,me;
	private GLabel scorecard,highscore;
	private boolean smooth,left,fire,flag;
	private RandomGenerator rgen;
	private String nickname;
	private Database_connect record;

}