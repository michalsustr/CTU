;; Creation of signs from the wooden boards by the robotic hand
;; Author: Michal Sustr

(define (problem ROBOTPROBLEM)
	(:domain ROBOT)
	(:objects 
	;; boards definitions
		b0 b1 - board
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


		(boardHasNoShape b0)
		(boardHasNoShape b1)
		(boardHasNoColor b0)
		(boardHasNoColor b1)

		(tableIsFree t1)
		(tableIsFree t2)
	;; Robot settings
		(robotHasNoTool r)
		(robotIsBasic   r)

	)

	(:goal (and 
		(isOnOutputPile b0)
		(isOnOutputPile b1)

		(boardHasShape b0 circle)
		(boardHasColor b0 red)
		(boardHasShape b1 triangle)
		(boardHasColor b1 yellow)
	))

	(:metric minimize (total-cost))
)

