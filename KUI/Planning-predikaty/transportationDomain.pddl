;; A very simple classical STRIPS domain
;; by Radek Marik, 2011.04.18

(define (domain TRANSPORTATION)
	(:requirements :strips :typing)
	(:types goods people truck airplane city)
	(:predicates
		(road ?city ?city)
		(flight ?city ?city)
		
		(truckAt ?truck ?city)
		(airplaneAt ?airplane ?city)
		(truckEmpty ?truck)
		(airplaneEmpty ?airplane)
		
		(goodsAt ?goods ?city)
		(goodsLoaded ?goods)
		(goodsInTruck ?goods ?truck)
		(goodsInAirplane ?goods ?airplane)
		(peopleAt ?people ?city)
		(peopleLoaded ?people)
		(peopleInTruck ?people ?truck)
		(peopleInAirplane ?people ?airplane)		
	)
  
;; ACTIONS
	(:action LOAD_GOODS_TO_TRUCK
		:parameters (?g - goods ?c - city ?t - truck)
		:precondition (and (truckAt ?t ?c) (truckEmpty ?t) (goodsAt ?g ?c) (not (goodsLoaded ?g)))
		:effect (and (goodsLoaded ?g) (not (truckEmpty ?t)) (goodsInTruck ?g ?t))
	)
	
	(:action LOAD_PEOPLE_TO_TRUCK
		:parameters (?p - people ?c - city ?t - truck)
		:precondition (and (truckAt ?t ?c) (truckEmpty ?t) (peopleAt ?p ?c) (not (peopleLoaded ?p)))
		:effect (and (peopleLoaded ?p) (not (truckEmpty ?t)) (peopleInTruck ?p ?t))
	)
	
	(:action UNLOAD_GOODS_FROM_TRUCK
		:parameters (?g - goods ?c - city ?t - truck)
		:precondition (and (truckAt ?t ?c) (not (truckEmpty ?t)) (goodsAt ?g ?c) (goodsLoaded ?g) (goodsInTruck ?g ?t))
		:effect (and (not (goodsLoaded ?g)) (truckEmpty ?t) (not (goodsInTruck ?g ?t)))
	)
	
	(:action UNLOAD_PEOPLE_FROM_TRUCK
		:parameters (?p - people ?c - city ?t - truck)
		:precondition (and (truckAt ?t ?c) (not (truckEmpty ?t)) (peopleAt ?p ?c) (peopleLoaded ?p) (peopleInTruck ?p ?t))
		:effect (and (not (peopleLoaded ?p)) (truckEmpty ?t) (not (peopleInTruck ?p ?t)))
	)
	
	(:action MOVE_TRUCK_LOADED_WITH_GOODS
		:parameters (?g - goods ?from - city ?to - city ?t - truck)
		:precondition (and (goodsLoaded ?g) (not (truckEmpty ?t)) (goodsInTruck ?g ?t) (truckAt ?t ?from) (goodsAt ?g ?from) (road ?from ?to))
		:effect (and (goodsAt ?g ?to) (truckAt ?t ?to) (not (goodsAt ?g ?from)) (not (truckAt ?t ?from)))
	)
	
	(:action MOVE_TRUCK_LOADED_WITH_PEOPLE
		:parameters (?p - people ?from - city ?to - city ?t - truck)
		:precondition (and (peopleLoaded ?p) (not (truckEmpty ?t)) (peopleInTruck ?p ?t) (truckAt ?t ?from) (peopleAt ?p ?from) (road ?from ?to))
		:effect (and (peopleAt ?p ?to) (truckAt ?t ?to) (not (peopleAt ?p ?from)) (not (truckAt ?t ?from)))
	)
	
	(:action MOVE_EMPTY_TRUCK
		:parameters (?from - city ?to - city ?t - truck)
		:precondition (and (truckEmpty ?t) (truckAt ?t ?from) (road ?from ?to))
		:effect (and (truckAt ?t ?to) (not (truckAt ?t ?from)))
	)
	
	(:action LOAD_GOODS_TO_AIRPLANE
		:parameters (?g - goods ?c - city ?a - airplane)
		:precondition (and (airplaneAt ?a ?c) (airplaneEmpty ?a) (goodsAt ?g ?c) (not (goodsLoaded ?g)))
		:effect (and (goodsLoaded ?g) (not (airplaneEmpty ?a)) (goodsInAirplane ?g ?a))
	)
	
	(:action LOAD_PEOPLE_TO_AIRPLANE
		:parameters (?p - people ?c - city ?a - airplane)
		:precondition (and (airplaneAt ?a ?c) (airplaneEmpty ?a) (peopleAt ?p ?c) (not (peopleLoaded ?p)))
		:effect (and (peopleLoaded ?p) (not (airplaneEmpty ?a)) (peopleInAirplane ?p ?a))
	)
	
	(:action UNLOAD_GOODS_FROM_AIRPLANE
		:parameters (?g - goods ?c - city ?a - airplane)
		:precondition (and (airplaneAt ?a ?c) (not (airplaneEmpty ?a)) (goodsAt ?g ?c) (goodsLoaded ?g) (goodsInAirplane ?g ?a))
		:effect (and (not (goodsLoaded ?g)) (airplaneEmpty ?a) (not (goodsInAirplane ?g ?a)))
	)
	
	(:action UNLOAD_PEOPLE_FROM_AIRPLANE
		:parameters (?p - people ?c - city ?a - airplane)
		:precondition (and (airplaneAt ?a ?c) (not (airplaneEmpty ?a)) (peopleAt ?p ?c) (peopleLoaded ?p) (peopleInAirplane ?p ?a))
		:effect (and (not (peopleLoaded ?p)) (airplaneEmpty ?a) (not (peopleInAirplane ?p ?a)))
	)
	
	(:action MOVE_AIRPLANE_LOADED_WITH_GOODS
		:parameters (?g - goods ?from - city ?to - city ?a - airplane)
		:precondition (and (goodsLoaded ?g) (not (airplaneEmpty ?a)) (goodsInAirplane ?g ?a) (airplaneAt ?a ?from) (goodsAt ?g ?from) (flight ?from ?to))
		:effect (and (goodsAt ?g ?to) (airplaneAt ?a ?to) (not (goodsAt ?g ?from)) (not (airplaneAt ?a ?from)))
	)
	
	(:action MOVE_AIRPLANE_LOADED_WITH_PEOPLE
		:parameters (?p - people ?from - city ?to - city ?a - airplane)
		:precondition (and (peopleLoaded ?p) (not (airplaneEmpty ?a)) (peopleInAirplane ?p ?a) (airplaneAt ?a ?from) (peopleAt ?p ?from) (flight ?from ?to))
		:effect (and (peopleAt ?p ?to) (airplaneAt ?a ?to) (not (peopleAt ?p ?from)) (not (airplaneAt ?a ?from)))
	)
	
	(:action MOVE_EMPTY_AIRPLANE
		:parameters (?from - city ?to - city ?a - airplane)
		:precondition (and (airplaneEmpty ?a) (airplaneAt ?a ?from) (flight ?from ?to))
		:effect (and (airplaneAt ?a ?to) (not (airplaneAt ?a ?from)))
	)
  
  

)
