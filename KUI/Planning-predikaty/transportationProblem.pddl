;; A very simple classical STRIPS domain
;; by Radek Marik, 2011.04.18

(define (problem TRANSPORTATION_PROBLEM)
(:domain TRANSPORTATION)
(:objects 
;; goods definitions
	tuzky - goods
	papir - goods
	televize - goods

;; people definitions
	Avrim - people
	Michelle - people
	Franta - people
	Jason - people

;; airplanes
	DL456 - airplane ; U.S. plane
	OK312 - airplane ; Europe plane

;; trucks
	truck1 - truck   ; Las Vegas truck
	truck2 - truck   ; CZ truck
	truck3 - truck   ; NY truck
	truck4 - truck   ; FR truck
	truck5 - truck   ; Boston truck

;; cities
	Prescott Flagstaff LasVegas Henderson Waymouth Newton Boston NewYork Lakewood Trenton Plzen Praha Liberec Kladno Stamford Paris Etampes Dreux Vernon Fontainebleau - city
)

(:init (and

;; roads
	(road Prescott Flagstaff)
	(road Flagstaff Prescott)
	(road Flagstaff LasVegas)
	(road LasVegas Flagstaff)
	(road LasVegas Henderson)
	(road Henderson LasVegas)
	(road Praha Plzen)
	(road Plzen Praha)
	(road Praha Liberec)
	(road Liberec Praha)
	(road Liberec Kladno)
	(road Kladno Liberec)
	(road Waymouth Newton)
	(road Newton Waymouth)
	(road Newton Boston)
	(road Boston Newton)
	(road Waymouth Boston)
	(road Boston Waymouth)
	(road NewYork Trenton)
	(road Trenton NewYork)
	(road NewYork Stamford)
	(road Stamford NewYork)
	(road Trenton Lakewood)
	(road Lakewood Trenton)
	(road Paris Vernon)
	(road Vernon Paris)
	(road Vernon Dreux)
	(road Dreux Vernon)
	(road Paris Etampes)
	(road Etampes Paris)
	(road Etampes Fontainebleau)
	(road Fontainebleau Etampes)

;; flights
	(flight NewYork Boston)
	(flight Boston NewYork)
	(flight LasVegas NewYork)
	(flight NewYork LasVegas)
	(flight Praha NewYork)
	(flight NewYork Praha)
	(flight Praha Paris)
	(flight Paris Praha)
	(flight Paris NewYork)
	(flight NewYork Paris)    

;; trucks
	(truckAt truck1 Flagstaff)
	(truckAt truck2 Liberec)
	(truckAt truck3 Stamford)
	(truckAt truck4 Etampes)
	(truckAt truck5 Waymouth)
	(truckEmpty truck1)
	(truckEmpty truck2)
	(truckEmpty truck3)
	(truckEmpty truck4)
	(truckEmpty truck5)

;; airplanes
	(airplaneAt DL456 LasVegas)
	(airplaneAt OK312 Praha)
	(airplaneEmpty DL456)
	(airplaneEmpty OK312)    

;; goods    
	(goodsAt tuzky Prescott)
	(goodsAt papir Kladno)
	(goodsAt televize Flagstaff)

;; people
	(peopleAt Avrim Boston)    
	(peopleAt Michelle Paris)
	(peopleAt Franta Paris)
	(peopleAt Jason Flagstaff)
))


(:goal (and 
;; goods  
	(goodsAt tuzky Liberec) (not (goodsLoaded tuzky))
	(goodsAt papir Lakewood) (not (goodsLoaded papir))
	(goodsAt televize Paris) (not (goodsLoaded televize))

;; people
	(peopleAt Avrim Waymouth) (not (peopleLoaded Avrim))
	(peopleAt Michelle NewYork) (not (peopleLoaded Michelle))
	(peopleAt Franta LasVegas) (not (peopleLoaded Franta))
	(peopleAt Jason Fontainebleau) (not (peopleLoaded Jason))
))
)

