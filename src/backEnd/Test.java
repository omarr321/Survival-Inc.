package backEnd;

import java.util.ArrayList;

import backEnd.Map.Tile;
import entities.*;

public class Test {
	
	public static void playerMove() throws InterruptedException {
		int speed = 500;
		
		Map farm = new Map(10,10,1,3,Tile.GRASS);
		System.out.println(farm);
		
		Thread.sleep(speed);
		
		Player devin = new Player(farm, -2, 31);
		System.out.println(farm);
		
		Thread.sleep(speed);
		
		for(int i = 0; i < 12; i++) {
			if(!devin.move(0, 1)) {
				devin.goToSpawn();
			}
			System.out.println(farm);
			
			Thread.sleep(speed);
		}
		
	}
	
	public static void test2() {
		Map farm = new Map(10, Tile.WATER);
		farm.setName("Farmhouse");
		
		// refresh(farm);
		
		Stick stick1 = new Stick(farm, 2, 1);
		Stick stick2 = new Stick(farm, 3, 1);
		Stick stick3 = new Stick(farm, 4, 1);
		Stick stick4 = new Stick(farm, 2, 2);
		Stick stick5 = new Stick(farm, 3, 2);
		Stick stick6 = new Stick(farm, 4, 2);
		
		refresh(farm);
		
		Player brian = new Player(farm, 0, 1);
		refresh(farm);
		Stick yeet = new Stick(farm, 0, 1);
		refresh(farm);
	}
	
	public static void test3() {
		Map world = new Map(8, Tile.STONE);
		for(int i = 0; i < 3; i++) {
			new Stick(world, 2, 2+i);
			new Stick(world, 5+i, 6);
		}
		Player j = new Player(world,6,4);
		
		ArrayList yeet = world.getEntitiesInBounds(5, 2, 3, 4);
		yeet.removeIf(n -> n.getClass() != Stick.class);
		
		refresh(world);
	}
	
	public static void refresh(Map map) {
		System.out.printf("Map: %s%n%n", map.getName());
		System.out.println(map);
	}
	
	public static void consoleMode() {
		Map box = new Map(12, Tile.GRASS);
		box.setName("Mince Islands");
		
		Player me = new Player(box);
		new Stick(box, 1, 2);
		new Stick(box, 2, 2);
		new Stick(box, 3, 2);
		
		if(box.getEntitiesAtPos(3, 2).size() > 0) {
			box.getEntitiesAtPos(3, 2).get(0).setVisibility(false);
		}
		
		ArrayList<Entity> test = box.getAllEntities(false);
		
		refresh(box);
	}
	
	public static void main(String[] args) throws InterruptedException {
		test3();
		/*
		Map cartman = new Map(7, Tile.STONE);
		Map kyle = new Map(7, Tile.GRASS);
		
		Player me = new Player(cartman);
		
		Stick yeeted = new Stick(cartman, 0, 0);

		System.out.print(cartman);
		System.out.println();
		System.out.print(kyle);
		System.out.println();
		
		yeeted.moveToMap(kyle);
		Stick boi = new Stick(kyle, 2, 2);
		Stick man = new Stick(kyle, 3, 2);

		System.out.print(cartman);
		System.out.println();
		System.out.print(kyle);
		System.out.println();
		*/
	}
}