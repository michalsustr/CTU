(define (domain logistics)
	(:requirements :typing)
	(:types location truck plane)
	(:predicates
		(truck-at ?t - truck ?at - location)
		(road ?from ?to - location)
	)

	(:action move-truck
		:parameters (?t - truck ?from ?to - location)
		:preconditions (and (truck-at ?t ?from) (road ?from ?to))
		:effect (and (not (truck-at ?t ?from)) (truck-at ?t ?to))
	)ľ
)