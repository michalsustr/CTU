/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrixcalculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;import java.util.logging.Level;
import java.util.logging.Logger;
;

/**
 *
 * @author michal
 */
public class Main {

	public static Scanner sc = new Scanner(System.in);
	public static boolean verbose = true;
	private static Map<Character, String> options;
	private static Map<Character, String> matrixInput;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		String equation = null;

		Main.options = new HashMap<Character, String>();
		Main.matrixInput = new HashMap<Character, String>();

		if(args.length > 0) {
			Character currentOption = null;
			for(String arg : args) {
				if(arg.equals("-h") || arg.equals("--help")) {
					showHelp();
					System.exit(0);
				}

				// options, something like -v
				if(currentOption == null && arg.length() > 1 && arg.charAt(0) == '-') {
					currentOption = arg.charAt(1);

					if(currentOption == 's' || currentOption == 'v') {
						Main.verbose = (currentOption == 'v');
						currentOption = null; // not expecting any paramater for this option
					}
				}
				// A=path/to/A.matrix
				else if(currentOption == null && arg.length() > 2 && arg.charAt(1) == '=') {
					Main.matrixInput.put(arg.charAt(0), arg.substring(2));
				}

				else if(currentOption != null) {
					Main.options.put(currentOption, arg);
					currentOption = null;
				}
				else { // equation
					equation = arg;
				}

			}
		}

		if(equation == null) {
			while((equation = sc.nextLine()).trim().isEmpty()) {
				System.out.println("Enter equation:");
			}
			System.out.println("");
		}
		
		MathParser parser = new MathParser(equation);
		Collection<Character> names;

		Map<Character, Matrix> matrices = new HashMap<Character, Matrix>();
		
		try {
			names = parser.getMatrices();
			
			for(Character name : names) {
				matrices.put(name, readMatrix(name));
				System.out.println("\nEntered matrix:");
				System.out.println(matrices.get(name));
			}

			Object result = parser.processPostfix(matrices);
			System.out.println("Result is:");
			System.out.println(result);

			if(Main.options.get('o') != null) { // save output
				File file = new File(Main.options.get('o'));
				try {
					System.out.println("Saving result to file "+file.getAbsoluteFile());
					Main.saveMatrix((Matrix) result,file);
				} catch(Exception e) {
					System.out.println("Cannot write result into file "+file.getAbsoluteFile());
					System.out.println(e);
				}
			}
		} catch (ParserException e) {
			System.err.println("Invalid equation input:");
			System.err.println(e.getMessage());
			System.out.println("");
			Main.saveInput(matrices);
		} catch(MatrixException e) {
			System.err.println("Invalid input:");
			System.err.println(e.getMessage());
			System.out.println("");
			Main.saveInput(matrices);
		} catch(Exception e) {
			System.err.println("An unkown error occured.");
			System.err.println(e);
			System.out.println("");
			Main.saveInput(matrices);
		}
    }

	private static Matrix readMatrix(Character name) {
		// get matrix from args A=path/to/A.matrix
		if(Main.matrixInput.containsKey(name)) {
			// try to load this file
			File file = new File(Main.matrixInput.get(name));
			if(file.exists()) {
				System.out.println("Loading matrix "+name+" from source file "+file.getAbsoluteFile());
				try {
					return loadMatrix(file);
				} catch(Exception e) {
					System.out.println("Problem loading matrix: ");
					System.out.println(e);
					e.printStackTrace();
				}
			} else {
				System.out.println("Cannot read matrix source file "+file.getAbsoluteFile());
			}
		}

		// get matrix from input directory
		if(Main.options.containsKey('i')) {
			// try to load this file
			File file = new File(Main.options.get('i')+"/"+name+".matrix");
			if(file.exists()) {
				System.out.println("Loading matrix "+name+" from source file "+file.getAbsoluteFile());
				try {
					return loadMatrix(file);
				} catch(Exception e) {
					System.out.println("Problem loading matrix: ");
					System.out.println(e);
				}
			} else {
				System.out.println("Cannot read matrix source file "+file.getAbsoluteFile());
			}
		}

		// get matrix from cmd input
		System.out.println("Enter matrix "+name+":");
		System.out.print("# of rows: ");
		int rows = sc.nextInt();
		System.out.print("# of columns: ");
		int cols = sc.nextInt();
		System.out.println("Enter "+rows*cols+" numbers:");

		Matrix matrix = new Matrix(rows, cols);
		for(int i = 0; i < rows*cols; i++) {
			matrix.insertItem(new Fraction(sc.nextInt()));
		}

		return matrix;
	}

	private static Matrix loadMatrix(File file) throws IOException, ParserException, MatrixException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		int rows = 0;
		int cols = -1;
		
		// get the size of matrix
		LineNumberReader ln = new LineNumberReader(in);
		while( (line = ln.readLine()) != null) {
			if(cols == -1) {
				String[] numbers = line.split("\\s");
				cols = 0;
				for(String number : numbers) {
					if(!number.isEmpty()) {
						cols++;
					}
				}
			}
			if(!line.isEmpty()) {
				rows++;
			}
		}
		ln.close();

		in = new BufferedReader(new FileReader(file));
		Fraction[] items = new Fraction[rows*cols];
		int i = 0;
		
		while( (line = in.readLine()) != null) {
			String[] numbers = line.split("\\s");
			
			// make sure that matrix has the same amount of columns everywhere
			if(cols != numbers.length) {
				throw new ParserException("Malformed matrix input");
			}

			for(String number : numbers) {
				if(number.isEmpty()) continue;
				
				// fraction number
				if(number.indexOf("/") != -1) {
					String[] fraction = number.split("/");
					if(fraction.length != 2) {
						throw new ParserException("Invalid fraction syntax on row "+rows);
					}

					items[i] = new Fraction( Integer.parseInt(fraction[0]), Integer.parseInt(fraction[1]) );
				} else {
				// normal number
					items[i] = new Fraction( Integer.parseInt(number) );
				}
				i++;
			}
			
		}

		in.close();
		return new Matrix(rows, cols, items);
	}

	private static void saveMatrix(Matrix matrix, File file) throws IOException, MatrixException {
		BufferedWriter wr = null;
		try {
			wr = new BufferedWriter(new FileWriter(file));
			wr.write(matrix.asFile());
			
			System.out.println("Matrix saved to file "+file.getAbsoluteFile());
		} finally {
			if(wr != null) {
				wr.close();
			}
		}
	}

	private static void saveInput(Map<Character, Matrix> matrices) {
		System.out.println("Saving matrices to /tmp:");

		File dir = new File("/tmp");
		if(!dir.exists()) {
			System.out.println("Sorry, /tmp does not exist");
			System.exit(1);
		}
		if(!dir.canWrite()) {
			System.out.println("Sorry, cannot write to /tmp");
			System.exit(1);
		}

		for (Character name: matrices.keySet()) {
	        Matrix matrix = matrices.get(name);
			File file = new File(dir.getAbsolutePath() + "/" +name+ ".matrix");

			try {
				file.createNewFile();

				if(!file.canWrite()) {
					System.out.println("Cannot write to file "+file.getAbsoluteFile());
				} else {
					try {
						Main.saveMatrix(matrix, file);
					} catch(Exception e) {
						System.err.println("Error while saving matrix to file.");
						System.err.println(e);
					}
				}
			} catch (IOException ex) {
				System.out.println("Cannot create file "+file.getAbsoluteFile());
			}

			
		}
	}

	private static void showHelp() {
		System.out.println("Usage: matrix [OPTION] [LOAD A=path/to/A.matrix] [EQUATION]");
		System.out.println("OPTION:");
		System.out.println("  -i input directory for matrices, looks for all *.matrix files");
		System.out.println("  -o output file for result matrix");
		System.out.println("  -s silent mode (do not show steps)");
		System.out.println("  -v verbose mode (default)");
		System.out.println("  -h displays this help");
		System.out.println("LOAD:");
		System.out.println("  set source files for each individual matrix");
		System.out.println("EQUATION:");
		System.out.println("  You can use any operation +-*/^ with numbers and matrices");
		System.out.println("  Example: A*B+C^-1*(E+F)");
		System.out.println("");
		System.out.println("Author: Michal Sustr, use this program for whatever you'd like to");
	}

	public static void verbose(Object output) {
		if(Main.verbose) {
			System.out.println(output);
		}
	}




}
