/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrixcalculator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author michal
 */
public class MathParser {
	protected String equation;
	protected HashSet<Character> matrices;
	protected Stack queue;

	public MathParser(String equation) {
		this.equation = equation;
	}

	public Stack infix2postfix() throws MatrixException, ParserException {
		Stack operatorStack = new Stack();
		Stack queue = new Stack();
		this.matrices = new HashSet<Character>();
		
		char prev = 0;
		String lastNumber = "";
		boolean negative = false;
		for(int i = 0; i < this.equation.length(); i++) {
			char ch = this.equation.charAt(i);

			if(ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r') {
				continue;
			}

			else if((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) { // matrices and numbers
				
				if(negative) {
					if(ch >= 'A' && ch <= 'Z') {
						queue.push("-1");
						queue.push(ch);
						queue.push('*');
					} else {
						queue.push("-"+ch);
					}
					negative = false;
				} else {
					queue.push(ch);
				}
				
				if(ch >= 'A' && ch <= 'Z') {
					this.matrices.add(ch);
				}
			}

			else if(ch == '*' || ch =='+' || ch == '-' || ch == '^' || ch == '/') {
				if(ch == '-') {
					if(prev == '^' || prev == '*' || prev == '(') {
						negative = true;
						continue;
					}

					// case -(A+B*C) or -A+B*C
					if(prev == 0 && i+1 != this.equation.length()) {
						char next = this.equation.charAt(i+1);
						if(next == '(' ) {
							queue.push(-1);
							ch = '*';
						} else {
							negative = true;
							continue;
						}
					}
				}

				while(!operatorStack.empty()) {
					Character lastOperator = (Character) operatorStack.peek();

					if(
						(opLeftAssoc(ch)  && (opPreced(ch) <= opPreced(lastOperator))) ||
						(!opLeftAssoc(ch) && (opPreced(ch) < opPreced(lastOperator)))
					)   {
                        // Pop o2 off the stack, onto the output queue;
                        operatorStack.pop();
						queue.push(lastOperator);
                    } else {
						break;
					}
				}

				operatorStack.push(ch);
			}

			else if(ch == '(') {
				operatorStack.push(ch);
			}
			else if(ch == ')') {
				boolean found = false;
				while(!operatorStack.empty()) {
					Character lastOperator = (Character) operatorStack.pop();
					if(lastOperator == '(') {
						found = true;
						break;
					} else {
						queue.push(lastOperator);
					}
				}

				if(!found) {
					throw new ParserException("Paranthesis mismatched");
				}
			}
			else {
				throw new ParserException("Unknown character "+ch);
			}

			prev = ch;
		}

		while(!operatorStack.empty()) {
			Character lastOperator = (Character) operatorStack.pop();
			if(lastOperator == '(' || lastOperator == ')') {
				throw new ParserException("Paranthesis mismatched");
			}

			queue.push(lastOperator);
		}

		this.queue = queue;
		return queue;
	}

	public Collection<Character> getMatrices() throws ParserException, MatrixException {
		if(this.matrices == null) {
			this.infix2postfix();
		}
		return this.matrices;
	}

	private boolean opLeftAssoc(char op) {
		switch(op)    {
			// left to right
			case '*':
			case '/':
			case '+':
			case '-':
				return true;
			// right to left
			case '^':
				return false;
		}
		return false;
	}

	private int opPreced(char op) {
		switch(op)    {
			case '^':
				return 3;
			case '*':
			case '/':
				return 2;
			case '+':
			case '-':
				return 1;
		}
		return 0;
	}

	public Matrix processPostfix(Map<Character, Matrix> matrices) throws MatrixException, IllegalAccessException, Exception, ParserException {
		if(this.queue == null) {
			this.infix2postfix();
		}

		// items in stack
		Stack stack = new Stack();
		Object one, two;
		for(int i = 0; i < this.queue.size(); i++) {
			String item = ""+this.queue.get(i);
			char firstChar = item.charAt(0);
			if(item.length() > 1) {
				firstChar = item.charAt(1);
			}
			// matrix
			if(firstChar >= 'A' && firstChar <= 'Z') {
				stack.push(matrices.get(firstChar));
			}
			// operation
			else if(firstChar == '*' || firstChar == '+' || firstChar == '-' || firstChar == '/' || firstChar == '^') {
				one = stack.pop();
				two = stack.pop();

				// Java spaghetti, mmm yummy
				switch(firstChar) {
					case '*':
						if(two instanceof Matrix) {
							if(one instanceof Matrix) {
								two = ((Matrix) two).times((Matrix) one);
							} else {
								((Matrix) two).times(one);
							}
							stack.push(two);
						} else if(one instanceof Matrix) {
							if(two instanceof Matrix) {
								one = ((Matrix) one).times((Matrix) two);
							} else {
								((Matrix) one).times(two);
							}
							stack.push(one);
						} else if(one instanceof Fraction && two instanceof Fraction) {
							stack.push( ((Fraction) one).times((Fraction) two) );
						} else throw new ParserException();
						break;

					case '+':
						if(one instanceof Matrix) {
							((Matrix) one).plus(two);
							stack.push(one);
						} else if(two instanceof Matrix) {
							((Matrix) two).plus(one);
							stack.push(two);
						} else if(one instanceof Fraction && two instanceof Fraction) {
							stack.push( ((Fraction) one).plus((Fraction) two) );
						} else throw new ParserException();
						break;

					case '-':
						if(two instanceof Matrix) {
							if(one instanceof Matrix) {
								((Matrix) one).times(new Fraction(-1));
							} else if(one instanceof Fraction) {
								one = ((Fraction) one).negative();
							} else throw new ParserException();
							((Matrix) two).plus(one);
							stack.push(two);
						} else if(one instanceof Matrix) {
							if(two instanceof Matrix) {
								((Matrix) two).times(new Fraction(-1));
							} else if(two instanceof Fraction) {
								two = ((Fraction) two).negative();
							} else throw new ParserException();
							((Matrix) one).plus(two);
							stack.push(one);
						} else if(one instanceof Fraction && two instanceof Fraction) {
							stack.push( ((Fraction) one).minus((Fraction) two) );
						} else throw new ParserException();
						break;

					case '/':
						if(one instanceof Matrix) {
							if(two instanceof Matrix) {
								throw new MatrixException("Cannot divide matrices");
							} else if(two instanceof Fraction) {
								two = ((Fraction) two).inverse();
							} else throw new ParserException();
							((Matrix) one).times(two);
							stack.push(one);
						} else if(two instanceof Matrix) {
							if(one instanceof Matrix) {
								throw new MatrixException("Cannot divide matrices");
							} else if(one instanceof Fraction) {
								one = ((Fraction) one).inverse();
							} else throw new ParserException();
							((Matrix) two).times(one);
							stack.push(two);
						} else if(one instanceof Fraction && two instanceof Fraction) {
							stack.push( ((Fraction) two).dividedBy((Fraction) one) );
						} else throw new ParserException();
						break;

					case '^':
						if(one instanceof Matrix) {
							if(two instanceof Matrix) {
								throw new MatrixException("Cannot divide matrices");
							} else if(two instanceof Fraction) {
								one = ((Matrix) one).power((Fraction) two);
							} else throw new ParserException();
							stack.push(one);
						} else if(two instanceof Matrix) {
							if(one instanceof Matrix) {
								throw new MatrixException("Cannot divide matrices");
							} else if(one instanceof Fraction) {
								two = ((Matrix) two).power((Fraction) one);
							} else throw new ParserException();
							stack.push(two);
						} else if(one instanceof Fraction && two instanceof Fraction) {
							throw new ParserException(("Operation (number)^(number) is not supported."));
						} else throw new ParserException();
						break;
				}
			} else {
				// number
				stack.push(new Fraction(Integer.parseInt(item)));
			}

		}
		return (Matrix) stack.pop();
	}

	



}
