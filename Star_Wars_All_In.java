//Citlalli Trejo Del Rio
//Homework 4

import java.util.*;
public class game
{
	static String CLS = "\033[2J\033[1;1H";
	static String Red = "\033[31;1m";
	static String Green = "\033[32;1m";
	static String Yellow = "\033[33;1m";
	static String Blue = "\033[34;1m";
	static String Purple = "\033[35;1m";
	static String Cyan = "\033[36;1m";
	static String White = "\033[37;1m";
	static String Normal = "\033[0m"; // default gray color & reset background to black

	public static void main (String[] args)
	{
		Scanner in = new Scanner(System.in);
		char Choice = ' ';
		Player P = new Player("Trejo");

		// create enemies and put into World
		ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
		Enemies.add(new Enemy("Darth Maul"));
		Enemies.add(new Enemy("General Grievous"));
		Enemies.add(new Enemy("Palpatine"));
		Enemies.add(new Enemy("Darth Vader"));
		Enemies.add(new Enemy("Count Dooku"));
		//Enemies.add(new Enemy("Droids"));
		// screen
		Screens screen = new Screens();
		screen.DrawIntroScreen();
		screen.DrawLosingScreen();
		screen.DrawWinningScreen();

		while (Choice != 'q' && P.HP > 0 && Enemies.size() > 0) // main game loop
		{
			P.PrintWorld();
			//System.out.println(" ");
			System.out.println("Please use wasd keypads ^-^");
			System.out.println("w = up | a = left | s = down | d = left");
			System.out.println("HP: " +P.HP);
			System.out.print("Enter your command: ");

			// player move
			Choice = in.nextLine().charAt(0);
			if (Choice == 'd') // move right
				P.MoveRight();
			if (Choice == 'a')   // move left
				P.MoveLeft();
			if (Choice == 'w')   // move up
				P.MoveUp();
			if (Choice == 's')   // move down
				P.MoveDown();

			// attack
			// step through Enemy ArrayList and attack if adjacent to player
			// this belongs in the gameloop after the player has moved
			for (int i=0; i<Enemies.size(); i++)
			{
			   if ((Enemies.get(i).Ypos == P.Ypos && (Enemies.get(i).Xpos == P.Xpos+1)) || // player is to the left
			      (Enemies.get(i).Ypos == P.Ypos && (Enemies.get(i).Xpos == P.Xpos-1)) ||  // player is to the right
			      (Enemies.get(i).Xpos == P.Xpos && (Enemies.get(i).Ypos == P.Ypos+1)) ||  // player is above
			      (Enemies.get(i).Xpos == P.Xpos && (Enemies.get(i).Ypos == P.Ypos-1)))    // player is below
			   {
			      Enemies.get(i).HP -= P.Attack;    // Player attacks Enemy
			      P.HP -= Enemies.get(i).Attack;    // Enemy attacks Player

			      // Here is a more advanced attack formula that utilizes attack and armor values.
			      //P.HP -= (100 * Enemies.get(i).Attack) / (100 + P.Armor);

			      if (Enemies.get(i).HP <= 0)    // Enemy dies
			      {
			         P.World[Enemies.get(i).Xpos][Enemies.get(i).Ypos] = ' ';
			         Enemies.remove(i);
			      }
			   }
			}

			// enemies move toward player
			// step through Enemy ArrayList and move towards the player
			// optionally add a range variable so enemies only move towards player if within range
			for (int i=0; i<Enemies.size(); i++)
			{
			   // check if enemy is in range
			   if (Math.abs(Enemies.get(i).Xpos - P.Xpos) <= Enemies.get(i).Range
			   	   && Math.abs(Enemies.get(i).Ypos - P.Ypos) <= Enemies.get(i).Range)
			{

			   if (Enemies.get(i).Xpos > P.Xpos)
			      Enemies.get(i).MoveLeft();
			   else
			      Enemies.get(i).MoveRight();
			   if (Enemies.get(i).Ypos > P.Ypos)
			      Enemies.get(i).MoveUp();
			   else
			      Enemies.get(i).MoveDown();
			}
			else // enemy moves random
				{
					int R = (int)(Math.random()*8)+1;
					if (R == 1)
						Enemies.get(i).MoveLeft();
					else if (R == 2)
						Enemies.get(i).MoveRight();
					else if (R == 3)
						Enemies.get(i).MoveUp();
					else if (R == 4)
						Enemies.get(i).MoveDown();
				}
			}



		}

		P.PrintWorld();
		if (P.HP <= 0)
			screen.DrawLosingScreen();
		else if (Enemies.size() <=0)
			screen.DrawWinningScreen();
	}
}

// gameobject class

class GameObject
{
	static String CLS = "\033[2J\033[1;1H";
	static String Red = "\033[31;1m";
	static String Green = "\033[32;1m";
	static String Yellow = "\033[33;1m";
	static String Blue = "\033[34;1m";
	static String Purple = "\033[35;1m";
	static String Cyan = "\033[36;1m";
	static String White = "\033[37;1m";
	static String Normal = "\033[0m"; // default gray color & reset background to black
	static String WhiteOnRed = "\033[41;1m";
	static String WhiteOnGreen = "\033[42;1m";
	static String WhiteOnYellow = "\033[43;1m";
	static String WhiteOnBlue = "\033[44;1m";
	static String WhiteOnPurple = "\033[45;1m";
	static String WhiteOnCyan = "\033[46;1m";
	static char[][] World = new char[40][20];
	int Xpos, Ypos;
	int HP, Attack, Armor, Speed;
	char Avatar;

	void PrintWorld()
	{
		System.out.print(CLS);
		for (int y=0; y<=19; y++)
		{
			for (int x=0; x<=39; x++)
			{
				if (World[x][y] == ' ')
					System.out.print("  ");
				else if (World[x][y] == '~')
					System.out.print(WhiteOnBlue+"  "+Normal);
				else if (World[x][y] == '~')
					System.out.print(WhiteOnBlue+"  "+Normal);
				else if (World[x][y] == 'D')
					System.out.print(Red+"D "+Normal);
				else if (World[x][y] == 'G')
					System.out.print(Green+"G "+Normal);
				else if (World[x][y] == 'P')
					System.out.print(Yellow+"P "+Normal);
				else if (World[x][y] == 'V')
					System.out.print(Red+"V "+Normal);
				else if (World[x][y] == 'C')
					System.out.print(Cyan+"C "+Normal);
				else if (World[x][y] == '+')
					System.out.print(Purple+"+ "+Normal);
				else if (World[x][y] == '*')
					System.out.print(Blue+"* "+Normal);
				else if (World[x][y] == 'r')
					System.out.print(Green+"r "+Normal);
				else if (World[x][y] == 's')
					System.out.print(Red+"s "+Normal);
				else if (World[x][y] == '.')
					System.out.print(". ");
				else if (World[x][y] == 'T')
					System.out.print(Purple + "T " + Normal);
				else
					System.out.print(World[x][y] + " ");
			}
			System.out.println(); // newline after printing each row
		}
	}

	void InitializeWorld()
	{
		//fill World with spaces

		for (int y=0; y<=19; y++)
		{
			for (int x=0; x<=39; x++)
			{
				if (y==0 || y ==19) // top or bottom border
					World[x][y] = '~';
				else
					World[x][y] = ' ';
				if (x==0 || x==39) // side border
					World[x][y] = '~';
			}
		}
		// create a medkit in a random location
		for (int i=1; i<=12; i++)
			World[(int)(Math.random()*38)+1][(int)(Math.random()*18)+1] = '+';

		// small particle wave of explosions
		//int a = (int)(Math.random()*22)+3;
		//int b = (int)(Math.random()*16)+2;
		//World[a][b]= '*'; World[a+1][b] = '*'; World [a+2][b] = '*';
		//World[a][b+1] = '*'; World[a+1][b+1] = '*'; World[a+2][b+1] = '*';
		//World[a][b+2] = '*'; World[a+1][b+2] = '*'; World[a+2][b+2] = '*';

		// create droids damage will be minimal
		for (int i=1; i<=4; i++)
			World[(int)(Math.random()*38)+1][(int)(Math.random()*18)+1] = 'r';

		// create storm troopers damage will be minimal
		for (int i=1; i<=4; i++)
			World[(int)(Math.random()*38)+1][(int)(Math.random()*18)+1] = 's';

		// create stars, we will be able to pass over them; no attack or HP
		for (int i=1; i<=30; i++)
			World[(int)(Math.random()*38)+1][(int)(Math.random()*18)+1] = '.';

	} // void end

} // class GameObject end

// player class

class Player extends GameObject
{
	String Name;

	Player (String theName)
	{
		Name = theName;
		HP = 300;
		Armor = 200;
		Xpos =1; Ypos =1;
		Attack = 50;
		Avatar = 'T';
		InitializeWorld();
		World[Xpos][Ypos] = Avatar; // put new player avatar into World
	}
	// player movement
	void MoveRight()
		{
			if (World[Xpos+1][Ypos] == ' ' ||(World[Xpos+1][Ypos] == '+' || (World[Xpos+1][Ypos] == 'r' || (World[Xpos+1][Ypos] == 's' || (World[Xpos+1][Ypos] == '.')))))
			{
				if(World[Xpos+1][Ypos] == '+')
					HP += 25;
				if(World[Xpos+1][Ypos] == 'r')
					HP -= 10;
				if(World[Xpos+1][Ypos] == 's')
					HP -=15;
				World[Xpos][Ypos] = ' ';
				Xpos++;
				World[Xpos][Ypos] = Avatar;
			}
		}
		void MoveLeft()
		{
			if (World[Xpos-1][Ypos] == ' ' ||(World[Xpos-1][Ypos] == '+' || (World[Xpos-1][Ypos] == 'r' || (World[Xpos-1][Ypos] == 's' || (World[Xpos-1][Ypos] == '.')))))
			{
				if(World[Xpos-1][Ypos] == '+')
					HP += 25;
				if(World[Xpos-1][Ypos] == 'r')
					HP -= 10;
				if(World[Xpos-1][Ypos] == 's')
					HP -= 15;
				World[Xpos][Ypos] = ' ';
				Xpos--;
				World[Xpos][Ypos] = Avatar;
			}
		}
		void MoveUp()
		{
			if (World[Xpos][Ypos-1] == ' ' || (World[Xpos][Ypos-1] == '+' || (World[Xpos][Ypos-1] == 'r' || (World[Xpos][Ypos-1] == 's' || (World[Xpos][Ypos-1] == '.')))))
			{
				if(World[Xpos][Ypos-1] == '+')
					HP += 25;
				if(World[Xpos][Ypos-1] == 'r')
					HP -= 10;
				if(World[Xpos][Ypos-1] == 's')
					HP -= 15;
				World[Xpos][Ypos] = ' ';
				Ypos--;
				World[Xpos][Ypos] = Avatar;
			}
		}
		void MoveDown()
		{
			if (World[Xpos][Ypos+1] == ' ' || (World[Xpos][Ypos+1] == '+' || (World[Xpos][Ypos+1] == 'r' || (World[Xpos][Ypos+1] == 's' || (World[Xpos][Ypos+1] == '.')))))
			{
				if(World[Xpos][Ypos+1] == '+')
					HP += 25;
				if(World[Xpos][Ypos+1] == 'r')
					HP -= 10;
				if(World[Xpos][Ypos+1] == 's')
					HP -= 15;
				World[Xpos][Ypos] = ' ';
				Ypos++;
				World[Xpos][Ypos] = Avatar;
			}
	}
} // player class ends


//  enemy class

class Enemy extends GameObject
{
	String Type;
	int Range;

	Enemy (String theType) // constructor
	{
		Type = theType;
		Xpos = (int)(Math.random()*38)+ 1;
		Ypos = (int)(Math.random()*18)+ 1;
		if (Type.equals("Darth Maul"))
		{
			Avatar = 'D'; HP = 100; Attack = 25; Armor = 10; Range = 8;
		}
		if (Type.equals("General Grievous"))
		{
			Avatar = 'G'; HP = 100; Attack = 25; Armor = 10; Range = 10;
		}
		if (Type.equals("Palpatine"))
		{
			Avatar = 'P'; HP = 100; Attack = 50; Armor = 10; Range = 10;
		}
		if (Type.equals("Darth Vader"))
		{
			Avatar = 'V'; HP = 150; Attack = 50; Armor = 20; Range = 12;
		}
		if (Type.equals("Count Dooku"))
		{
			Avatar = 'C'; HP = 100; Attack = 25; Armor = 10; Range = 8;
		}
		//if (Type.equals("Droids"))
		//{
			//Avatar = 'R'; HP = 50; Attack = 10; Armor = 0; Range = 2;
		//}
		World[Xpos][Ypos] = Avatar; // put new enemy's avatar into World
	}// enemy type ends
	// enemy movement
	void MoveRight()
		{
			if (World[Xpos+1][Ypos] == ' ')
			{
				World[Xpos][Ypos] = ' ';
				Xpos++;
				World[Xpos][Ypos] = Avatar;
			}
		}
		void MoveLeft()
		{
			if (World[Xpos-1][Ypos] == ' ')
			{
				World[Xpos][Ypos] = ' ';
				Xpos--;
				World[Xpos][Ypos] = Avatar;
			}
		}
		void MoveUp()
		{
			if (World[Xpos][Ypos-1] == ' ')
			{
				World[Xpos][Ypos] = ' ';
				Ypos--;
				World[Xpos][Ypos] = Avatar;
			}
		}
		void MoveDown()
		{
			if (World[Xpos][Ypos+1] == ' ')
			{
				World[Xpos][Ypos] = ' ';
				Ypos++;
				World[Xpos][Ypos] = Avatar;
			}
 		}

} // enemy class ends
// Intro screen
class Screens
{
	public static final String CLS = "\033[2J\033[1;1H";
	public static final String Red = "\033[31;1m";
	public static final String Green = "\033[32;1m";
	public static final String Yellow = "\033[33;1m";
	public static final String Blue = "\033[34;1m";
	public static final String Purple = "\033[35;1m";
	public static final String Cyan = "\033[36;1m";
	public static final String White = "\033[37;1m";
	public static final String Normal = "\033[0m"; // default gray color & reset background to black
	void DrawIntroScreen()
	{
		System.out.print(CLS+Yellow);
		System.out.println("		.		      ________________.  ___     .______	.");
		System.out.println("	.			     /                | /   \\    |   _  \\			.");
		System.out.println("			.	    |   (-----|  |----`/  ^  \\   |  |_)  |	.			.");
		System.out.println("	.			     \\   \\    |  |    /  /_\\ \\  |      /		.");
		System.out.println("				.-----)   |   |  |   /  _____  \\ |  |\\  \\-------.");
		System.out.println(".			.	|________/    |__|  /__/     \\__\\| _| `.________|		.		.");
		System.out.println("		.		 ____    __    ____  ___     .______    ________.");
		System.out.println("				 \\   \\  /  \\  /   / /   \\    |   _  \\  /        |	.		.");
		System.out.println("	.		.	  \\   \\/    \\/   / /  ^  \\   |  |_)  ||   (-----`		.		.");
		System.out.println("				   \\            / /  /_\\  \\  |      /  \\   \\		.			");
		System.out.println(".			.	    \\    /\\    / /  _____  \\ |  |\\  \\---)   |		.");
		System.out.println("	.			     \\__/  \\__/ /__/     \\__\\|__| `._______/	.		.");
		System.out.print(Normal);
		System.out.println(" ");
		System.out.println("					Let's make the galaxy safer!");
		System.out.println("					    Destroy the enemies!");
		System.out.println("					  Be careful of the droids!");
		System.out.println("						*roger roger*");
		System.out.println(" ");
		System.out.println(Blue+"Enemies			HP	Attack"+Normal);
		System.out.println(" ");
		System.out.println(Red+"D "+Normal+"Darth Maul		100	25");
		System.out.println(Green+"G "+Normal+"General Grievous	100	25");
		System.out.println(Yellow+"P "+Normal+"Palpatine		100	25");
		System.out.println(Red+"V "+Normal+"Darth Vader		150	50");
		System.out.println(Cyan+"C "+Normal+"Count Dooku		100	25");
		System.out.println(Green+"r "+Normal+"Droids		50	10");
		System.out.println(Red+"s "+Normal+"Stormtroopers		50	15");
		System.out.println(" Items");
		System.out.println(Purple+"+ "+Normal+"+25 HP");
		System.out.println("Press <Enter> To Begin");
		Scanner in = new Scanner(System.in);
		String T = in.nextLine();
	}
//losing screen
void DrawLosingScreen()
{
	System.out.println(CLS + Red);
	//System.out.println("__   __            _                       _           _  ");
	//System.out.println("\\ \\ / /           | |                     | |         | |");
	//System.out.println(" \\ V /___  _   _  | |__   __ ___   _____  | | ___  ___| |_");
	//System.out.println("  \\ // _ \\| | | | | '_ \\ / _` \\ \\ / / _ \\ | |/ _ \\/ __| __|");
	//System.out.println("  | | (_) | |_| | | | | | (_| |\\ V /  __/ | | (_) \\__ \\ |_ ");
	//System.out.println("  \\_/\\___/ \\__,_| |_| |_|\\__,_| \\_/ \\___| |_|\\___/|___/\\__|");
	System.out.println("            ________   ___   ____");
	System.out.println("           / __   __| / _ \\ |  _ \\");
	System.out.println("     ______> \\ | |   |  _  ||    /_____________________________");
	System.out.println("    / _______/ |_|   |_| |_||_|\\______________________________ \\");
	System.out.println("   / /                                                        \\ \\");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                  better luck next time                   | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("   \\ \\____________________________    _   ___   ____   _______/ /");
	System.out.println("    \\___________________________  |  | | / _ \\ |  _ \\ / _______/");
	System.out.println("                                | |/\\| ||  _  ||    / > \\        LS");
System.out.println("                                 \\_/\\_/ |_| |_||_|\\_\\|__/");
	System.out.println(Normal);
}
//winning sccreen
void DrawWinningScreen()
{
	System.out.println(CLS + Blue);
	System.out.println("            ________   ___   ____");
	System.out.println("           / __   __| / _ \\ |  _ \\");
	System.out.println("     ______> \\ | |   |  _  ||    /_____________________________");
	System.out.println("    / _______/ |_|   |_| |_||_|\\______________________________ \\");
	System.out.println("   / /                                                        \\ \\");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                 you have saved the galaxy                | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("  | |                                                          | |");
	System.out.println("   \\ \\____________________________    _   ___   ____   _______/ /");
	System.out.println("    \\___________________________  |  | | / _ \\ |  _ \\ / _______/");
	System.out.println("                                | |/\\| ||  _  ||    / > \\        LS");
	System.out.println("                                 \\_/\\_/ |_| |_||_|\\_\\|__/");
}

}