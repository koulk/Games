import acm.program.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.*;

import acm.graphics.*;


public class frontpage extends Program
 {
	 
	 public static void main(String args[])
	 {
		 new frontpage().start(args);	
		 arg=args;
	 }
	 
	 
	public void init()
	{
		  setLayout(new GridLayout(1,3));	 
		 
		 ufo= new JButton("UFO");
		 snake=new JButton("SNAKES");
		 hangman=new JButton("HANGMAN");
		 
		 add(ufo);
		 add(snake);
		 add(hangman);
		 try{
			
		 String path="C:\\Users\\Federer\\Desktop\\sound\\Catch.mp3";
			File f=new File(path); 
			// Create a Player object that realizes the audio 
			final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
			// Start the music
			p.start();
			
		     }
		 catch(Exception e)
		 { 
		 }
		 
		 addActionListeners(this);
		
	}
	 public void run()
	 {
		 
		   while(option!=4)
		   {   
			   if(option==1)
			   {
				   option=0;
        try
           { 
				   do{
				          username=JOptionPane.showInputDialog(this, "NICKNAME:","FEDERER");
				    }
				    
				    while(username.length()==0);
				   
					  
					      game1=new UFO(username);
					      game1.start(arg);
           }
        
					   catch(Exception e)
					     {
					    	 new UFO("ANONYMOUS").start(arg);
					     }
					
			   }
			
			if(option==2)
				{
				
				try{
				String path="C:\\Users\\Federer\\Desktop\\sound\\Catch.mp3";
				File f=new File(path); 
				// Create a Player object that realizes the audio 
				final Player p=Manager.createRealizedPlayer(f.toURI().toURL());
				// Start the music
				p.start();
				
			     }
			 catch(Exception e)
			 { 
			 }
			 
				    option=0;
			try
			{
				    do{
				          username=JOptionPane.showInputDialog(this, "NICKNAME:","FEDERER");
				    }
				    
				    while(username.length()==0);
				  
				
				      game2=new Snakes(username);
				      game2.start(arg);
	   	  }
				   catch(Exception e)
				     {
   					    println("hi");
				    	 new Snakes("ANONYMOUS").start(arg);
				     }
				     
				}
			
			 if(option==3)
			    {
				   option=0;
				  try{
					  
				   do{
				          username=JOptionPane.showInputDialog(this, "NICKNAME:","FEDERER");
				    }
				    
				    while(username.length()==0);
				   
				  
				   game3=new Hangman(username);
				   game3.start(arg);
				  
				  }
				  
				     catch(Exception e)
				     {
				    	 new Hangman("ANONYMOUS").start(arg);
				     }
				     
			    }
			 
			 
		    }
		
	 }
	 
	 public void actionPerformed(ActionEvent e)
	 {
		 String str= e.getActionCommand();
		   
		     if(str.equals("UFO"))
		 {
			option=1;	 
			 
		 }
		 
		    if(str.equals("SNAKES"))
		 {
			 
		   option=2;
		 }
		 

		    if(str.equals("HANGMAN"))
		 {
			 
		   option=3;
		 }
		 
	 }

	 static int option=0;
	 static String arg[];
	 static JButton ufo,snake,hangman;
	 public String username;
	 private UFO game1;
	 private Snakes game2;
	 private Hangman game3;
	 
 }
  