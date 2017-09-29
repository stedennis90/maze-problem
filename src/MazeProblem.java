
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class MazeProblem {

    public final static Logger log = Logger.getLogger(Maze.class.getName());
    public final static String TEST_FILE = "./resources/Hound Maze(tsv).txt";
    
    static Maze mazeProblem = new Maze();
   
    public static void main(String[] args) {
        run(args);        
    }    
    
     /**
     * @param args the command line arguments
     *  Position 0: start X position
     *  Position 1: start Y position
     *  Position 2: end X position
     *  Position 3: end Y position
     *  Position 4: file path (optional)
     */
    public static boolean run(String[] args) {
        boolean solucionado = false;
        log.setLevel(Level.WARNING);
        
        loadFile(args);
        
        //printArray(maze.maze);
        log.log(Level.INFO, "filas: {0}", Maze.maze.length);
        log.log(Level.INFO, "cols: {0}", Maze.maze[0].length);
        try{
            if (mazeProblem.next(Maze.startPoint)){
                log.log(Level.WARNING,"Solucionado...");
                solucionado = true;
                Collections.reverse(mazeProblem.solution);
                printList(mazeProblem.solution);
            } else {
                log.log(Level.SEVERE,"No tiene solución...");
            }
        } catch(Exception e){
            log.log(Level.SEVERE,"Error general");
            e.printStackTrace();
        }
        return solucionado;
    }
    
    
    private static void loadFile(String[] args) {
        File source=null;
        String filename = TEST_FILE;
        if(args!=null && args.length==5){
            Maze.startPoint = new Maze.Coordinate(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            Maze.endPoint = new Maze.Coordinate(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            filename = args[4];
        } else {
            //throw new IllegalArgumentException("Argumentos insuficientes");
        }
        log.log(Level.WARNING, ">>> >>> Loading {0}", filename);
        log.log(Level.WARNING, ">>> >>> Start Point {0}", Maze.startPoint);
        log.log(Level.WARNING, ">>> >>> End Point {0}", Maze.endPoint);
        
        
        
        source = new File(filename);
        
        ArrayList<String> lines=new ArrayList<>();        
        if( source.exists() && source.isFile() && source.canRead() ){
            try (BufferedReader in = new BufferedReader(new FileReader(source))) {
                loadLines(in, lines);
                Maze.maze = loadMaze(lines);
            } catch (FileNotFoundException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }            
        } else {
            System.out.println("Archivo no encontrado: " + filename);
        }
    }
    
    
    public static void printArray(char array[][]){
        System.out.println("\nARRAY ---------------------------------------");
        for(int y=0; y<array.length; y++){
            System.out.println("");
            for(int x=0; x<array[y].length; x++){                
                System.out.print("\t"+array[y][x]);
            }
        }
        System.out.println("\n---------------------------------------\n");
    }


    private static void printList(List<Maze.Coordinate> solution) {
        String response="";
        for(Maze.Coordinate coordinate: solution){
            response += (coordinate+",");
        }
        log.log(Level.WARNING, "Solution: [{0}]", response);
    }


    private static char[][] loadMaze(ArrayList<String> lines) {
        char maze[][]=null;
        
        if( !lines.isEmpty() ){
            maze = new char [lines.size()][];
            
            int fila = 0;
            for(String line: lines){
                String realLine="";
                for(int i=0; i<line.length(); i++){
                    char c = line.charAt(i);
                    if( c == 'F'){
                        i++;
                    } else {
                        c= '0';
                    }
                    realLine+=c;
                }
                maze[fila] = realLine.toCharArray();
                fila++;
            }
            
        }
        
        return maze;        
    }
    
    
    public static void loadLines(BufferedReader in, ArrayList<String> lineas){
        try{ 
            String line;
            while( (line=in.readLine()) !=null){
                lineas.add(line);
            }            
        } catch(Exception e){
        
        }
    }


    
    
}
