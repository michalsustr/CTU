;; Creation of signs from the wooden boards by the robotic hand
;; Author: Michal Sustr

(define (domain ROBOT)
	(:requirements :strips :typing)

	
	(:types board table shape color robot)

	(:predicates
		(isOnInputPile   ?board - board)
		(isCut 			 ?board - board)
		(isColored 		 ?board - board)
		(boardHasNoShape ?board - board)
		(boardHasShape 	 ?board - board ?shape - shape)
		(boardHasNoColor ?board - board)
		(boardHasColor   ?board - board ?color - color)
		(isOnOutputPile  ?board - board)

		(robotHasNoTool ?robot - robot)
		(robotHasSaw    ?robot - robot)
		(robotHasBrush  ?robot - robot)

		(robotIsBasic	 ?robot - robot)
		(robotIsAdvanced ?robot - robot)
		(robotIsExpert	 ?robot - robot)

		(boardIsLoaded		?board - board)
		(tableIsFree		?table - table)
		(boardLoadedOnTable ?board - board ?table - table)
	)

	(:functions
	   	(total-cost)
    )
  
;; ACTIONS
	(:action LOAD_BOARD_BASIC
		:parameters (?robot - robot ?board - board ?table - table)
		:precondition (and 
			(isOnInputPile ?board)
			(tableIsFree ?table)
			(robotHasNoTool ?robot)
			(robotIsBasic ?robot)
		)
		:effect (and 
			(not (tableIsFree ?table))
			(not (isOnInputPile ?board))
			(boardLoadedOnTable ?board ?table)
			(boardIsLoaded ?board)
			(increase (total-cost) 60)
		)
	)
	(:action LOAD_BOARD_ADVANCED
		:parameters (?robot - robot ?board - board ?table - table)
		:precondition (and 
			(isOnInputPile ?board)
			(tableIsFree ?table)
			(robotHasNoTool ?robot)
			(robotIsAdvanced ?robot)
		)
		:effect (and 
			(not (tableIsFree ?table))
			(not (isOnInputPile ?board))
			(boardLoadedOnTable ?board ?table)
			(boardIsLoaded ?board)
			(increase (total-cost) 45)
		)
	)
	(:action LOAD_BOARD_EXPERT
		:parameters (?robot - robot ?board - board ?table - table)
		:precondition (and 
			(isOnInputPile ?board)
			(tableIsFree ?table)
			(robotHasNoTool ?robot)
			(robotIsExpert ?robot)
		)
		:effect (and 
			(not (tableIsFree ?table))
			(not (isOnInputPile ?board))
			(boardLoadedOnTable ?board ?table)
			(boardIsLoaded ?board)
			(increase (total-cost) 30)
		)
	)

	(:action UNLOAD_BOARD_BASIC
		:parameters (?robot - robot ?board - board ?table - table)
		:precondition (and 
			(robotHasNoTool ?robot)
			(boardLoadedOnTable ?board ?table)
			(isCut ?board)
			(isColored ?board)
			(robotIsBasic ?robot)
		)
		:effect (and 
			(not (boardLoadedOnTable ?board ?table))
			(tableIsFree ?table)
			(not (boardIsLoaded ?board))
			(isOnOutputPile ?board)
			(increase (total-cost) 60)
		)
	)
	(:action UNLOAD_BOARD_ADVANCED
		:parameters (?robot - robot ?board - board ?table - table)
		:precondition (and 
			(robotHasNoTool ?robot)
			(boardLoadedOnTable ?board ?table)
			(isCut ?board)
			(isColored ?board)
			(robotIsAdvanced ?robot)
		)
		:effect (and 
			(not (boardLoadedOnTable ?board ?table))
			(tableIsFree ?table)
			(not (boardIsLoaded ?board))
			(isOnOutputPile ?board)
			(increase (total-cost) 45)
		)
	)
	(:action UNLOAD_BOARD_EXPERT
		:parameters (?robot - robot ?board - board ?table - table)
		:precondition (and 
			(robotHasNoTool ?robot)
			(boardLoadedOnTable ?board ?table)
			(isCut ?board)
			(isColored ?board)
			(robotIsExpert ?robot)
		)
		:effect (and 
			(not (boardLoadedOnTable ?board ?table))
			(tableIsFree ?table)
			(not (boardIsLoaded ?board))
			(isOnOutputPile ?board)
			(increase (total-cost) 30)
		)
	)

	(:action BRUSH_BOARD_BASIC
		:parameters (?robot - robot ?board - board ?color - color)
		:precondition (and 
			(robotHasBrush ?robot)
			(boardIsLoaded ?board)
			(boardHasNoColor ?board)
			(robotIsBasic ?robot)
		)
		:effect (and 
			(boardHasColor ?board ?color) 
			(isColored ?board)
			(not (boardHasNoColor ?board))
			(increase (total-cost) 180)
		)
	)
	(:action BRUSH_BOARD_ADVANCED
		:parameters (?robot - robot ?board - board ?color - color)
		:precondition (and 
			(robotHasBrush ?robot)
			(boardIsLoaded ?board)
			(boardHasNoColor ?board)
			(robotIsAdvanced ?robot)
		)
		:effect (and 
			(boardHasColor ?board ?color) 
			(isColored ?board)
			(not (boardHasNoColor ?board))
			(increase (total-cost) 135)
		)
	)
	(:action BRUSH_BOARD_EXPERT
		:parameters (?robot - robot ?board - board ?color - color)
		:precondition (and 
			(robotHasBrush ?robot)
			(boardIsLoaded ?board)
			(boardHasNoColor ?board)
			(robotIsExpert ?robot)
		)
		:effect (and 
			(boardHasColor ?board ?color) 
			(isColored ?board)
			(not (boardHasNoColor ?board))
			(increase (total-cost) 90)
		)
	)

	(:action CUT_BOARD_BASIC
		:parameters (?robot - robot ?board - board ?shape - shape)
		:precondition (and 
			(robotHasSaw ?robot)
			(boardHasNoShape ?board)
			(boardIsLoaded ?board)
			(robotIsBasic ?robot)
		)
		:effect (and 
			(boardHasShape ?board ?shape) 
			(not (boardHasNoShape ?board))
			(isCut ?board)
			(increase (total-cost) 120)
		)
	)
	(:action CUT_BOARD_ADVANCED
		:parameters (?robot - robot ?board - board ?shape - shape)
		:precondition (and 
			(robotHasSaw ?robot)
			(boardHasNoShape ?board)
			(boardIsLoaded ?board)
			(robotIsAdvanced ?robot)
		)
		:effect (and 
			(boardHasShape ?board ?shape) 
			(not (boardHasNoShape ?board))
			(isCut ?board)
			(increase (total-cost) 90)
		)
	)
	(:action CUT_BOARD_EXPERT
		:parameters (?robot - robot ?board - board ?shape - shape)
		:precondition (and 
			(robotHasSaw ?robot)
			(boardHasNoShape ?board)
			(boardIsLoaded ?board)
			(robotIsExpert ?robot)
		)
		:effect (and 
			(boardHasShape ?board ?shape) 
			(not (boardHasNoShape ?board))
			(isCut ?board)
			(increase (total-cost) 60)
		)
	)

	(:action TAKE_BRUSH
		:parameters (?robot - robot)
		:precondition (and 
			(robotHasNoTool ?robot)
		)
		:effect (and 
			(not (robotHasNoTool ?robot))
			(robotHasBrush ?robot)

			(increase (total-cost) 10)
		)
	)

	(:action TAKE_SAW
		:parameters (?robot - robot)
		:precondition (and 
			(robotHasNoTool ?robot)
		)
		:effect (and 
			(not (robotHasNoTool ?robot))
			(robotHasSaw ?robot)
			
			(increase (total-cost) 10)
		)
	)

	(:action LAY_BRUSH
		:parameters (?robot - robot)
		:precondition (and 
			(robotHasBrush ?robot)
		)
		:effect (and 
			(robotHasNoTool ?robot)
			(not (robotHasBrush ?robot))
			
			(increase (total-cost) 10)
		)
	)

	(:action LAY_SAW
		:parameters (?robot - robot)
		:precondition (and 
			(robotHasSaw ?robot)
		)
		:effect (and 
			(robotHasNoTool ?robot)
			(not (robotHasSaw ?robot))
			
			(increase (total-cost) 10)
		)
	)

	(:action UPGRADE_ADVANCED
		:parameters (?robot - robot)
		:precondition (and 
			(robotIsBasic ?robot)
		)
		:effect (and 
			(robotIsAdvanced ?robot)
			(not (robotIsBasic ?robot))
			(increase (total-cost) 200)
		)
	)


	(:action UPGRADE_EXPERT
		:parameters (?robot - robot)
		:precondition (and 
			(robotIsAdvanced ?robot)
		)
		:effect (and 
			(robotIsExpert ?robot)
			(not (robotIsAdvanced ?robot))
			(increase (total-cost) 350)
		)
	)
)
