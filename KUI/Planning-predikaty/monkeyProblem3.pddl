;; A very simple classical STRIPS domain
;; by Radek Marik, 2011.04.18

(define (problem MONKEY1)
  (:domain MONKEY)
  (:objects monkeyJudy monkeyGigi monkeyHungry - monkey 
            bananas kiwi apple - fruit
            boxA - box
            locX locY locZ  - location)
  (:init (and
    (onFloor monkeyJudy)
    (onFloor monkeyGigi)
    (onFloor monkeyHungry)
    (atM monkeyJudy locX)
    (atM monkeyGigi locX)
    (atM monkeyHungry locZ)
    (atB boxA locY)
    (atF bananas locZ)
    (atF apple locX)
    (atF kiwi locX)
    (isClear boxA)
    )
  )
  
  (:goal (and 
    (hasFruit monkeyJudy bananas)
    (hasFruit monkeyGigi kiwi)
    (hasFruit monkeyHungry kiwi)
    (hasFruit monkeyHungry apple)
    )
  )
  

)