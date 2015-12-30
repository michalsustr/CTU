(define (problem grid-2)
(:domain grid-visit-all)
(:objects 
	loc-x0-y0
	loc-x0-y1
	loc-x0-y2
	loc-x0-y3
	loc-x0-y4
	loc-x0-y5
	loc-x0-y6
	loc-x0-y7
	loc-x0-y8
	loc-x0-y9
	loc-x0-y10
	loc-x0-y11
- place 
        
)
(:init
	(at-robot loc-x0-y0)
	(visited loc-x0-y0)
 	(connected loc-x0-y0 loc-x0-y1)
 	(connected loc-x0-y1 loc-x0-y0)
 	(connected loc-x0-y1 loc-x0-y2)
 	(connected loc-x0-y2 loc-x0-y1)
 	(connected loc-x0-y2 loc-x0-y3)
 	(connected loc-x0-y3 loc-x0-y2)
 	(connected loc-x0-y3 loc-x0-y4)
 	(connected loc-x0-y4 loc-x0-y3)
 	(connected loc-x0-y4 loc-x0-y5)
 	(connected loc-x0-y5 loc-x0-y4)
 	(connected loc-x0-y5 loc-x0-y6)
 	(connected loc-x0-y6 loc-x0-y5)
 	(connected loc-x0-y6 loc-x0-y7)
 	(connected loc-x0-y7 loc-x0-y6)
 	(connected loc-x0-y7 loc-x0-y8)
 	(connected loc-x0-y8 loc-x0-y7)
 	(connected loc-x0-y8 loc-x0-y9)
 	(connected loc-x0-y9 loc-x0-y8)
 	(connected loc-x0-y9 loc-x0-y10)
 	(connected loc-x0-y10 loc-x0-y9)
 	(connected loc-x0-y10 loc-x0-y11)
 	(connected loc-x0-y11 loc-x0-y10)
)
(:goal
(and 
	(visited loc-x0-y0)
	(visited loc-x0-y1)
	(visited loc-x0-y2)
	(visited loc-x0-y3)
	(visited loc-x0-y4)
	(visited loc-x0-y5)
	(visited loc-x0-y6)
	(visited loc-x0-y7)
	(visited loc-x0-y8)
	(visited loc-x0-y9)
	(visited loc-x0-y10)
	(visited loc-x0-y11)
)
)
)
