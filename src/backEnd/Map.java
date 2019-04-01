package backEnd;
import java.util.ArrayList;

import entities.*;

/*
 * A game environment for entities to exist in. Each Map instance
 * contains a 2D array of tiles. Tiles represent the terrain of
 * the map which is an enumerated list of selectable landscape.
 * 
 * Entities can be binded to a Map and stored in a linear array,
 * Here, they are provided methods that allows for interaction with
 * neighboring entities. The front end job of the Map is to provide
 * data to the UI class on all render-able entities and tiles and their
 * positions and shapes.
 * 
 * @author Wyatt Phillips
 */
public class Map {

	public static final int DEFAULT_SIZE = 24;
	public static final Tile DEFAULT_TILE = Tile.GRASS;
	
	private int spawnX;
	private int spawnY;
	
	public enum Tile {
		STONE("S"),
		GRASS("G"),
		WATER("W");
		
		public final String printSymbol;
		
		Tile(String printSymbol) {
			this.printSymbol = printSymbol;
		}
	}
	
	private Tile[][] tilegrid;
	private ArrayList<Entity> entities;
	
	/*
	 * Constructor.
	 * 
	 * @param width The amount of tiles that expand horizontally
	 * @param height The amount of tiles that expand vertically
	 * @param spawnX The default spawn point X for all entities
	 * @param spawnY The default spawn point Y for all entities
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int width, int height, int spawnX, int spawnY, Tile fill) {
		this.entities = new ArrayList<Entity>();
		
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		tilegrid = new Tile[height][width];
		tileFill(fill);
	}
	
	/*
	 * Constructor.
	 * Spawn is automatically set to the center of the map.
	 * 
	 * @param width The amount of tiles that expand horizontally
	 * @param height The amount of tiles that expand vertically
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int width, int height, Tile fill) {
		this(width, height, width/2, height/2, fill);
	}
	
	/*
	 * Constructor.
	 * Aspect ratio of map is 1:1 and spawns are auto set to map center.
	 * 
	 * @param The amount of tiles that expand horizontally and vertically (size = width = height)
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int size, Tile fill) {
		this(size, size, fill);
	}
	
	/*
	 * Constructor.
	 * Width and Height are both set to DEFAULT_SIZE and spawn is centered.
	 * 
	 * @param fill The tile that will fill the entire map
	 */
	public Map(Tile fill) {
		this(DEFAULT_SIZE, DEFAULT_SIZE, fill);
	}
	
	/*
	 * Constructor.
	 * Width and Height are both set to DEFAULT_SIZE, spawn is centered, and default tile is DEFAULT_TILE.
	 */
	public Map() {
		this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_TILE);
	}
	
	/*
	 * Sets a tile at a valid position
	 * 
	 * @param x The x location of the tile (must be in bounds)
	 * @param y The y location of the tile (must be in bounds)
	 */
	public void setTile(int x, int y, Tile tile) {
		if(isValidPosition(x, y)) {
			tilegrid[y][x] = tile;
		}
	}
	
	/*
	 * Gets a tile from a valid position
	 * 
	 * @param x The x location of the tile (must be in bounds)
	 * @param y The y location of the tile (must be in bounds)
	 * @return The tile at (x,y)
	 */
	public Tile getTile(int x, int y) {
		if(isValidPosition(x, y)) {
			return tilegrid[y][x];
		} else {
			return null;
		}
	}
	
	/*
	 * Sets all tiles on the map to fill.
	 * 
	 * @param fill The tile that will replace every tile on the map.
	 */
	public void tileFill(Tile fill) {
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				setTile(x, y, fill);
			}
		}
	}
	
	/*
	 * Gets current width of the map in units of tiles
	 * 
	 * @return The amount of tiles that expand horizontally
	 */
	public int getWidth() {
		return tilegrid[0].length;
	}
	
	/*
	 * Gets current height of the map in units of tiles
	 * 
	 * @return The amount of tiles that expand vertically
	 */
	public int getHeight() {
		return tilegrid.length;
	}
	
	/*
	 * Checks if the provided position is in bounds of the map
	 * 
	 * @param x The x position on the map
	 * @param y The y position on the map
	 * @return if the (x,y) coordinate is in bounds of the map space
	 */
	public boolean isValidPosition(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}
	
	/*
	 * Obtains the default x spawn position for entities
	 * 
	 * @return x co-ordinate for spawn
	 */
	public int getDefaultSpawnX() {
		return spawnX;
	}
	
	/*
	 * Obtains the default y spawn position for entities
	 * 
	 * @return y co-ordinate for spawn
	 */
	public int getDefaultSpawnY() {
		return spawnY;
	}
	
	/*
	 * Sets the spawn location for all entities. Sets to
	 * previous of co-ordinates are invalid to map.
	 * 
	 * @param x The x co-ordinate for spawn (Must be in bounds)
	 * @param y The y co-ordinate for spawn (Must be in bounds)
	 */
	public void setDefaultSpawn(int x, int y) {
		if(isValidPosition(x, y)) {
			spawnX = x;
			spawnY = y;
		}
	}
	
	/*
	 * Adds an entity to the list. Can only be called under the following conditions:
	 * 1) Entity must not exist on another Map's list.
	 * 2) Entity must have it's map reference set to this Map instance.
	 * 3) Position must be valid to render properly.
	 * 
	 * @return The entity added to the list.
	 */
	public Entity addEntity(Entity entity) {
		if(!entities.contains(entity)) {
			entities.add(entity);	
			return entity;
		} else {
			return null;
		}
	}
	
	/*
	 * Removes the entity from the list. Assuming only one of the same reference exists.
	 * 
	 * @return The entity removed from the list.
	 */
	public Entity removeEntity(Entity entity) {
		if(entities.contains(entity)) {
			entities.remove(entity);
			return entity;
		} else {
			return null;
		}
		
	}
	/*
	 * Returns a ASCII visual of the map. For debugging purposes of UI is not working.
	 * (Ex):
	 * 
	 * | [G][G][S] |
	 * | [G][S][S] |
	 * | [G][G][S] |
	 * 
	 * @return A string that is a printer friendly map visual.
	 */
	@Override
	public String toString() {
		StringBuilder grid = new StringBuilder();
		
		String[][] overlay = new String[getHeight()][getWidth()];
		
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).getVisibility()) {
				overlay[entities.get(i).getPosY()][entities.get(i).getPosX()] = entities.get(i).getPrintSymbol();
			}
		}
		
		for(int y = 0; y < getHeight(); y++) {
			grid.append("| ");
			for(int x = 0; x < getWidth(); x++) {
				grid.append("[");
				
				if(overlay[y][x] != null) {
					grid.append(overlay[y][x]);
				} else {
					grid.append(getTile(x, y).printSymbol);
				}
				grid.append("]");
			}
			grid.append(" |\n");
		}
		
		return grid.toString();
	}
}
