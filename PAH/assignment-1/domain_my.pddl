;; Creation of signs from the wooden boards by the robotic hand
;; Author: Michal Sustr

(define (domain ROBOT)
	(:requirements :strips :typing)
	
	(:predicates
		(robotHasNoTool ?robot)
		(boardAtTable ?board)
	)

;; ACTIONS
	(:action LOAD_BOARD
		:parameters (?board ?robot)
		:precondition (and 
			(robotHasNoTool ?robot)
			(not (boardAtTable ?board))
		)
		:effect (and 
			(boardAtTable ?board)
		)
	)

	(:action UNLOAD_BOARD
		:parameters (?board ?robot)
		:precondition (and 
			(robotHasNoTool ?robot)
			(boardAtTable ?board)
		)
		:effect (and 
			(not (boardAtTable ?board))
		)
	)

)
