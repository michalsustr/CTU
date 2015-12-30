;; Creation of signs from the wooden boards by the robotic hand
;; Author: Michal Sustr

(define (problem ROBOTPROBLEM)
	(:domain ROBOT)
	(:objects 
	;; boards definitions
		b0 b1 b2 b3 b4 - board
	;; shapes definitions
		triangle square circle hexagon - shape
	;; color definitions
		red blue yellow - color
	;; robots
		r - robot
	;; tables
		t1 t2 - table
	)

	(:init 
		(= (total-cost) 0)

	;; Initialize boards
		(isOnInputPile b0)	
		(isOnInputPile b1)
		(isOnInputPile b2)
		(isOnInputPile b3)
		(isOnInputPile b4)


		(boardHasNoShape b0)
		(boardHasNoShape b1)
		(boardHasNoShape b2)
		(boardHasNoShape b3)
		(boardHasNoShape b4)

		(boardHasNoColor b0)
		(boardHasNoColor b1)
		(boardHasNoColor b2)
		(boardHasNoColor b3)
		(boardHasNoColor b4)

		(tableIsFree t1)
		(tableIsFree t2)
	;; Robot settings
		(robotHasNoTool r)
		(robotIsBasic   r)

	)

	(:goal (and 
		(isOnOutputPile b0)
		(isOnOutputPile b1)
		(isOnOutputPile b2)
		(isOnOutputPile b3)
		(isOnOutputPile b4)

		(boardHasShape b0 circle)
		(boardHasColor b0 red)
		(boardHasShape b1 triangle)
		(boardHasColor b1 yellow)
		(boardHasShape b2 square)
		(boardHasColor b2 yellow)
		(boardHasShape b3 square)
		(boardHasColor b3 yellow)
		(boardHasShape b4 hexagon)
		(boardHasColor b4 blue)
	))

	(:metric minimize (total-cost))
)

