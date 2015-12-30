/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrixcalculator;

import java.util.Arrays;

/**
 *
 * @author michal
 */
public class Matrix implements Cloneable {
	private Fraction[][] items;
	/* [row,col]:
	 * [0,0] [0,1] [0,2] ...
	 * [1,0] [1,1] [1,2] ...
	 * ...
	 */
	
	private int rows;
	private int cols;
	private int index = 0; // [0,0] has index 0, [n,n] has index (n+1)*(n+1)-1

	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.items = new Fraction[rows][cols];
	}

	public Matrix(int rows, int cols, Fraction[] items) throws MatrixException {
		this.rows = rows;
		this.cols = cols;
		this.items = new Fraction[rows][cols];
		for(int i = 0; i < items.length; i++) {
			this.insertItem(items[i]);
		}
		if(this.index != items.length) {
			throw new MatrixException("Not enough items specified");
		}
	}

	public static Matrix identityMatrix(int n) {
		Matrix matrix = new Matrix(n,n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				matrix.insertItem( new Fraction( (i==j) ? 1 : 0 ));
			}
		}
		return matrix;
	}

	public final int getRows() {
		return this.rows;
	}

	public final int getCols() {
		return this.cols;
	}

	public final void insertItem(Fraction item) {
		if(index >= rows*cols) {
			throw new ArrayIndexOutOfBoundsException("Index "+index+" is greater than number of rows ("+rows+") and cols("+cols+")");
		}

		int row = (int) Math.floor( index/cols );
		int col = index - row*cols;

		// make sure that at this position there is no item already set
		if(this.items[row][col] != null) {
			throw new InternalError("There should be no item at ["+row+"]["+col+"] (for index "+index+")");
		}

		this.items[row][col] = item;
		
		index++;
	}

	public boolean isItemAt(int i, int j) {
		if(i >= this.rows || i < 0) return false;
		if(j >= this.cols || j < 0) return false;
		if(this.items[i][j] == null) return false;
		
		return true;
	}

	public final Fraction getItemAt(int row, int col) throws IllegalAccessException {
		if(this.items[row][col] == null) {
			throw new IllegalAccessException("Cannot access item at ["+row+"]["+col+"], it has not been initialized");
		}

		return this.items[row][col];
	}

	public final boolean setItemAt(Fraction item, int row, int col) {
		boolean exists = this.items[row][col] != null;
		this.items[row][col] = item;
		return exists;
	}
	/**
	 * @param row counted from 0
	 * @return row items
	 */
	public final Fraction[] getRow(int row) {
		return this.items[row];
	}

	/**
	 * @param col counted from 0
	 * @return col items
	 */
	public final Fraction[] getCol(int col) {
		Fraction[] result = new Fraction[this.rows];

		for(int i = 0; i < this.rows; i++) {
			result[i] = this.items[i][col];
		}

		return result;
	}

	public final Fraction[][] getItems() {
		return this.items;
	}

	public final Fraction[] getLinearItems() {
		Fraction[] result = new Fraction[this.rows*this.cols];
		int cnt = 0;
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				result[cnt++] = this.items[i][j];
			}
		}
		return result;
	}

	public final Fraction sumItems() {
		Fraction result = new Fraction(0);
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				result = result.plus(this.items[i][j]);
			}
		}
		return result;
	}

	/*
	 * Main diagonal is
	 * x a a a
	 * a x a a
	 * a a x a
	 * a a a x
	 *
	 * or if matrix is not square
	 * x a a a a
	 * a x a a a
	 * a a x a a
	 */
	public final Fraction[] getMainDiagonal() {
		Fraction[] result = new Fraction[this.rows];

		for(int i = 0; i < this.rows; i++) {
			result[i] = this.items[i][i];
		}

		return result;
	}

	public final void plus(Fraction number) {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				this.items[i][j] = this.items[i][j].plus(number);
			}
		}
	}

	public final void plus(Matrix matrix) throws MatrixException, IllegalAccessException {
		// check that the matrices are of the same dimensions
		if(matrix.getRows() != this.rows || matrix.getCols() != this.cols) {
			throw new MatrixException("Matrices are not of the same dimensions");
		}

		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				this.items[i][j] = this.items[i][j].plus(matrix.getItemAt(i, j));
			}
		}
	}

	public final void plus(Object obj) throws Exception, MatrixException {
		if(obj instanceof Matrix) {
			this.plus((Matrix) obj);
		} else if (obj instanceof Fraction) {
			this.plus((Fraction) obj);
		} else throw new Exception();
	}

	public final void times(Fraction number) {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				this.items[i][j] = this.items[i][j].times(number);
			}
		}
	}

	public final void times(int row, Fraction number) {
		for(int j = 0; j < this.cols; j++) {
			this.items[row][j] = this.items[row][j].times(number);
		}
	}

	public final Matrix times(Matrix matrix) throws MatrixException, IllegalAccessException {
		// check that the matrices are of compatible dimensions
		if(this.cols != matrix.getRows()) {
			throw new MatrixException("Matrices are not of compatible dimensions"+((this.rows == matrix.getCols()) ? ". Maybe wrong order for multiplication?" : ""));
		}

		Matrix result = new Matrix(this.rows, matrix.getCols());
		Fraction item;
		for(int x = 0; x < result.getRows(); x++) {
			for(int y = 0; y < result.getCols(); y++) {
				item = new Fraction(0);
				for(int i = 0; i < this.cols; i++) {
					item = item.plus( this.items[x][i].times(matrix.getItemAt(i, y)) );
				}
				result.setItemAt(item, x, y);
			}
		}

		return result;
	}

	public final void times(Object obj) throws Exception, MatrixException {
		if(obj instanceof Matrix) {
			this.times((Matrix) obj);
		} else if (obj instanceof Fraction) {
			this.times((Fraction) obj);
		} else throw new Exception();
	}

	public final Matrix power(int power) throws MatrixException, IllegalAccessException {
		if(power < -1) {
			throw new MatrixException("Operation negative power does not exist for matrices.");
		}
		if(power == -1) {
			return this.getInverse();
		}
		if(power == 1) {
			return this.clone();
		}

		Matrix result = this.clone();
		for(int i = 1; i < power; i++) {
			result = result.times(result);
		}

		return result;
	}

	public final Matrix power(Fraction power) throws MatrixException, IllegalAccessException {
		return this.power(power.asInt());
	}

	public void transpose() {
		Fraction[][] result = new Fraction[this.cols][this.rows];
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				result[j][i] = this.items[i][j];
			}
		}

		this.items = result;
	}

	public void upperTriangularMatrix() throws IllegalAccessException {
		Fraction a;

		Main.verbose("Getting upper triangular form");
		Main.verbose("#############################");
		Main.verbose("Original matrix:");
		Main.verbose(this.toString());
		
		for(int r = 0; r < this.rows;) {
			for(int c = 0; c < this.cols; c++) {
				a = this.getItemAt(r,c);

				if(a.numerator() == 0) {
					// find out if all items in the same column are 0 under "a"
					boolean allZero = true;
					int notZeroAt;
					for(notZeroAt = r; notZeroAt < this.rows; notZeroAt++) {
						if(this.getItemAt(notZeroAt,c).numerator() != 0) {
							allZero = false;
							break;
						}
					}

					if(allZero) { // increases c by 1 (next loop)
						Main.verbose("All elements are 0 under ["+r+"]["+c+"] (value "+a+")");
						Main.verbose(this.toString());
						continue;
					}

					Main.verbose("Switching rows #"+r+" and #"+notZeroAt+"");
					Main.verbose(this.toString());


					// there is item in the same column that is not zero
					// therefore switch current row and the one where item is not zero
					this.switchRows(r, notZeroAt);
				}


				// rows could have been switched
				a = this.getItemAt(r,c);
				Main.verbose("Creating zeros under element ["+r+"]["+c+"] (value "+a+")");
				Main.verbose(this.toString());
				// create zero items under the item
				for(int i = r+1; i < this.rows; i++) {
					Main.verbose("\trow "+i+" original:"+r+" coef ["+i+"]["+c+"]:"+(this.getItemAt(i,c).dividedBy(a).negative()));
					this.addRow(r,i, this.getItemAt(i,c).dividedBy(a).negative());
				}

				Main.verbose("Removing zero rows");
				Main.verbose(this.toString());
				// remove zero rows, they should be at the bottom of the matrix
				int zeroRow;
				for(zeroRow = this.rows-1; zeroRow >= 0; zeroRow--) {
					if(!this.isRowZero(zeroRow)) {
						break;
					}
				}

				// update matrix size, this will not free the memory
				this.rows = zeroRow+1;

				Main.verbose("Without zero rows");
				Main.verbose(this.toString());

				r++;
				if(r >= this.rows) {
					break;
				}
			}
		}
	}

	public Fraction getDeterminant() throws IllegalAccessException, MatrixException {
		Fraction a;

		Main.verbose("Calculating matrix determinant:");
		Main.verbose("###############################");
		Main.verbose("Original matrix:");
		Main.verbose(this.toString());

		if(this.rows != this.cols) {
			throw new MatrixException("This matrix is not square");
		}

		Fraction determinantCoefficient = new Fraction(1);
		Matrix matrix = this.clone();
		
		for(int r = 0; r < matrix.rows;) {
			for(int c = 0; c < matrix.cols; c++) {
				a = matrix.getItemAt(r,c);

				if(a.numerator() == 0) {
					// find out if all items in the same column are 0 under "a"
					boolean allZero = true;
					int notZeroAt;
					for(notZeroAt = r; notZeroAt < matrix.rows; notZeroAt++) {
						if(matrix.getItemAt(notZeroAt,c).numerator() != 0) {
							allZero = false;
							break;
						}
					}

					if(allZero) { // increases c by 1 (next loop)
						Main.verbose("All elements are 0 under ["+r+"]["+c+"] (value "+a+")");
						Main.verbose(matrix.toString());
						continue;
					}

					Main.verbose("Switching rows #"+r+" and #"+notZeroAt+"");
					Main.verbose(matrix.toString());


					// there is item in the same column that is not zero
					// therefore switch current row and the one where item is not zero
					matrix.switchRows(r, notZeroAt);
					determinantCoefficient.negative();
				}


				// rows could have been switched
				a = matrix.getItemAt(r,c);
				Main.verbose("Creating zeros under element ["+r+"]["+c+"] (value "+a+")");
				Main.verbose(matrix.toString());
				// create zero items under the item
				for(int i = r+1; i < matrix.rows; i++) {
					Main.verbose("\trow "+i+" original:"+r+" coef ["+i+"]["+c+"]:"+(matrix.getItemAt(i,c).dividedBy(a).negative()));
					matrix.addRow(r,i, matrix.getItemAt(i,c).dividedBy(a).negative());
				}

				Main.verbose("Removing zero rows");
				Main.verbose(matrix.toString());
				// remove zero rows, they should be at the bottom of the matrix
				int zeroRow;
				for(zeroRow = matrix.rows-1; zeroRow >= 0; zeroRow--) {
					if(!matrix.isRowZero(zeroRow)) {
						break;
					}
				}

				// update matrix size, this will not free the memory
				matrix.rows = zeroRow+1;

				Main.verbose("Without zero rows");
				Main.verbose(matrix.toString());

				r++;
				if(r >= matrix.rows) {
					break;
				}
			}
		}

		if(matrix.rows != matrix.cols) { // some rows have been left out
			return new Fraction(0);
		}

		Fraction determinant = determinantCoefficient;
		Fraction[] diagonal = matrix.getMainDiagonal();
		for(int i = 0; i < diagonal.length;i++) {
			determinant = determinant.times(diagonal[i]);
		}

		return determinant;
	}

	protected void switchRows(int a, int b) {
		Fraction[] rowA = this.getRow(a);
		Fraction[] rowB = this.getRow(b);

		this.items[b] = rowA;
		this.items[a] = rowB;
	}

	protected void switchCols(int a, int b) {
		Fraction[] colA = this.getCol(a);
		Fraction[] colB = this.getCol(b);

		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				this.items[i][a] = colB[i];
				this.items[i][b] = colA[i];
			}
		}
	}

	protected boolean isRowZero(int row) {
		boolean allZero = true;
		for(int j = 0; j < this.cols; j++) {
			if(this.items[row][j].numerator() != 0) {
				allZero = false;
				break;
			}
		}
		return allZero;
	}

	/**
	 * Add originalRow to appliedToRow
	 * Example:
	 * For matrix
	 * 1 2 3
	 * 4 5 6
	 * 7 8 9
	 * addRow(1,2,1)
	 * 1 2 3
	 * 5 7 9
	 * 7 8 9
	 *
	 * @param originalRow
	 * @param appliedToRow
	 * @param multiplyCoefficient
	 */
	protected void addRow(int originalRow, int appliedToRow, Fraction multiplyCoefficient) {
		Fraction[] original = this.getRow(originalRow);
		Fraction[] applied  = this.getRow(appliedToRow);

		for(int i = 0; i < this.cols; i++) {
			applied[i] = applied[i].plus( original[i].times(multiplyCoefficient) );
		}

		this.items[appliedToRow] = applied;
	}

	/**
	 * Find the first item in the row that is not zero
	 * @param row
	 * @return index of the col
	 * @throws IllegalAccessException
	 */
	public int firstItemNotZeroOnRow(int row) throws IllegalAccessException, MatrixException {
		Integer first = null;
		for(int j = 0; j < this.getCols(); j++) {
			if(!this.getItemAt(row, j).equals(new Fraction(0))) {
				first = j;
				break;
			}
		}
		if(first == null) {
			throw new MatrixException("There is no item on row #"+row+" that is not zero");
		}
		return first;
	}

	/**
	 * Returns if the matrices are equivalent (using GEM)
	 * @param matrix
	 * @return
	 */
	public boolean equivalent(Matrix matrix) throws IllegalAccessException, MatrixException {
		Matrix original = this.clone();
		Matrix searched = matrix.clone();

		Main.verbose("Finding out if matrices are equivalent:");
		Main.verbose("#######################################");
		Main.verbose("Original matrix");
		Main.verbose(original);
		Main.verbose("Searched matrix");
		Main.verbose(searched);

		original.upperTriangularMatrix();
		searched.upperTriangularMatrix();

		if(original.getRows() != searched.getRows() || original.getCols() != searched.getCols()) {
			return false;
		}

		// go from the bottom of matrix
		for(int i = original.getRows()-1; i >= 0; i--) {
			// get the first item of this row
			int firstOriginal = original.firstItemNotZeroOnRow(i);
			int firstSearched = searched.firstItemNotZeroOnRow(i);

			// this should not happen, because both matrices are in upper triangular format
			// if they are equivalent, then they both should have first items in the same places
			if(firstOriginal != firstSearched) {
				return false;
			}

			// make sure that the first item in this row is the same
			Fraction coefficient;
			coefficient = original.getItemAt(i, firstOriginal).dividedBy( searched.getItemAt(i, firstSearched));
			searched.times(i, coefficient);

			Main.verbose("Row #"+i+" is multiplied by "+coefficient+" taken from ["+i+"]["+firstOriginal+"]");
			Main.verbose(searched);

			// check if following items in the row are the same as well
			// if not, then try to make a linear combination of the rows below the current one
			for(int j = firstOriginal+1; j < original.getCols(); j++) {
				if(!original.getItemAt(i,j).equals(searched.getItemAt(i, j))) {
					// there is no row below current one, no linear combination can be made
					if(!searched.isItemAt(i+1,j)) {
						return false;
					}
					coefficient = original.getItemAt(i,j).dividedBy( searched.getItemAt(i+1, j));
					searched.addRow(i+1, i, coefficient);
					Main.verbose("Row #"+i+" is linear combination of row #"+(i+1)+" by coeffiecnt "+coefficient);
					Main.verbose(searched);
				}
			}
		}

		return true;
	}

	public int getRank() throws IllegalAccessException {
		Matrix matrix = this.clone();
		matrix.upperTriangularMatrix();
		return matrix.getRows();
	}

	public Matrix getInverse() throws MatrixException, IllegalAccessException {
		Main.verbose("Calculating inverse matrix:");
		Main.verbose("###########################");
		Main.verbose("Original matrix:");
		Main.verbose(this);
		
		if(this.rows != this.cols) {
			throw new MatrixException("Matrix must be square to create inverse");
		}

		Matrix original = this.clone();
		Matrix identity = Matrix.identityMatrix(this.rows);
		Fraction a;

		// use GEM to change identity into original and vice versa
		Main.verbose("Identity matrix:");
		Main.verbose(identity.toString());

		// make upper triangular form
		for(int r = 0; r < original.rows;) {
			for(int c = 0; c < original.cols; c++) {
				a = original.getItemAt(r,c);

				if(a.numerator() == 0) {
					// find out if all items in the same column are 0 under "a"
					boolean allZero = true;
					int notZeroAt;
					for(notZeroAt = r; notZeroAt < original.rows; notZeroAt++) {
						if(original.getItemAt(notZeroAt,c).numerator() != 0) {
							allZero = false;
							break;
						}
					}

					if(allZero) { // increases c by 1 (next loop)
						Main.verbose("All elements are 0 under ["+r+"]["+c+"] (value "+a+")");
						Main.verbose(original.toString());
						continue;
					}

					Main.verbose("Switching rows #"+r+" and #"+notZeroAt+"");
					Main.verbose(original.toString());
					Main.verbose(identity.toString());


					// there is item in the same column that is not zero
					// therefore switch current row and the one where item is not zero
					original.switchRows(r, notZeroAt);
					identity.switchRows(r, notZeroAt);
				}


				// rows could have been switched
				a = original.getItemAt(r,c);
				Main.verbose("Creating zeros under element ["+r+"]["+c+"] (value "+a+")");
				Main.verbose(original.toString());
				Main.verbose(identity.toString());
				// create zero items under the item
				for(int i = r+1; i < original.rows; i++) {
					original.addRow(r,i, original.getItemAt(i,c).dividedBy(a).negative());
					identity.addRow(r,i, original.getItemAt(i,c).dividedBy(a).negative());
				}

				Main.verbose("Removing zero rows");
				Main.verbose(original.toString());
				Main.verbose(identity.toString());
				// remove zero rows, they should be at the bottom of the matrix
				int zeroRow;
				for(zeroRow = original.rows-1; zeroRow >= 0; zeroRow--) {
					if(!original.isRowZero(zeroRow)) {
						break;
					}
				}

				// update matrix size, this will not free the memory
				if(original.rows != zeroRow+1) {
					throw new MatrixException("Matrix is not regular, cannot create inverse");
				}
				//original.rows = zeroRow+1;

				r++;
				if(r >= original.rows) {
					break;
				}
			}
		}

		// create 1 on diagonal and 0 elsewhere
		for(int i = original.getRows()-1; i >= 0; i--) {
			Fraction coefficient;
			coefficient = original.getItemAt(i,i).inverse();
			original.times(i, coefficient);
			identity.times(i, coefficient);

			// check if next items are 0. If not, make linear combination of a row below to get 0
			for(int j = i+1; j < original.getCols(); j++) {
				if(!original.getItemAt(i,j).equals(0)) {
					// there is no row below current one, no linear combination can be made
					if(!original.isItemAt(j,j)) {
						throw new MatrixException("Inverse matrix cannot be done.");
					}
					coefficient = original.getItemAt(i,j).dividedBy( original.getItemAt(j, j)).negative();
					original.addRow(j, i, coefficient);
					identity.addRow(j, i, coefficient);
					Main.verbose("Row #"+i+" is linear combination of row #"+(j)+" by coeffiecnt "+coefficient);
					Main.verbose(original);
					Main.verbose(identity);
				}
			}
		}

		if(!original.equals( Matrix.identityMatrix(original.getRows()) )) {
			throw new MatrixException("Inverse matrix was not done for unknown reason");
		}
		return identity;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Matrix other = (Matrix) obj;
		if (!Arrays.deepEquals(this.items, other.items)) {
			return false;
		}
		if (this.rows != other.rows) {
			return false;
		}
		if (this.cols != other.cols) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Arrays.deepHashCode(this.items);
		hash = 79 * hash + this.rows;
		hash = 79 * hash + this.cols;
		hash = 79 * hash + this.index;
		return hash;
	}

	@Override
	public Matrix clone() {
		try {
			return new Matrix(this.rows, this.cols, this.getLinearItems());
		} catch(MatrixException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		String returnString = "";
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				if(j != 0) {
					returnString += "\t";
				}
				if(items[i][j] != null) {
					returnString += items[i][j];
				} else {
					returnString += "?";
				}
			}
			returnString += "\n";
		}

		return returnString;
	}

	public String asFile() throws MatrixException {
		String returnString = "";
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				if(items[i][j] != null) {
					if(j != 0) {
						returnString += "\t";
					}
					returnString += items[i][j];
				} else {
					throw new MatrixException("Item ["+i+"]["+j+"] is not defined");
				}
			}
			returnString += "\n";
		}

		return returnString;
	}
}
