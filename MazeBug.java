package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown

	int[] priority = {1,1,1,1};

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		if(stepCount == 0){
			last = getLocation();
			ArrayList<Location> current = new ArrayList<Location>();
			current.add(last);
			crossLocation.add(current);
		}
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			Location old = getLocation();
			if(getValid(old).size() > 1){
				crossLocation.push(new ArrayList<Location>());
			}
			last = old;
			move();
			//increase step count when move 
			stepCount++;
			//
			ArrayList<Location> current = crossLocation.pop();
			current.add(getLocation());
			crossLocation.push(current);
			if(current.size() == 1){
				int a = old.getDirectionToward(getLocation()) / 90;
				priority[a]++;
			}
		} else {
			ArrayList<Location> current = crossLocation.pop();
			current.remove(current.size()-1);
			if(current.size() > 0)
				crossLocation.push(current);
			backTrack();
			stepCount++;
			if(current.size() == 0){
				int a = old.getDirectionToward(getLocation()) / 90;
				if(priority[a] > 1)
					priority[a]--;
			}
		}
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();

		int[] direction = {Location.NORTH,Location.EAST,Location.SOUTH,Location.WEST};
		if(crossLocation.size() > 0){
			ArrayList<Location> top = crossLocation.peek();
			for(int i = 0; i< 4;i++){
				Location nx = loc.getAdjacentLocation(direction[i]);
				if(gr.isValid(nx)){
					Actor actor = gr.get(nx);

					//
					if(actor instanceof Rock && actor.getColor().equals(Color.RED)){
						isEnd = true;
						next = nx;
						valid.Clear();
						valid.add(next);
						return valid;
					}

					//
					else if( actor == null && !top.contains(nx)){
						valid.add(nx);
					}
				}
			}
		}

		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		if(getValid(getLocation()).size() > 0)
			return true;
		return false;
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		//
		ArrayList<Location> valid = getValid(loc);
		//
		next = valid.get(getNext(valid));

		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else
			removeSelfFromGrid();
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}

	private int getNext(ArrayList<Location> valid){
		Random rand = new Random();

		int max = 0;
		int res = 0;
		int i = 0;
		for(Location loc : valid){
			int dir = getLocation().getDirectionToward(loc);
			if(priority[dir/90] > max){
				max = priority[dir/90];
				res = i;
			}
			i++;
		}
		return res;
	}

	private void backTrack(){
		Grid<Actor> gr = getGrid();
		Location loc = getLocation();
		setDirection(loc.getDirectionToward(last));
		moveTo(last);

		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, last);
	}
}
