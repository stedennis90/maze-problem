
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmartinezb
 */
public class Maze {
    
    public final static Logger log = Logger.getLogger(Maze.class.getName());

    public static Coordinate startPoint = new Coordinate(59, 82);
    public static Coordinate endPoint = new Coordinate(17, 25);
    
    Set<Coordinate> forbidden = new HashSet<>();
    List<Coordinate> solution = new ArrayList<>();
    
    public static char maze[][];    
    
    
    public boolean next(Coordinate currentPosition){
        if (currentPosition.x == endPoint.x && currentPosition.y == endPoint.y){
            solution.add(currentPosition);
            return true;
        }
        
        boolean finalized = false;        
        int x = currentPosition.x;
        int y = currentPosition.y; 
        
        Coordinate nextPosition;
        
        forbidden.add(currentPosition);
        
        nextPosition = new Coordinate(x+1, y);
        if( isValidCoord(y, nextPosition)){
            finalized = next(nextPosition);
        }
        
        if (!finalized){
            nextPosition = new Coordinate(x-1, y);
            if( isValidCoord(y, nextPosition)){
                finalized = next(nextPosition);
            }
        }
        
        if (!finalized){
            nextPosition = new Coordinate(x, y+1);
            if( isValidCoord(y+1, nextPosition)){
                finalized = next(nextPosition);
            }
        }
        
        if (!finalized){
            nextPosition = new Coordinate(x, y-1);
            if( isValidCoord(y-1, nextPosition)){
                finalized = next(nextPosition);
            }
        }
        
        if (finalized){
            solution.add(currentPosition);
        }
        return finalized;
        
    }
    
    
    public boolean isValidCoord(int row, Coordinate position) {
        log.log(Level.INFO, "Validating coord: {0},{1}", new Object[]{position.x, position.y});
        
        if (row <0 || row >= maze.length){
            log.log(Level.INFO,"Invalid row");
            return false;
        }
        
        if (position.x<0 || position.x>=maze[row].length ){
            log.log(Level.INFO, "Invalid X coord: {0},{1}", new Object[]{position.x, position.y});
            return false;
        }
        
        if (position.y<0 || position.y>=maze.length){
            log.log(Level.INFO, "Invalid Y coord: {0},{1}", new Object[]{position.x, position.y});
            return false;
        }
        
        if (Maze.maze[position.y][position.x]!='F'){
            log.log(Level.INFO, "Found a Wall: {0},{1}", new Object[]{position.x, position.y});
            return false;
        }
        
        if (forbidden.contains(position)){
            return false;
        }
        
        return true;
    }
    


    
    
    public static class Coordinate{
        int x;
        int y;      

        public Coordinate() {
        }
        
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return 86 * x + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj!=null || obj instanceof Coordinate ){
                Coordinate other = (Coordinate) obj;
                if (other.x == this.x && other.y == this.y){
                    return true;
                }
            }
            return false;            
        }

        @Override
        public String toString() {
            return "["+x+","+y+"] ";
        }
        
        
        
    }
    

}
