;; Transportation of goods and people by airplanes and trucks
;; Author: Michal Sustr

(define (domain TRANSPORTATION)
	(:requirements :strips :typing)
	(:types object truck airplane city)
	(:predicates
		(road ?city ?city)
		(flight ?city ?city)
		
		(truckAt ?truck ?city)
		(airplaneAt ?airplane ?city)
		(objectAt ?object ?city)
	)
  
;; ACTIONS
	(:action MOVE_LOADED_TRUCK
		:parameters (?g - object ?from - city ?to - city ?t - truck)
		:precondition (and (truckAt ?t ?from) (objectAt ?g ?from) (road ?from ?to))
		:effect (and (objectAt ?g ?to) (truckAt ?t ?to) (not (objectAt ?g ?from)) (not (truckAt ?t ?from)))
	)

	(:action MOVE_EMPTY_TRUCK
		:parameters (?from - city ?to - city ?t - truck)
		:precondition (and (truckAt ?t ?from) (road ?from ?to))
		:effect (and (truckAt ?t ?to) (not (truckAt ?t ?from)))
	)
	
	(:action MOVE_LOADED_AIRPLANE
		:parameters (?g - object ?from - city ?to - city ?a - airplane)
		:precondition (and (airplaneAt ?a ?from) (objectAt ?g ?from) (flight ?from ?to))
		:effect (and (objectAt ?g ?to) (airplaneAt ?a ?to) (not (objectAt ?g ?from)) (not (airplaneAt ?a ?from)))
	)
	
	
	(:action MOVE_EMPTY_AIRPLANE
		:parameters (?from - city ?to - city ?a - airplane)
		:precondition (and (airplaneAt ?a ?from) (flight ?from ?to))
		:effect (and (airplaneAt ?a ?to) (not (airplaneAt ?a ?from)))
	)
)
