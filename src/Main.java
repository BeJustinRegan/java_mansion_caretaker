import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	Scanner scan = new Scanner(System.in);
	
	public Main() {
		
		ArrayList<CareTaker> careTakerList = new ArrayList<>();
		careTakerList.add(new Human(2));
		careTakerList.add(new Human(2));
		
		int robotAmount = 0;
		int humanAmount = 2;
		int chefRobotAmount = 0;
		int cleanerRobotAmount = 0;
		
		int remainingMove = 10;
		int cookingTask;
		int cleaningTask;
		
		// min : 2
		// max : 8
		
		do {
			cookingTask = (int)(Math.random() * (8 - 2 + 1) + 2);
			cleaningTask = (int)(Math.random() * (8 - 2 + 1) + 2);
		} while (cookingTask + cleaningTask != 8);
		
		boolean runMenu = true;
		
		int chooseMenu;
		
		do {
			System.out.println("Remaining moves: " + remainingMove);
			System.out.println("Cooking  tasks: " + cookingTask);
			System.out.println("Cleaning tasks: " + cleaningTask);
			System.out.println("--------------------------------------");
			System.out.println("Helper type                    Stamina");
			System.out.println("--------------------------------------");
			for (CareTaker careTaker : careTakerList) {
				if(careTaker instanceof Human) {
					System.out.println("class Human " + careTaker.stamina);
				}
				else if(careTaker instanceof ChefRobot) {
					System.out.println("class ChefRobot " + careTaker.stamina);
				}
				else {
					System.out.println("class CleanerRobot " + careTaker.stamina);
				}
			}
			System.out.println("---------------------------------------");
			System.out.println("1. Buy new robot");
			System.out.println("2. Recharge a robot");
			System.out.println("3. Perform a cooking task");
			System.out.println("4. Perform a cleaning task");
			System.out.println("5. Exit");
			
			do {
				System.out.println(">> ");
				chooseMenu = scan.nextInt();
				scan.nextLine();
			} while (chooseMenu > 5 || chooseMenu < 1);
			
			switch (chooseMenu) {
				case 1:{ // 1. Buy new robot
					if(robotAmount == 2) {
						System.out.println("You cannot buy more robots!");
					}
					else {
						int chooseRobot;
						System.out.println("Choose robot type:");
						System.out.println("1. Chef Robot");
						System.out.println("2. Cleaning Robot");
						
						do {
							System.out.println(">> ");
							chooseRobot = scan.nextInt();
							scan.nextLine();
						} while (chooseRobot > 2 || chooseRobot < 1);
						
						if(chooseRobot == 1) {
							careTakerList.add(new ChefRobot(3));
							System.out.println("You bought a chef robot!");
							chefRobotAmount++;
						}
						else {
							careTakerList.add(new CleanerRobot(3));
							System.out.println("You bought a cleaner robot!");
							cleanerRobotAmount++;
						}
						System.out.print("Press enter to continue...");
						scan.nextLine();
						
						remainingMove--;
						
					}
					break;
				}
				case 2:{ // 2. Recharge a robot
					int idx = 1;
					int robotRecharge = 0;
					if(robotAmount == 0) {
						System.out.println("You do not have any robots!");
					}
					else {
						System.out.println("---------------------------------");
						System.out.println("No.  Helper type          Stamina");
						System.out.println("---------------------------------");
						for(int i = 0 ; i < careTakerList.size(); i++) {
							if(careTakerList.get(i) instanceof CleanerRobot){
								System.out.println(idx + " class CleanerRobot " + careTakerList.get(i).stamina);
								idx++;
							}
							else if(careTakerList.get(i) instanceof ChefRobot){
								System.out.println(idx + " class ChefRobot " + careTakerList.get(i).stamina);
								idx++;
							}
						}
						System.out.println("---------------------------------");
						do {
							System.out.print("Enter robot number to recharge: ");
							robotRecharge = scan.nextInt();
							scan.nextLine();
						} while ((robotRecharge - 1) < 1 || (robotRecharge - 1) > idx);
						
						int idxRobot = 0;
						for(int i = 0 ; i < careTakerList.size(); i++) {
							if(careTakerList.get(i).stamina == 3) {
								System.out.println("This robot has full stamina");
							}
							else {
								Robot robot = (Robot)(careTakerList).get(i);
								robot.recharge();
								
								System.out.println("This robot is recharged (+1 stamina)");
								System.out.print("Press enter to continue...");
								scan.nextLine();
								
								remainingMove--;
							}
						}
					}
					break;
				}
				case 3:{ // 3. Perform a cooking task
					if(cookingTask == 0) {
						System.out.println("There are no cooking tasks left!");
					}
					else if(humanAmount == 0 && chefRobotAmount == 0) {
						System.out.println("There are no available helpers to cook!");
					}
					else {
						if(chefRobotAmount > 0) {
							for (int i = 0; i < careTakerList.size(); i++) {
								if(careTakerList.get(i) instanceof ChefRobot) {
									careTakerList.get(i).stamina--;
									
									cookingTask--;
									remainingMove--;
									chefRobotAmount--;
									
									if(careTakerList.get(i).stamina == 0) {
										careTakerList.remove(i);
									}
									
									System.out.println("Chef robot finished a cooking task!");
									System.out.print("Press enter to continue...");
									scan.nextLine();
									
									break;
								}
							}
						}
						else {
							for (int i = 0; i < careTakerList.size(); i++) {
								if(careTakerList.get(i) instanceof Human) {
									careTakerList.get(i).stamina--;
									
									cookingTask--;
									remainingMove--;
									humanAmount--;
									
									if(careTakerList.get(i).stamina == 0) {
										careTakerList.remove(i);
									}
									
									System.out.println("Human finished a cooking task!");
									System.out.print("Press enter to continue...");
									scan.nextLine();
									
									break;
								}
							}
						}
					}
					break;
				}
				case 4:{ // 4. Perform a cleaning task
					if(cleaningTask == 0) {
						System.out.println("There are no cleaning tasks left!");
					}
					else if(humanAmount == 0 && cleanerRobotAmount == 0) {
						System.out.println("There are no available helpers to clean!");
					}
					else {
						if(cleanerRobotAmount > 0) {
							for (int i = 0; i < careTakerList.size(); i++) {
								if(careTakerList.get(i) instanceof CleanerRobot) {
									careTakerList.get(i).stamina--;
									
									cookingTask--;
									remainingMove--;
									cleanerRobotAmount--;
									
									if(careTakerList.get(i).stamina == 0) {
										careTakerList.remove(i);
									}
									
									System.out.println("Cleaner robot finished a cleaning task!");
									System.out.print("Press enter to continue...");
									scan.nextLine();
									
									break;
								}
							}
						}
						else {
							for (int i = 0; i < careTakerList.size(); i++) {
								if(careTakerList.get(i) instanceof Human) {
									careTakerList.get(i).stamina--;
									
									cookingTask--;
									remainingMove--;
									humanAmount--;
									
									if(careTakerList.get(i).stamina == 0) {
										careTakerList.remove(i);
									}
									
									System.out.println("Human finished a cleaning task!");
									System.out.print("Press enter to continue...");
									scan.nextLine();
									
									break;
								}
							}
						}
					}
					break;
				}
				case 5:{ // 5. Exit
					runMenu = false;
					break;
				}
			}
			
			if(cookingTask == 0 && cleaningTask == 0) {
				System.out.println("You Win!!!");
				runMenu = false;
			}
			else if(remainingMove == 0){
				System.out.println("You Lose");
				runMenu = false;
			}
			
		} while (runMenu);
		
	}
	
}
